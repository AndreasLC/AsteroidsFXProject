package dk.sdu.cbse.common.data;

public interface IDamageable {
    void onHit();
    boolean isBlinkingRed();
    void setBlinkRedUntil(long durationNano);

    default boolean isInvincible() { return false; }
    default void setInvincibleUntil(long durationNano) {}

    default boolean canTakeDamage() { return true; }
    default void applyDamage() {}
    default boolean isDead() { return false; }
}
