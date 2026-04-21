package dk.sdu.cbse.asteroid;

import dk.sdu.cbse.asteroid.AsteroidSplitterImpl;
import dk.sdu.cbse.common.asteroids.Asteroid;
import dk.sdu.cbse.common.asteroids.IAsteroidSplitter;
import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.services.IEntityProcessingService;
import static dk.sdu.cbse.asteroid.AsteroidsPlugin.createAsteroid;

public class AsteroidProcessor implements IEntityProcessingService {
    private int frameCount = 0;
    @Override
    public void process(GameData gameData, World world) {

        // Spawn an asteroid each 5 second. 360 hz
        frameCount++;
        if(frameCount % 1800 == 0)
        {
            Entity asteroid = createAsteroid(gameData); // Create Entity.
            world.addEntity(asteroid); // Add Entity to the world.
        }



        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            double changeX = Math.cos(Math.toRadians(asteroid.getRotation()));
            double changeY = Math.sin(Math.toRadians(asteroid.getRotation()));

            asteroid.setX(asteroid.getX() + changeX * 0.5);
            asteroid.setY(asteroid.getY() + changeY * 0.5);

            // Bug fix: + i stedet for - så asteroiden wrapper til modsat kant
            if (asteroid.getX() < 0) {
                asteroid.setX(asteroid.getX() + gameData.getDisplayWidth());
            }

            if (asteroid.getX() > gameData.getDisplayWidth()) {
                asteroid.setX(asteroid.getX() % gameData.getDisplayWidth());
            }

            // Bug fix: + i stedet for - så asteroiden wrapper til modsat kant
            if (asteroid.getY() < 0) {
                asteroid.setY(asteroid.getY() + gameData.getDisplayHeight());
            }

            if (asteroid.getY() > gameData.getDisplayHeight()) {
                asteroid.setY(asteroid.getY() % gameData.getDisplayHeight());
            }

        }
    }
    // Undersøg OSGi
}


