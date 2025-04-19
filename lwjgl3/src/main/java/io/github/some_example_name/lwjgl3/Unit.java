package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Unit implements Alive, Attackable, Positionable, Entity {
    protected Sprite sprite;
    protected float x, y;
    protected int health;
    protected int maxHealth;
    protected int damage;
    protected String name;
    protected int level;
    protected float speed;
    protected long lastAttackTime = 0;
    protected long attackCooldown;
    protected Rectangle movementCollider;
    protected Rectangle attackCollider;

    public Unit(Texture texture, float x, float y, int maxHealth, int damage, String name, int level, float speed, long attackCooldown) {
        this.sprite = new Sprite(texture);
        this.x = x;
        this.y = y;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.damage = damage;
        this.name = name;
        this.level = level;
        this.speed = speed;
        this.attackCooldown = attackCooldown;
        this.movementCollider = new Rectangle(x, y, sprite.getWidth(), sprite.getHeight());
        this.attackCollider = new Rectangle(x - sprite.getWidth(), y - sprite.getHeight(), sprite.getWidth() * 3, sprite.getHeight() * 3);
        sprite.setPosition(x, y);
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
        sprite.setX(x);
        updateColliders();
    }

    public void setY(float y) {
        this.y = y;
        sprite.setY(y);
        updateColliders();
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void setHealth(int health) {
        this.health = Math.max(0, Math.min(health, maxHealth));
    }

    @Override
    public void takeDamage(int damage) {
        setHealth(health - damage);
    }

    @Override
    public boolean isDead() {
        return health <= 0;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getLvl() {
        return level;
    }

    @Override
    public Rectangle getMovementCollider() {
        return movementCollider;
    }

    @Override
    public Rectangle getAttackCollider() {
        return attackCollider;
    }

    @Override
    public void Attack(Alive target) {
        target.takeDamage(damage);
    }

    @Override
    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public long getLastAttackTime() {
        return lastAttackTime;
    }

    public void setLastAttackTime(long time) {
        this.lastAttackTime = time;
    }

    protected void updateColliders() {
        movementCollider.setPosition(x, y);
        attackCollider.setPosition(x - sprite.getWidth(), y - sprite.getHeight());
    }

    @Override
    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    @Override
    public void update() {

    }
}
