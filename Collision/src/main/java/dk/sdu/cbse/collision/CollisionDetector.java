package dk.sdu.cbse.collision;

import dk.sdu.cbse.common.asteroids.Asteroid;
import dk.sdu.cbse.common.asteroids.IAsteroidSplitter;
import dk.sdu.cbse.common.bullet.Bullet;
import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.IDamageable;
import dk.sdu.cbse.common.data.IHealth;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.cbse.common.util.ServiceLocator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CollisionDetector implements IPostEntityProcessingService {

    private boolean isPlayer(Entity e) { return "Player".equals(e.getType()); }
    private boolean isEnemy(Entity e) { return "Enemy".equals(e.getType()); }

    @Override
    public void process(GameData gameData, World world) {
        List<Entity> entities = new ArrayList<>(world.getEntities());

        for (int i = 0; i < entities.size(); i++) {
            for (int j = i + 1; j < entities.size(); j++) {
                Entity entity1 = entities.get(i);
                Entity entity2 = entities.get(j);

                if (entity1.getClass() == entity2.getClass()) continue; // Two of the same objects cant collide.
                if ("Star".equals(entity1.getType()) || "Star".equals(entity2.getType())) continue; // No collision with stars.
                if (friendlyFire(entity1, entity2)) continue; // A bullet never hits an entity of the same type as its shooter.

                boolean e1IsPlayer = isPlayer(entity1);
                boolean e1IsEnemy  = isEnemy(entity1);
                boolean e2IsPlayer = isPlayer(entity2);
                boolean e2IsEnemy  = isEnemy(entity2);

                if (collision(entity1, entity2)) {
                    if (entity1 instanceof Bullet && entity2 instanceof Asteroid) {
                        world.removeEntity(entity1);
                        getAsteroidSplitters().stream().findFirst()
                                .ifPresent(splitter -> splitter.createAsteroidsSpilt(entity2, world));
                        gameData.getScoreService().ifPresent(s -> s.addScore(1));
                        world.removeEntity(entity2);

                    } else if (entity1 instanceof Asteroid && entity2 instanceof Bullet) {
                        world.removeEntity(entity2);
                        getAsteroidSplitters().stream().findFirst()
                                .ifPresent(splitter -> splitter.createAsteroidsSpilt(entity1, world));
                        gameData.getScoreService().ifPresent(s -> s.addScore(1));
                        world.removeEntity(entity1);

                    } else if (e1IsPlayer && entity2 instanceof Asteroid || entity1 instanceof Asteroid && e2IsPlayer) {
                        Entity playerEntity = e1IsPlayer ? entity1 : entity2;
                        Entity asteroid = e1IsPlayer ? entity2 : entity1;
                        IDamageable player = (IDamageable) playerEntity;
                        if (player.isInvisible()) continue;
                        damage(playerEntity, 1, world);
                        gameData.getScoreService().ifPresent(s -> s.setLives(((IHealth) playerEntity).getHealth()));
                        getAsteroidSplitters().stream().findFirst()
                                .ifPresent(splitter -> splitter.createAsteroidsSpilt(asteroid, world));
                        world.removeEntity(asteroid);
                        gameData.getScoreService().ifPresent(s -> s.addScore(1));

                    } else if (entity1 instanceof Bullet && e2IsPlayer || e1IsPlayer && entity2 instanceof Bullet) {
                        Entity bullet = entity1 instanceof Bullet ? entity1 : entity2;
                        Entity playerEntity = e1IsPlayer ? entity1 : entity2;
                        IDamageable player = (IDamageable) playerEntity;
                        if (player.isInvisible()) continue;
                        world.removeEntity(bullet);
                        damage(playerEntity, 1, world);
                        gameData.getScoreService().ifPresent(s -> s.setLives(((IHealth) playerEntity).getHealth()));

                    } else if (entity1 instanceof Bullet && e2IsEnemy) {
                        world.removeEntity(entity1);
                        damage(entity2, 25, world);

                    } else if (e1IsEnemy && entity2 instanceof Bullet) {
                        world.removeEntity(entity2);
                        damage(entity1, 25, world);

                    } else if (e1IsPlayer && e2IsEnemy || e1IsEnemy && e2IsPlayer) {
                        ((IDamageable) entity1).onHit();
                        ((IDamageable) entity2).onHit();

                    } else if (e1IsEnemy || e2IsEnemy) {
                        // Do nothing if Enemy collides with another enemy

                    } else {
                        world.removeEntity(entity1);
                        world.removeEntity(entity2);
                    }
                }
            }
        }
    }

    private boolean friendlyFire(Entity first, Entity second) {
        Bullet bullet;
        Entity victim;

        if (first instanceof Bullet b) {
            bullet = b;
            victim = second;
        } else if (second instanceof Bullet b) {
            bullet = b;
            victim = first;
        } else {
            return false;
        }

        String shooterType = bullet.getShooterType();
        return shooterType != null && shooterType.equals(victim.getType());
    }

    private void damage(Entity victim, int amount, World world) {
        if (victim instanceof IHealth h) {
            h.setHealth(h.getHealth() - amount);
            if (victim instanceof IDamageable d) d.onHit();
            if (h.getHealth() <= 0) world.removeEntity(victim);
        }
    }

    public boolean collision(Entity a, Entity b) {
        double dx = a.getX() - b.getX();
        double dy = a.getY() - b.getY();
        double distanceSquared = dx * dx + dy * dy;
        double radiusSum = a.getRadius() + b.getRadius();
        return distanceSquared <= radiusSum * radiusSum;
    }

    private Collection<? extends IAsteroidSplitter> getAsteroidSplitters() {
        return ServiceLocator.INSTANCE.locateAll(IAsteroidSplitter.class);
    }
}
