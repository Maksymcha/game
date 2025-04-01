package io.github.some_example_name.lwjgl3.Enemy;

import com.badlogic.gdx.graphics.Texture;

public class RunningEnemy extends Enemy {
    int SprintPower;
    public RunningEnemy(int health, int speed, int SprintPower,int damage, Texture texture) {
        super(health, speed,damage, texture);
        this.SprintPower = SprintPower;
    }
}
