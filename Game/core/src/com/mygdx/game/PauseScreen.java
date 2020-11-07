package UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Background;

public class PauseScreen implements Screen {
    private SpriteBatch batch;
    private static Texture PauseTitle;

    private ImageButton ResumeButton;
    private ImageButton ExitButton;
    private Skin skin;
    private MainClass mainClass;
    private Table table;
    private Background background;

    BitmapFont font;
    GlyphLayout layout;

    private Stage stage;


    public PauseScreen(final MainClass mainClass) {
        this.mainClass = mainClass;
        batch = new SpriteBatch();
        PauseTitle = new Texture("skin/Pause.png");

        background = new Background();
        background.create();
        background.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        skin = new Skin(Gdx.files.internal("skin/ButtonPack.json"));
        font = new BitmapFont(Gdx.files.internal("skin/minecraft.fnt"));

        layout = new GlyphLayout();
        layout.setText(font, "PAUSE");

        stage = new Stage(new ScreenViewport());

        table = new Table();
        table.setWidth(stage.getWidth());
        table.align(Align.center | Align.top);
        table.setPosition(0, Gdx.graphics.getHeight() / 2);

        ResumeButton = new ImageButton(skin, "Resume");
        ResumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("Resume Game", "BEEP");
                mainClass.setNewGameScreen();
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
        table.add(ResumeButton).pad(30);
        table.row();
        table.add(ExitButton).pad(30);

        stage.addActor(table);

    }

    public void show() {
        stage.addAction(Actions.fadeIn(1));
        Gdx.input.setInputProcessor(stage); // kieu nhu no add input vao thang render. -- call before render each frame.
    }

    public void render(float delta) {
        background.render();

        stage.act();
        stage.draw();

        batch.begin();
        batch.draw(PauseTitle, Gdx.graphics.getWidth()/2 - PauseTitle.getWidth()/2, Gdx.graphics.getHeight()/2);
        batch.end();
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


    public void dispose() {
        stage.dispose();
        batch.dispose();
        skin.dispose();
    }
}
