package io.github.some_example_name.lwjgl3;

public interface Alive {
    int getLvl();
    int getHealth();
    void setHealth(int health);
    void takeDamage(int damage);
    boolean isDead();
}

