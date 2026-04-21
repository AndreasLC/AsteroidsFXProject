package dk.sdu.cbse.asteroid;

import dk.sdu.cbse.common.asteroids.Asteroid;
import dk.sdu.cbse.common.asteroids.IAsteroidSplitter;
import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.World;

import java.util.Random;

public class AsteroidSplitterImpl implements IAsteroidSplitter {
    @Override
    public void createAsteroidsSpilt(Entity e, World w) {
        if (e.getRadius() <25) return; // asteroid is too small to split.
        for(int i = 0 ; i<2 ; i++ )
        {
            Asteroid asteroid = new Asteroid();
            asteroid.setColor("BLACK"); // Changes the color of the asteroids to a darker gray to give a field of depth.
            asteroid.setFillColor("DIMGRAY");

            float size = e.getRadius()/2; // changes the size of the asteroid to half the size.
            Random rnd = new Random();
            int points = 8; // 8 points
            double[] coords = new double[points * 2];
            for (int j = 0; j < points; j++) {
                double angle = (2 * Math.PI / points) * j;
                double radius = size + rnd.nextFloat(size / 2 + 1) - size / 4.0; // makes the asteroids random and more rough
                coords[j * 2]     = Math.cos(angle) * radius;
                coords[j * 2 + 1] = Math.sin(angle) * radius;
            }
            asteroid.setPolygonCoordinates(
                    coords[0],  coords[1],  coords[2],  coords[3],
                    coords[4],  coords[5],  coords[6],  coords[7],
                    coords[8],  coords[9],  coords[10], coords[11],
                    coords[12], coords[13], coords[14], coords[15]
            );
            asteroid.setX(e.getX()); //Spawns the asteroid in the same place.
            asteroid.setY(e.getY());
            asteroid.setRadius(size);
            asteroid.setRotation(rnd.nextInt(360)); // rotates the asteroids in random direction in full 360 spectrum.
            w.addEntity(asteroid);

        }
    }
}
