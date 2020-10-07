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
    private int x;
    private int y;


    public Bullet(int x_c, int y_c){
        x=x_c;
        y=y_c;
    }
    public void create(){

        batch = new SpriteBatch();
        texture = new Texture("Bullet_plane.png");
    }
    public void render () { // loop
        batch.begin();
        batch.draw(texture, x, y);
        batch.end();
        y++;
    }

    public void dispose () {
        batch.dispose();
        texture.dispose();
    }
}
