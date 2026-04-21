package dk.sdu.cbse.common.enemy;

import dk.sdu.cbse.common.data.Entity;

public class Enemy extends Entity {
    int health = 150; // Enemy has 150 health

    public int getHealth() {return health;}
    public void setHealth(int health) {this.health = health;}

    private long blinkRedUntil = 0;
    public boolean isBlinkingRed() {return System.nanoTime() < blinkRedUntil;}
    public void setBlinkRedUntil(long durationNano) {blinkRedUntil = System.nanoTime() + durationNano;}
}
