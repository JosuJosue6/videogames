package io.github.some_example_name.actores;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ActorObstacle extends Actor {

    private Texture tObstacle;
    private boolean alive;

    public ActorObstacle(Texture tObstacle) {
        this.tObstacle = tObstacle;
        this.alive = true;
        setSize(tObstacle.getWidth(),tObstacle.getHeight());
    }

    @Override
    public void act(float delta) {
        //super.act(delta);
        if(alive) {
            setX(getX() - 250 * delta);
        } else {
            setX(getX());
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(tObstacle,getX(),getY());
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}
