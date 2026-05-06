package dk.sdu.cbse.stars;

import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.services.IGamePluginService;

import java.util.Random;

public class starPluggin implements IGamePluginService {
    private Entity star;
    @Override
    public void start(GameData gameData, World world) {
        for (int i=0 ;i<100 ; i++) // Spawn 100 stars
        {
           star = createStar(gameData);
           world.addEntity(star);
        }

    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(star);
    }

    private Entity createStar(GameData gameData)
    {
        Entity star = new stars();
        Random rnd = new Random();
        double s = rnd.nextDouble(1.5) + 0.5;
        star.setPolygonCoordinates(-s, -s, s, -s, s, s, -s, s);
        star.setX(rnd.nextDouble() * gameData.getDisplayWidth());
        star.setY(rnd.nextDouble() * gameData.getDisplayHeight());
        return star;
    }
}
