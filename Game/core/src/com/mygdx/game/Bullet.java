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
    public boolean State=true; // State is alive or dead, default = 1


    public Bullet(int x_in, int y_in, int Sauce){
        x=x_in;
        y=y_in;
        Source=Sauce;
        Type=1;
    }
    public Bullet(int x_in, int y_in, int Sauce,boolean State){
        x=x_in;
        y=y_in;
        Source=Sauce;
        State=State;
    }

    public void create(){
        batch = new SpriteBatch();
        if (Source==1){art = new Texture("AAM.png");}
        else art = new Texture("LSB.png");

    }

    public void render_bullet(){
        if (State) {
            batch.begin();
            if(firstRenderFlag==true){
                cali_x= x - art.getWidth()/2;
                cali_y= S_height-(y +art.getHeight()/2);
                firstRenderFlag=false;
            }
            batch.draw(art, cali_x,cali_y);
            batch.end();
            if (Source==1){
                cali_y+=5;
                if (cali_y>S_height-art.getHeight()){
                    State=false;
                }
            }
            else {
                cali_y-=5;
                if (cali_y<0){
                    State=false;
                }
            }

        }

    }

    public boolean isDed(){
        return !State;
    }



    public boolean Revive(int x,int y, int sauce){
        this.x=x;
        this.y=y;
        this.Source=sauce;
        this.State=true;
        this.firstRenderFlag=true;
        return true;
    }
}