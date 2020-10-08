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


    public Bullet(int x_c, int y_c, int x_v, int y_v){
        x=x_c; x_var=x_v;
        y=y_c; y_var=y_v;
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
