package io.github.some_example_name.lwjgl3.Enemy;

import com.badlogic.gdx.graphics.Texture;

public class ShieldEnemy extends Enemy{
    int ShieldHealth;


    public ShieldEnemy(float x, float y, int health, int damage, Texture texture,float speed) {
        super(x, y, health*2, damage, texture, speed);
    }
}
