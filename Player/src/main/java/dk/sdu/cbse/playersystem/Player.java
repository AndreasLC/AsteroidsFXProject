package dk.sdu.cbse.playersystem;

import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.IDamageable;

public class Player extends Entity implements IDamageable {
    private int lives = 3;
    private long invisibleUntil = 0;
    private long blinkRedUntil = 0;

    public Player() { setType("Player"); }

    @Override public int getLives() { return lives; }
    @Override public void setLives(int lives) { this.lives = lives; }

    @Override public boolean isInvisible() { return System.nanoTime() < invisibleUntil; }
    @Override public void setInvisibleUntil(long durationNano) { invisibleUntil = System.nanoTime() + durationNano; }

    @Override public boolean isBlinkingRed() { return System.nanoTime() < blinkRedUntil; }
    @Override public void setBlinkRedUntil(long durationNano) { blinkRedUntil = System.nanoTime() + durationNano; }
}
