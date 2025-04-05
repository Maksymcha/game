package io.github.some_example_name.lwjgl3;

public interface Positionable {
    float getX();
    float getY();
    default float distanceBetween(Alive other) {
        float dx = this.getX() - other.getX();
        float dy = this.getY() - other.getY();
        return (float) Math.sqrt(dx * dx + dy * dy);
    }
}
