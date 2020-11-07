package UI;

import com.badlogic.gdx.*;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.PlayerData;

public class MainClass extends Game implements ApplicationListener {

    private MyGdxGame gameScreen;
    private MainMenu mainMenu;
    private PauseScreen pauseScreen;
    private GameOverScreen gameOver;
    private ScoreBoardScreen scoreBoardScreen;
    public int score;
    public String name;

    @Override
    public void create() {
        gameScreen=new MyGdxGame(this);

        mainMenu = new MainMenu(this);
        pauseScreen = new PauseScreen(this);
        gameOver = new GameOverScreen(this);

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
        scoreBoardScreen = new ScoreBoardScreen(new PlayerData(name, score));
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
