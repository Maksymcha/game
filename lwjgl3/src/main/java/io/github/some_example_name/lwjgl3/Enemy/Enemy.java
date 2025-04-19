package io.github.some_example_name.lwjgl3.Enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import io.github.some_example_name.lwjgl3.Alive;
import io.github.some_example_name.lwjgl3.Unit;

import java.util.List;

public class Enemy extends Unit {
    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    public Enemy(float x, float y, int maxHealth, int damage, Texture texture, float speed) {
        super(texture, x, y, maxHealth, damage, "Enemy", 1, speed, 3000);
    }

    public void update(float deltaTime, List<Rectangle> obstacles, Vector2 basePosition, List<Alive> targets, long currentTime) {
        move(obstacles, basePosition);
        if (currentTime - lastAttackTime > attackCooldown) {
            Alive target = getClosestTarget(targets);
            if (target != null && getAttackCollider().overlaps(target.getMovementCollider())) {
                Attack(target);
                lastAttackTime = currentTime;
            }
        }
    }

    public void move(List<Rectangle> obstacles, Vector2 basePosition) {
        float deltaTime = Gdx.graphics.getDeltaTime();
        float dx = basePosition.x - x;
        float dy = basePosition.y - y;
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        if (distance > 5) {
            float moveX = (dx / distance) * speed;
            float moveY = (dy / distance) * speed;
            float newX = x + moveX * deltaTime;
            float newY = y + moveY * deltaTime;
            Rectangle tempCollider = new Rectangle(newX, newY, movementCollider.width, movementCollider.height);
            boolean canMove = true;
            for (Rectangle obstacle : obstacles) {
                if (tempCollider.overlaps(obstacle)) {
                    canMove = false;
                    break;
                }
            }
            if (canMove) {
                setX(newX);
                setY(newY);
            }
        }
    }

    private Alive getClosestTarget(List<Alive> targets) {
        if (targets.isEmpty()) return null;
        Alive closest = targets.get(0);
        float minDist = distanceBetween(closest);
        for (Alive target : targets) {
            float dist = distanceBetween(target);
            if (dist < minDist) {
                minDist = dist;
                closest = target;
            }
        }
        return closest;
    }

    public void drawColliders(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(1, 0.5f, 0, 1);
        shapeRenderer.rect(movementCollider.x, movementCollider.y, movementCollider.width, movementCollider.height);
        shapeRenderer.setColor(1, 0, 1, 1);
        shapeRenderer.rect(attackCollider.x, attackCollider.y, attackCollider.width, attackCollider.height);
    }
}
