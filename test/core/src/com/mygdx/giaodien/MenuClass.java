package com.mygdx.giaodien;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MyGdxGame;

public class MenuClass implements Screen {


    private Stage stage;
    private MyActor background;
    private MyActor label_menu;
    private MyActor button_resume;
    private MyActor button_restart;

    private MainClass mainClass;

    public MenuClass(final MainClass mainClass) {
        this.mainClass = mainClass;
        stage = new Stage(new ScreenViewport());
        background = new MyActor(new Texture("Background_mainmenu.png"));
        label_menu = new MyActor(new Texture("Label_mainmenu.png"));
        button_resume = new MyActor(new Texture("Button_resumegame.png"));
        button_restart = new MyActor(new Texture("Button_restartgame.png"));

        // y trong day nguoc voi XD.
        label_menu.spritePos(246,720-39-label_menu.getHeight());
        button_resume.spritePos(400,720-303-button_resume.getHeight());
        button_restart.spritePos(400,720-520-button_restart.getHeight());

        button_resume.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                mainClass.setGameScreen();
                return true;
            }
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                button_resume.setTexture(new Texture("Button_click_resumegame.png"));
            }
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor){
                button_resume.setTexture(new Texture("Button_resumegame.png"));
            }

        });

        button_restart.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                mainClass.setNewGameScreen();
                return true;
            }
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                button_restart.setTexture(new Texture("Button_click_restartgame.png"));
            }
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor){
                button_restart.setTexture(new Texture("Button_restartgame.png"));
            }
        });
        stage.addActor(background);
        stage.addActor(label_menu);
        stage.addActor(button_restart);
        stage.addActor(button_resume);

        stage.setKeyboardFocus(button_resume);
        stage.setKeyboardFocus(button_restart);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage); // kieu nhu no add input vao thang render. -- call before render each frame.
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
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
