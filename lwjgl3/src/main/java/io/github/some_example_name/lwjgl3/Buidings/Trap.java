package io.github.some_example_name.lwjgl3.Buildings;

import com.badlogic.gdx.graphics.Texture;
import io.github.some_example_name.lwjgl3.Buidings.Building;

public class Trap extends Building {
    private int damage;

    public Trap(int level, int cost, String type, Texture[] textures) {
        super(level, cost, type, textures);
        damage = 20;
    }

    public int getDamage() {
        return damage;
    }

    @Override
    protected void onUpgrade() {
        damage += damage * (level / 10);
    }
}
