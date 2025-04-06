package io.github.some_example_name.lwjgl3.Buidings;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public abstract class Building {
    protected int level;
    protected int cost;
    protected String type;
    protected Texture[] textures;
    protected Sprite currentSprite;
    protected float x, y;
    protected Rectangle movementCollider;

    public Building(int level, int cost, String type, Texture[] textures) {
        this.level = level;
        this.cost = cost;
        this.type = type;
        this.textures = textures;
        this.currentSprite = new Sprite(textures[level - 1]);
        this.x = 0;
        this.y = 0;
        this.movementCollider = new Rectangle(x, y, currentSprite.getWidth(), currentSprite.getHeight());
    }

    public int getLevel() {
        return level;
    }
    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }
    public void setX(float x) {
        this.x = x;
        currentSprite.setX(x);
    }
    public void setY(float y) {
        this.y = y;
        currentSprite.setY(y);
    }
    public float getWidth() {
        return currentSprite.getWidth();
    }
    public float getHeight() {
        return currentSprite.getHeight();
    }
    public Rectangle getMovementCollider() {
        return movementCollider;
    }
    public Sprite getCurrentSprite() {
        return currentSprite;
    }

    public abstract void upgradeAttributes();
    public void draw(Batch batch) {
        currentSprite.draw(batch);
    }
}
