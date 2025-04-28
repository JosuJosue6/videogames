package io.github.some_example_name.entities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;


import static io.github.some_example_name.Constants.FCONVERTION;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class GroundEntity extends Actor {
    private Texture texture;
    private Body body;

    public GroundEntity(Texture texture, World world, Vector2 position, float width, float height) {
        this.texture = texture;

        // Crear cuerpo estático
        BodyDef def = new BodyDef();
        def.position.set(position);
        def.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(def);

        // Crear forma y fixture
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2); // Mitad porque Box2D mide desde el centro
        body.createFixture(shape, 0);
        shape.dispose();

        // Tamaño gráfico (escala)
        setSize(width * FCONVERTION, height * FCONVERTION);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Vector2 pos = body.getPosition();
        setPosition(pos.x * FCONVERTION - getWidth() / 2, pos.y * FCONVERTION - getHeight() / 2);
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    public Rectangle getBoundingRectangle() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }
}
