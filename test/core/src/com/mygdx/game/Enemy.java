package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class Enemy extends MyObject{
    int previous = 1;
    int multiply;
    public void create(){
        exist = true;
        Random rand = new Random();
        xObject= rand.nextInt(600);
        yObject= rand.nextInt(480);

        batch = new SpriteBatch();
        texture = new Texture("Enemy.png");
        widthObject = texture.getWidth()*scaleObject;
        heightObject = texture.getHeight()*scaleObject;
    }
    public void update(){
        Random rand = new Random();
        int multiply = check_pos(previous);
        xObject+= rand.nextInt(2)-1 *multiply;
        yObject+= rand.nextInt(2)-1 *multiply;

        updateDraw();

        previous = multiply;
    }

    public int check_pos(int previous){

        if(xDraw<0){
            return -1;
        }
        else if(xDraw> 480){
            return 1;
        }
        else if(yDraw<0){
            return -1;
        }
        else if(yDraw> 600){
            return 1;
        }
        else{
            return previous;
        }
    }

    public boolean fire(){
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            return true;
        }
        else{
            return false;
        }
    }
    public void render_enemy () { // loop
        render();
        update();
    }

}

