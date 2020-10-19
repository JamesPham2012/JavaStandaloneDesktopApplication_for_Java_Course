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
    private long t=0l;
    private static Bullet fakebase=new Bullet(0,0,0);


    public Bullet(float x_c, float y_c, float xfromhost, float yfromhost, int Id) {
        x = x_c + xfromhost;
        x_b = x_c;
        y = y_c + yfromhost;
        y_b = y_c;
        id = Id;
        State=true;
        scale = 0.7f;
        t=System.currentTimeMillis();
    }

    public Bullet(float x_c, float y_c, int Id) {
        x = x_c;
        x_b = x_c;
        y = y_c;
        y_b = y_c;
        scale = 0.7f;
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

    public void setMove() {                  //Furthermore edit bullet orbit here
        X = x - x_b;
        Y = y - y_b;
        D = Math.sqrt((double) X * (double) X + (double) Y * (double) Y);
        switch (Math.abs(id)) {
            case 0://spin and spread
                setX_move((Y*5+X)/(float)D);
                setY_move((-X*5+Y)/(float)D);
                break;
            case 1://straight
                setX_move(10 * X / (float) D);
                setY_move(10 * Y / (float) D);
                break;
            case 2://spin around host
                setX_move(((Y*15-X*10)*5/(float)D));
                setY_move(((-X*15-Y*10)*5/(float)D));
                x_b=Gdx.input.getX();
                y_b=480-Gdx.input.getY();
                if (System.currentTimeMillis()-t>500){
                    t=System.currentTimeMillis();
                    id=0;
                }
                break;
            case 3:
                x_b=fakebase.x_b;
                y_b=fakebase.y_b;
                id=1;
        }
    }

    public void create() {

        batch = new SpriteBatch();
    }

    public void render() { // loop
        batch.begin();
        batch.draw(Assets.texture_bullet, (int)(x- (Assets.texture_bullet.getWidth()*scale/2)),(int)(y- (Assets.texture_bullet.getHeight()*scale/2)),Assets.texture_bullet.getWidth()*scale,Assets.texture_bullet.getHeight()*scale);
        batch.end();
        y += y_move;
        x += x_move;
        if (System.currentTimeMillis()-t>10000){
            State=false;
        }
    }

    public static void render(Vector<Bullet> bullet_arr) {
        for (int i = 0; i < bullet_arr.size(); i++) {
            if (bullet_arr.elementAt(i).State) {
                bullet_arr.elementAt(i).setMove();
                bullet_arr.elementAt(i).render();
            }
        }
    }

    public void dispose() {
        batch.dispose();
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