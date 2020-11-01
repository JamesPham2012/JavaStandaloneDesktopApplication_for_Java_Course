package UI;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import UI.MainClass;
import game.demo.Background;

public class MainMenu implements Screen {
    private SpriteBatch batch;
    private ImageButton PlayButton;
    private ImageButton ExitButton;
    private Label multiplayer;
    private Skin skin;
    private MainClass mainClass;
    private Table table;
    private Background background;

    private Image title;

    private Stage stage;

    float tableY = Gdx.graphics.getHeight()/2 - 100;

    public MainMenu(final MainClass mainClass){
        this.mainClass = mainClass;

        batch = new SpriteBatch();

        title = new Image(new Texture("skin/GameTitle.png"));

        background = new Background();
        background.create();
        background.resize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        skin = new Skin(Gdx.files.internal("skin/ButtonPack.json"));

        stage = new Stage(new ScreenViewport());
        multiplayer = new Label("MultiplayerGame",new Skin(Gdx.files.internal("skin/Textfield.json")));

        table = new Table();
        table.setWidth(stage.getWidth());
        table.align(Align.center|Align.top);
        table.setPosition(0, tableY);

        PlayButton = new ImageButton(skin, "Play");
        PlayButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.addAction(Actions.sequence(Actions.fadeOut(1), Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        mainClass.setNewGameScreen();
                    }
                })));
            }
        });
        multiplayer.addListener(new ClickListener(){
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
        ExitButton = new ImageButton(skin, "Exit");
        ExitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        table.pad(30);
        table.add(title).size(700,60);
        table.row();
        table.add(PlayButton);
        table.row();
        table.add(ExitButton);
        table.row();
        table.add(multiplayer);

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
    }
}
