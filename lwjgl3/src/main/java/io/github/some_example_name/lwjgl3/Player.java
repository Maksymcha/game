package io.github.some_example_name.lwjgl3;

import io.github.some_example_name.lwjgl3.Buidings.Building;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private int money;
    private List<Building> buildings;
    private Hero hero;

    public Player(Hero hero, int initialMoney) {
        this.hero = hero;
        this.money = initialMoney;
        buildings = new ArrayList<>();
    }

    public void addCoins(int coins) {
        this.money += coins;
    }

    public boolean canAffordBuilding(Building building) {
        return money >= building.getCost();
    }

    public void buildBuilding(Building building) {
        if (canAffordBuilding(building)) {
            buildings.add(building);
            money -= building.getCost();
        }
    }

    public void upgradeBuilding(Building building) {
        if (buildings.contains(building) && money >= building.getCost() * 2) {
            building.upgrade();
            money -= building.getCost();
        }
    }

    public List<Building> getBuildings() {
        return buildings;
    }

    public int getMoney() {
        return money;
    }

    public Hero getHero() {
        return hero;
    }
}
