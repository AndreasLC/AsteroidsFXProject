package dk.sdu.cbse.common.data;

public interface IDamageable {
    boolean isBlinkingRed();
    void setBlinkRedUntil(long durationNano);

    default boolean isInvisible() { return false; }
    default void setInvisibleUntil(long durationNano) {}
    default int getLives() { return 0; }
    default void setLives(int lives) {}
}
