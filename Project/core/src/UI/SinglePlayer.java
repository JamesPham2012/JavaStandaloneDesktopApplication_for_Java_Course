package UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import game.demo.Background;

public class SinglePlayer implements Screen {
    MainClass mainClass;
    Stage stage;
    private SpriteBatch batch;
    private ImageButton PlayButton;
    private ImageButton ScoreBoardButton;
    private ImageButton backButton;
    private Table table;
    private Background background;
    TextField textfield;
    float widthTextField;
    float xTextField;
    private Skin skin2;
    private Label label;
    private Skin skin;
    public String PlayerName;
    FileHandle file;


    public SinglePlayer(final MainClass mainClass){
        this.mainClass = mainClass;
        background = new Background();
        background.create();
        background.resize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        stage = new Stage(new ScreenViewport());
        skin2 = new Skin(Gdx.files.internal("skin/Textfield.json"));
        skin = new Skin(Gdx.files.internal("skin/ButtonPack.json"));

        textfield = new TextField("Input your name here", skin2);

        label = new Label("SPACESHOOTER",skin2);
        label.setFontScale(2f);
        resizeTextField();

        table = new Table();
        table.setWidth(stage.getWidth());
        table.align(Align.center|Align.top);

        file = Gdx.files.local("Scores.json");
        backButton = new ImageButton(skin, "Back");

        backButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainClass.setMenuScreen();
            }
        });

        PlayButton = new ImageButton(skin, "Play");
        PlayButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                PlayerName = textfield.getText();
                mainClass.setNewGameScreen();
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
        table.add(ScoreBoardButton).pad(30);
        table.row();
        table.add(backButton).pad(30);
        table.row();


        stage.addActor(table);
    }
    @Override
    public void show() {
        textfield.setText("Input your name here: ");
        stage.addAction(Actions.fadeIn(1));
        Gdx.input.setInputProcessor(stage); // kieu nhu no add input vao thang render. -- call before render each frame.
    }

    public void render (float delta) {
        background.render();

        stage.act();
        stage.draw();

        resizeTextField();
    }


    public String getName()
    {
        return PlayerName;
    }
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true); // this line is important. update size of stage = current screen size.
        table.setWidth(stage.getWidth());
        table.align(Align.center|Align.top);
        table.setPosition(0, Gdx.graphics.getHeight()/2+table.getMinHeight()/2);
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
        skin2.dispose();
    }

}
