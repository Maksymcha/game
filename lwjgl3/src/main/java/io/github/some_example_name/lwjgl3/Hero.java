package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import io.github.some_example_name.lwjgl3.Enemy.Enemy;

import java.util.List;

public class Hero extends Unit {
    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    public Hero(Texture texture, float x, float y) {
        super(texture, x, y, 10, 100, "hero", 1, 200f, 1000);
    }

    public void update(float deltaTime, List<Rectangle> obstacles, List<Enemy> enemies, long currentTime) {
        move(obstacles);
        if (currentTime - lastAttackTime > attackCooldown) {
            Enemy target = getClosestEnemy(enemies.toArray(new Enemy[0]));
            if (target != null && getAttackCollider().overlaps(target.getMovementCollider())) {
                Attack(target);
                lastAttackTime = currentTime;
            }
        }
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
        setX(newX);
        setY(newY);
    }

    private boolean isColliding(float x, float y, List<Rectangle> colliders) {
        Rectangle tempCollider = new Rectangle(x, y, movementCollider.width, movementCollider.height);
        for (Rectangle r : colliders) {
            if (tempCollider.overlaps(r)) return true;
        }
        return false;
    }

    public Enemy getClosestEnemy(Enemy[] enemies) {
        if (enemies.length == 0) return null;
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
    public void drawColliders(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(0, 1, 0, 1); // Green
        shapeRenderer.rect(movementCollider.x, movementCollider.y, movementCollider.width, movementCollider.height);
        shapeRenderer.setColor(1, 0, 0, 1); // Red
        shapeRenderer.rect(attackCollider.x, attackCollider.y, attackCollider.width, attackCollider.height);
    }
}
