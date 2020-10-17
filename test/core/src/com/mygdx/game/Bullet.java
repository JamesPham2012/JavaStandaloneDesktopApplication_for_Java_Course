package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bullet extends MyObject {
    MyObject Obj;
    public Bullet(MyObject Obj){
        exist = true;
        this.Obj = Obj;
        xObject=Obj.getX();
        yObject=Obj.getY();
        System.out.println(xObject);
    }

    @Override
    public void updateDraw() {
        xDraw= (int)(xObject- (widthObject/2)) ;
        yDraw= (int)(yObject+ (Obj.heightObject/2) - (heightObject));
    }

    public void create(){
        batch = new SpriteBatch();
        texture = new Texture("Bullet.png");
        updateDraw();
    }

    public void render_bullet () { // loop
        render();
        yObject++;
        updateDraw();
    }



}
