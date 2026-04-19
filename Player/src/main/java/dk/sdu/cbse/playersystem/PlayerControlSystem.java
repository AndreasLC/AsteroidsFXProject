package dk.sdu.cbse.playersystem;

import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.data.GameKeys;
import dk.sdu.cbse.common.services.IEntityProcessingService;

public class PlayerControlSystem implements IEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {
        for (Entity player : world.getEntities(Player.class)) {
            // Movement of player
            if(gameData.GetGamekeys().keyIsDown(GameKeys.LEFT))
            {
                player.setRotation(player.getRotation()-1); // Rotate left, change rotation if to fast or slow.
            }
            if (gameData.GetGamekeys().keyIsDown(GameKeys.RIGHT))
            {
                player.setRotation(player.getRotation()+1); // Rotate right
            }
            if(gameData.GetGamekeys().keyIsDown(GameKeys.UP)) // Move forward
            {
                double changeX = Math.cos(Math.toRadians(player.getRotation()));
                double changeY = Math.sin(Math.toRadians(player.getRotation()));
                player.setX(player.getX() + changeX);
                player.setY(player.getY() + changeY);
            }
            if (gameData.GetGamekeys().keyIsDown(GameKeys.DOWN)) // Move Backwards
                {
                double changeX = Math.cos(Math.toRadians(player.getRotation()));
                double changeY = Math.sin(Math.toRadians(player.getRotation()));
                player.setX(player.getX() - changeX);
                player.setY(player.getY() - changeY);
                }


        }
    }
}
