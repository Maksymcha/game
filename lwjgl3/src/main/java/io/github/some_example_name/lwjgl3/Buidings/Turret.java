package io.github.some_example_name.lwjgl3.Buidings;

import com.badlogic.gdx.graphics.Texture;
import io.github.some_example_name.lwjgl3.Enemy.Enemy;


public class Turret extends Building {
    private int damage;
    private float range;
    private float attackSpeed;
    private float lastShotTime = 0;

    public Turret(int level, int cost, String type, Texture[] textures, float attackSpeed, float range, int damage) {
        super(level, cost, type, textures);
        this.attackSpeed = attackSpeed;
        this.range = range;
        this.damage = damage;
    }

    public void shoot(Enemy enemy) {}

    public float getLastShotTime() {
        return lastShotTime;
    }

    public void setLastShotTime(float lastShotTime) {
        this.lastShotTime = lastShotTime;
    }

    public float getAttackSpeed() {
        return attackSpeed;
    }

    public float getRange() {
        return range;
    }

}
