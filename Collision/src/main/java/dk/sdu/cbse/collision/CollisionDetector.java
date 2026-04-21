package dk.sdu.cbse.collision;

import dk.sdu.cbse.common.asteroids.Asteroid;
import dk.sdu.cbse.common.asteroids.IAsteroidSplitter;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.player.Player;
import dk.sdu.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.bullet.Bullet;
import dk.sdu.cbse.common.util.ServiceLocator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class CollisionDetector implements IPostEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {
        List<Entity> entities = new ArrayList<>(world.getEntities()); // Store all entity's in a List

        for (int i = 0; i < entities.size(); i++) {  // nested for loop that iterates over the list and secures that the pair aren't checked twice fx. AB and BA
            for (int j = i + 1; j < entities.size(); j++) {
                Entity entity1 = entities.get(i);
                Entity entity2 = entities.get(j);

                if (entity1.getClass() == entity2.getClass()) continue; // if to of the same entities collide they won't get removed.

                if (collision(entity1, entity2)) {
                    if (entity1 instanceof Bullet && entity2 instanceof Asteroid) {
                        world.removeEntity(entity1);
                        getAsteroidSplitters().stream().findFirst()
                                .ifPresent(splitter -> splitter.createAsteroidsSpilt(entity2, world));
                        gameData.setAsteroidsDestroyed(gameData.getAsteroidsDestroyed() +1); // add one to destroyed asteroids
                        world.removeEntity(entity2);
                    } else if (entity1 instanceof Asteroid && entity2 instanceof Bullet) {
                        world.removeEntity(entity2);
                        getAsteroidSplitters().stream().findFirst()
                                .ifPresent(splitter -> splitter.createAsteroidsSpilt(entity1, world));
                        gameData.setAsteroidsDestroyed(gameData.getAsteroidsDestroyed() +1); // add one to destroyed asteroids
                        world.removeEntity(entity1);
                    }
                    else if (entity1 instanceof Player && entity2 instanceof Asteroid || entity1 instanceof Asteroid && entity2 instanceof Player) // checks if it is a collision between a player and an asteroid
                    {
                        Player player;
                        Entity asteroid;
                        if (entity1 instanceof Player) {
                            player = (Player) entity1;
                            asteroid = entity2;
                        } else {
                            player = (Player) entity2;
                            asteroid = entity1;
                        }
                        if(player.isInvisible())continue; // if the player is invisible it cant be hit by asteroids. Cooldown after hit by Entity
                        player.setLives(player.getLives() - 1);
                        gameData.setLives(player.getLives()); // set the game lives to the players lives.
                        player.setInvisibleUntil(3_000_000_000L); // 3 seconds cooldown.
                        if (player.getLives() <= 0) {
                            world.removeEntity(player);
                        }
                        getAsteroidSplitters().stream().findFirst() //splits the asteroid and removes the first asteroid when you hit it and still have lives.
                                .ifPresent(splitter -> splitter.createAsteroidsSpilt(asteroid, world));
                        world.removeEntity(asteroid);
                        gameData.setAsteroidsDestroyed(gameData.getAsteroidsDestroyed() +1); // Also counts as destroying astroid if you kamikaze ;).

                    }
                    else {
                        world.removeEntity(entity1);
                        world.removeEntity(entity2);
                    }
                }
            }
        }
    }
    public boolean collision(Entity a, Entity b) {
        double dx = a.getX() - b.getX(); // distance between the entities X
        double dy = a.getY() - b.getY(); // distance between the entities Y
        double distanceSquared = dx * dx + dy * dy; // Squared distance between them. Instead of using a^2+b^2 = c^2
        double radiusSum = a.getRadius() + b.getRadius();
        return distanceSquared <= radiusSum * radiusSum; // Checking the SquaredDistance with the SquaredRadiusSum instead of using math.sqrt to get the real distance.
    }

    private Collection<? extends IAsteroidSplitter> getAsteroidSplitters() { // loads all implementations of IAsteroidSplitter from module-path and returns it as a list.
        return
                ServiceLoader.load(IAsteroidSplitter.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}

