package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.Random;

public class GameObj {
    protected float x;
    protected float y;
    private Background bg=new Background();
    protected float cali_x=bg.getWORLD_WIDTH()/1280;
    protected float cali_y=bg.getWORLD_HEIGHT()/720;
    protected float x_b;
    protected float y_b;
    protected float x_move;
    protected float y_move;
    protected float X;
    protected float Y;
    protected double D;
    public int S_width;
    public int S_height;
    protected float scale = 0.3f;
    Texture art;
    protected int id; //negative for player, positive for enemy
    protected int moveId;
    protected boolean State;
    protected long value;
    protected float hitboxRadius;
    protected float speed;
    protected Random randPara= new Random();



    public void setParam(){
        this.S_width=Gdx.graphics.getWidth();
        this.S_height=Gdx.graphics.getHeight();
    }
    public void setScale(float scale){
        this.scale = scale;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public long getValue() { return value;}
    public void dispose(){

        art.dispose();

    }
}
