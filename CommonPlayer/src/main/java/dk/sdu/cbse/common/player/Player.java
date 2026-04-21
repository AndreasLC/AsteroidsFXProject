package dk.sdu.cbse.common.player;
import dk.sdu.cbse.common.data.Entity;

public class Player extends Entity {
    int lives = 3; // player has 3 lives


    // getter and setter
    public int getLives()
    {
        return lives;
    }
    public int setLives(int lives)
    {
        this.lives = lives;
        return lives;
    }

    // Invisible cool down for player after hit by asteroid
    private long invisibleUntil = 0;
    public boolean isInvisible()
    {
        return System.nanoTime() < invisibleUntil;
    }
    public void setInvisibleUntil(long durationNano) // set the duration of cooldown
    {
        invisibleUntil = System.nanoTime() + durationNano;
    }

    private long blinkRedUntil = 0;
    public boolean isBlinkingRed() {return System.nanoTime() < blinkRedUntil;}
    public void setBlinkRedUntil(long durationNano) {blinkRedUntil = System.nanoTime() + durationNano;}
}
