package io.github.some_example_name.lwjgl3.Buidings;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import io.github.some_example_name.lwjgl3.Entity;

public abstract class Building implements Entity {
    protected Sprite currentSprite;
    protected float x, y;
    protected int level, cost;
    protected String type;
    protected Texture[] textures;
    protected Rectangle movementCollider;

    public Building(int level, int cost, String type, Texture[] textures) {
        this.level = level;
        this.cost = cost;
        this.type = type;
        this.textures = textures;
        this.currentSprite = new Sprite(textures[level - 1]);
        this.movementCollider = new Rectangle(x, y, currentSprite.getWidth(), currentSprite.getHeight());
    }
    @Override
    public void draw(SpriteBatch batch) {
        currentSprite.draw(batch);
    }
    public void setX(float x) { this.x = x; currentSprite.setX(x); updateColliders(); }
    public void setY(float y) { this.y = y; currentSprite.setY(y); updateColliders(); }
    public void updateColliders() { movementCollider.setPosition(x, y); }
    public float getX() { return x; }
    public float getY() { return y; }
    public int getCost() { return cost; }
    public Rectangle getMovementCollider() { return movementCollider; }
}
