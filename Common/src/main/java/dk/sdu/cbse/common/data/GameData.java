package dk.sdu.cbse.common.data;

public class GameData {

    private int displayWidth = 1920;
    private int displayHeight = 1080;

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
