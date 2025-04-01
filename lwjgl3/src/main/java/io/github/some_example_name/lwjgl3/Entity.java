package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import io.github.some_example_name.lwjgl3.Buidings.Base;
import io.github.some_example_name.lwjgl3.Buildings.Barrier;
import io.github.some_example_name.lwjgl3.Enemy.Enemy;

public interface Entity {
    Rectangle getCollider();
    void setCollider(Rectangle collider, Sprite sprite);
    void visitBase(Base base);
    void visitBarrier(Barrier barrier);
    void visitEnemy(Enemy enemy);
    void draw(SpriteBatch batch);
}
