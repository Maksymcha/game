package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import io.github.some_example_name.lwjgl3.Enemy.Enemy;
import io.github.some_example_name.lwjgl3.Enemy.RunningEnemy;
import io.github.some_example_name.lwjgl3.Enemy.ShieldEnemy;

import java.util.List;

public class EnemySpawner {
    private List<Enemy> enemies;
    private float spawnInterval = 5f;
    private float timeSinceLastSpawn = 0f;
    private Texture runningEnemy, shieldEnemy;
    private float x;
    private float y;

    public EnemySpawner(List<Enemy> enemies,int x,int y) {
        this.enemies = enemies;
    }
    public int getX(){return (int) x;}
    public int getY(){return (int) y;}
    public void update(float deltaTime, List<Rectangle> obstacles) {
        timeSinceLastSpawn += deltaTime;
        if (timeSinceLastSpawn >= spawnInterval) {
            spawnRandomEnemy(obstacles);
            timeSinceLastSpawn = 0f;
        }
    }

    private void spawnRandomEnemy(List<Rectangle> obstacles) {
        runningEnemy = new Texture("hero.png");
        shieldEnemy = new Texture("hero.png");
        int type = (int) (Math.random() * 2); // 0 or 1
        Enemy newEnemy;
        if (type == 0) {
            newEnemy = new RunningEnemy(x,y, 100, 30,runningEnemy);
        } else {
            newEnemy = new ShieldEnemy(x,y, 100, 30,shieldEnemy);
        }
        enemies.add(newEnemy);
    }
}
