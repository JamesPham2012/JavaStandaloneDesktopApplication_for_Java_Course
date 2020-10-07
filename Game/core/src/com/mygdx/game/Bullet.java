package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/*
public class Bullet {


    private int Source; //Source is 1 for Player and -1 for Enemy
    private int Type;   // Type control 3 way bullets move, default is 1
    Texture AAM;
    SpriteBatch batch;
    private int State=1; // State is alive or dead, default = 1


    public Bullet(int x_in, int y_in, int Sauce, int InType){
        x=x_in;
        y=y_in;
        Source=Sauce;
        Type=InType;
    }

    public Bullet(int x_in, int y_in, int Sauce){
        x=x_in;
        y=y_in;
        Source=Sauce;
        Type=1;
    }

    public void create(){

        batch = new SpriteBatch();
        AAM = new Texture("AAM.png");
    }

    public void render_bullet(){// should depends on Bullet.Type

        batch.begin();
        batch.draw(AAM, x,720-y);
        batch.end();


    }

    public void dispose () {
        batch.dispose();
        AAM.dispose();
    }
}
*/

public class Bullet extends GameObj{
    SpriteBatch batch;
    boolean firstRenderFlag=true;
    private int Source; //Source is 1 for Player and -1 for Enemy
    private int Type;   // Type control 3 way bullets move, default is 1
    private int State=1; // State is alive or dead, default = 1
    public Bullet(int x_in, int y_in, int Sauce, int InType){
        x=x_in;
        y=y_in;
        Source=Sauce;
        Type=InType;
    }

    public Bullet(int x_in, int y_in, int Sauce){
        x=x_in;
        y=y_in;
        Source=Sauce;
        Type=1;
    }

    public void create(){

        batch = new SpriteBatch();
        art = new Texture("AAM.png");
    }

    public void render_bullet(){

        batch.begin();
        if(firstRenderFlag==true){
            cali_x= x -art.getWidth()/2;
            cali_y= S_height-(y +art.getHeight()/2);
            firstRenderFlag=false;
        }
        batch.draw(art, cali_x,cali_y);
        batch.end();
        cali_y+=5;
    }

    public void dispose () {
        batch.dispose();
        art.dispose();
    }
}