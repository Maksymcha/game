package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import io.github.some_example_name.lwjgl3.Buidings.Barrier;
import io.github.some_example_name.lwjgl3.Buidings.Base;
import io.github.some_example_name.lwjgl3.Buidings.Building;
import io.github.some_example_name.lwjgl3.Enemy.Enemy;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Input;


public class ScreenRenderer extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture heroTexture, baseTexture, enemyTexture, barrierTexture;
    private Player player;
    private Base base;
    private List<Enemy> enemies = new ArrayList<>();
    private Texture[] barrierTextures;
    private Texture backGroundTexture;
    private EnemySpawner enemySpawner;

    @Override
    public void create() {
        batch = new SpriteBatch();
        heroTexture = new Texture("hero.png");
        baseTexture = new Texture("base.png");
        enemyTexture = new Texture("hero.png");
        barrierTexture = new Texture("building.png");
        barrierTextures = new Texture[]{barrierTexture};
        backGroundTexture = new Texture("background.png");
        Hero hero = new Hero(heroTexture, 100, 100);
        player = new Player(hero);
        base = new Base(baseTexture, 1600, 400, 600, 600);
        enemySpawner = new EnemySpawner(enemies,0,0);
    }

    private void handleAttacks() {
        long currentTime = System.currentTimeMillis();
        for (Enemy enemy : enemies) {
            if (currentTime - enemy.lastAttackTime > 3000) {
                Alive target = enemy.getClosestTarget(enemy,player,base);
                if (target != null && enemy.getAttackCollider().overlaps(target.getMovementCollider())) {
                    int damage = enemy.getDamage();
                    int healthBefore = target.getHealth();
                    enemy.Attack(target);
                    System.out.println("Enemy attacked "+damage);
                    if (target.isDead() && target instanceof Barrier) {
                        int excessDamage = damage - healthBefore;
                        if (excessDamage > 0) {
                            base.takeDamage(excessDamage);
                        }
                    }
                    enemy.lastAttackTime = currentTime;
                }
            }
        }
        Hero hero = player.getHero();
            Enemy target = hero.getClosestEnemy(enemies.toArray(new Enemy[0]));
            if (target != null && hero.getAttackCollider().overlaps(target.getMovementCollider())) {
                hero.Attack(target);
                System.out.println("Hero attacked!");
                hero.setLastAttackTime(currentTime);
            }

    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        float deltaTime = Gdx.graphics.getDeltaTime();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            player.buildBarrier(base, barrierTextures, 100, 100);
        }
        List<Rectangle> obstacles = new ArrayList<>();
        obstacles.add(base.getMovementCollider());
        List<Rectangle> enemysObstacles = new ArrayList<>();
        enemysObstacles.add(base.getMovementCollider());
        enemysObstacles.add(player.getHero().getMovementCollider());
        for (Building building : player.getBuildings()) {
            obstacles.add(building.getMovementCollider());
        }
        enemySpawner.update(deltaTime, obstacles);
        Vector2 basePosition = new Vector2(base.getX(), base.getY());
        for (Enemy enemy : enemies) {
            enemy.move(enemysObstacles, basePosition);
            obstacles.add(enemy.getMovementCollider());
            enemysObstacles.add(enemy.getMovementCollider());
        }
        handleAttacks();
        player.getHero().move(obstacles);
        for (Iterator<Enemy> iterator = enemies.iterator(); iterator.hasNext();) {
            Enemy enemy = iterator.next();
            if (enemy.isDead()) {
                iterator.remove();
            }
        }
        for (Iterator<Building> iterator = player.getBuildings().iterator(); iterator.hasNext();) {
            Building building = iterator.next();
            if (building instanceof Barrier && ((Barrier) building).isDead()) {
                iterator.remove();
            }
        }
        batch.begin();
        batch.draw(backGroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        base.draw(batch);
        player.getHero().draw(batch);
        for (Building building : player.getBuildings()) {
            building.draw(batch);
        }
        for (Enemy enemy : enemies) {
            enemy.draw(batch);
        }
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        heroTexture.dispose();
        baseTexture.dispose();
        enemyTexture.dispose();
        barrierTexture.dispose();
    }
}
