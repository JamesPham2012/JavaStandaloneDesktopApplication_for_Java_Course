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
     static Texture art1;
     static Texture art2;

    private boolean Source; //Source is true for Player and false for Enemy
    private int Type;   // Type control 3 way bullets move, default is 1

    public void modelGenerator(){
        art1 = new Texture("AAM.png");
        art2 = new Texture("AAMR.png");
    }

    public Bullet(int x_in, int y_in, boolean Sauce){
        x=x_in;
        y=y_in;
        Source=Sauce;
    }
    public Bullet(int x_in, int y_in, boolean Sauce,boolean State){
        x=x_in;
        y=y_in;
        Source=Sauce;
        this.State=State;
    }


    public void render_bullet(Texture art1,Texture art2,SpriteBatch batch){
        if (State) {

            if((firstRenderFlag==true)&&(Source)) {
                cali_x= x - art1.getWidth()/2;
                cali_y= S_height-(y +art1.getHeight()/2);
                firstRenderFlag=false;
            }
            if (Source) batch.draw(art1, cali_x,cali_y);

            if((firstRenderFlag==true)&&(!Source)) {
                cali_x= x - art2.getWidth()/2;
                cali_y= S_height-(y +art2.getHeight()/2);
                firstRenderFlag=false;
            }
            if (!Source) batch.draw(art2, cali_x,cali_y);

            if (Source){
                cali_y+=5;
                if (cali_y>S_height-art1.getHeight()){
                    State=false;
                }
            }
            else if(!Source){
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



    public boolean Revive(int x,int y, boolean sauce){
        this.x=x;
        this.y=y;
        this.Source=sauce;
        this.State=true;
        this.firstRenderFlag=true;
        return true;
    }
}