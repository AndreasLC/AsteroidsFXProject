package dk.sdu.cbse.playersystem;

import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.IDamageable;
import dk.sdu.cbse.common.data.IHealth;

public class Player extends Entity implements IDamageable, IHealth {
    private int health = 3;
    private long invincibleUntil = 0;
    private long blinkRedUntil = 0;

    public Player() {
        setType("Player");
        setSpeed(1);
        setColor("CYAN");
        setFillColor("DARKBLUE");
    }

    @Override public int getHealth() { return health; }

    @Override public boolean isInvincible() { return System.nanoTime() < invincibleUntil; }
    @Override public void setInvincibleUntil(long durationNano) { invincibleUntil = System.nanoTime() + durationNano; }

    @Override public boolean canTakeDamage() { return !isInvincible(); }

    @Override public void applyDamage() {
        health--;
        onHit();
    }
    @Override public boolean isDead() { return health <= 0; }

    @Override public void onHit() {
        setBlinkRedUntil(1_500_000_000L);
        setInvincibleUntil(3_000_000_000L);
    }
    @Override public boolean isBlinkingRed() { return System.nanoTime() < blinkRedUntil; }
    @Override public void setBlinkRedUntil(long durationNano) { blinkRedUntil = System.nanoTime() + durationNano; }
}
