package game.demo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Background {

    //Screen
    private Camera camera;
    private Viewport viewport;

    //Graphics
    SpriteBatch batch;
    Texture[] background;

    //Background movement
    private float[] backgroundOffset = {0,0,0,0};
    private float speed;

    //World parameters (Based on the game resolution)
    private final int WORLD_WIDTH = 720;
    private final int WORLD_HEIGHT = 1280;
    public int getWORLD_WIDTH() {return WORLD_WIDTH;}
    public int getWORLD_HEIGHT(){return WORLD_HEIGHT;}


    public void create () {
        background = new Texture[4];
        camera = new OrthographicCamera();
        viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

        speed = WORLD_HEIGHT / 2;

        batch = new SpriteBatch();

        background[0] = new Texture("Star0.png");
        background[1] = new Texture("Star1.png");
        background[2] = new Texture("Star2.png");
        background[3] = new Texture("Star3.png");

    }

    public void render () {
        float deltaTime = Gdx.graphics.getDeltaTime(); //Time between frames

        batch.begin();

        MoveBackground(deltaTime);

        batch.end();
    }

    public void MoveBackground(float deltaTime)
    {
        backgroundOffset[0] += speed/8 * deltaTime;
        backgroundOffset[1] += speed/4 * deltaTime;
        backgroundOffset[2] += speed/2 * deltaTime;
        backgroundOffset[3] += speed * deltaTime;

        for(int i = 0; i < background.length; i++)
        {
            backgroundOffset[i]++;
            if(backgroundOffset[i] > WORLD_HEIGHT)
                backgroundOffset[i] = 0;
            batch.draw(background[i], 0, -backgroundOffset[i], WORLD_WIDTH, WORLD_HEIGHT);
            batch.draw(background[i], 0, -backgroundOffset[i] + WORLD_HEIGHT, WORLD_WIDTH, WORLD_HEIGHT);
        }
    }


    public void resize(int width, int height) {
        viewport.update(width, height, true);
        batch.setProjectionMatrix(camera.combined);
    }


    public void dispose () {
        batch.dispose();
        for(int i = 0; i < background.length; i++)
            background[i].dispose();
    }
}