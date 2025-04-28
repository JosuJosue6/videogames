package io.github.some_example_name;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import io.github.some_example_name.entities.PlayEntity;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {

    private AssetManager manager;

    public AssetManager getManager() {
        return manager;
    }

    @Override
    public void create() {
        manager = new AssetManager();
        manager.load("suelo.jpg", Texture.class);
        manager.load("Pool.png", Texture.class);
        manager.load("bala.png", Texture.class);
        manager.finishLoading();
        setScreen(new GameScreen(this));
    }
}
