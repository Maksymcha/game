package io.github.some_example_name.lwjgl3.Buidings;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import io.github.some_example_name.lwjgl3.Entity;

public abstract class Building implements Entity {
        protected int level;
        protected int cost;
        protected String type;
        protected float x, y;
        protected Sprite[] sprites;
        protected Sprite currentSprite;
        protected Rectangle collider;

        public Building(int level, int initialCost, String type, Texture[] textures) {
            this.level = level;
            this.cost = initialCost;
            this.type = type;
            this.sprites = new Sprite[textures.length];
            for (int i = 0; i < textures.length; i++) {
                this.sprites[i] = new Sprite(textures[i]);
                this.sprites[i].setSize(textures[i].getWidth(), textures[i].getHeight());
            }
            this.currentSprite = sprites[Math.min(level - 1, sprites.length - 1)];
            this.x = 0;
            this.y = 0;
            this.collider = new Rectangle(x, y, currentSprite.getWidth(), currentSprite.getHeight());
        }

        public void setX(float x) {
            this.x = x;
            currentSprite.setX(x);
            collider.setX(x);
        }

        public void setY(float y) {
            this.y = y;
            currentSprite.setY(y);
            collider.setY(y);
        }

        public Rectangle getCollider() {
            return collider;
        }
public String getType() {
            return type;
}
        public float getWidth() {
            return currentSprite.getWidth();
        }

        public float getHeight() {
            return currentSprite.getHeight();
        }
      public float getX() {return x;}
    public float getY() {return y;}
    public void updateCollider() {
        collider.set(currentSprite.getX(), currentSprite.getY(), currentSprite.getWidth(), currentSprite.getHeight());
    }
    @Override
    public void draw(SpriteBatch batch) {
        currentSprite.draw(batch);
    }

    public int getCost() { return cost; }

    public void upgrade() {
        cost *= 2;
        level++;
        int spriteIndex = Math.min(level - 1, sprites.length - 1);
        currentSprite = sprites[spriteIndex];
        currentSprite.setX(x);
        currentSprite.setY(y);
        onUpgrade();
    }
    protected abstract void onUpgrade();
}
