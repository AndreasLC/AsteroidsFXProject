package dk.sdu.cbse.common.data;

public interface IDamageable {
    void onHit();
    boolean isBlinkingRed();
    void setBlinkRedUntil(long durationNano);

    default boolean isInvisible() { return false; }
    default void setInvisibleUntil(long durationNano) {}
}
