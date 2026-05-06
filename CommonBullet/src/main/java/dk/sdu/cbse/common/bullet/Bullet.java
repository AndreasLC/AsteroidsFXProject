package dk.sdu.cbse.common.bullet;
import dk.sdu.cbse.common.data.Entity;

public class Bullet extends Entity {
    public Bullet() {
        setType("Bullet");
        setColor("RED");
        setFillColor("RED");
        setSpeed(2);
    }
}
