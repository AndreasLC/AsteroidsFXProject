package dk.sdu.cbse.common.data;

public class GameData {

    private int displayWidth = 1024;
    private int displayHeight = 768;
    private int lives = 3;
    private int asteroidsDestroyed = 0;
    public int getLives(){return lives;}
    public void setLives(int lives){this.lives = lives;}

    public int getAsteroidsDestroyed(){return asteroidsDestroyed;}

    public void setAsteroidsDestroyed(int asteroidsDestroyed) {
        this.asteroidsDestroyed = asteroidsDestroyed;
    }

    private final GameKeys keys = new GameKeys();

    public GameKeys GetGamekeys()
    {
        return keys;
    }

    public void setDisplayWidth(int width) {
        this.displayWidth = width;
    }

    public void setDisplayHeight (int height)
    {
        this.displayHeight = height;
    }

    public int getDisplayWidth()
    {
        return displayWidth;
    }

    public int getDisplayHeight()
    {
        return displayHeight;
    }

}
