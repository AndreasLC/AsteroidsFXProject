package dk.sdu.cbse.common.data;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
public class World {
    private final Map <String , Entity> entityMap = new ConcurrentHashMap<>(); // map for saving all enititys in the game.

    public String addEntity (Entity entity){ // Add entity to the map and returns its ID
        entityMap.put(entity.getID(),entity);
        return entity.getID();
    }

    public void removeEntity(String entityId) // Removes entity by id
    {
        entityMap.remove(entityId);
    }

    public void removeEntity (Entity entity) // Removes entity by object
    {
        entityMap.remove(entity.getID());
    }

    public Collection<Entity> getEntities() //Returns all entities in world.
    {
        return entityMap.values();
    }
    public <E extends Entity> List<Entity> getEntities(Class<E>... entityTypes) {
        List<Entity> r = new ArrayList<>();
        for (Entity e : getEntities()) { // runs through all entities
            for (Class<E> entityType : entityTypes) { // runs through the specific type of entities
                if (entityType.equals(e.getClass())) { // if entity is equal to the search add to list.
                    r.add(e);
                }
            }
        }
        return r; // return list.
    }

    public Entity getEntity(String ID) // returns specific entity from entityMap.
    {
        return entityMap.get(ID);
    }
}
