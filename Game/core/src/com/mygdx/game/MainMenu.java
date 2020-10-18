package com.mygdx.game;

import com.badlogic.gdx.Gdx;
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

public class MainMenu {
    private SpriteBatch batch;
    private ImageButton PlayButton;
    private ImageButton ExitButton;
    private Skin skin;

    private Table table;

    BitmapFont font;
    GlyphLayout layout;

    private Stage stage;

    public void create () {
        batch = new SpriteBatch();

        skin = new Skin(Gdx.files.internal("UI/ButtonPack.json"));
        font = new BitmapFont(Gdx.files.internal("UI/minecraft.fnt"));

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
            }
        });

        ExitButton = new ImageButton(skin, "Exit");
        ExitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("Exit Game", "BEEP");
            }
        });

        table.pad(30);
        table.add(PlayButton);
        table.row();
        table.add(ExitButton);

        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);
    }

    public void render () {

        float width = layout.width;
        float height = layout.height;

        stage.act();
        stage.draw();

        batch.begin();
        font.draw(batch, layout, Gdx.graphics.getWidth()/2 - width/2, Gdx.graphics.getHeight()/2 + height/2);
        batch.end();
    }



    public void resize(int width, int height) {

    }



    public void resume() {

    }


    public void hide() {

    }


    public void dispose() {
        stage.dispose();
        batch.dispose();
        skin.dispose();
    }
}
