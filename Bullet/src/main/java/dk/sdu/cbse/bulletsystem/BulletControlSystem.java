package dk.sdu.cbse.bulletsystem;

import dk.sdu.cbse.common.bullet.Bullet;
import dk.sdu.cbse.common.bullet.BulletSPI;
import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.services.IEntityProcessingService;

public class BulletControlSystem implements IEntityProcessingService , BulletSPI {
    @Override
    public void process(GameData gameData, World world) {

            for (Entity bullet : world.getEntities(Bullet.class)) {
                double changeX = Math.cos(Math.toRadians(bullet.getRotation()));
                double changeY = Math.sin(Math.toRadians(bullet.getRotation()));
                bullet.setX(bullet.getX() + changeX * 8); // Faster bullet, to make it look like a laser.
                bullet.setY(bullet.getY() + changeY * 8);
            }
    }

    @Override
    public Entity createBullet(Entity shooter, GameData gameData) {
        double changeX = Math.cos(Math.toRadians(shooter.getRotation()));
        double changeY = Math.sin(Math.toRadians(shooter.getRotation()));

        Entity bullet = new Bullet();
        bullet.setColor("RED");
        bullet.setFillColor("RED");
        bullet.setPolygonCoordinates(-20, -1,  20, -1,  20, 1,  -20, 1); // longer bullet, to make it look like a laser.
        bullet.setX(shooter.getX() - 9 + changeX * 50);
        bullet.setY(shooter.getY() + changeY * 50);
        bullet.setRotation(shooter.getRotation());
        bullet.setRadius(20f);
        return bullet;
    }
}
