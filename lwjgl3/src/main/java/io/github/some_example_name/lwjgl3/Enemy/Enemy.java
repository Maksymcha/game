package io.github.some_example_name.lwjgl3.Enemy;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import io.github.some_example_name.lwjgl3.*;
import io.github.some_example_name.lwjgl3.Buidings.Base;
import io.github.some_example_name.lwjgl3.Buidings.Building;

import java.util.ArrayList;
import java.util.List;


public abstract class Enemy implements Alive, Attackable, Entity,Positionable {
    protected Sprite sprite;
    protected float x, y;
    protected int health;
    protected int damage;
    protected float maxSpeed;
    private Rectangle movementCollider = new Rectangle();
    private Rectangle attackCollider = new Rectangle();
    public long lastAttackTime = 0;

    public Enemy(float x, float y, int health, int damage, Texture texture) {
        this.x = x;
        this.y = y;
        this.health = health;
        this.damage = damage;
        sprite = new Sprite(texture);
        sprite.setPosition(x, y);
        updateColliders();
    }

    @Override
    public Rectangle getMovementCollider() {
        return movementCollider;
    }

    @Override
    public Rectangle getAttackCollider() {
        return attackCollider;
    }

    public void updateColliders() {
        movementCollider.set(x, y, sprite.getWidth(), sprite.getHeight());
        attackCollider.set(x, y, sprite.getWidth(), sprite.getHeight()); // Adjust as needed
    }


    public float getSpeed() {
        return maxSpeed;
    }


    public void setSpeed(int speed) {
          this.maxSpeed = speed;
    }

   public void move(List<Rectangle> enemysObstacles, Vector2 basePosition) {
        float speed = 100f;
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
            for (Rectangle obstacle : enemysObstacles) {
                if (tempCollider.overlaps(obstacle)) {
                    canMove = false;
                    break;
                }
            }
            if (canMove) {
                x = newX;
                y = newY;
                sprite.setPosition(x, y);
                updateColliders();
            }
        }
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
        return 0;
    }

    @Override
    public boolean isDead() {
        return health <= 0;
    }

    @Override
    public void Attack(Alive target) {
        target.takeDamage(damage);
    }
    @Override
    public int getDamage() {
        return damage;
    }
    @Override
    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }
    public float getX() { return x; }
    public float getY() { return y; }
    public Alive getClosestTarget(Enemy enemy, Player player, Base base) {
        List<Alive> targets = new ArrayList<>();
        targets.add(player.getHero());
        targets.add(base);
        for (Building building : player.getBuildings()) {
            if (building instanceof Alive) {
                targets.add((Alive) building);
            }
        }
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
}
