package UI;

import com.badlogic.gdx.*;
import game.demo.Multiplayer.MultiplayerGame;
import game.demo.MyGdxGame;
import com.badlogic.gdx.audio.Music;
import UI.PlayerData;

public class MainClass extends Game implements ApplicationListener {
    private Music BGMMM;
    private Music BGMIG;
    private MyGdxGame gameScreen;
    private MainMenu mainMenu;
    private PauseScreen pauseScreen;
    private GameOverScreen gameOver;
    private ScoreBoardScreen scoreBoardScreen;
    private MultiplayerGame multiplayerScreen;
    private Multiplay_LostScreen lostScreen;
    private Multiplay_WinScreen winScreen;
    private SinglePlayer singlePlayerScreen;
    private Multiplayer multiplayerUIScreen;
    public int score;
    public String name;

    @Override
    public void create() {
        BGMMM = Gdx.audio.newMusic(Gdx.files.internal("Audio/BGM1.wav"));
        BGMIG = Gdx.audio.newMusic(Gdx.files.internal("Audio/BGMIG.mp3"));
        BGMIG.setVolume(0.1f);
        BGMMM.setVolume(0.25f);
        gameScreen=new MyGdxGame(this);

        mainMenu = new MainMenu(this);
        pauseScreen = new PauseScreen(this);
        gameOver = new GameOverScreen(this);
        multiplayerScreen = new MultiplayerGame(this);
        winScreen = new Multiplay_WinScreen(this);
        lostScreen = new Multiplay_LostScreen(this);
        singlePlayerScreen = new SinglePlayer(this);
        multiplayerUIScreen = new Multiplayer(this);

        setMenuScreen();
    }
    public void setMultiplayerUIScreen(){
        setScreen(multiplayerUIScreen);
    }
    public void setSinglePlayerScreen(){
        setScreen(singlePlayerScreen);
    }
    public void setNewGameScreen(){
        gameScreen=new MyGdxGame(this);
        setScreen(gameScreen);
        BGMMM.stop();
        BGMIG.play();
    }

    public void setMenuScreen()
    {
        setScreen(mainMenu);
        gameScreen.pause();
        BGMMM.play();
        BGMIG.stop();

    }
    public void setMultiplayerScreen(){
        multiplayerScreen = new MultiplayerGame(this);
        setScreen(multiplayerScreen);
        BGMMM.stop();
        BGMIG.play();
    }
    public void setPauseScreen()
    {
        setScreen(pauseScreen);
        gameScreen.pause();
        BGMMM.play();
        BGMIG.stop();

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
    public void setWinScreen(){
        setScreen(winScreen);
        gameScreen.pause();
    }
    public void setLostScreen(){

        setScreen(lostScreen);
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