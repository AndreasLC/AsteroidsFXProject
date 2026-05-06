package dk.sdu.cbse.enemy;

import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.IDamageable;
import dk.sdu.cbse.common.data.IHealth;

public class Enemy extends Entity implements IDamageable, IHealth {
    private int health = 150;
    private long blinkRedUntil = 0;

    public Enemy() {
        setType("Enemy");
        setSpeed(0.25);
        setColor("DARKGREEN");
        setFillColor("GREEN");
    }

    @Override public int getHealth() { return health; }
    @Override public void setHealth(int health) { this.health = health; }

    @Override public void onHit() { setBlinkRedUntil(1_500_000_000L); }
    @Override public boolean isBlinkingRed() { return System.nanoTime() < blinkRedUntil; }
    @Override public void setBlinkRedUntil(long durationNano) { blinkRedUntil = System.nanoTime() + durationNano; }
}
