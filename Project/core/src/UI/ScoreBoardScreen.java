package UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


public class ScoreBoardScreen implements Screen {
    SpriteBatch batch;

    MainClass mainClass;

    private BitmapFont font;

    private ImageButton backButton;
    private Skin skin;
    private Stage stage;

    int maxPlayer = 10;
    PlayerData[] list;
    PlayerData playerData;

    Json json;

    FileHandle file;

    public ScoreBoardScreen(PlayerData data,MainClass mainClass_c)
    {
        this.mainClass = mainClass_c;
        stage = new Stage(new ScreenViewport());

        batch = new SpriteBatch();
        json = new Json();
        font = new BitmapFont(Gdx.files.internal("skin/minecraft.fnt"));

        list = new PlayerData[maxPlayer];

        this.playerData = data;

        System.out.println(Gdx.files.internal("Scores.json").exists());

        if(Gdx.files.internal("Scores.json").exists())
        {
            file = Gdx.files.local("Scores.json");

            list = json.fromJson(PlayerData[].class, PlayerData.class, file.readString());

            System.out.println(json.prettyPrint(list));

            if(playerData != null)
            {
                for(int i = list.length-1; i >= 0; i--)
                {
                    if(playerData.score > list[i].score)
                        list[i] = playerData;
                    break;
                }
                list = sortBoard(list);
                String text = json.toJson(list);

                file = Gdx.files.local("Scores.json");
                file.writeString(text, false);
            }
            System.out.println(json.prettyPrint(list));
        }
        else
        {
            System.out.println("BEEP");
            for(int i = 0; i < maxPlayer; i++)
            {
                list[i] = new PlayerData("---", 10);
            }

            System.out.println(json.prettyPrint(list));
            String text = json.toJson(list);

            list = json.fromJson(PlayerData[].class, text);

            file = Gdx.files.local("Scores.json");
            file.writeString(text, false);
        }

        skin = new Skin(Gdx.files.internal("skin/ButtonPack.json"));
        backButton = new ImageButton(skin, "Back");

        backButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainClass.setMenuScreen();
            }
        });

        stage.addActor(backButton);
    }

    public PlayerData[] sortBoard(PlayerData[] players)
    {
        for(int i = 0; i < players.length; i++)
        {
            for(int j = players.length-1; j > i; j--)
            {
                if(players[i].score < players[j].score)
                {
                    PlayerData temp;
                    temp = players[i];
                    players[i] = players[j];
                    players[j] = temp;
                }
            }
        }

        return players;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        GlyphLayout layout;

        float width, height;
        String title = "HighScore";

        layout = new GlyphLayout();
        layout.setText(font, title);

        width = layout.width;
        height = layout.height;
        float DrawY = Gdx.graphics.getHeight() - 100;
        font.draw(batch, layout, (Gdx.graphics.getWidth() - width)/2, DrawY);

        for(int i = 0; i < maxPlayer; i++)
        {
            float w, h;
            title = String.format("%2d. %3s : %d", i+1, list[i].name, list[i].score);
            layout.setText(font, title);
            w = layout.width;
            h = layout.height;
            font.draw(batch, layout, (Gdx.graphics.getWidth() - w)/2, DrawY - height*2 - i * 2*h);
        }

        stage.act();
        stage.draw();

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

    public void dispose () {
        batch.dispose();
        font.dispose();
    }

}