package io.github.some_example_name.lwjgl3.Enemy;

import com.badlogic.gdx.graphics.Texture;

public class ShieldEnemy extends Enemy{
    int ShieldHealth;


    public ShieldEnemy(float x, float y, int health, int damage, Texture texture) {
        super(x, y, health, damage, texture);
    }
}
