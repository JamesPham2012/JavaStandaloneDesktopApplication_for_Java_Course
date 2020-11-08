package game.demo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.Random;
import java.util.Vector;

public class GameObj {
    public float x;
    public float y;
    private Background bg=new Background();
    protected float cali_x=bg.getWORLD_WIDTH()/1280;
    protected float cali_y=bg.getWORLD_HEIGHT()/720;
    public float x_b;
    public float y_b;
    public float x_move;
    public float y_move;
    protected float X;
    protected float Y;
    protected double D;
    public int S_width;
    public int S_height;
    protected float scale = 1f;
    Texture art;
    public int id; //negative for player, positive for enemy
    public int moveId;
    public boolean State;
    public long value;
    public float hitboxRadius;
    protected float speed;
    protected Random randPara= new Random();
    protected int Texture_Width=9;
    protected int Texture_Height=9;
    public Vector<PixelCoord> Pixel=new Vector<PixelCoord>();



    public void setParam(){
        this.S_width=Gdx.graphics.getWidth();
        this.S_height=Gdx.graphics.getHeight();
    }
    public void setScale(float scale){
        this.scale = scale;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
    public double getSonarRange(){

        return Math.max(this.Texture_Width/2,this.Texture_Height/2);
    }
    public double distanceto(GameObj another){
        return Math.sqrt(Math.pow(this.x+this.Texture_Width/2-another.x-another.Texture_Width/2,2)  + Math.pow(this.x+this.Texture_Height/2-another.x-another.Texture_Height/2,2));
    }
    public double distanceto2(GameObj another){
        return Math.sqrt(Math.pow(this.x-this.Texture_Width/2-another.x+another.Texture_Width/2,2)  + Math.pow(this.x-this.Texture_Height/2-another.x+another.Texture_Height/2,2));
    }
    public void PixelClear(){
        Pixel.clear();
    }
    public void setHitbox(Vector<PixelCoord> Sonar){
        PixelClear();
        for(int i=0;i<Sonar.size();i++){
            Pixel.addElement(new PixelCoord(x-Texture_Width/2,y-Texture_Height/2,Sonar.elementAt(i)));
        }
    }
    public void setHitboxRoundBullet(PixelCoord Hitbox){
        Pixel.addElement(new PixelCoord(Hitbox));
    }
    public long getValue() { return value;}
    public void dispose(){

        art.dispose();

    }
}
