package dk.sdu.cbse.common.services;

public interface IScoreService {
    void addScore(int points);
    int getScore();
    void setLives(int lives);
    int getLives();
}
