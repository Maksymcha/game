package io.github.some_example_name.lwjgl3;

public interface Attackable {
    int getDamage();
    void setDamage(int damage);
    void Attack(Alive target);
}
