package UI;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import game.demo.MyGdxGame;
import sun.rmi.rmic.Main;

public class MainClass extends Game implements ApplicationListener {

    private MyGdxGame gameScreen;
    private MainMenu mainMenu;
//    private HudClass hudScreen;
//    private StartClass startScreen;
    @Override
    public void create() {
        gameScreen=new MyGdxGame(this);
        //menuScreen=new MenuClass(this);
        mainMenu = new MainMenu(this);
//        startScreen=new StartClass(this);

        setMenuScreen();

    }
    public void setGameScreen()
    {

        setScreen(gameScreen);

    }
//    public void setStartScreen(){
//        setScreen(startScreen);
//    }
    public void setNewGameScreen(){
        gameScreen=new MyGdxGame(this);
        setScreen(gameScreen);
    }
    public void setMenuScreen()
    {

        setScreen(mainMenu);
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
