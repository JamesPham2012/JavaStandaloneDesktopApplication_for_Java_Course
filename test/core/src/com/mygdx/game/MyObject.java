package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyObject {
    protected boolean exist;
    protected int xObject;
    protected int yObject;

    protected int xDraw;
    protected int yDraw;

    protected Texture texture;
    protected SpriteBatch batch;

    protected float scaleObject = 0.4f;
    protected float widthObject;
    protected float heightObject;


    public void setScale(float scaleObject){
        this.scaleObject = scaleObject;
    }

    protected void setX(int offset){ xObject+=offset; }
    protected void setY(int offset){ yObject+=offset;  yDraw+=offset; }

    public int getX(){
        return xObject;
    }
    public int getY(){
        return yObject;
    }

    public float getWidth(){
        return widthObject;
    }
    public float getHeight(){
        return heightObject;
    }

    public void existFalse(){
        exist = false;
    }

    public boolean getExist(){
        return exist;
    }

    public boolean RectangleCollision(MyObject Obj){
        if(!Obj.exist){ return false; }

        // compare x_draw , x_draw + size.
        int xEndDraw = (int) (xDraw + widthObject);
        int yEndDraw = (int) (yDraw + heightObject);

        int xObjectEndDraw = (int) (Obj.xDraw + Obj.widthObject);
        int yObjectEndDraw = (int) (Obj.yDraw + Obj.heightObject);

        if(xDraw<=xObjectEndDraw && Obj.xDraw<=xEndDraw){
            if(yDraw<=yObjectEndDraw && Obj.yDraw<=yEndDraw){
                return true;
            }
        }
        return false;
    }

    public void render(){
        batch.begin();
        if(exist){
            batch.draw(texture, xDraw,yDraw,texture.getWidth()*scaleObject,texture.getHeight()*scaleObject);
        }
        batch.end();
    }

    public void dispose(){
        batch.dispose();
        texture.dispose();
    }
}
