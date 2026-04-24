package dk.sdu.cbse.enemy;

import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.services.IGamePluginService;

public class EnemyPlugin implements IGamePluginService {
    @Override
    public void start(GameData gameData, World world) {
        // Don't spawn Enemies when starting the game.
    }

    @Override
    public void stop(GameData gameData, World world) {
        for (Entity enemy : world.getEntities(Enemy.class))
        {
            world.removeEntity(enemy); // remove enemy entity from world.
        }

    }

    public static Entity createEnemy(GameData gameData)
    {
        Entity enemy = new Enemy();
        enemy.setPolygonCoordinates(
                // Top-kuppel
                -12, -16,
                12, -16,

                // Øvre kant
                28, -6,

                // Bred midte (bund af kuppel)
                40,  0,
                28,  8,

                // Højre bund
                16,  16,
                -16,  16,

                // Venstre bund
                -28,  8,
                -40,  0,
                -28, -6
        );
        enemy.setX(Math.random() * gameData.getDisplayWidth()); // spawns enemy ship random near screens border.
        enemy.setY(Math.random() * gameData.getDisplayHeight());
        enemy.setRotation(Math.random() * 360); // rotates enemy ship random.
        enemy.setRadius(24f);
        return enemy;
    }
}
