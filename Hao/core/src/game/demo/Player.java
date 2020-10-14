package game.demo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.IntMap;

import java.util.Vector;

public class Player extends GameObj{


    int loaded=1;
    long t=System.currentTimeMillis()-200;
    public void create(){

        batch = new SpriteBatch();
        setId(1);
    }
    public void input(){
        x= Gdx.input.getX();
        y= Gdx.graphics.getHeight() - Gdx.input.getY();

    }
    public int getHeight(){
        return Assets.texture_plane.getHeight();
    }
    public void setId(int id){
        this.id=id;
    }
    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }
    public boolean fire() {

        if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
            if (System.currentTimeMillis() - t > 200) {
                t = System.currentTimeMillis();
                loaded = 1;
            } else {
                loaded = 0;
            }
            if (loaded==1) return true;
            else return false;

        }
        else return false;

    }

    // Id : type of bullet.
    public void Bullet_Call(Vector<Bullet> bullet_arr) {                        //Furthermore edit here
        switch (id) {
            case 0:
            {
                Bullet.Bullet_Reallo(bullet_arr,getX(), getY(), 0, 30, 0);
                Bullet.Bullet_Reallo(bullet_arr,getX(), getY(), 0, -30, 0);
                Bullet.Bullet_Reallo(bullet_arr,getX(), getY(), 30, 0, 0);
                Bullet.Bullet_Reallo(bullet_arr,getX(), getY(), -30, 0, 0);
                break;
            }
            case 1:
           /*     bullet_arr.addElement(new Bullet(getX(), getY(), 0, 30, 1));*/
            {
                Bullet.Bullet_Reallo(bullet_arr,getX(), getY(), 0, 30, 1);
                Bullet.Bullet_Reallo(bullet_arr,getX(), getY(), -5, 29, 1);
                Bullet.Bullet_Reallo(bullet_arr,getX(), getY(), 5, 29, 1);
                /*bullet_arr.addElement(new Bullet(getX(), getY(), -5, 29, 1));
                bullet_arr.addElement(new Bullet(getX(), getY(), 5, 29, 1));*/
                if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                    Bullet.Bullet_Reallo(bullet_arr,getX(), getY(), 0, 30, 2);
                    Bullet.Bullet_Reallo(bullet_arr,getX(), getY(), 0, -30, 2);
                    Bullet.Bullet_Reallo(bullet_arr,getX(), getY(), 30, 0, 2);
                    Bullet.Bullet_Reallo(bullet_arr,getX(), getY(), -30, 0, 2);
                    /*bullet_arr.addElement(new Bullet(getX(), getY(), 0, 30, 0));
                    bullet_arr.addElement(new Bullet(getX(), getY(), 0, -30, 0));
                    bullet_arr.addElement(new Bullet(getX(), getY(), 30, 0, 0));
                    bullet_arr.addElement(new Bullet(getX(), getY(), -30, 0, 0));*/
                }
            }
                break;
        }
    }
    public void render_player () { // loop
        batch.begin();
        batch.draw(Assets.texture_plane, (int)(x- (Assets.texture_plane.getWidth()*scale/2)),(int)(y- (Assets.texture_plane.getHeight()*scale/2)),Assets.texture_plane.getWidth()*scale,Assets.texture_plane.getHeight()*scale);
        batch.end();
        input();

    }
    public void dispose () {
        batch.dispose();

    }
}

