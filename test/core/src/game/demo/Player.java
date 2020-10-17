package game.demo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Vector;

public class Player extends GameObj{
    private SpriteBatch batch;
    private Texture texture;
    private int loaded=0;
    private long t=System.currentTimeMillis()-200;
    private float varyDistance;
    private long rapidity;

    public void create(){

        batch = new SpriteBatch();
        texture = new Texture("Plane.png");
        setId(1);
    }
    public void input(){
        switch (id) {
            case 1:
                if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                    varyDistance=1;
                    rapidity=10;
                }
                else {
                    varyDistance=5;
                    rapidity=100;
                }
                break;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) x-=varyDistance;
        if (x<0) x=0;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) x+=varyDistance;
        if (x>640) x=640;
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) y+=varyDistance;
        if (y>480) y=480;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) y-=varyDistance;
        if (y<0) y=0;
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
    public boolean fire(){
        if (Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_LEFT)) Bullet.resetfakebase();
        if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
            if (System.currentTimeMillis() - t > rapidity) {
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
    public void Bullet_Call(Vector<Bullet> bullet_arr) {              //Furthermore edit here
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
                if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                    Bullet.Bullet_Reallo(bullet_arr,getX(), getY(), 0, 30, 3);
                    Bullet.Bullet_Reallo(bullet_arr,getX(), getY(), -5, 29, 3);
                    Bullet.Bullet_Reallo(bullet_arr,getX(), getY(), 5, 29, 3);
                    Bullet.Bullet_Reallo(bullet_arr,getX(), getY(), 0, 25, 3);
                    Bullet.Bullet_Reallo(bullet_arr,getX(), getY(), -10, 28, 3);
                    Bullet.Bullet_Reallo(bullet_arr,getX(), getY(), 10, 28, 3);
                }
                else {
                    Bullet.Bullet_Reallo(bullet_arr,getX(), getY(), 0, 30, 1);
                    Bullet.Bullet_Reallo(bullet_arr,getX(), getY(), -5, 29, 1);
                    Bullet.Bullet_Reallo(bullet_arr,getX(), getY(), 5, 29, 1);
                    Bullet.Bullet_Reallo(bullet_arr,getX(), getY(), 0, 25, 1);
                    Bullet.Bullet_Reallo(bullet_arr,getX(), getY(), -10, 28, 1);
                    Bullet.Bullet_Reallo(bullet_arr,getX(), getY(), 10, 28, 1);
                }
            }
                break;
        }
    }
    public void render_player () { // loop
        batch.begin();
        batch.draw(texture, x-15, y-28, 40, 56);
        batch.end();
        input();

    }
    public void dispose () {
        batch.dispose();
        texture.dispose();
    }
}

