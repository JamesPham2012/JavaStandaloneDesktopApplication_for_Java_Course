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
import com.mygdx.game.Background;
import com.mygdx.game.Player;
import com.mygdx.game.PlayerData;

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


    public MainMenu()
    {

    }

    public MainMenu(final MainClass mainClass){
        this.mainClass = mainClass;
        batch = new SpriteBatch();

//        playerData = new PlayerData();

        font = new BitmapFont(Gdx.files.internal("skin/minecraft.fnt"));

        image = new Image(new Texture(Gdx.files.internal("skin/GameTitle.png")));

        file = Gdx.files.local("Scores.json");

        background = new Background();
        background.create();
        background.resize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        stage = new Stage(new ScreenViewport());

        skin = new Skin(Gdx.files.internal("skin/ButtonPack.json"));
        skin2 = new Skin(Gdx.files.internal("skin/Textfield.json"));

        label = new Label("SPACESHOOTER",skin2);
        label.setFontScale(1.5f);

        textfield = new TextField("Input your name here:", skin2);

        resizeTextField();
        

        // table will be affected by size of stage.
        table = new Table();
        table.setWidth(stage.getWidth());
        table.align(Align.center|Align.top);


        PlayButton = new ImageButton(skin, "Play");
        PlayButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                PlayerName = textfield.getText();
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

        ScoreBoardButton = new ImageButton(skin, "ScoreBoard");
        ScoreBoardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.addAction(Actions.sequence(Actions.fadeOut(1), Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        mainClass.setScoreBoardScreen();
                    }
                })));
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

        textfield.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                Gdx.app.log("Text Field", "BEEP");
                textfield.setText("");
                resizeTextField();
            }
        });

        table.pad(30);
        table.add(label);
        table.row();
        table.add(textfield);
        table.row();
        table.add(PlayButton).pad(30);
        table.row();
        table.add(MultiplayerButton).pad(30);
        table.row();
        table.add(ScoreBoardButton).pad(30);
        table.row();
        table.add(ExitButton).pad(30);
        table.setPosition(0, Gdx.graphics.getHeight()/2+table.getMinHeight()/2);


        stage.addActor(table);
    }

    public void resizeTextField(){ // phan biet giua them space va giam bot spacce.
        widthTextField = textfield.getText().length()*20;

        xTextField = Gdx.graphics.getWidth()/2 - widthTextField/2;

        if(widthTextField==0){
            textfield.setX(xTextField);
            textfield.setSize(20,40);
        }
        else{
            textfield.setX(xTextField);
            textfield.setSize(widthTextField,40);
        }
    }

    public String getName()
    {
        return PlayerName;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage); // kieu nhu no add input vao thang render. -- call before render each frame.
    }

    public void render (float delta) {
        background.render();

        stage.act();
        stage.draw();

        resizeTextField();
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
        font.dispose();
    }
}
