package io.github.some_example_name.lwjgl3.Enemy;

import com.badlogic.gdx.graphics.Texture;

public class ShieldEnemy extends Enemy{
    int ShieldHealth;
    public ShieldEnemy(int health, int speed,int ShieldHealth,int damage, Texture texture) {
        super(health, speed,damage, texture);this.ShieldHealth = ShieldHealth;
    }
}
