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

public class Box2DScreen extends BaseScreen {

    private World world;
    private Box2DDebugRenderer renderer;
    private OrthographicCamera camera;
    private Body bodyPlayer;
    private Fixture fixturePlayer;

    private Body bodySuelo;
    private Fixture fixtureSuelo;

    public Box2DScreen(Main game) {
        super(game);
    }

    @Override
    public void show() {
        //-- definir dos condiciones, gravedad
        world = new World(new Vector2(0, -10), true);
        renderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera(32, 18);

        //-- PLayer
        BodyDef playerDef = createBodyDef();
        bodyPlayer = world.createBody(playerDef);
        PolygonShape shapePlayer = new PolygonShape();
        shapePlayer.setAsBox(1, 1);
        fixturePlayer = bodyPlayer.createFixture(shapePlayer, 1);
        shapePlayer.dispose();

        //-- Suelo
        BodyDef sueloDef = createBodyDef2();
        bodySuelo = world.createBody(sueloDef);
        PolygonShape shapeSuelo = new PolygonShape();
        shapeSuelo.setAsBox(100, 10);
        fixtureSuelo = bodySuelo.createFixture(shapeSuelo, 1);
        shapeSuelo.dispose();

        //-- Agregar el ContactListener para escuchar las colisiones
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                // Verificamos si el jugador está en contacto con el suelo
                if (contact.getFixtureA() == fixturePlayer || contact.getFixtureB() == fixturePlayer) {
                    // Detener el jugador, fijando su velocidad a 0
                    bodyPlayer.setLinearVelocity(0, 0);
                    bodyPlayer.setAngularVelocity(0); // Detener la rotación también si es necesario
                }
            }

            @Override
            public void endContact(Contact contact) {
                // Aquí podrías agregar lógica adicional si necesitas algo cuando el contacto termine
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
                // Se puede usar para manejar las colisiones antes de que ocurran
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
                // Se puede usar para manejar cosas después de la resolución de la colisión
            }
        });
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

        //-- método que sirve para simular los mundos
        world.step(delta, 6, 2);
        camera.update();
        renderer.render(world, camera.combined);
    }

    private BodyDef createBodyDef() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(0, 10); // Ajusta la posición del jugador
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        return bodyDef;
    }

    private BodyDef createBodyDef2() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(0, -10); // Ajusta la posición del suelo
        bodyDef.type = BodyDef.BodyType.StaticBody;
        return bodyDef;
    }
}
