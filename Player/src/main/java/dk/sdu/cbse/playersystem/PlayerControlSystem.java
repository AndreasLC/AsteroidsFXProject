package dk.sdu.cbse.playersystem;

import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.bullet.BulletSPI;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.data.GameKeys;
import dk.sdu.cbse.common.player.Player;
import dk.sdu.cbse.common.services.IEntityProcessingService;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class PlayerControlSystem implements IEntityProcessingService {
    private long lastShot = System.nanoTime(); //
    @Override
    public void process(GameData gameData, World world) {
        for (Entity player : world.getEntities(Player.class)) {
            if (gameData.GetGamekeys().keyIsDown(GameKeys.LEFT))
            {
                player.setRotation(player.getRotation() - 1);
            }
            if (gameData.GetGamekeys().keyIsDown(GameKeys.RIGHT))
            {
                player.setRotation(player.getRotation() + 1);
            }
            if (gameData.GetGamekeys().keyIsDown(GameKeys.UP))
            {
                double changeX = Math.cos(Math.toRadians(player.getRotation()));
                double changeY = Math.sin(Math.toRadians(player.getRotation()));
                player.setX(player.getX() + changeX);
                player.setY(player.getY() + changeY);
            }
            if (gameData.GetGamekeys().keyIsDown(GameKeys.DOWN))
            {
                double changeX = Math.cos(Math.toRadians(player.getRotation()));
                double changeY = Math.sin(Math.toRadians(player.getRotation()));
                player.setX(player.getX() - changeX);
                player.setY(player.getY() - changeY);
            }
            if (gameData.GetGamekeys().keyIsDown(GameKeys.SPACE))
            {
                long now = System.nanoTime();
                if (now - lastShot > 250_000_000L) { // 0.25 second cooldown for bullets.
                getBulletSPIs().stream().findFirst().ifPresent(
                        spi -> world.addEntity(spi.createBullet(player, gameData))
                );
                lastShot = now;}
            }
        }
        for (Entity e : world.getEntities(Player.class))  // Set the color
        {
            Player player = (Player) e;
            if(player.isInvisible())
            {
                long now = System.nanoTime();
                player.setColor( now % 200_000_000L < 100_000_000L ? "WHITE" : "BLACK"); // Over 100ms black under 100 White
                player.setFillColor( now % 200_000_000L < 100_000_000L ? "WHITE" : "BLACK");
            }
            else
            {
                player.setColor("BLACK"); // Change the color of the player back to BLACK when not Invisible.
                player.setFillColor("BLACK");
            }
        }
    }

    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
