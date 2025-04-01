package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.some_example_name.lwjgl3.Buidings.Base;
import io.github.some_example_name.lwjgl3.Buidings.Building;
import io.github.some_example_name.lwjgl3.Buidings.Turret;
import io.github.some_example_name.lwjgl3.Enemy.Enemy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ScreenRenderer extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture heroTexture, backgroundTexture, baseTexture, enemyTexture;
    private Texture[] barrierTextures, trapTextures, turretTextures;
    private OrthographicCamera camera;
    private Player player;
    private Stage stage;
    private Skin skin;
    private Label moneyLabel;
    private Base base;
    private List<Enemy> enemies = new ArrayList<>();
    private Building pendingBuilding;
    private float timeSinceLastSpawn = 0;
    private static final float BASE_X = 1600;
    private static final float BASE_Y = 400;

    @Override
    public void create() {
        batch = new SpriteBatch();
        heroTexture = new Texture(Gdx.files.internal("hero.png"));
        backgroundTexture = new Texture(Gdx.files.internal("background.png"));
        baseTexture = new Texture(Gdx.files.internal("base.png"));
        enemyTexture = new Texture(Gdx.files.internal("hero.png"));
        barrierTextures = new Texture[]{new Texture(Gdx.files.internal("building.png"))};
        trapTextures = new Texture[]{new Texture(Gdx.files.internal("hero.png"))};
        turretTextures = new Texture[]{new Texture(Gdx.files.internal("hero.png"))};

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 1080);
        player = new Player(new Hero(100, 400, 100, 100, 10, heroTexture), 1000);
        base = new Base(baseTexture, BASE_X, BASE_Y, 100);

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        Table menu = new Table();
        menu.setFillParent(true);
        menu.right().top();

        moneyLabel = new Label("Money: " + player.getMoney(), skin);
        menu.add(moneyLabel).pad(10).row();

        TextButton buildBarrier = new TextButton("Build Barrier (100)", skin);
        buildBarrier.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                io.github.some_example_name.lwjgl3.Buildings.Barrier barrier = new io.github.some_example_name.lwjgl3.Buildings.Barrier(1, 100, "Barrier", barrierTextures);
                if (player.canAffordBuilding(barrier) && getBarrierAtBase() == null) {
                    barrier.setX(BASE_X);
                    barrier.setY(BASE_Y);
                    player.buildBuilding(barrier);
                    updateMoneyLabel();
                }
            }
        });
        menu.add(buildBarrier).pad(5).row();

        TextButton buildTrap = new TextButton("Build Trap (50)", skin);
        buildTrap.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (player.getMoney() >= 50) {
                    pendingBuilding = new io.github.some_example_name.lwjgl3.Buildings.Trap(1, 50, "Trap", trapTextures);
                }
            }
        });
        menu.add(buildTrap).pad(5).row();

        TextButton buildTurret = new TextButton("Build Turret (150)", skin);
        buildTurret.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (player.getMoney() >= 150) {
                    pendingBuilding = new Turret(1, 150, "Turret", turretTextures, 1, 200, 10);
                }
            }
        });
        menu.add(buildTurret).pad(5).row();

        stage.addActor(menu);
    }

    private void updateMoneyLabel() {
        moneyLabel.setText("Money: " + player.getMoney());
    }

    @Override
    public void render() {
        updateGame();
        drawGame();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    private void updateGame() {
        player.getHero().move();
        for (Enemy enemy : enemies) {
            enemy.move();
        }
        base.updateCollider();
        for (Building building : player.getBuildings()) {
            building.updateCollider();
        }
        handleEnemyAttacks();
        handleTraps();
        handleTurrets();
        handleHeroAttacks();
        if (pendingBuilding != null && Gdx.input.justTouched()) {
            float x = Gdx.input.getX();
            float y = Gdx.graphics.getHeight() - Gdx.input.getY();
            pendingBuilding.setX(x - pendingBuilding.getWidth() / 2);
            pendingBuilding.setY(y - pendingBuilding.getHeight() / 2);
            if (canPlaceBuilding(pendingBuilding)) {
                player.buildBuilding(pendingBuilding);
                updateMoneyLabel();
                pendingBuilding = null;
            }
        }

    }

    private boolean canPlaceBuilding(Building building) {
        Rectangle newCollider = new Rectangle(building.getX(), building.getY(), building.getWidth(), building.getHeight());
        if (newCollider.overlaps(base.getCollider())) {
            return false;
        }
        for (Building existing : player.getBuildings()) {
            if (newCollider.overlaps(existing.getCollider())) {
                return false;
            }
        }
        return true;
    }

    private void drawGame() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, 1920, 1080);
        base.draw(batch);
        for (Building building : player.getBuildings()) {
            building.draw(batch);
        }
        player.getHero().draw(batch);
        for (Enemy enemy : enemies) {
            enemy.draw(batch);
        }
        batch.end();
    }

    private Building getBarrierAtBase() {
        for (Building building : player.getBuildings()) {
            if (building instanceof io.github.some_example_name.lwjgl3.Buildings.Barrier && building.getX() == BASE_X && building.getY() == BASE_Y) {
                return building;
            }
        }
        return null;
    }

    private void handleEnemyAttacks() {
        Iterator<Enemy> iterator = enemies.iterator();
        while (iterator.hasNext()) {
            Enemy enemy = iterator.next();
            if (!enemy.hasAttacked()) {
                Rectangle enemyCollider = enemy.getCollider();
                Building barrier = getBarrierAtBase();
                if (barrier != null && enemyCollider.overlaps(barrier.getCollider())) {
                    int damage = enemy.getDamage();
                    int currentHealth = ((io.github.some_example_name.lwjgl3.Buildings.Barrier) barrier).getHealth();
                    ((io.github.some_example_name.lwjgl3.Buildings.Barrier) barrier).takeDamage(damage);
                    if (((io.github.some_example_name.lwjgl3.Buildings.Barrier) barrier).isDead()) {
                        player.getBuildings().remove(barrier);
                        int remainingDamage = damage - currentHealth;
                        base.takeDamage(remainingDamage);
                    }
                    enemy.setHasAttacked(true);
                    iterator.remove();
                } else if (enemyCollider.overlaps(base.getCollider())) {
                    base.takeDamage(enemy.getDamage());
                    enemy.setHasAttacked(true);
                    iterator.remove();
                }
            }
        }
    }

    private void handleTraps() {
        for (Building building : player.getBuildings()) {
            if (building instanceof io.github.some_example_name.lwjgl3.Buildings.Trap) {
                io.github.some_example_name.lwjgl3.Buildings.Trap trap = (io.github.some_example_name.lwjgl3.Buildings.Trap) building;
                for (Enemy enemy : enemies) {
                    if (!enemy.isDead() && trap.getCollider().overlaps(enemy.getCollider())) {
                        enemy.takeDamage(trap.getDamage());
                    }
                }
            }
        }
    }

    private void handleTurrets() {
        float currentTime = TimeUtils.millis() / 1000f;
        for (Building building : player.getBuildings()) {
            if (building instanceof Turret) {
                Turret turret = (Turret) building;
                if (currentTime - turret.getLastShotTime() >= turret.getAttackSpeed()) {
                    Enemy target = null;
                    float minDistance = Float.MAX_VALUE;
                    for (Enemy enemy : enemies) {
                        if (!enemy.isDead()) {
                            float dx = enemy.getX() - turret.getX();
                            float dy = enemy.getY() - turret.getY();
                            float distance = (float) Math.sqrt(dx * dx + dy * dy);
                            if (distance <= turret.getRange() && distance < minDistance) {
                                minDistance = distance;
                                target = enemy;
                            }
                        }
                    }
                    if (target != null) {
                        turret.shoot(target);
                        turret.setLastShotTime(currentTime);
                    }
                }
            }
        }
    }

    private void handleHeroAttacks() {
        Hero hero = player.getHero();
        if (!hero.isDead()) {
            for (Enemy enemy : enemies) {
                if (!enemy.isDead() && hero.getCollider().overlaps(enemy.getCollider())) {
                    hero.Attack(enemy);
                    break; // Attack one enemy per frame
                }
            }
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        heroTexture.dispose();
        backgroundTexture.dispose();
        baseTexture.dispose();
        enemyTexture.dispose();
        for (Texture texture : barrierTextures) texture.dispose();
        for (Texture texture : trapTextures) texture.dispose();
        for (Texture texture : turretTextures) texture.dispose();
        stage.dispose();
        skin.dispose();
    }
}
