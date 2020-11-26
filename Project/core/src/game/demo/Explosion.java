package game.demo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Explosion {
    SpriteBatch batch;
    Texture img;
    TextureRegion[] animationFrame;
    Animation animation;

    public float elapsedTime = 0;
    public float frameDuration = 0.1f;

    private float offset = 40;

    public float x,y;
    public boolean isDone = false;

    public Explosion () {
        batch = new SpriteBatch();
        img = new Texture("ExploRegion3.png");

        animationFrame = new TextureRegion[4]; // animation with 4 frame
        TextureRegion[][] tempFrame = TextureRegion.split(img, 70,71); //Dividing the .png into a grid

        int temp = 0;
        for(int i = 0; i < 2; i++)
        {
            for(int j = 0; j < 2; j++)
                animationFrame[temp++] = tempFrame[i][j]; //Fill up the animation frame with frames
        }

        animation = new Animation(frameDuration, animationFrame);
    }

    public void draw() {
        elapsedTime += Gdx.graphics.getDeltaTime();
        System.out.println("DRAW");
        batch.begin();
        batch.draw((TextureRegion) animation.getKeyFrame(elapsedTime, false), x - offset, y - offset);
        batch.end();
        isDone = animation.isAnimationFinished(elapsedTime);
    }

    public void setPos(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public void dispose () {
        batch.dispose();
        img.dispose();
    }
}