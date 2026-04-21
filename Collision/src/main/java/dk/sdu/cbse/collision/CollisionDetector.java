package dk.sdu.cbse.collision;

import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.bullet.Bullet;
import java.util.ArrayList;
import java.util.List;

public class CollisionDetector implements IPostEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {
        List<Entity> entities = new ArrayList<>(world.getEntities()); // Store all entity's in a List

        for (int i = 0; i < entities.size(); i++) {  // nested for loop that iterates over the list and secures that the pair aren't checked twice fx. AB and BA
            for (int j = i + 1; j < entities.size(); j++) {
                Entity entity1 = entities.get(i);
                Entity entity2 = entities.get(j);

                if (entity1.getClass() == entity2.getClass()) continue; // if to of the same entities collide they won't get removed.

                if (collision(entity1, entity2)) { // If the collide remove entities.
                    world.removeEntity(entity1);
                    world.removeEntity(entity2);
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

