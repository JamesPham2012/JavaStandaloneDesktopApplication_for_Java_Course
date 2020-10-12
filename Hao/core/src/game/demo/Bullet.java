package game.demo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Vector;
import java.lang.Math;

public class Bullet extends GameObj {

    public float x_b;
    public float y_b;
    private float x_var;
    private float y_var;
    private float X;
    private float Y;
    private double D;
    private long t=0l;

    public Bullet(float x_c, float y_c, float xfromhost, float yfromhost, int Id) {
        x = x_c + xfromhost;
        x_b = x_c;
        y = y_c + yfromhost;
        y_b = y_c;
        id = Id;
        State=true;
        t=System.currentTimeMillis();
    }

    public Bullet(float x_c, float y_c, int Id) {
        x = x_c;
        x_b = x_c;
        y = y_c;
        y_b = y_c;
        id = Id;
    }

    public void setX_var(float x_var) {
        this.x_var = x_var;
    }

    public void setY_var(float y_var) {
        this.y_var = y_var;
    }

    public void setVary() {                  //Furthermore edit here
        X = x - x_b;
        Y = y - y_b;
        D = Math.sqrt((double) X * (double) X + (double) Y * (double) Y);
        switch (id) {
            case 0:
                setX_var((Y * 100 / (float) D / (float) Math.sqrt(D)));
                setY_var((-X * 100 / (float)D/(float) Math.sqrt(D)));
                break;
            case 1:
                setX_var(10 * X / (float) D);
                setY_var(10 * Y / (float) D);
                break;
            case 2:
                setX_var(((Y*15-X*2)/(float)D));
                setY_var(((-X*15-Y*2)/(float)D));
                x_b=Gdx.input.getX();
                y_b=720-Gdx.input.getY();
                break;
        }
    }

    public void create() {

        batch = new SpriteBatch();
        texture = new Texture("Bullet_plane.png");
    }

    public void render() { // loop
        batch.begin();
        batch.draw(texture, (int)(x- (texture.getWidth()*scale/2)),(int)(y- (texture.getHeight()*scale/2)),texture.getWidth()*scale,texture.getHeight()*scale);
        batch.end();
        y += y_var;
        x += x_var;
        if (System.currentTimeMillis()-t>1000){
            State=false;}
    }

    public static void render_bullet(Vector<Bullet> bullet_arr) {
        for (int i = 0; i < bullet_arr.size(); i++) {
            bullet_arr.elementAt(i).setVary();
            if (bullet_arr.elementAt(i).State) bullet_arr.elementAt(i).render();

        }
    }

    public void dispose() {
        batch.dispose();
        texture.dispose();
    }

    public static void Bullet_Reallo(Vector<Bullet> bullet_arr, float x_c, float y_c, float x_offset, float y_offset,int id) {
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

    public void Revive(float x_c,float y_c, float x_offset, float y_offset,int id){
        this.x_b=x_c;// omit for more effects
        this.y_b=y_c;// omit for more effects
        this.x=x_c+x_offset;
        this.y=y_c+y_offset;
        this.id=id;
        this.State=true;
        t=System.currentTimeMillis();

    }

}