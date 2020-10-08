package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Enemy extends GameObj {
    /*protected int x;
    protected int y;
    protected int cali_x;
    protected int cali_y;
    public int S_width;
    public int S_height;
    Texture art;*/
    SpriteBatch batch;
    long timer1=0l;
    public int Loaded=1;
    public  Enemy(int x,int y){
        this.x=x;
        this.y=y;
    }
    public void create(){
        batch = new SpriteBatch();
        art =new Texture("chow.png");//169X169
    }

    public void render_enemy(){
        batch.begin();
        batch.draw(art, x,y);
        batch.end();

    }

    public boolean Autoshoot(){  //reload time 0.5 sec
        if (System.currentTimeMillis()-timer1>1000){
            timer1=System.currentTimeMillis();
            Loaded=1;
        }else {Loaded=0;}

        if (Loaded==1){
            return true;
        }
        else return false;
    }


}
