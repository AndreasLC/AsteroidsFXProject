package dk.sdu.cbse.enemy;

import dk.sdu.cbse.common.bullet.BulletSPI;
import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.services.IEntityProcessingService;

import java.util.Collection;
import java.util.ServiceLoader;

import static dk.sdu.cbse.enemy.EnemyPlugin.createEnemy;
import static java.util.stream.Collectors.toList;

public class EnemyControlSystem implements IEntityProcessingService {
    private static final long spawnInterval =  30_000_000_000L; // 30 sekunder mellem hver spawn
    private long lastSpawnTime = System.nanoTime();
    private static final double speed = 0.5;
    private static final long directionChangeInterval = 2_000_000_000L; // skifter tilfældig retning hvert 2. sekund når ingen spiller findes
    private long lastDirectionChange = System.nanoTime();
    private static final long SHOT_COOLDOWN = 5_000_000_000L; // skyder hvert 5. sekund
    private long lastShot = System.nanoTime();

    @Override
    public void process(GameData gameData, World world) {
        long now = System.nanoTime();

        // Spawn en ny fjende med jævne mellemrum
        if(now - lastSpawnTime > spawnInterval) {
            world.addEntity(createEnemy(gameData));
            lastSpawnTime = now;
        }

        for (Entity e : world.getEntities(Enemy.class))
        {
            Enemy enemy = (Enemy) e;

            // Blink rød når fjenden er ramt, ellers normal farve
            if (enemy.isBlinkingRed()) {
                e.setColor(now % 500_000_000L < 250_000_000L ? "RED" : "DARKGREEN");
                e.setFillColor(now % 500_000_000L < 250_000_000L ? "RED" : "GREEN");
            } else {
                e.setColor("DARKGREEN");
                e.setFillColor("GREEN");
            }

            // Find spilleren i verden
            Entity player = world.getEntities().stream()
                    .filter(en -> "Player".equals(en.getType()))
                    .findFirst().orElse(null);

            if (player != null) {
                // Beregn vinkel mod spilleren og drej fjenden mod den
                double dx = player.getX() - e.getX();
                double dy = player.getY() - e.getY();
                e.setRotation(Math.toDegrees(Math.atan2(dy, dx)));

                // Skyd mod spilleren hvis cooldown er overstået
                if (now - lastShot > SHOT_COOLDOWN) {
                    ServiceLoader.load(BulletSPI.class).findFirst()
                            .ifPresent(spi -> world.addEntity(spi.createBullet(e, gameData)));
                    lastShot = now;
                }
            } else if (now - lastDirectionChange > directionChangeInterval) {
                // Ingen spiller — bevæg i tilfældig retning
                e.setRotation(Math.random() * 360);
            }

            // Flyt fjenden fremad i den aktuelle retning
            double changeX = Math.cos(Math.toRadians(e.getRotation()));
            double changeY = Math.sin(Math.toRadians(e.getRotation()));
            e.setX(e.getX() + changeX * e.getSpeed());
            e.setY(e.getY() + changeY * e.getSpeed());

            // Wrap enemies around if they go outside screen border.
            if (e.getX() < 0) e.setX(gameData.getDisplayWidth());
            if (e.getX() > gameData.getDisplayWidth()) e.setX(0);
            if (e.getY() < 0) e.setY(gameData.getDisplayHeight());
            if (e.getY() > gameData.getDisplayHeight()) e.setY(0);
        }

        if (now - lastDirectionChange > directionChangeInterval) {
            lastDirectionChange = now;
        }
    }

}

