package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import io.github.some_example_name.lwjgl3.Buidings.Barrier;
import io.github.some_example_name.lwjgl3.Buidings.Base;
import io.github.some_example_name.lwjgl3.Buidings.Building;
import io.github.some_example_name.lwjgl3.Enemy.Enemy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ScreenRenderer extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture heroTexture, baseTexture, enemyTexture, barrierTexture;
    private Player player;
    private Base base;
    private Texture[] barrierTextures;
    private Texture backGroundTexture;
    private EnemySpawner enemySpawner;
    private ShapeRenderer shapeRenderer;

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
        enemySpawner = new EnemySpawner(new ArrayList<>(), 0, 0);
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float deltaTime = Gdx.graphics.getDeltaTime();
        long currentTime = System.currentTimeMillis();

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            player.buildBarrier(base, barrierTextures, 100, 100);
        }
        if (player.getHero().isDead() || base.isDead()) {
            restartLevel();
            return;
        }
        List<Rectangle> obstacles = new ArrayList<>();
        obstacles.add(base.getMovementCollider());
        for (Building building : player.getBuildings()) {
            obstacles.add(building.getMovementCollider());
        }
        List<Rectangle> enemysObstacles = new ArrayList<>();
        enemysObstacles.add(base.getMovementCollider());
        enemysObstacles.add(player.getHero().getMovementCollider());
        for (Enemy enemy : enemySpawner.enemies()) {
            enemysObstacles.add(enemy.getMovementCollider());
        }
        List<Alive> targets = new ArrayList<>();
        targets.add(player.getHero());
        targets.add(base);
        for (Building building : player.getBuildings()) {
            if (building instanceof Alive) {
                targets.add((Alive) building);
            }
        }

        enemySpawner.update(deltaTime, obstacles);
        player.getHero().update(deltaTime, obstacles, enemySpawner.enemies(), currentTime);

        Vector2 basePosition = new Vector2(base.getX(), base.getY());
        for (Enemy enemy : enemySpawner.enemies()) {
            List<Rectangle> enemySpecificObstacles = new ArrayList<>(enemysObstacles);
            enemySpecificObstacles.remove(enemy.getMovementCollider());
            enemy.update(deltaTime, enemySpecificObstacles, basePosition, targets, currentTime);
        }

        Iterator<Enemy> enemyIterator = enemySpawner.enemies().iterator();
        while (enemyIterator.hasNext()) {
            Enemy enemy = enemyIterator.next();
            if (enemy.isDead()) {
                enemyIterator.remove();
            }
        }

        Iterator<Building> buildingIterator = player.getBuildings().iterator();
        while (buildingIterator.hasNext()) {
            Building building = buildingIterator.next();
            if (building instanceof Barrier && ((Barrier) building).isDead()) {
                buildingIterator.remove();
            }
        }

        batch.begin();
        batch.draw(backGroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        base.draw(batch);
        player.getHero().draw(batch);
        for (Building building : player.getBuildings()) {
            building.draw(batch);
        }
        for (Enemy enemy : enemySpawner.enemies()) {
            enemy.draw(batch);
        }
        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        player.getHero().drawColliders(shapeRenderer);
        base.drawColliders(shapeRenderer);
        for (Enemy enemy : enemySpawner.enemies()) {
            enemy.drawColliders(shapeRenderer);
        }
        shapeRenderer.end();
    }

    private void restartLevel() {
        batch.dispose();
        shapeRenderer.dispose();
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        Hero hero = new Hero(heroTexture, 100, 100);
        player = new Player(hero);
        base = new Base(baseTexture, 1600, 400, 600, 600);
        enemySpawner = new EnemySpawner(new ArrayList<>(), 0, 0);
        System.out.println("Level restarted.");
    }

    @Override
    public void dispose() {
        batch.dispose();
        heroTexture.dispose();
        baseTexture.dispose();
        enemyTexture.dispose();
        barrierTexture.dispose();
        backGroundTexture.dispose();
        shapeRenderer.dispose();
    }
}
