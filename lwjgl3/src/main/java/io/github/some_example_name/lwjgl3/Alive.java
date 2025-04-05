package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.math.Rectangle;

public interface Alive {
    float getX();
    float getY();
    int getLvl();
    boolean isDead();
    void takeDamage(int damage);
    int getHealth();
    void setHealth(int health);
    Rectangle getMovementCollider();
}
