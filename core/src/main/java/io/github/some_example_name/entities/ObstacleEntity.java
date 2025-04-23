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

import io.github.some_example_name.Constants;

public class ObstacleEntity extends Actor {

    private Texture texture;
    private World world;
    private Fixture fixture;
    private Body body;




    public ObstacleEntity(World world, Texture texture, Vector2 position) {
        this.world = world;
        this.texture = texture;

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position);
        bodyDef.type = BodyDef.BodyType.StaticBody;

        body = world.createBody(bodyDef);

        PolygonShape shapePlayer = new PolygonShape();
        shapePlayer.setAsBox(.5f, .5f);
        fixture = body.createFixture(shapePlayer, 1);
        shapePlayer.dispose();

        setSize(Constants.FCONVERTION, Constants.FCONVERTION);
        //--  test
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition(body.getPosition().x*Constants.FCONVERTION,body.getPosition().y*Constants.FCONVERTION);
        batch.draw(texture, getX(),getY(),getWidth(),getHeight());
    }

    @Override
    public void act(float delta) {
        //super.act(delta);

            setX(getX() - 250 * delta);

    }
}
