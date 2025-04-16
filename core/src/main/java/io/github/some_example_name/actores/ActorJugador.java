package io.github.some_example_name.actores;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ActorJugador extends Actor {

    private Texture tjugador;

    private boolean alive;

    public ActorJugador(Texture tjugador) {
        this.tjugador = tjugador;
        setSize(tjugador.getWidth(),tjugador.getHeight());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(tjugador,getX(),getY());
    }
}
