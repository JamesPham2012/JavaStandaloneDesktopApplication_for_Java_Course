package com.mygdx.game;

import UI.GameOverScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class Boss extends GameObj{
    private SpriteBatch batch;
    private Sprite sprite;
    private Texture BossTexture;

    public float WORLD_WIDTH;
    public float WORLD_HEIGHT;

    public Explosion explosion;

    private static float speed = 100f;

    private float des_x, des_y;
    private GameObj[] wayPoints;

    private float HP = 1;
    private Texture HPTexture, HPBackground;

    Random random;
    private int posIndex;

    //    private float offset_x;
    private float offset_y;

    //Player position
    private GameObj playerPos = new GameObj();


    private boolean isDead = false;
    private int score = 100;

    private float hitBoxWidth;
    private float hitBoxHeight;

    public Boss()
    {
        WORLD_HEIGHT = Gdx.graphics.getHeight();
        WORLD_WIDTH = Gdx.graphics.getWidth();

        batch = new SpriteBatch();

        State = true;

        //Setting up boss texture to draw
        BossTexture = new Texture(Gdx.files.internal("Texture/Boss/EnemyBoss.png"));
        sprite = new Sprite(BossTexture);

        //Setting up Hitbox
        hitBoxHeight = sprite.getHeight();
        hitBoxWidth = sprite.getWidth();

        //HPTexture && HPBackground are same size
        HPTexture = new Texture(Gdx.files.internal("Texture/Boss/blankHP.png"));
        HPBackground = new Texture(Gdx.files.internal("Texture/Boss/blankBG.png"));

        offset_y = HPTexture.getHeight();

        //Draw boss on top of screen
        x = WORLD_WIDTH/2 - sprite.getWidth()/2;
        y = WORLD_HEIGHT + sprite.getHeight();

        //Waypoints for boss movement
        wayPoints = new GameObj[3];
        for(int i = 0; i < wayPoints.length; i++)
        {
            wayPoints[i] = new GameObj();
            wayPoints[i].x = WORLD_WIDTH/2 -sprite.getWidth()/2 * i;
            wayPoints[i].y = WORLD_HEIGHT/2 + sprite.getHeight()/2 * i/ 2;
        }

        random = new Random();
        posIndex = random.nextInt(wayPoints.length); //Index to decide where to go next
        des_x = wayPoints[1].x;
        des_y = wayPoints[1].y;

        setHitBoxRadius();

        explosion = new Explosion();
    }

    public void render()
    {
        if(HP > 0)
        {
            sprite.setPosition(x,y);

            batch.begin();
            sprite.draw(batch);

            //Draw boss's HP bar
            batch.draw(HPBackground, WORLD_WIDTH/4, WORLD_HEIGHT - offset_y*100,
                    WORLD_WIDTH/2 + 2, 22);

            batch.draw(HPTexture, WORLD_WIDTH/4, WORLD_HEIGHT - offset_y*100,
                    WORLD_WIDTH/2 * HP, 20);

            batch.end();

            moveBoss();
        }


//        RotateBoss();

//        System.out.println("BEEP" + pos);
    }

    public void getPlayerPos(float x, float y)
    {
        playerPos.x = x;
        playerPos.y = y;
    }

    private void moveBoss()
    {
        if(y > des_y)
            y -= speed * Gdx.graphics.getDeltaTime();
        else if(y < des_y)
            y += speed * Gdx.graphics.getDeltaTime();

        if(x > des_x)
            x -= speed * Gdx.graphics.getDeltaTime();
        else if(x < des_x)
            x += speed * Gdx.graphics.getDeltaTime();

        if(des_x % x <= 0.1 & des_y % y <= 0.1)
        {
            setDes();
        }
    }

    private void setDes()
    {
        posIndex = random.nextInt(3);
        des_x = wayPoints[posIndex].x;
        des_y = wayPoints[posIndex].y;
    }
//
//    private void RotateBoss()
//    {
//        degree = (float) Math.atan2(y - playerPos.y, x - playerPos.x) * MathUtils.radDeg;
//
//        sprite.setRotation(degree - 95f);
//    }

    public void checkCollision(){
            if (State)
            {
                checksCollision();
                checkHP();
            }
    }

    public void checksCollision(){
        for (int i=0;i<Bullet.bullet_arr.size();i++)
        {
            if (Bullet.bullet_arr.elementAt(i).State)
            {
                if((Bullet.bullet_arr.elementAt(i).getX() >= x) &&
                        (Bullet.bullet_arr.elementAt(i).getX() <= x+hitBoxWidth) &&
                        (Bullet.bullet_arr.elementAt(i).getY() >= y) &&
                        (Bullet.bullet_arr.elementAt(i).getY() <= y+hitBoxHeight))
                {
                    System.out.println("HIT");
                    value-=Bullet.bullet_arr.elementAt(i).getValue();
                    Bullet.bullet_arr.elementAt(i).Execute();
                    HP -= 0.001f;
                    checkHP();
                }
            }
        }
    }

    private void checkHP()
    {
        if(HP <= 0)
            Execute();
    }

    private void Execute(){
        explosion.draw();
        if(explosion.isDone)
        {
            State=false;
            id=0;
            GameOverScreen.score += score;
        }
    }

    private void setHitBoxRadius()
    {
        hitboxRadius = sprite.getHeight()/2;
    }

    private void hitBox()
    {

    }

    public void dispose()
    {
        batch.dispose();
    }
}