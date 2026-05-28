package dk.sdu.cbse.common.bullet;
import dk.sdu.cbse.common.data.Entity;

public class Bullet extends Entity {
    private String shooterType;

    public Bullet() {
        setType("Bullet");
        setColor("RED");
        setFillColor("RED");
        setSpeed(2);
    }

    public String getShooterType() { return shooterType; }
    public void setShooterType(String shooterType) { this.shooterType = shooterType; }
}
