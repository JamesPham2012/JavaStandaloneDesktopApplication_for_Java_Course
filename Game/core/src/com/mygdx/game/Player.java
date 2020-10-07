package com.mygdx.game;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
/*public class Player {

START----------------------------TRANSFERED TO SUPERCLASS GAMEOBJ-------------------------------------------------------
    protected int x;
    protected int y;
    protected int cali_x;
    protected int cali_y;
    public int S_width;
    public int S_height;
    //need normalization for cursor to get to middle of the img
    protected Texture noodle;
    SpriteBatch batch;
    public void setParam(int width, int height){
        this.S_width=width;
        this.S_height=height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
END----------------------------------TRANSFERED TO SUPERCLASS GAMEOBJ---------------------------------------------------
    public void create(){
        batch = new SpriteBatch();
        noodle =new Texture("logo_noodle-02.png");//169X169
    }

    public void input(){
        x=Gdx.input.getX();
        y=Gdx.input.getY();
        cali_x= x -noodle.getWidth()/2;
        cali_y= S_height-(y +noodle.getHeight()/2);
    }


    public boolean Shoot(){
        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
            return true;
        }
        else{
            return false;
        }
    }


    public void render_player(){

        batch.begin();
        batch.draw(noodle, cali_x,cali_y);
        batch.end();
        input();
    }


}*/
public class Player extends GameObj {

    SpriteBatch batch;

    public void create(){
        batch = new SpriteBatch();
        art =new Texture("logo_noodle-02.png");//169X169
    }





    public boolean Shoot(){
        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
            return true;
        }
        else{
            return false;
        }
    }


    public void render_player(){

        batch.begin();

        batch.draw(art, cali_x,cali_y);
        batch.end();
        input();


    }


    public void input(){
        x=Gdx.input.getX();
        y=Gdx.input.getY();
        cali_x= x -art.getWidth()/2;
        cali_y= S_height-(y +art.getHeight()/2);}
}