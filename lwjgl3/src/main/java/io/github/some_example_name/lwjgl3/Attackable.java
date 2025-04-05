package io.github.some_example_name.lwjgl3;
import com.badlogic.gdx.math.Rectangle;

public interface Attackable {
    void Attack(Alive target);
    int getDamage();
    Rectangle getAttackCollider();
}
