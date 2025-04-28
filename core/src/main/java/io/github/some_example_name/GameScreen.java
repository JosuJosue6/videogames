package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FillViewport;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FillViewport;

import java.util.ArrayList;
import java.util.List;

import io.github.some_example_name.entities.PlayEntity;
import io.github.some_example_name.entities.GroundEntity;
import io.github.some_example_name.entities.ObstacleEntity;
import com.badlogic.gdx.Input.Keys;

import io.github.some_example_name.entities.FloorEntity;
import io.github.some_example_name.entities.ObstacleEntity;
import io.github.some_example_name.entities.PlayEntity;

import jdk.javadoc.internal.doclets.formats.html.markup.Text;

public class GameScreen extends BaseScreen {
    private Stage stage;
    private Texture texture;
    private World world ;
    private PlayEntity player;
    private List<ObstacleEntity> obstaculos = new ArrayList<>();
    private ShapeRenderer shapeRenderer;
    public GameScreen(Main game) {
        super(game);
        stage =new Stage(new FillViewport(640,360));
        world = new World(new Vector2(0,-10),true);
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void dispose() {
        stage.dispose();
        world.dispose();

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.justTouched()) {
            player.jump();
        }

        player.update();

        // Detección de colisiones
        for (ObstacleEntity obstaculo : obstaculos) {
            if (player.getBoundingRectangle().overlaps(obstaculo.getBoundingRectangle())) {
                player.stopMove();
                System.out.printf("colision");
            }
        }

        world.step(delta, 6, 2);
        stage.act();

        // Hacer que la cámara siga al jugador
        stage.getCamera().position.x = player.getX() + player.getWidth() / 2;
        stage.getCamera().update();

        stage.draw();
        // Dibujar hitboxes
        shapeRenderer.setProjectionMatrix(stage.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

// Dibujar hitbox del jugador
        shapeRenderer.setColor(0,1,0,1); // Verde
        Rectangle playerRect = player.getBoundingRectangle();
        shapeRenderer.rect(playerRect.x, playerRect.y, playerRect.width, playerRect.height);

// Dibujar hitboxes de obstáculos
        shapeRenderer.setColor(1,0,0,1); // Rojo
        for (ObstacleEntity obstaculo : obstaculos) {
            Rectangle obstaculoRect = obstaculo.getBoundingRectangle();
            shapeRenderer.rect(obstaculoRect.x, obstaculoRect.y, obstaculoRect.width, obstaculoRect.height);
        }

        shapeRenderer.end();

    }

    public void show() {
        // Cargar el suelo
        Texture sueloTexture = game.getManager().get("suelo.jpg");
        GroundEntity suelo = new GroundEntity(sueloTexture, world, new Vector2(4, 0.2f), 200, 1);
        stage.addActor(suelo);

        // Cargar varias texturas para obstáculos
        Texture obstaculoTexture1 = game.getManager().get("bala.png");
        Texture obstaculoTexture2 = game.getManager().get("bala.png");
        Texture obstaculoTexture3 = game.getManager().get("bala.png");

        Texture[] obstaculoTextures = {obstaculoTexture1, obstaculoTexture2, obstaculoTexture3};

        for (int i = 0; i < 50; i++) {
            float x = 6 + i * 5;

            float yAbajo = 0.7f;
            float yArriba = 2f;

            Texture texturaSeleccionada = obstaculoTextures[i % obstaculoTextures.length];


            if (i % 2 == 0) {
                ObstacleEntity obstaculoAbajo = new ObstacleEntity(texturaSeleccionada, world, new Vector2(x, yAbajo), 1, 1);
                obstaculos.add(obstaculoAbajo);
                stage.addActor(obstaculoAbajo);
            } else {
                ObstacleEntity obstaculoArriba = new ObstacleEntity(texturaSeleccionada, world, new Vector2(x, yArriba), 1, 1);
                obstaculos.add(obstaculoArriba);
                stage.addActor(obstaculoArriba);
            }
        }

        // Cargar al jugador
        Texture playerTexture = game.getManager().get("Pool.png");
        player = new PlayEntity(playerTexture, world, new Vector2(2, 0.5f));
        stage.addActor(player);
    }

}
