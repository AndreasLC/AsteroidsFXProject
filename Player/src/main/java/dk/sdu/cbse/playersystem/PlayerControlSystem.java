package dk.sdu.cbse.playersystem;

import dk.sdu.cbse.common.bullet.BulletSPI;
import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.GameKeys;
import dk.sdu.cbse.common.data.IDamageable;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.services.IEntityProcessingService;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class PlayerControlSystem implements IEntityProcessingService {
    private static final long SHOT_COOLDOWN    = 250_000_000L;
    private static final long BLINK_INTERVAL   = 500_000_000L;
    private static final long BLINK_HALF       = 250_000_000L;
    private static final long INVISIBLE_INTERVAL = 200_000_000L;
    private static final long INVISIBLE_HALF   = 100_000_000L;

    private long lastShot = System.nanoTime();

    @Override
    public void process(GameData gameData, World world) {
        for (Entity e : world.getEntities(Player.class)) {
            long now = System.nanoTime();
            IDamageable player = (IDamageable) e;

            if (gameData.GetGamekeys().keyIsDown(GameKeys.LEFT))  e.setRotation(e.getRotation() - 1);
            if (gameData.GetGamekeys().keyIsDown(GameKeys.RIGHT)) e.setRotation(e.getRotation() + 1);

            if (gameData.GetGamekeys().keyIsDown(GameKeys.UP)) {
                double changeX = Math.cos(Math.toRadians(e.getRotation()));
                double changeY = Math.sin(Math.toRadians(e.getRotation()));
                e.setX(e.getX() + changeX * e.getSpeed());
                e.setY(e.getY() + changeY * e.getSpeed());
            }
            if (gameData.GetGamekeys().keyIsDown(GameKeys.DOWN)) {
                double changeX = Math.cos(Math.toRadians(e.getRotation()));
                double changeY = Math.sin(Math.toRadians(e.getRotation()));
                e.setX(e.getX() - changeX * e.getSpeed());
                e.setY(e.getY() - changeY * e.getSpeed());
            }
            if (gameData.GetGamekeys().keyIsDown(GameKeys.SPACE) && now - lastShot > SHOT_COOLDOWN) {
                getBulletSPIs().stream().findFirst().ifPresent(spi -> world.addEntity(spi.createBullet(e, gameData)));
                lastShot = now;
            }

            if (player.isBlinkingRed()) {
                boolean flash = now % BLINK_INTERVAL < BLINK_HALF;
                e.setColor(flash ? "RED" : "CYAN");
                e.setFillColor(flash ? "DARKRED" : "DARKBLUE");
            } else if (player.isInvisible()) {
                String color = now % INVISIBLE_INTERVAL < INVISIBLE_HALF ? "WHITE" : "BLACK";
                e.setColor(color);
                e.setFillColor(color);
            } else {
                e.setColor("CYAN");
                e.setFillColor("DARKBLUE");
            }
        }
    }

    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
