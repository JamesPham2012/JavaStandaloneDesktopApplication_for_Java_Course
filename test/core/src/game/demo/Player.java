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

    public void create(){

        batch = new SpriteBatch();
        texture = new Texture("Plane.png");
        setId(1);
    }
    public void input(){
        x= Gdx.input.getX();
        y= 480 - Gdx.input.getY();

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
        return true;
    }
    public void Bullet_Call(Vector<Bullet> bullet_arr) {                        //Furthermore edit here
        switch (id) {
            case 0:
            {
                bullet_arr.addElement(new Bullet(getX(), getY(), 0, 30, 0));
                bullet_arr.addElement(new Bullet(getX(), getY(), 0, -30, 0));
                bullet_arr.addElement(new Bullet(getX(), getY(), 30, 0, 0));
                bullet_arr.addElement(new Bullet(getX(), getY(), -30, 0, 0));
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
                if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                    Bullet.Bullet_Reallo(bullet_arr,getX(), getY(), 0, 30, 0);
                    Bullet.Bullet_Reallo(bullet_arr,getX(), getY(), 0, -30, 0);
                    Bullet.Bullet_Reallo(bullet_arr,getX(), getY(), 30, 0, 0);
                    Bullet.Bullet_Reallo(bullet_arr,getX(), getY(), -30, 0, 0);
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
        batch.draw(texture, x-15, y-28, 40, 56);
        batch.end();
        input();

    }
    public void dispose () {
        batch.dispose();
        texture.dispose();
    }
}

