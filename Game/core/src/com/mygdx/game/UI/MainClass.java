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
    private Multiplay_WinScreen winScreen;
    private Multiplay_LostScreen lostScreen;
    private GameModesMenu gameModesMenu;

    public int score;
    public String name;

    @Override
    public void create() {
        gameScreen=new MyGdxGame(this);

        mainMenu = new MainMenu(this);
        pauseScreen = new PauseScreen(this);
        gameOver = new GameOverScreen(this);
        gameModesMenu = new GameModesMenu(this);

        setMenuScreen();
    }

    public void setGameModeScreen()
    {
        setScreen(gameModesMenu);
        gameScreen.pause();;
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
        scoreBoardScreen = new ScoreBoardScreen(new PlayerData(name, score), this);
        setScreen(scoreBoardScreen);
        gameScreen.pause();
    }
    public void setWinScreen()
    {
        winScreen = new Multiplay_WinScreen(this);
        setScreen(winScreen);
    }
    public void setLostScreen()
    {
        lostScreen = new Multiplay_LostScreen(this);
        setScreen(lostScreen);
    }


    @Override
    public void dispose() {
        super.dispose();
        gameScreen.dispose();
        pauseScreen.dispose();
        mainMenu.dispose();
        scoreBoardScreen.dispose();
        winScreen.dispose();
        gameOver.dispose();
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
