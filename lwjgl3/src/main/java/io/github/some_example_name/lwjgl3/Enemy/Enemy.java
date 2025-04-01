package io.github.some_example_name.lwjgl3.Enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import io.github.some_example_name.lwjgl3.Alive;
import io.github.some_example_name.lwjgl3.Attackable;
import io.github.some_example_name.lwjgl3.Entity;
import io.github.some_example_name.lwjgl3.Movable;

public abstract class Enemy implements Alive, Movable, Attackable, Entity {
    private int health;
    private int speed;
    private int damage;
    protected Sprite sprite;
    boolean hasAttacked;

    public Enemy(int health, int speed, int damage, Texture texture) {
        this.health = health;
        this.speed = speed;
        this.damage = damage;
        this.sprite = new Sprite(texture);
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void setHealth(int health) {
        this.health = Math.max(0, health);
    }

    @Override
    public void takeDamage(int damage) {
        setHealth(this.health - damage);
    }

    @Override
    public boolean isDead() {
        return health <= 0;
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public int getDamage() {
        return damage;
    }

    @Override
    public void setDamage(int damage) {
        this.damage = damage;
    }

    @Override
    public void Attack(Alive target) {
        if (!isDead()) {
            target.takeDamage(damage);
        }
    }

    @Override
    public int getLvl() {
        return 0;
    }

    @Override
    public void move() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        sprite.translate(speed * deltaTime, 0);
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (sprite != null) {
            sprite.draw(batch);
        }
    }
    public Rectangle getCollider() {
        return new Rectangle(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
    }

    public boolean hasAttacked() {
        return hasAttacked;
    }

    public void setHasAttacked(boolean hasAttacked) {
        this.hasAttacked = hasAttacked;
    }

    public float getX() {
        return sprite.getX();
    }
    public float getY() {
        return sprite.getY();
    }
}
