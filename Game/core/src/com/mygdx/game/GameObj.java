package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class GameObj {
    protected int x;
    protected int y;
    protected int cali_x;
    protected int cali_y;
    public int S_width;
    public int S_height;
    boolean firstRenderFlag=true;
    public boolean State=true;// State is alive or dead, default = 1
    Texture art;

    public void setParam(){
        this.S_width=Gdx.graphics.getWidth();
        this.S_height=Gdx.graphics.getHeight();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public void dispose(){

        art.dispose();

    }

}


