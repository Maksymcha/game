package io.github.some_example_name.lwjgl3.Buidings;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import io.github.some_example_name.lwjgl3.Alive;
import io.github.some_example_name.lwjgl3.Entity;
import io.github.some_example_name.lwjgl3.Buildings.Barrier;

public class Base implements Entity, Alive {
    private Sprite base;
    private int health;
    private Rectangle collider;
    private Barrier barrier;

    public Base(Texture texture, float x, float y, int initialHealth) {
        base = new Sprite(texture);
        base.setPosition(x, y);
        health = initialHealth;
        collider = new Rectangle(x, y, base.getWidth(), base.getHeight());
    }

    @Override
    public int getLvl() {
        return 0;
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
    public void draw(SpriteBatch batch) {
        base.draw(batch);
    }

    public float getX() {
        return base.getX();
    }

    public float getY() {
        return base.getY();
    }

    public float getWidth() {
        return base.getWidth();
    }

    public float getHeight() {
        return base.getHeight();
    }

    public Rectangle getCollider() {
        return collider;
    }


    public void updateCollider() {
        collider.set(base.getX(), base.getY(), base.getWidth(), base.getHeight());
    }
}
