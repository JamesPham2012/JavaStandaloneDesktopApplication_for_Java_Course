package UI;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import UI.MainClass;
import game.demo.Background;

public class MainMenu implements Screen {
    private SpriteBatch batch;
    private ImageButton PlayButton;
    private ImageButton ExitButton;
    private Skin skin;
    private MainClass mainClass;
    private Table table;
    private Background background;

    BitmapFont font;
    GlyphLayout layout;

    private Stage stage;

    public MainMenu(final MainClass mainClass){
        this.mainClass = mainClass;
        batch = new SpriteBatch();

        background = new Background();
        background.create();
        background.resize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        skin = new Skin(Gdx.files.internal("skin/ButtonPack.json"));
        font = new BitmapFont(Gdx.files.internal("skin/minecraft.fnt"));

        layout = new GlyphLayout();
        layout.setText(font, "SPACESHOOTER");

        stage = new Stage(new ScreenViewport());

        table = new Table();
        table.setWidth(stage.getWidth());
        table.align(Align.center|Align.top);
        table.setPosition(0, Gdx.graphics.getHeight()/2);

        PlayButton = new ImageButton(skin, "Play");
        PlayButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("Start Game", "BEEP");
                mainClass.setNewGameScreen();
            }
        });

        ExitButton = new ImageButton(skin, "Exit");
        ExitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("Exit Game", "BEEP");
                Gdx.app.exit();
            }
        });

        table.pad(30);
        table.add(PlayButton);
        table.row();
        table.add(ExitButton);

        stage.addActor(table);
    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage); // kieu nhu no add input vao thang render. -- call before render each frame.
    }

    public void render (float delta) {
        background.render();
        float width = layout.width;
        float height = layout.height;

        stage.act();
        stage.draw();

        batch.begin();
        font.draw(batch, layout, Gdx.graphics.getWidth()/2 - width/2, Gdx.graphics.getHeight()/2 + height/2);
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