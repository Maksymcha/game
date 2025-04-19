package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import io.github.some_example_name.lwjgl3.Enemy.Enemy;
import io.github.some_example_name.lwjgl3.Enemy.RunningEnemy;
import io.github.some_example_name.lwjgl3.Enemy.ShieldEnemy;

import java.util.Iterator;
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
    public List<Enemy> enemies(){
        return enemies;
    }

    private void spawnRandomEnemy(List<Rectangle> obstacles) {
        runningEnemy = new Texture("RunningEnemy.png");
        shieldEnemy = new Texture("ShieldEnemy.png");
        int type = (int) (Math.random() * 2);
        Enemy newEnemy;
        if (type == 0) {
            newEnemy = new RunningEnemy(x,y, 100, 30,runningEnemy,300);
        } else {
            newEnemy = new ShieldEnemy(x,y, 100, 30,shieldEnemy,300);
        }
        enemies.add(newEnemy);
    }
}
