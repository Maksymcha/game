package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import io.github.some_example_name.lwjgl3.Buidings.Barrier;
import io.github.some_example_name.lwjgl3.Buidings.Base;
import io.github.some_example_name.lwjgl3.Buidings.Building;
import java.util.ArrayList;


public class Player {
    private Hero hero;
    private ArrayList<Building> buildings = new ArrayList<>();
    private int money = 500;

    public Player(Hero hero) {
        this.hero = hero;
    }

    public void buildBarrier(Base base, Texture[] barrierTextures, float barrierWidth, float barrierHeight) {
        if (money >= 100 && base.canBuildHere(hero)) {
            float barrierX = base.getX() + base.getWidth() / 2 - barrierWidth / 2;
            float barrierY = base.getY() + base.getHeight() / 2 - barrierHeight / 2;
            Rectangle intendedCollider = new Rectangle(barrierX, barrierY, barrierWidth, barrierHeight);
            boolean occupied = false;
            for (Building building : buildings) {
                if (building.getMovementCollider().overlaps(intendedCollider)) {
                    occupied = true;
                    break;
                }
            }
            if (!occupied) {
                Barrier barrier = new Barrier(1, 100, "Barrier", barrierTextures);
                barrier.setX(barrierX);
                barrier.setY(barrierY);
                buildings.add(barrier);
                money -= 100;

            }
        }
    }

    public Hero getHero() { return hero; }
    public ArrayList<Building> getBuildings() { return buildings; }
    public int getMoney() { return money; }
}
