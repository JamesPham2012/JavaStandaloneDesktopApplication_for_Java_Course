package UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Background;

public class GameModesMenu implements Screen {
    private SpriteBatch batch;
    private ImageButton SingleplayerButton;
    private ImageButton MultiplayerButton;
    private ImageButton BackButton;

    private Skin skin;
    private Skin skin2;

    private Image image;

    private MainClass mainClass;

    private Table table;
    private Background background;

    private Stage stage;

    public GameModesMenu(final MainClass mainClass){
        this.mainClass = mainClass;
        batch = new SpriteBatch();

        image = new Image(new Texture(Gdx.files.internal("skin/GameTitle.png")));

        background = new Background();
        background.create();
        background.resize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        stage = new Stage(new ScreenViewport());

        skin = new Skin(Gdx.files.internal("skin/ButtonPack.json"));
        skin2 = new Skin(Gdx.files.internal("skin/Textfield.json"));

        // table will be affected by size of stage.
        table = new Table();
        table.setWidth(stage.getWidth());
        table.align(Align.center|Align.top);


        SingleplayerButton = new ImageButton(skin, "Singleplayer");
        SingleplayerButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainClass.setNewGameScreen();
            }
        });

        MultiplayerButton = new ImageButton(skin, "Multiplayer");
        MultiplayerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("COMING SOON");
            }
        });

        BackButton = new ImageButton(skin, "Back");
        BackButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("Exit Game", "BEEP");
                Gdx.app.exit();
            }
        });

        table.pad(30);
        table.add(SingleplayerButton).pad(30).align(Align.left);
        table.row();
        table.add(MultiplayerButton).pad(30).align(Align.left);
        table.row();
        table.add(BackButton).pad(30).align(Align.left);
        table.setPosition(0, Gdx.graphics.getHeight()/2+table.getMinHeight()/2);

        stage.addActor(table);
    }

    public void show() {
        stage.addAction(Actions.fadeIn(1));
        Gdx.input.setInputProcessor(stage); // kieu nhu no add input vao thang render. -- call before render each frame.
    }

    public void render (float delta) {
        background.render();

        stage.act();
        stage.draw();
    }


    public void resize(int width, int height) {
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true); // this line is important. update size of stage = current screen size.
        table.setWidth(stage.getWidth());
        table.align(Align.center|Align.top);
        table.setPosition(0, Gdx.graphics.getHeight()/2+table.getMinHeight()/2);
    }

    public void pause() {
        SingleplayerButton.setDisabled(true);
    }

    public void resume() {

    }

    public void hide() {
        Gdx.input.setInputProcessor(null);
    }


    public void dispose() {
        stage.dispose();
        batch.dispose();
        skin.dispose();
    }
}
