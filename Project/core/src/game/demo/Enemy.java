package game.demo;

import UI.GameOverScreen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;
import java.util.Vector;

public class Enemy extends GameObj {
    private SpriteBatch batch;
    private long t=0;
    private long t1=0;
    private long point;
    private long Wave;
    private Random randPara= new Random();

    public Enemy(float x_c, float y_c, int Id, int Wave) {
        x = x_c;
        x_b = x_c;
        y = y_c;
        y_b = y_c;
        id = Id;
        moveId=Id;
        State=true;
        this.Wave=Wave;
        setPoint();
        setValue();
        setHitboxRadius();
        t=t1=System.currentTimeMillis();
    }

    public void create() {

    }

    public static void render(Vector<Enemy> enemy_arr, SpriteBatch batch) {
        for (int i = 0; i < enemy_arr.size(); i++) {
            if (enemy_arr.elementAt(i).State) {
                enemy_arr.elementAt(i).setMove();
                enemy_arr.elementAt(i).render(batch);
            }
        }
    }
    public void setX_move(float x_move){this.x_move=x_move;}
    public void setY_move(float y_move){this.y_move=y_move;}

    public boolean isDed() {
        return !State;
    }

    public boolean isExecuted() {
        if ((value<0)&&State){
            State=false;
            return true;
        }
        return false;
    }

    public void render(SpriteBatch batch) { // loop

        batch.draw(Assets.texture_enemy, (int)(x- (Assets.texture_enemy.getWidth()*scale/2)),(int)(y- (Assets.texture_enemy.getHeight()*scale/2)),Assets.texture_enemy.getWidth()*scale,Assets.texture_enemy.getHeight()*scale);

        y += y_move;
        x += x_move;
    }

    public static void fire(Vector<Enemy> enemy_arr, Vector<Bullet> bullet_arr){
        for (int i=0; i<enemy_arr.size(); i++){
            if (enemy_arr.elementAt(i).State)
                enemy_arr.elementAt(i).Bullet_Call(bullet_arr);
        }
    }

    public static void Enemy_Reallo(Vector<Enemy> enemy_arr, float x_c, float y_c, int id, int Wave) {
        int EnemyGen = 0;
        loop:     while (EnemyGen == 0) {
            // need change to replace DED value with ALIVE value in order for the vector not to be too long, waste of memory
            for (int i = 0; i < enemy_arr.size(); i++) {                                                                           //there exists at least an element in the array
                if (enemy_arr.elementAt(i).isDed()) {                                    // there is 1 dead enemy
                    enemy_arr.elementAt(i).Revive(x_c, y_c, id, Wave);//revive it as a new enemy
                    EnemyGen = 1;
                    continue loop;
                }
            }
            // if the code get here, there is NO dead enemy in the array
            enemy_arr.addElement(new Enemy(x_c, y_c, id, Wave));// create a new enemy
            enemy_arr.lastElement().setParam();
            enemy_arr.lastElement().create();
            EnemyGen = 1;
        }
    }

    public void Revive(float x_c,float y_c,int id, int Wave){
        this.x_b=x_c;
        this.y_b=y_c;
        this.x=x_c;
        this.y=y_c;
        this.id=id;
        this.moveId=id;
        this.State=true;
        this.Wave=Wave;
        setPoint();
        setValue();
        setHitboxRadius();
        t=t1=System.currentTimeMillis();
    }

    public static void checkCollision(Vector<Enemy> enemy_arr, Vector<Bullet> bullet_arr){
        for (int i=0;i<enemy_arr.size();i++) {
            if (enemy_arr.elementAt(i).State) {
                enemy_arr.elementAt(i).checkCollision(bullet_arr);
                if (enemy_arr.elementAt(i).value < 0) enemy_arr.elementAt(i).Execute();
            }
        }
    }

    private void Execute(){
        State=false;
        id=0;
        GameOverScreen.score += 1;
    }

    public void checkCollision(Vector<Bullet> bullet_arr){
        for (int i=0;i<bullet_arr.size();i++) {
            if ((!bullet_arr.elementAt(i).isDed())&&
                    (bullet_arr.elementAt(i).id*id<0)&&
                    (Math.sqrt(Math.pow((double)bullet_arr.elementAt(i).getX()-x,2.0)+
                            Math.pow((double)bullet_arr.elementAt(i).getY()-y,2.0))<
                            (double)bullet_arr.elementAt(i).hitboxRadius+hitboxRadius)){
                value-=bullet_arr.elementAt(i).getValue();
                bullet_arr.elementAt(i).Execute();
            }
        }
    }
    /*----------------------------------------------------------------------
     * ----------------------------------------------------------------------
     * --------------------------Edit - add more-----------------------------
     * -------------Enemies' move, shooting, HP, point, hitbox---------------
     * ----------------------------------------------------------------------
     * ----------------------------------------------------------------------*/

    public void setMove() {                  //Furthermore edit enemy move here
        X = x - x_b;
        Y = y - y_b;
        D = Math.sqrt((double) X * (double) X + (double) Y * (double) Y);
        switch (moveId) {
            case 1:
                x_move=2*randPara.nextFloat();
                y_move=2*randPara.nextFloat();
                if (randPara.nextFloat()<0.5) x_move=-x_move;
                if (randPara.nextFloat()<0.5) y_move=-y_move;
                if ((x+x_move<480)||(x+x_move>800)) x_move=-x_move;
                if ((y+y_move<480)||(y+y_move>720)) y_move=-y_move;
                if ((System.currentTimeMillis()-t1>10000)&&(x>640)) x_move=2;
                if ((System.currentTimeMillis()-t1>10000)&&(x<640)) x_move=-2;
                if ((x<0)||(x>1280)) State=false;
                break;
            case 2:
                x_move=0;
                y_move=-1;
                if(System.currentTimeMillis()-t1>2000) y_move=0;
                if(System.currentTimeMillis()-t1>7000) y_move=1;
                if(System.currentTimeMillis()-t1>10000) State=false;
                break;
            case 3:
                x_move=2;
                y_move=0;
                if(x>1280) State=false;
                break;
        }
    }
    //Furthermore edit enemy move here

    public void Bullet_Call(Vector<Bullet> bullet_arr) {              //Furthermore edit here
        X = x - MyGdxGame.player.x;
        Y = y - MyGdxGame.player.y;
        D = Math.sqrt((double) X * (double) X + (double) Y * (double) Y);
        switch (id) {
            case 1:
                if ((System.currentTimeMillis() - t) > 1000 - Wave * 10) {
                    Bullet.Bullet_Reallo(bullet_arr, x, y, -X / (float) D, -Y / (float) D, 1);
                    Bullet.Bullet_Reallo(bullet_arr, x - 10, y, -X / (float) D, -Y / (float) D, 1);
                    Bullet.Bullet_Reallo(bullet_arr, x + 10, y, -X / (float) D, -Y / (float) D, 1);
                    t = System.currentTimeMillis();
                }
                break;
            case 2:
            case 3:
                if ((System.currentTimeMillis() - t) > 1000) {
                    Bullet.Bullet_Reallo(bullet_arr, x, y, -X / (float) D, -Y / (float) D, 1);
                    Bullet.Bullet_Reallo(bullet_arr, x - 20, y, -(X - 20) / (float) D, -Y / (float) D, 1);
                    Bullet.Bullet_Reallo(bullet_arr, x + 20, y, -(X + 20) / (float) D, -Y / (float) D, 1);
                    t=System.currentTimeMillis();
                }
                break;
        }
    }
    //edit enemy shooting

    public void setValue(){
        switch (id) {
            case 1:
                value=10+Wave*2;
                break;
            case 2:
                value=5+Wave;
                break;
            case 3:
                value=10+Wave;
                break;
        }
    }
    //set enemy HP base on id

    public void setPoint(){
        switch (id){
            case 1:
            case 2:
            case 3:
                point=1;
                break;
        }
    }
    //set enemy point base on id

    public void setHitboxRadius(){
        switch (id){
            case 1:
            case 2:
            case 3:
                hitboxRadius=20;
                break;
        }
    }
    //set enemy hitbox base on id

}