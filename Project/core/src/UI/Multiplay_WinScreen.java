package UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Multiplay_WinScreen implements Screen{
    private SpriteBatch batch;

    private ImageButton RetryButton;
    private ImageButton ExitButton;
    private ImageButton MainMenuButton;
    private Skin skin;
    private MainClass mainClass;
    private Table table;

    private Stage stage;

    private float tableY = Gdx.graphics.getHeight()/2;

    private Sound sound = Gdx.audio.newSound(Gdx.files.internal("Audio/GiornoTheme.mp3"));

    private BitmapFont scoreFont;
    private Label label;
    private Skin textSkin;

    Animation<TextureRegion> animation;
    float elapsed;

    public Multiplay_WinScreen(final MainClass mainClass) {
        this.mainClass = mainClass;

        animation = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("Gif/DancingEngi.gif").read());

        batch = new SpriteBatch();

        scoreFont = new BitmapFont(Gdx.files.internal("skin/minecraft.fnt"));

        skin = new Skin(Gdx.files.internal("skin/ButtonPack.json"));

        stage = new Stage(new ScreenViewport());

        textSkin = new Skin(Gdx.files.internal("skin/testingfont.json"));
        label = new Label("VICTORY", textSkin);

        label.setAlignment(Align.center | Align.top);
        label.setColor(Color.GOLD);
        label.setFontScale(3f);

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
                        mainClass.setMultiplayerScreen();
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

        table.add(label).size(500,100).pad(30);
        table.row();
        table.add(RetryButton).pad(30);
        table.row();
        table.add(MainMenuButton).pad(30);
        table.row();
        table.add(ExitButton).pad(30);

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
        playSong();
        Gdx.input.setInputProcessor(stage); // kieu nhu no add input vao thang render. -- call before render each frame.
    }

    public void render(float delta) {
        elapsed += Gdx.graphics.getDeltaTime(); //Get time frame

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(animation.getKeyFrame(elapsed), 20,20); //Draw the gif
        batch.end();

        stage.act();
        stage.draw();
    }

    public void resize(int width, int height) {
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true); // this line is important. update size of stage = current screen size.
        table.setWidth(stage.getWidth());
        table.align(Align.center|Align.top);
        table.setPosition(0, tableY +table.getMinHeight()/2);
    }

    public void pause() {

    }

    public void resume() {

    }

    public void hide() {

    }


    public void dispose() {
        stage.dispose();
        batch.dispose();
        skin.dispose();
        scoreFont.dispose();
    }
}
