package com.mygdx.giaodien;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class HudClass implements Screen {

    private Label label1;
    private Stage stage;

    public HudClass() {
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        label1 = new Label("Hud class",new Skin(Gdx.files.internal("uiskin.json")));
        label1.setSize(200,300);
        label1.setPosition(200,200);
        stage.addActor(label1);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 10, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
