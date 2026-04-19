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
        Entity player = new Entity();
        player.setPolygonCoordinates(-5,-5,10,0,-5,5); // makes a triangle spaceship.
        player.setX(gameData.getDisplayHeight()/2); // to make sure that the player spawn in the middle of the screen.
        player.setY(gameData.getDisplayHeight()/2);
        player.setRadius(8f);
        return player;
    }
}
