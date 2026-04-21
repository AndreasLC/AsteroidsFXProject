package dk.sdu.cbse.collision;

import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.cbse.common.data.Entity;
import java.util.ArrayList;
import java.util.List;

public class CollisionDetector implements IPostEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {
        List<Entity> entities = new ArrayList<>(world.getEntities()); // Store all entity's in a List

        for (int i = 0; i < entities.size(); i++) {  // nested for loop that iterates over the list and secures that the pair aren't checked twice fx. AB and BA
            for (int j = i + 1; j < entities.size(); j++) {
                if (collision(entities.get(i), entities.get(j))) { // If the collide remove entities.
                    world.removeEntity(entities.get(i));
                    world.removeEntity(entities.get(j));
                }
            }
        }
    }
    public boolean collision(Entity a, Entity b) {
        double dx = a.getX() - b.getX(); // distance between the entities X
        double dy = a.getY() - b.getY(); // distance between the entities Y
        double distanceSquared = dx * dx + dy * dy; // Squared distance between them. Instead of using a^2+b^2 = c^2
        double radiusSum = a.getRadius() + b.getRadius();
        return distanceSquared <= radiusSum * radiusSum; // Checking the SquaredDistance with the SquaredRadiusSum instead of using math.sqrt to get the real distance.
    }
}

