package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

import org.w3c.dom.Text;

import java.util.Random;

import io.github.some_example_name.actores.ActorJugador;
import io.github.some_example_name.actores.ActorObstacle;

public class MainGameScreen extends BaseScreen{
    private Stage stage;
    private Actor jugador;
    private Texture tjugador;
    private Actor obstacle;
    private Texture tObstacle;

    public MainGameScreen(Main game) {
        super(game);
        tjugador=new Texture("Pool.png");
        tObstacle = new Texture("bala.png");
    }

    @Override
    public void show() {
        stage= new Stage();
        stage.setDebugAll(true); //-- modo de depuracion
        jugador= new ActorJugador(tjugador);
        obstacle = new ActorObstacle(tObstacle);
        stage.addActor(jugador);
        stage.addActor(obstacle);
        jugador.setPosition(0,50);
        obstacle.setPosition(600,100);
    }

    @Override
    public void hide() {
        stage.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor((float) (Math.random()*1),(float) (Math.random()*1),(float) (Math.random()*1),0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //-- colisiones
        colition();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        tjugador.dispose();
        tObstacle.dispose();
    }

    private void colition(){
        if(jugador.getX()+jugador.getWidth() > obstacle.getX()){
            System.out.println("Detected collition");
        }
    }
}

