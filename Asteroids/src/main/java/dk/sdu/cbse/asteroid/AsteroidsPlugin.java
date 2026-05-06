package dk.sdu.cbse.asteroid;

import dk.sdu.cbse.common.asteroids.Asteroid;
import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.services.IGamePluginService;

import java.util.Random;

public class AsteroidsPlugin implements IGamePluginService {
    @Override
    public void start(GameData gameData, World world) {  // add asteroids to world.

        for (int i=0 ;i<5 ; i++) // spawn 5 asteroids when starting the game.
        {
            Entity asteroid = createAsteroid(gameData); // Create Entity.
            world.addEntity(asteroid); // Add Entity to the world.
        }

    }

    @Override
    public void stop(GameData gameData, World world) { // removes asteroids.
       for (Entity asteroid : world.getEntities(Asteroid.class)) {
           world.removeEntity(asteroid);
       }

    }

     static Entity createAsteroid(GameData gameData) {
        Entity asteroid = new Asteroid();
        asteroid.setColor("DIMGRAY");
        asteroid.setFillColor("GREY");
        Random rnd = new Random();
        float size = rnd.nextInt(50)+10; // random size between 10 - 59 for asteroid. Change if to big or small.

        int points = 8; // points (hjørner)
        double[] coords = new double[points * 2];
        for (int i = 0; i < points; i++) {
            double angle = (2 * Math.PI / points) * i;
            double radius = size + rnd.nextFloat(size / 2 + 1) - size / 4.0; // makes the asteroids random and more rough
            coords[i * 2]     = Math.cos(angle) * radius;
            coords[i * 2 + 1] = Math.sin(angle) * radius;
        }

        asteroid.setPolygonCoordinates(
                coords[0],  coords[1],  coords[2],  coords[3],
                coords[4],  coords[5],  coords[6],  coords[7],
                coords[8],  coords[9],  coords[10], coords[11],
                coords[12], coords[13], coords[14], coords[15]
        );
        asteroid.setX(0);
        asteroid.setY(0);
        asteroid.setRadius(size);
        asteroid.setRotation(rnd.nextInt(360)); // rotates the asteroids in random direction in full 360 spectrum.
        return asteroid;

    }
}
