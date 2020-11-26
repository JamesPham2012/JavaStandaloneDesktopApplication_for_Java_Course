package UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import game.demo.Background;
import game.demo.Player;
import UI.PlayerData;

public class MainMenu implements Screen {
    private SpriteBatch batch;
    private ImageButton PlayButton;
    private ImageButton ExitButton;
    private ImageButton MultiplayerButton;
    private ImageButton ScoreBoardButton;
    private Skin skin;
    private Skin skin2;

    private Image image;

    private MainClass mainClass;

    private Table table;
    private Background background;
    private Label label;

    BitmapFont font;

    TextField textfield;
    float widthTextField;
    float xTextField;

    FileHandle file;
    public String PlayerName;

    private Stage stage;

    public MainMenu(final MainClass mainClass){
        this.mainClass = mainClass;
        batch = new SpriteBatch();

//        playerData = new PlayerData();

        font = new BitmapFont(Gdx.files.internal("skin/minecraft.fnt"));

        background = new Background();
        background.create();
        background.resize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        stage = new Stage(new ScreenViewport());

        skin = new Skin(Gdx.files.internal("skin/ButtonPack.json"));
        skin2 = new Skin(Gdx.files.internal("skin/Textfield.json"));

        label = new Label("SPACESHOOTER",skin2);
        label.setFontScale(2f);


        // table will be affected by size of stage.
        table = new Table();
        table.setWidth(stage.getWidth());
        table.align(Align.center|Align.top);


        PlayButton = new ImageButton(skin, "Singleplayer");
        PlayButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainClass.setSinglePlayerScreen();
            }
        });

        MultiplayerButton = new ImageButton(skin, "Multiplayer");
        MultiplayerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainClass.setMultiplayerUIScreen();
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
        table.add(label);
        table.row();
        table.add(PlayButton).pad(30);
        table.row();
        table.add(MultiplayerButton).pad(30);
        table.row();
        table.add(ExitButton).pad(30);
        table.setPosition(0, Gdx.graphics.getHeight()/2+table.getMinHeight()/2);


        stage.addActor(table);
    }

    @Override
    public void show() {
        stage.addAction(Actions.fadeIn(1));

        Gdx.input.setInputProcessor(stage); // kieu nhu no add input vao thang render. -- call before render each frame.
    }

    public void render (float delta) {
        background.render();

        stage.act();
        stage.draw();
    }



    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true); // this line is important. update size of stage = current screen size.
        table.setWidth(stage.getWidth());
        table.align(Align.center|Align.top);
        table.setPosition(0, Gdx.graphics.getHeight()/2+table.getMinHeight()/2);
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
