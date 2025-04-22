package io.github.some_example_name.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class PlayerEntity extends Actor {

    private Texture texture;
    private World world;
    private Fixture fixture;
    private Body body;


    public PlayerEntity(World world, Texture texture, Vector2 position) {
        this.world = world;
        this.texture = texture;

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(-4, -7.5f); // Ajusta la posición del jugador
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        body = world.createBody(bodyDef);

        PolygonShape shapePlayer = new PolygonShape();
        shapePlayer.setAsBox(.5f, .5f);
        fixture = body.createFixture(shapePlayer, 1);
        shapePlayer.dispose();
        //--  test
    }
}
