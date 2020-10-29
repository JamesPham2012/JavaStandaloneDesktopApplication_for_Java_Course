package game.demo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static java.lang.Integer.max;

public class GameObj {
    protected float x;
    protected float y;
    private Background bg=new Background();
    protected float cali_x;
    protected float cali_y;
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
    protected float scaleWidth;
    protected float scaleHeight;
    protected float hitboxRadius;
    protected SpriteBatch batch;
    protected int Texture_Width=22;
    protected int Texture_Height=22;


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
    public double getSonarRange(){
        return max(this.Texture_Width/2,this.Texture_Height/2);
    }

    public void getCalX(){
        cali_x=x-Texture_Width/2;
    }
    public void getCalY(){
        cali_x=y-Texture_Height/2;
    }


    public double distanceto(GameObj another){
        return Math.sqrt(Math.pow(this.x+this.Texture_Width/2-another.x-another.Texture_Width/2,2)  + Math.pow(this.x+this.Texture_Height/2-another.x-another.Texture_Height/2,2));
    }
    public double distanceto2(GameObj another){
        return Math.sqrt(Math.pow(this.x-this.Texture_Width/2-another.x+another.Texture_Width/2,2)  + Math.pow(this.x-this.Texture_Height/2-another.x+another.Texture_Height/2,2));
    }



}






