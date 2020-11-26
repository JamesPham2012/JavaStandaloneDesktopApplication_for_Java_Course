package game.demo;

import UI.GameOverScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Random;

public class Boss extends GameObj{
    private SpriteBatch batch;
    private Sprite sprite;

    public float WORLD_WIDTH;
    public float WORLD_HEIGHT;

    //Boss movement
    private static float speed = 100f;

    private float des_x, des_y;
    private GameObj[] wayPoints;

    private boolean arrived = false;
    Random random;
    private int posIndex;

    //Player position
    private GameObj playerPos = new GameObj();
    /////////////////////////

    //Boss stats
    private float HP = 1;
    private int score = 100;

    private float hitBoxWidth;
    private float hitBoxHeight;

    private Texture HPTexture, HPBackground;
    private float offset_y;
    private boolean isDead = false;
    //////////////////////////

    //How fast the boss shoot
    private float fireRate = 3f;
    private float timeSinceLastFire = 0f;
    ArrayList<Rocket> rocketList;
    ///////////////////////////

    //private Sound sound= Gdx.audio.newSound(Gdx.files.internal("Audio/Explosion3.mp3"));

    public Boss()
    {
        WORLD_HEIGHT = Gdx.graphics.getHeight();
        WORLD_WIDTH = Gdx.graphics.getWidth();

        batch = new SpriteBatch();

        State = true;

        //Setting up boss texture to draw
        sprite = new Sprite(Assets.texture_boss);

        //Setting up Hitbox
        hitBoxHeight = sprite.getHeight();
        hitBoxWidth = sprite.getWidth();

        //HPTexture && HPBackground are same size
        HPTexture = new Texture(Gdx.files.internal("Boss/blankHP.png"));
        HPBackground = new Texture(Gdx.files.internal("Boss/blankBG.png"));

        offset_y = HPTexture.getHeight();

        //Draw boss on top of screen
        x = WORLD_WIDTH/2 - sprite.getWidth()/2;
        y = WORLD_HEIGHT + sprite.getHeight();

        //Waypoints for boss movement
        wayPoints = new GameObj[3];
        for(int i = 0; i < wayPoints.length; i++)
        {
            wayPoints[i] = new GameObj();
            wayPoints[i].x = (WORLD_WIDTH/2 - sprite.getWidth()/2) * i;
            wayPoints[i].y = WORLD_HEIGHT/2 + sprite.getHeight()/2 * i/2;
        }

        random = new Random();
        posIndex = random.nextInt(wayPoints.length); //Index to decide where to go next
        des_x = wayPoints[1].x;
        des_y = wayPoints[1].y;

        setHitBoxRadius();

        rocketList = new ArrayList<>();

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
            shootRocket();

            for(int i = 0; i < rocketList.size(); i++)
            {
                rocketList.get(i).render();
                rocketList.get(i).getPlayerPos(playerPos.x, playerPos.y);
            }

            timeSinceLastFire += Gdx.graphics.getDeltaTime();
            checkRocket();
        }

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
            arrived = true;
            setDes();
        }
        else
            arrived = false;
    }

    private void setDes()
    {
        posIndex = random.nextInt(3);
        des_x = wayPoints[posIndex].x;
        des_y = wayPoints[posIndex].y;
    }

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

    public void checkCollisionWithPlayer()
    {
        if((playerPos.x >= x) &&
                (playerPos.x <= x+hitBoxWidth) &&
                (playerPos.y >= y) &&
                (playerPos.y <= y+hitBoxHeight))
        {
            MyGdxGame.player.State = false;
            MyGdxGame.player.id = 0;
        }
    }

    private void checkHP()
    {
        if(HP <= 0)
        {
            Execute();
        }
    }

    private void Execute()
    {
        //sound.dispose();
        State=false;
        id=0;
        GameOverScreen.score += score;
    }

    private void setHitBoxRadius()
    {
        hitboxRadius = sprite.getHeight()/2;
    }

    public void getPlayerPos(float x, float y)
    {
        playerPos.x = x;
        playerPos.y = y;
    }

    public void shootRocket() {
        if (timeSinceLastFire >= fireRate)
        {
            rocketList.add(new Rocket(x, y));
            rocketList.add(new Rocket(x+sprite.getWidth(), y));

            System.out.println(rocketList.size());
            timeSinceLastFire = 0f;
        }
    }

    private void checkRocket()
    {
        for(int i = 0; i < rocketList.size(); i++)
        {
            if(!rocketList.get(i).State)
            {
                //sound.play();
                rocketList.remove(i);
                System.out.println("DEAD");
            }
        }
    }

    public void dispose()
    {
//        sound.dispose();
        batch.dispose();
    }
}