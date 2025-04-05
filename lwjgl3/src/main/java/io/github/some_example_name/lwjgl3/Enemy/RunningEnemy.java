package io.github.some_example_name.lwjgl3.Enemy;

import com.badlogic.gdx.graphics.Texture;

public class RunningEnemy extends Enemy {
    int SprintPower;


    public RunningEnemy(float x, float y, int health, int damage, Texture texture) {
        super(x, y, health, damage, texture);
    }
}
