package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player extends MyObject{
    public void create(){
        exist = true;
        batch = new SpriteBatch();
        texture = new Texture("Spacefighter.png");
        widthObject = texture.getWidth()*scaleObject;
        heightObject = texture.getHeight()*scaleObject;
    }
    public void update(){
        xObject= (Gdx.input.getX()) ;
        yObject= (720 - Gdx.input.getY());

        updateDraw();
    }

    public boolean fire(){
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            return true;
        }
        else{
            return false;
        }
    }
    public void render_player () { // loop
        render();
        update();
    }

}
