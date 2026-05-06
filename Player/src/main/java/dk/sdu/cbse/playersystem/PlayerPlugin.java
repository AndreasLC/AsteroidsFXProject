package dk.sdu.cbse.playersystem;

import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.services.IGamePluginService;

public class PlayerPlugin implements IGamePluginService {
    private Entity player;
    @Override
    public void start(GameData gameData, World world) {
        // Create entity player
        player = createPlayerShip(gameData);
        world.addEntity(player);
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(player); // removes player entity

    }

    private Entity createPlayerShip(GameData gameData)
    {
        Entity player = new Player(); // Create Entity which is a playership.
        player.setPolygonCoordinates(
                -16, -4,
                -20, -8,   // øverste finne
                -12, -4,
                6, -4,
                12, 0,     // snude

                // bund
                6, 4,
                -12, 4,
                -20, 8,    // nederste finne
                -16, 4,

                //flamme
                -22, 3,
                -30, 1,
                -26, 0,
                -30, -1,
                -22, -3
        );
        player.setX(gameData.getDisplayWidth()/2); // to make sure that the player spawn in the middle of the screen.
        player.setY(gameData.getDisplayHeight()/2);
        player.setRadius(8f);
        player.setColor("CYAN");
        player.setFillColor("DARKBLUE");
        return player;
    }
}
