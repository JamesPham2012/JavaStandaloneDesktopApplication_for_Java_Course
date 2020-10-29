package game.demo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameObj {
    protected float x;
    protected float y;
    protected int cali_x;
    protected int cali_y;
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
    protected float scaleWidth;
    protected float scaleHeight;
    protected SpriteBatch batch;

    public void setParam(){
        this.S_width=Gdx.graphics.getWidth();
        this.S_height=Gdx.graphics.getHeight();

    }
    public void setScale(float scaleWidth, float scaleHeight){
        this.scaleWidth = scaleWidth;
        this.scaleHeight = scaleHeight;
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






