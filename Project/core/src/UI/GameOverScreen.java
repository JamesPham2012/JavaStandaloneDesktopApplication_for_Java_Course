package UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameOverScreen implements Screen {
    private SpriteBatch batch;
    private Texture GameOverTitle;
    private Image title;

    private ImageButton RetryButton;
    private ImageButton ExitButton;
    private ImageButton MainMenuButton;
    private Skin skin;
    private MainClass mainClass;
    private Table table;

    private Stage stage;

    float tableY = Gdx.graphics.getHeight()/2;

    public static int score, highscore;
    GlyphLayout scorelayout, highscorelayout;

    Animation<TextureRegion> animation;
    float elapsed;

    BitmapFont scoreFont;

    Sound sound = Gdx.audio.newSound(Gdx.files.internal("Audio/Astronomia.mp3"));;

    public GameOverScreen(final MainClass mainClass) {
        this.mainClass = mainClass;
        batch = new SpriteBatch();
        GameOverTitle = new Texture("skin/GAMEOVER.png");
        title = new Image(GameOverTitle);

        animation = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("coffindance.gif").read());

        Preferences preferences = Gdx.app.getPreferences("GameScore"); //Create file to store score
        highscore = preferences.getInteger("highscore", score);

        if(score > highscore)
        {
            preferences.putInteger("highscore", score);
            preferences.flush(); //Flush will save file
        }

        scoreFont = new BitmapFont(Gdx.files.internal("skin/minecraft.fnt"));

        skin = new Skin(Gdx.files.internal("skin/ButtonPack.json"));

        stage = new Stage(new ScreenViewport());

        CreateTable(stage);
    }

    public void CreateTable(final Stage stage)
    {
        table = new Table();
        table.setWidth(stage.getWidth());
        table.align(Align.center | Align.top);
        table.setPosition(0, tableY);

        RetryButton = new ImageButton(skin, "Retry");
        RetryButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.addAction(Actions.sequence(Actions.fadeOut(1), Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        mainClass.setNewGameScreen();
                        sound.stop();
                    }
                })));
            }
        });

        MainMenuButton = new ImageButton(skin, "MainMenu");
        MainMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.addAction(Actions.sequence(Actions.fadeOut(1), Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        mainClass.setMenuScreen();
                        sound.stop();
                    }
                })));
            }
        });

        ExitButton = new ImageButton(skin, "Exit");
        ExitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("Exit Game", "BEEP");
                Gdx.app.exit();
            }
        });

        table.pad(30);
        table.add(title).size(500,100);
        table.row();
        table.add(RetryButton);
        table.row();
        table.add(MainMenuButton);
        table.row();
        table.add(ExitButton);

        stage.addActor(table);
    }

    private void playSong()
    {
        long id = sound.play(1f);
        sound.setPitch(id, 1f);
        sound.setVolume(id, 0.5f);
        sound.setLooping(id,true);
    }

    public void show() {
        stage.addAction(Actions.fadeIn(1));
        Gdx.input.setInputProcessor(stage); // kieu nhu no add input vao thang render. -- call before render each frame.
        playSong();
    }

    public void render(float delta) {
        elapsed += Gdx.graphics.getDeltaTime(); //Get time frame

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(animation.getKeyFrame(elapsed), 20,20); //Draw the gif

        //Score text
        scorelayout = new GlyphLayout(scoreFont, "Score: \n" + score, Color.WHITE, 0, Align.left, false);
        highscorelayout = new GlyphLayout(scoreFont, "High Score: \n" + highscore, Color.WHITE, 0, Align.left, false);

        scoreFont.draw(batch, scorelayout, 20, tableY + title.getHeight()*2);
        scoreFont.draw(batch, highscorelayout, 20, tableY + title.getHeight());

        batch.end();

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true); // this line is important. update size of stage = current screen size.
        table.setWidth(stage.getWidth());
        table.align(Align.center|Align.top);
        table.setPosition(0, tableY +table.getMinHeight()/2);
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


    public void dispose() {
        stage.dispose();
        batch.dispose();
        skin.dispose();
        scoreFont.dispose();
        sound.dispose();
    }
}