package dk.sdu.cbse.score;

import dk.sdu.cbse.common.services.IScoreService;

public class ScoreService implements IScoreService {
    private int score = 0;
    private int lives = 3;

    @Override public void addScore(int points) { score += points; }
    @Override public int getScore() { return score; }
    @Override public void setLives(int lives) { this.lives = lives; }
    @Override public int getLives() { return lives; }
    @Override public void reset() { score = 0; lives = 3; }
}
