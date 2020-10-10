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
    Vector<Bullet> bullet_arr= new Vector<>();


    public void create(){

        batch = new SpriteBatch();
        texture = new Texture("Plane.png");
    }
    public void input(){
        x= Gdx.input.getX();
        y= 480 - Gdx.input.getY();

    }
    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }
    public boolean fire(){
        return Gdx.input.isKeyJustPressed(Input.Keys.SPACE);
    }
    public void Bullet_Call() {
        bullet_arr.addElement(new Bullet(getX(),getY(),-5,28));

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
