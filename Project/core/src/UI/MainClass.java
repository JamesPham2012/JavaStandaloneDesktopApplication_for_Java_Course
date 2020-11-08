package UI;

import com.badlogic.gdx.*;
import game.demo.Multiplayer.MultiplayerGame;
import game.demo.MyGdxGame;
import UI.PlayerData;

public class MainClass extends Game implements ApplicationListener {

    private MyGdxGame gameScreen;
    private MainMenu mainMenu;
    private PauseScreen pauseScreen;
    private GameOverScreen gameOver;
    private ScoreBoardScreen scoreBoardScreen;
    private MultiplayerGame multiplayerScreen;
    public int score;
    public String name;

    @Override
    public void create() {
        gameScreen=new MyGdxGame(this);

        mainMenu = new MainMenu(this);
        pauseScreen = new PauseScreen(this);
        gameOver = new GameOverScreen(this);
        multiplayerScreen = new MultiplayerGame(this);

        setMenuScreen();
    }

    public void setNewGameScreen(){
        gameScreen=new MyGdxGame(this);
        setScreen(gameScreen);
    }

    public void setMenuScreen()
    {
        setScreen(mainMenu);
        gameScreen.pause();

    }
    public void setMultiplayerScreen(){
        setScreen(multiplayerScreen);
    }
    public void setPauseScreen()
    {
        setScreen(pauseScreen);
        gameScreen.pause();
    }
    public void setGameOverScreen()
    {
        score = gameOver.getScore();
        setScreen(gameOver);
        gameScreen.pause();
    }
    public void setScoreBoardScreen()
    {
        scoreBoardScreen = new ScoreBoardScreen(new PlayerData(name, score),this);
        setScreen(scoreBoardScreen);
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