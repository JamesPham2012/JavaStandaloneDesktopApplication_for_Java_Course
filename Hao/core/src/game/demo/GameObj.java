package game.demo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameObj {
    public float x;
    public float y;
    protected int cali_x;
    protected int cali_y;
    public int S_width;
    public int S_height;
    protected float scale = 2f;
    protected SpriteBatch batch;
    protected Texture texture;
    protected int id; //2 for player, -2 for enemy
    boolean State;


    public void setParam(){
        this.S_width=Gdx.graphics.getWidth();
        this.S_height=Gdx.graphics.getHeight();
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
    public void dispose(){

        texture.dispose();

    }




}


