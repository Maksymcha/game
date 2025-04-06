package io.github.some_example_name.lwjgl3.Buidings;
import com.badlogic.gdx.graphics.Texture;
import io.github.some_example_name.lwjgl3.Alive;

public class Barrier extends Building implements Alive {
    private int maxHealth;
    private int health;
    private String name= "Barrier";
    public Barrier(int level, int cost, String type, Texture[] textures) {
        super(level, cost, type, textures);
        maxHealth = 100;
        health = maxHealth;
    }

    @Override
    public void upgradeAttributes() {

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getLvl() {
        return 0;
    }
    @Override
    public int getHealth() {
        return health;
    }
    @Override
    public void setHealth(int health) {
        this.health = Math.max(0, Math.min(health, maxHealth));
    }
    @Override
    public void takeDamage(int damage) {
        setHealth(health - damage);
    }
    @Override
    public boolean isDead() {
        return health <= 0;
    }
}
