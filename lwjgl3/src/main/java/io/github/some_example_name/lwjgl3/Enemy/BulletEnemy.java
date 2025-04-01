package io.github.some_example_name.lwjgl3.Enemy;

import com.badlogic.gdx.graphics.Texture;

public class BulletEnemy extends Enemy{
    int Range;
    public BulletEnemy(int health, int speed,int damage, int range, Texture texture) {
        super(health, speed,damage, texture);
        this.Range = range;
    }
}
