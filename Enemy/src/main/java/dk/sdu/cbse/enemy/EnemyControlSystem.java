package dk.sdu.cbse.enemy;

import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.services.IEntityProcessingService;

import static dk.sdu.cbse.enemy.EnemyPlugin.createEnemy;

public class EnemyControlSystem implements IEntityProcessingService {
    private static final long spawnInterval =  30_000_000_000L; // spawn enemy every 30 seconds.
    private long lastSpawnTime = System.nanoTime(); // last spawn time
    private static final double speed = 0.5;
    private static final long directionChangeInterval = 2_000_000_000L; // 2 seconds i Nanoseconds
    private long lastDirectionChange = System.nanoTime();
    @Override
    public void process(GameData gameData, World world) {
        long now = System.nanoTime();
        if(now - lastSpawnTime > spawnInterval ) // spawn Enemies each 30 seconds.
        {
            Entity enemy = createEnemy(gameData);
            world.addEntity(enemy);
            lastSpawnTime = now;
        }
        for (Entity e : world.getEntities(Enemy.class))
        {
            if (e.isBlinkingRed()) {
                e.setColor(now % 500_000_000L < 250_000_000L ? "RED" : "BLACK");
                e.setFillColor(now % 500_000_000L < 250_000_000L ? "RED" : "BLACK");
            } else {
                e.setColor("BLACK");
                e.setFillColor("BLACK");
            }

            if(now-lastDirectionChange > directionChangeInterval)
            {
                e.setRotation(Math.random()*360); // rotates anemy in a random direction every 2 seconds ( 360 degrees)
            }
            //Move enemies forward with speed.
            double changeX = Math.cos(Math.toRadians(e.getRotation()));
            double changeY = Math.sin(Math.toRadians(e.getRotation()));
            e.setX(e.getX() + changeX * speed);
            e.setY(e.getY() + changeY * speed);

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

