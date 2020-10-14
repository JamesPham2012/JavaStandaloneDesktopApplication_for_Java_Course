package game.demo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class GameObj {
    protected float x;
    protected float y;
    protected int cali_x;
    protected int cali_y;
    public int S_width;
    public int S_height;
    Texture art;
    protected int id; //negative for player, positive for enemy
    protected boolean State;
    protected int value;


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

        art.dispose();

    }

}


