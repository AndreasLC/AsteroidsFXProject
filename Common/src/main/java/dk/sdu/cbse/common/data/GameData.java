package dk.sdu.cbse.common.data;

public class GameData {

    private int displayWidth = 1024;
    private int displayHeight = 768;
    // Optional because the Score module is an optional JPMS plugin — the game runs without it
    private java.util.Optional<dk.sdu.cbse.common.services.IScoreService> scoreService = java.util.Optional.empty();

    public java.util.Optional<dk.sdu.cbse.common.services.IScoreService> getScoreService() { return scoreService; }
    public void setScoreService(dk.sdu.cbse.common.services.IScoreService scoreService) { this.scoreService = java.util.Optional.of(scoreService); }

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
