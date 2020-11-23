package com.mygdx.game;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;

public class Rocket extends GameObj{
    private Sprite sprite;
    private SpriteBatch batch;

    private float speed = 100f;

    //Player position
    private GameObj playerPos = new GameObj();

    private float degree = 0;

    public Explosion explosion;
    private float fuseTime = 5f;
    float lifeTime;

//    private Sound sound= Gdx.audio.newSound(Gdx.files.internal("Audio/Explosion3.mp3"));

    public Rocket(float x, float y)
    {
        batch = new SpriteBatch();
        sprite = new Sprite(Assets.texture_rocket);
        
        this.x = x;
        this.y = y;

        explosion = new Explosion();
        State = true;

        hitboxRadius = (sprite.getHeight() + sprite.getWidth())/4;
    }

    public void render() {

            sprite.setPosition(x, y);

            batch.begin();
            sprite.draw(batch);
            batch.end();

            rotateRocket();
            moveRocket();

            explosion.setPos(x, y);
            lifeTime += Gdx.graphics.getDeltaTime();
            checkFuse();
            checkCollision();
    }

    private void checkCollision()
    {
//        if(x <= MyGdxGame.player.x + MyGdxGame.player.hitboxRadius && x >= MyGdxGame.player.x
//        && y <= MyGdxGame.player.y + MyGdxGame.player.hitboxRadius && y >= MyGdxGame.player.y)
//        {
//            Execute();
//            MyGdxGame.player.State = false;
//        }

//        if(MyGdxGame.player.x % x <= 0.1 & MyGdxGame.player.y % y <= 0.1)
//        {
//            Execute();
//        }

        if(Math.sqrt(Math.pow(MyGdxGame.player.x - x, 2) + Math.pow(MyGdxGame.player.y - y, 2)) <
                hitboxRadius + MyGdxGame.player.hitboxRadius)
        {
            Execute();
            MyGdxGame.player.Execute();
        }
    }

    private void checkFuse()
    {
        if(lifeTime >= fuseTime)
        {
            explosion.draw();
//            sound.play();
            if(explosion.isDone)
                Execute();
        }
    }

    public void getPlayerPos(float x, float y)
    {
        playerPos.x = x;
        playerPos.y = y;
    }

    public void moveRocket()
    {
        if(y > playerPos.y)
            y -= speed * Gdx.graphics.getDeltaTime();
        else if(y < playerPos.y)
            y += speed * Gdx.graphics.getDeltaTime();

        if(x > playerPos.x)
            x -= speed * Gdx.graphics.getDeltaTime();
        else if(x < playerPos.x)
            x += speed * Gdx.graphics.getDeltaTime();
    }

    private void rotateRocket()
    {
        degree = (float) Math.atan2(y - playerPos.y, x - playerPos.x) * MathUtils.radDeg;

        sprite.setOriginCenter();
        sprite.setRotation(degree - 90f);
    }

    public void Execute()
    {
        State=false;
        id=0;
        lifeTime = 0;
    }

    public void dispose()
    {
        batch.dispose();
    }
}
