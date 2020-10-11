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
    private SpriteBatch batch;
    private Texture texture;
    public float x_b;
    public float y_b;
    private float x_var;
    private float y_var;
    private float X;
    private float Y;
    private double D;


    public Bullet(float x_c, float y_c, float xfromhost, float yfromhost,int Id){
        x=x_c+xfromhost; x_b=x_c;
        y=y_c+yfromhost; y_b=y_c;
        id=Id;
    }
    public Bullet(float x_c, float y_c, int Id){
        x=x_c; x_b=x_c;
        y=y_c; y_b=y_c;
        id=Id;
    }

    public void setX_var(float x_var) {
        this.x_var = x_var;
    }

    public void setY_var(float y_var) {
        this.y_var = y_var;
    }

    public void setVary(){                  //Furthermore edit here
        X=x-x_b;
        Y=y-y_b;
        D=Math.sqrt((double)X*(double)X+(double)Y*(double)Y);
        switch (id) {
            case 0:
                setX_var((Y*100/(float)D/(float)Math.sqrt(D)));
                setY_var((-X*100/(float)D/(float)Math.sqrt(D)));
                break;
            case 1:
                setX_var(10*X/(float)D);
                setY_var(10*Y/(float)D);
                break;
        }
    }

    public void create(){

        batch = new SpriteBatch();
        texture = new Texture("Bullet_plane.png");
    }
    public void render () { // loop
        batch.begin();
        batch.draw(texture, x, y);
        batch.end();
        y+=y_var;
        x+=x_var;
    }

    public static void render(Vector<Bullet> bullet_arr){
        for (int i=0; i<bullet_arr.size(); i++){
            bullet_arr.elementAt(i).setVary();
            bullet_arr.elementAt(i).render();
            if (bullet_arr.elementAt(i).y>480) {
                bullet_arr.removeElementAt(i);
            }
        }
    }

    public void dispose () {
        batch.dispose();
        texture.dispose();
    }
}
