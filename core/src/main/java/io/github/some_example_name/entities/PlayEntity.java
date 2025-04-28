package io.github.some_example_name.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import com.badlogic.gdx.math.Rectangle;

import static io.github.some_example_name.Constants.FCONVERTION;

public class PlayEntity extends Actor {
    private Texture texture;
    private World world;
    private Fixture fixture;
    private Body body;
    private boolean isJumping = false;
    private float speed = 2.0f;

    private boolean ismove=true;

    public PlayEntity(Texture texture, World world, Vector2 position) {
        this.texture = texture;
        this.world = world;

        // Crear cuerpo dinámico
        BodyDef def = new BodyDef();
        def.position.set(position);
        def.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.25f, 0.25f); // Tamaño del jugador (caja de 1x1)
        body.createFixture(shape, 1);
        shape.dispose();

        setSize(1 * FCONVERTION, 1 * FCONVERTION); // Tamaño gráfico
    }
    public void move(float x, float y) {
        // Aplica la velocidad para moverlo
        body.setLinearVelocity(x * speed, y * speed); // Movimiento en X e Y
    }
    public void jump() {
        if (!isJumping) {
            // Aplicar fuerza hacia arriba
            body.applyLinearImpulse(new Vector2(0, 1.5f), body.getWorldCenter(), true);
            isJumping = true;
        }
    }

    public void stopJumping() {
        isJumping = false;
    }

    public void stopMove() {
        ismove= false;
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        Vector2 pos = body.getPosition();
        setPosition(pos.x * FCONVERTION - getWidth() / 2, pos.y * FCONVERTION - getHeight() / 2);
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    public void update() {
        if (body.getLinearVelocity().y == 0) {
            stopJumping();
        }
        if (ismove){
            body.setLinearVelocity(3f, body.getLinearVelocity().y);
        }

    }

    public Rectangle getBoundingRectangle() {
        Vector2 pos = body.getPosition();
        return new Rectangle(
            pos.x * FCONVERTION - getWidth() / 2,
            pos.y * FCONVERTION - getHeight() / 2,
            getWidth(),
            getHeight()
        );
    }

}
