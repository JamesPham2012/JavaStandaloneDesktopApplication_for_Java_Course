package game.demo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bullet {
    private SpriteBatch batch;
    private Texture texture;
    public int x;
    public int y;
    private int x_var;
    private int y_var;
    private int x_b;
    private int y_b;



//    public Bullet(int x_c, int y_c, int xfromhost, int yfromhost){
//        x=x_c+xfromhost; x_b=x_c-5;
//        y=y_c+yfromhost; y_b=y_c;
//    }

    public Bullet(int x_c, int y_c){
        x=x_c; x_b=x_c;
        y=y_c; y_b=y_c;
    }

    public void setX_var(int x_var) {
        this.x_var = x_var;
    }

    public void setY_var(int y_var) {
        this.y_var = y_var;
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

    public void dispose () {
        batch.dispose();
        texture.dispose();
    }
}
