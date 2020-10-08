package game.demo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
public class Player{
    private SpriteBatch batch;
    private Texture texture;
    private int x;
    private int y;



    public void create(){

        batch = new SpriteBatch();
        texture = new Texture("Spacefighter.png");
    }
    public void input(){
        x= Gdx.input.getX();
        y= 480 - Gdx.input.getY();

    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public boolean fire(){
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            return true;
        }
        else{
            return false;
        }
    }
    public void render_player () { // loop
        batch.begin();
        batch.draw(texture, x, y);
        batch.end();
        input();

    }
    public void dispose () {
        batch.dispose();
        texture.dispose();
    }
}
