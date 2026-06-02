package dk.sdu.cbse.collision;
import dk.sdu.cbse.common.bullet.Bullet;
import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.IDamageable;
import dk.sdu.cbse.common.data.IHealth;
import dk.sdu.cbse.common.data.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CollisionDetectorTest {

    private CollisionDetector collisionDetector;
    private GameData gameData;
    private World world;

    @BeforeEach
    void setUp() {
        // Mock objects using Mockito
        collisionDetector = new CollisionDetector();
        gameData = new GameData();
        world = mock(World.class);
    }

    @Test
    void bulletHitsEnemy_removesBullet_andDamagesEnemy() {
        //Bullet and Enemy on same position
        Entity enemy = enemy(100, 100, 10);
        Bullet bullet = bullet("Player", 100, 100, 5); // shot by the player, so not friendly fire
        when(world.getEntities()).thenReturn(List.of(enemy, bullet));

        collisionDetector.process(gameData, world);

        verify(world).removeEntity(bullet);              // bullet is removed
        verify((IDamageable) enemy).applyDamage();       // enemy takes damage
    }
    @Test
    void playerCollidesWithEnemy_bothTakeOnHit() {
        Entity player = player(100, 100, 10);
        Entity enemy = enemy(100, 100, 10);
        when(world.getEntities()).thenReturn(List.of(player, enemy));

        collisionDetector.process(gameData, world);

        verify((IDamageable) player).onHit();
        verify((IDamageable) enemy).onHit();
    }

    @Test
    void collision_returnsTrue_whenEntitiesJustOverlap() {
        Entity a = mock(Entity.class);
        Entity b = mock(Entity.class);
        stubGeometry(a, 10, 10, 2.5f);
        stubGeometry(b, 15, 10, 2.5f);  // Since the radius is set to 2.5 the two entities just touch and there collision = true.

        assertTrue(collisionDetector.collision(a, b));
    }

    @Test
    void collision_returnsTrue_whenEntitiesOverlap() {
        Entity a = mock(Entity.class);
        Entity b = mock(Entity.class);
        stubGeometry(a, 10, 10, 2.5f);
        stubGeometry(b, 10, 10, 2.5f);  // Entities are exactly on top of each other and therefor collision = true.

        assertTrue(collisionDetector.collision(a, b));
    }

    @Test
    void collision_returnsFalse_whenEntitiesAreFarApart() {
        Entity a = mock(Entity.class);
        Entity b = mock(Entity.class);
        stubGeometry(a, 0, 0, 5);
        stubGeometry(b, 100, 0, 5); // Entities are far apart no overlap collision = false.

        assertFalse(collisionDetector.collision(a, b));
    }

    private static Entity player(double x, double y, float radius) {
        Entity p = mock(Entity.class, withSettings().extraInterfaces(IDamageable.class));//Creating a mock Player, that is also implementations of IDamageable.
        when(p.getType()).thenReturn("Player"); // Returns Player when getType is called.
        stubGeometry(p, x, y, radius);
        return p;
    }

    private static Entity enemy(double x, double y, float radius) { //Creating a mock Enemy, that is also implementations of IDamageable and IHealth
        Entity e = mock(Entity.class, withSettings().extraInterfaces(IDamageable.class, IHealth.class));
        when(e.getType()).thenReturn("Enemy"); //Return Enemy when getType is called
        stubGeometry(e, x, y, radius);
        return e;
    }

    private static Bullet bullet(String shooterType, double x, double y, float radius) {
        Bullet b = mock(Bullet.class);
        when(b.getShooterType()).thenReturn(shooterType);
        stubGeometry(b, x, y, radius);
        return b;
    }

    private static void stubGeometry(Entity e, double x, double y, float radius) {
        when(e.getX()).thenReturn(x);
        when(e.getY()).thenReturn(y);
        when(e.getRadius()).thenReturn(radius);
    }
}
