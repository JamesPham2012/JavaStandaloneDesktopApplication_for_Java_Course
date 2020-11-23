package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Vector;
import java.lang.Math;

public class Bullet extends GameObj {
    private SpriteBatch batch;
    private long t=0l;
    private static Bullet fakebase=new Bullet(0,0,0);
    public static Vector<Bullet> bullet_arr=new Vector<>();

    private Bullet(float x_c, float y_c, float xfromhost, float yfromhost, int Id) {
        x = x_c + xfromhost;
        x_b = x_c;
        y = y_c + yfromhost;
        y_b = y_c;
        moveId = Id*100;
        id=Id;
        State=true;
        t=System.currentTimeMillis();
    }

    private Bullet(float x_c, float y_c, int Id) {
        x = x_c;
        x_b = x_c;
        y = y_c;
        y_b = y_c;
        id = Id;
    }

    public static void resetfakebase(){
        fakebase.x_b=MyGdxGame.player.getX();
        fakebase.y_b=MyGdxGame.player.getY();
    }

    public void setX_move(float x_move) { this.x_move = x_move; }

    public void setY_move(float y_move) {
        this.y_move = y_move;
    }

    private void create() {
        batch = new SpriteBatch();
    }

    private void renders() { // loop
        batch.begin();
        batch.draw(Assets.texture_bullet, (int)(x- (Assets.texture_bullet.getWidth()*scale/2)),(int)(y- (Assets.texture_bullet.getHeight()*scale/2)),Assets.texture_bullet.getWidth()*scale,Assets.texture_bullet.getHeight()*scale);
        batch.end();
        y += y_move;
        x += x_move;
    }

    public static void render() {
        for (int i = 0; i < bullet_arr.size(); i++) {
            if (bullet_arr.elementAt(i).State) {
                bullet_arr.elementAt(i).setMove();
                bullet_arr.elementAt(i).renders();
            }
        }
    }

    public void dispose() {
        batch.dispose();
    }

    public static void Bullet_Reallo(float x_c, float y_c, float x_offset, float y_offset,int id) {
        int BulletGen = 0;
        loop:     while (BulletGen == 0) {
            // need change to replace DED value with ALIVE value in order for the vector not to be too long, waste of memory
            for (int i = 0; i < bullet_arr.size(); i++) {                                                                           //there exists at least an element in the array
                if (bullet_arr.elementAt(i).isDed()) {                                    // there is 1 dead bullet
                    bullet_arr.elementAt(i).Revive(x_c, y_c, x_offset,y_offset,id);//revive it as a new bullet
                    BulletGen = 1;

                    /*Gdx.app.log("Log", "Bullet number "+i+" revived");*/
                    continue loop;
                }
            }
            // if the code get here, there is NO dead bullet in the array
            bullet_arr.addElement(new Bullet(x_c, y_c,x_offset,y_offset,id));// create a new bullet
            bullet_arr.lastElement().setParam();
            bullet_arr.lastElement().create();
            BulletGen = 1;
        }
    }

    public boolean isDed() {
        return !State;
    }

    private void Revive(float x_c,float y_c, float x_offset, float y_offset,int id){
        this.x_b=x_c;
        this.y_b=y_c;
        this.x=x_c+x_offset;
        this.y=y_c+y_offset;
        this.id=id;
        this.moveId=id*100;
        this.State=true;
        setHitboxRadius();
        setValue();
        t=System.currentTimeMillis();
    }

    public void Execute(){
        State=false;
        id=0;
    }

    /* ----------------------------------------------------------------------
     * ----------------------------------------------------------------------
     * --------------------------Edit - add more-----------------------------
     * -------------------Bullets' hitbox, value, orbit----------------------
     * ----------------------------------------------------------------------
     * ----------------------------------------------------------------------*/

    private void setHitboxRadius() {
        switch (id){
            case -1:
            case -2:
            case -3:
            case 1:
                hitboxRadius=((float)Assets.texture_bullet.getHeight()+(float)Assets.texture_bullet.getWidth())/4;
                break;
        }
    }

    private void setValue(){
        value=1;
    }

    private void setMove() {                  //Furthermore edit bullet orbit here
        X = x - x_b;
        Y = y - y_b;
        D = Math.sqrt((double) X * (double) X + (double) Y * (double) Y);
        switch (moveId) {
            case -100://straight
                speed=10;
                moveId=1;
                break;
            case -200://spin around host
                speed=10/(float)Math.sqrt(D);
                moveId=2;
                break;
            case -300:
                x_b=fakebase.x_b;
                y_b=fakebase.y_b;
                moveId=-100;
                break;
            case 100:
                speed=3;
                moveId+=1;
                break;
        }
        switch (moveId%10) {  // Default and simple move
            case 1: //straight
                setX_move(speed * X / (float) D);
                setY_move(speed * Y / (float) D);
                if((x>1280)||(x<0)||(y<0)||(y>720)) Execute();
                break;
            case 2: //spin and keep distance
                setX_move(((Y-X*0.1f)*speed/(float)D));
                setY_move(((-X-Y*0.1f)*speed/(float)D));
                if (System.currentTimeMillis()-t>5000) Execute();
                break;
            case 3: //spin and spread
                setX_move(((Y+X*0.3f)*speed/(float)D));
                setY_move(((-X+Y*0.3f)*speed/(float)D));
                break;
        }
    }
}
