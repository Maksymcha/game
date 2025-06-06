package io.github.some_example_name.lwjgl3.Buidings;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import io.github.some_example_name.lwjgl3.Alive;
import io.github.some_example_name.lwjgl3.Entity;
import io.github.some_example_name.lwjgl3.Hero;

public class Base implements Entity, Alive {
    private Sprite baseSprite;
    private int health = 500;
    private Rectangle movementCollider = new Rectangle();
    public Base(Texture texture, float x, float y, float width, float height) {
        baseSprite = new Sprite(texture);
        baseSprite.setPosition(x, y);
        baseSprite.setSize(width, height);
        movementCollider.set(x, y, width, height);
    }
    @Override
    public Rectangle getMovementCollider() {
        return movementCollider;
    }
    public boolean canBuildHere(Hero hero) {
        return hero.getAttackCollider().overlaps(this.getMovementCollider());
    }
    @Override
    public int getHealth() {
        return health;
    }
    @Override
    public void setHealth(int health) {
    }
    @Override
    public void takeDamage(int damage) {
        health -= damage;
        if (health < 0) health = 0;
    }
    @Override
    public int getLvl() {
        return 1;
    }
    @Override
    public boolean isDead() {
        return health <= 0;
    }
    @Override
    public void draw(SpriteBatch batch) {
        baseSprite.draw(batch);
    }
    public float getX() { return baseSprite.getX(); }
    public float getY() { return baseSprite.getY(); }
    public float getWidth() { return baseSprite.getWidth(); }
    public float getHeight() { return baseSprite.getHeight(); }
}
