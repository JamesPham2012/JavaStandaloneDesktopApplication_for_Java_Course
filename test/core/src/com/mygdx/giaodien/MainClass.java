package com.mygdx.giaodien;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.game.MyGdxGame;

public class MainClass extends Game implements ApplicationListener {

    private MyGdxGame gameScreen;
    private MenuClass menuScreen;
    private HudClass hudScreen;
    @Override
    public void create() {
        gameScreen=new MyGdxGame(this);
        menuScreen=new MenuClass(this);


        setMenuScreen();

    }
    public void setGameScreen()
    {

        setScreen(gameScreen);

    }
    public void setNewGameScreen(){
        gameScreen=new MyGdxGame(this);
        setScreen(gameScreen);
    }
    public void setMenuScreen()
    {

        setScreen(menuScreen);
        gameScreen.pause();
    }




    @Override
    public void dispose() {

        super.dispose();
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void resize(int width, int height) {

        super.resize(width, height);
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }
}