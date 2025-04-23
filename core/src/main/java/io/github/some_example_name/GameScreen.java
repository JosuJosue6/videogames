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

import io.github.some_example_name.entities.FloorEntity;
import io.github.some_example_name.entities.ObstacleEntity;
import io.github.some_example_name.entities.PlayerEntity;
import jdk.javadoc.internal.doclets.formats.html.markup.Text;

public class GameScreen extends BaseScreen{

    private Stage stage;
    private World world;

    private OrthographicCamera camera;

    private PlayerEntity player;
    private List<FloorEntity> floorList = new ArrayList<FloorEntity>();

    private List<ObstacleEntity> obstacleList = new ArrayList<ObstacleEntity>();

    public GameScreen(Main game) {
        super(game);
        stage = new Stage(new FillViewport(640,360));
        world = new World(new Vector2(0,-10),true);
    }

    @Override
    public void show() {

        camera = new OrthographicCamera(7, 5);
        camera.translate(0,-7);

        Texture playerTexture = game.getManager().get("Pool.png");
        player = new PlayerEntity(world,playerTexture,new Vector2(1,2));

        Texture floorTexture = game.getManager().get("suelo.jpg");
        floorList.add(new FloorEntity(world, floorTexture,0,1,1000));

        Texture obstacleTexture = game.getManager().get("bala.png");
        obstacleList.add(new ObstacleEntity(world,obstacleTexture,new Vector2(5,1)));

        stage.addActor(player);
        for (FloorEntity floor:floorList){
            stage.addActor(floor);
        }

        for (ObstacleEntity obstacle:obstacleList){
            stage.addActor(obstacle);
        }
    }

    @Override
    public void dispose() {
        stage.dispose();
        world.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (player.getBody().getPosition().x>1000 && player.isAlive()){
            camera.translate(4*delta,0,0);
        }
        stage.act();
        world.step(delta,6,2);
        stage.draw();
        camera.update();
    }

    @Override
    public void hide() {
        player.remove();
    }
}
