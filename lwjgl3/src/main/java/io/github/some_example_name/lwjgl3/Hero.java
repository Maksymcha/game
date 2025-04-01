package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Hero implements Movable, Alive, Attackable,Entity {
    private int health;
    private boolean dead;
    private int damage;
    private int speed;
    private float x, y;
    private int level = 1;
    private static final int MAX_LEVEL = 4;
    private int xp = 0;
    private Sprite sprite;
    private Rectangle collider;

    public Hero(int health, int speed, float startX, float startY, int damage, Texture texture) {
        this.health = health;
        this.speed = speed;
        this.x = startX;
        this.y = startY;
        this.damage = damage;
        this.dead = false;
        this.sprite = new Sprite(texture);
        this.sprite.setPosition(x, y);
        this.sprite.setSize(texture.getWidth(), texture.getHeight());
        this.collider = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
    }
    @Override
    public void move() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        float newX = x;
        float newY = y;

        if (Gdx.input.isKeyPressed(Input.Keys.A)) newX -= speed * deltaTime;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) newX += speed * deltaTime;
        if (Gdx.input.isKeyPressed(Input.Keys.W)) newY += speed * deltaTime;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) newY -= speed * deltaTime;

        x = Math.max(sprite.getWidth() / 2, Math.min(newX, Gdx.graphics.getWidth() - sprite.getWidth() / 2));
        y = Math.max(sprite.getHeight() / 2, Math.min(newY, Gdx.graphics.getHeight() - sprite.getHeight() / 2));
        sprite.setPosition(x, y);
        collider.set(x, y, sprite.getWidth(), sprite.getHeight());
        collider.set(x, y, sprite.getWidth(), sprite.getHeight());
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
    public int getLvl() {
        return level;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void setHealth(int health) {
        this.health = Math.max(0, health);
        this.dead = (this.health == 0);
    }

    @Override
    public void takeDamage(int damage) {
        setHealth(this.health - damage);
    }

    @Override
    public boolean isDead() {
        return dead;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
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
        if (!dead) {
            target.takeDamage(damage);
        }
    }

    public void levelUp() {
        if (level < MAX_LEVEL) {
            level++;
            health += 10;
            damage += 2;
            speed += 1;
        }
    }
    public Rectangle getCollider() {
        return collider;
    }

    public float getWidth() {
        return sprite.getWidth();
    }

    public float getHeight() {
        return sprite.getHeight();
    }
    @Override
    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }
}
