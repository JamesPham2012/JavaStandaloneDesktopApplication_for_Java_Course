package com.mygdx.giaodien;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class testWindow extends ApplicationAdapter{
    Skin skin;
    Dialog d1;
    Stage stage;
    public void create(){
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        d1 = new Dialog("Click Message",skin);
        d1.text("HelloWorld");
        stage = new Stage(new ScreenViewport());
    }
    @Override
    public void render() {

        stage.draw();
    }
}
