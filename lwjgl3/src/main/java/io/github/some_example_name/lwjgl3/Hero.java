package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import io.github.some_example_name.lwjgl3.Enemy.Enemy;

import java.util.List;

public class Hero implements Entity, Alive, Attackable,Positionable {
    private Sprite sprite;
    private float x, y;
    private float speed = 200f;
    private int health = 100;
    private int damage = 10;
    private float lastAttackTime = 0f;
    private Rectangle movementCollider;
    private Rectangle attackCollider;

    public Hero(Texture texture, float x, float y) {
        this.sprite = new Sprite(texture);
        this.x = x;
        this.y = y;
        this.sprite.setPosition(x, y);
        this.movementCollider = new Rectangle(x, y, sprite.getWidth(), sprite.getHeight());
        this.attackCollider = new Rectangle(x - 150, y - 150, sprite.getWidth()*3, sprite.getHeight()*3);

    }

    public void move(List<Rectangle> enemyColliders) {
        float deltaTime = Gdx.graphics.getDeltaTime();
        float moveX = 0, moveY = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.A)) moveX -= 1;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) moveX += 1;
        if (Gdx.input.isKeyPressed(Input.Keys.W)) moveY += 1;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) moveY -= 1;
        if (moveX != 0 && moveY != 0) {
            float norm = (float) Math.sqrt(2);
            moveX /= norm;
            moveY /= norm;
        }

        float deltaX = moveX * speed * deltaTime;
        float deltaY = moveY * speed * deltaTime;

        float newX = movementCollider.x;
        float newY = movementCollider.y;
        if (!isColliding(newX + deltaX, newY, enemyColliders)) {
            newX += deltaX;
        }
        if (!isColliding(newX, newY + deltaY, enemyColliders)) {
            newY += deltaY;
        }
        x = newX;
        y = newY;
        updateColliders();
        sprite.setPosition(x, y);
    }

    private boolean isColliding(float x, float y, List<Rectangle> colliders) {
        for (Rectangle r : colliders) {
            if (movementCollider.overlaps(r)) return true;
        }
        return false;
    }
    public void updateColliders() {
        movementCollider.setPosition(x, y);
        attackCollider.setPosition(x, y);
    }


    @Override public void draw(SpriteBatch batch) { sprite.draw(batch); }
    @Override public int getLvl() { return 1; }
    @Override public boolean isDead() { return health <= 0; }
    @Override public void takeDamage(int damage) { health -= damage; }
    @Override public int getHealth() { return health; }
    @Override public void setHealth(int health) { this.health = health; }

    @Override
    public void Attack(Alive target) {
       target.takeDamage(damage);
    }

    @Override public int getDamage() { return damage; }
     public void setDamage(int damage) { this.damage = damage; }
    public float getLastAttackTime() { return lastAttackTime; }
     public void setLastAttackTime(float time) { this.lastAttackTime = time; }
    public float getX() { return x; }
    public float getY() { return y; }
    public Rectangle getMovementCollider() { return movementCollider; }
    public Rectangle getAttackCollider() { return attackCollider; }
    public Enemy getClosestEnemy(Enemy[] enemies) {
        if (enemies.length==0) return null;
        Enemy closest = enemies[0];
        float minDist = distanceBetween(closest);
        for (Enemy enemy : enemies) {
            float dist = distanceBetween(enemy);
            if (dist < minDist) {
                minDist = dist;
                closest = enemy;
            }
        }
        return closest;
    }
}
