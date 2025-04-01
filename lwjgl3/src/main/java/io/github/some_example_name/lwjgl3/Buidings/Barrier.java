package io.github.some_example_name.lwjgl3.Buildings;

import com.badlogic.gdx.graphics.Texture;
import io.github.some_example_name.lwjgl3.Alive;
import io.github.some_example_name.lwjgl3.Buidings.Building;

public class Barrier extends Building implements Alive {
    private int health;

    public Barrier(int level, int cost, String type, Texture[] textures) {
        super(level, cost, type, textures);
        health = 100;
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
        this.health = Math.max(0, health);
    }

    @Override
    public void takeDamage(int damage) {
        setHealth(health - damage);
    }

    @Override
    public boolean isDead() {
        return health <= 0;
    }

    @Override
    protected void onUpgrade() {
        health += health * (level / 10);
    }
}
