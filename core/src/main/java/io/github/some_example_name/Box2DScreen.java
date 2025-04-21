package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.sun.org.apache.xpath.internal.operations.Bool;

public class Box2DScreen extends BaseScreen {

    private World world;
    private Box2DDebugRenderer renderer;
    private OrthographicCamera camera;
    private Body bodyPlayer;
    private Fixture fixturePlayer;

    private Body bodySuelo;
    private Fixture fixtureSuelo;

    //-- Obstaculos
    private Body bodyRock;
    private Fixture fixtureRock;

    //-- Colisiones
    private Boolean playerAlive = true;


    public Box2DScreen(Main game) {
        super(game);
    }

    @Override
    public void show() {
        //-- definir dos condiciones, gravedad
        world = new World(new Vector2(0, -10), true);
        renderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera(7, 5);
        camera.translate(0,-7);
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Fixture fixtureA, fixtureB;
                fixtureA = contact.getFixtureA();
                fixtureB = contact.getFixtureB();

                if (fixtureA == fixturePlayer && fixtureB == fixtureRock){
                    playerAlive = false;
                }
                if (fixtureB ==fixturePlayer && fixtureA == fixtureRock){
                    playerAlive = false;
                }
            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });

        //-- PLayer
        BodyDef playerDef = createBodyDef();
        bodyPlayer = world.createBody(playerDef);
        PolygonShape shapePlayer = new PolygonShape();
        shapePlayer.setAsBox(.5f, .5f);
        fixturePlayer = bodyPlayer.createFixture(shapePlayer, 1);
        shapePlayer.dispose();

        //-- Suelo
        BodyDef sueloDef = createBodyDef2();
        bodySuelo = world.createBody(sueloDef);
        PolygonShape shapeSuelo = new PolygonShape();
        shapeSuelo.setAsBox(100, 2);
        fixtureSuelo = bodySuelo.createFixture(shapeSuelo, 0);
        shapeSuelo.dispose();

        //-- Roca
        Vector2[] vertices = new Vector2[3];
        vertices[0] = new Vector2(-.5f, -.5f);
        vertices[1] = new Vector2(.5f, -.5f);
        vertices[2] = new Vector2(0f, .5f);

        for (int i = 0; i<10; i++) {

            float number = (float)Math.random()*25;
            if (number > 5) {
                BodyDef rocaDef = createObstacleDef(number);
                bodyRock = world.createBody(rocaDef);
                PolygonShape shapeRock = new PolygonShape();
                shapeRock.set(vertices);
                fixtureRock = bodyRock.createFixture(shapeRock, 0);
                shapeRock.dispose();
            }
        }


    }

    @Override
    public void dispose() {

        world.destroyBody(bodyPlayer);
        world.dispose();
        renderer.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(Gdx.input.justTouched()){
            jump();
        }
        if (playerAlive) {
            float velocity = bodyPlayer.getLinearVelocity().y;
            bodyPlayer.setLinearVelocity(2.5f, velocity);
        }

        if (bodyPlayer.getPosition().x > 1 && playerAlive){
            camera.translate(2.5f*delta,0,0);
        }

        //-- método que sirve para simular los mundos
        world.step(delta, 6, 2);
        camera.update();
        renderer.render(world, camera.combined);
        //jump();
    }

    private BodyDef createBodyDef() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(-4, -7.5f); // Ajusta la posición del jugador
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        return bodyDef;
    }

    private BodyDef createBodyDef2() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(0, -10); // Ajusta la posición del suelo
        bodyDef.type = BodyDef.BodyType.StaticBody;
        return bodyDef;
    }

    private BodyDef createObstacleDef(float number) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(number, -7.5f);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        return bodyDef;
    }
    private void jump (){
        Vector2 position = bodyPlayer.getPosition();
        bodyPlayer.applyLinearImpulse(0,5.5f,position.x, position.y, true );
    }
}
