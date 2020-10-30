package game.demo;

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
    public static Vector<Enemy> enemy_arr=new Vector<>();

    public Enemy(float x_c, float y_c, int Id, int Wave) {
        x = x_c;
        x_b = x_c;
        y = y_c;
        y_b = y_c;
        id = Id;
        moveId=Id*100;
        State=true;
        this.Wave=Wave;
        setPoint();
        setValue();
        setHitboxRadius();
        t=t1=System.currentTimeMillis();
    }

    public void create() {
        batch = new SpriteBatch();
    }

    public static void render() {
        for (int i = 0; i < enemy_arr.size(); i++) {
            if (enemy_arr.elementAt(i).State) {
                enemy_arr.elementAt(i).setMove();
                enemy_arr.elementAt(i).renders();
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

    private void renders() { // loop
        batch.begin();
        batch.draw(Assets.texture_enemy, (int)(x- (Assets.texture_enemy.getWidth()*scale/2)),(int)(y- (Assets.texture_enemy.getHeight()*scale/2)),Assets.texture_enemy.getWidth()*scale,Assets.texture_enemy.getHeight()*scale);
        batch.end();
        y += y_move;
        x += x_move;
    }

    public static void fire(){
        for (int i=0; i<enemy_arr.size(); i++){
            if (enemy_arr.elementAt(i).State)
            enemy_arr.elementAt(i).Bullet_Call();
        }
    }

    public static void Enemy_Reallo(float x_c, float y_c, int id, int Wave) {
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

    private void Revive(float x_c,float y_c,int id, int Wave){
        this.x_b=x_c;
        this.y_b=y_c;
        this.x=x_c;
        this.y=y_c;
        this.id=id;
        this.moveId=id*100;
        this.State=true;
        this.Wave=Wave;
        setPoint();
        setValue();
        setHitboxRadius();
        t=t1=System.currentTimeMillis();
    }

    public static void checkCollision(){
        for (int i=0;i<enemy_arr.size();i++) {
            if (enemy_arr.elementAt(i).State) {
                enemy_arr.elementAt(i).checksCollision();
                if (enemy_arr.elementAt(i).value < 0) enemy_arr.elementAt(i).Execute();
            }
        }
    }

    private void Execute(){
        State=false;
        Item.drop(x,y,id);
        id=0;
    }

    private void checksCollision(){
        for (int i=0;i<Bullet.bullet_arr.size();i++) {
            if ((!Bullet.bullet_arr.elementAt(i).isDed())&&
                    (Bullet.bullet_arr.elementAt(i).id*id<0)&&
                    (Math.sqrt(Math.pow((double)Bullet.bullet_arr.elementAt(i).getX()-x,2.0)+
                    Math.pow((double)Bullet.bullet_arr.elementAt(i).getY()-y,2.0))<
                            (double)Bullet.bullet_arr.elementAt(i).hitboxRadius+hitboxRadius)){
                value-=Bullet.bullet_arr.elementAt(i).getValue();
                Bullet.bullet_arr.elementAt(i).Execute();
            }
        }
    }
/*----------------------------------------------------------------------
* ----------------------------------------------------------------------
* --------------------------Edit - add more-----------------------------
* -------------Enemies' move, shooting, HP, point, hitbox---------------
* ----------------------------------------------------------------------
* ----------------------------------------------------------------------*/

    private void Bullet_Call() {              //Furthermore edit here
        X = x - MyGdxGame.player.x;
        Y = y - MyGdxGame.player.y;
        D = Math.sqrt((double) X * (double) X + (double) Y * (double) Y);
        switch (id) {
            case 1:
                if ((System.currentTimeMillis() - t) > 1000 - Wave * 10) {
                    Bullet.Bullet_Reallo(x, y, -X / (float) D, -Y / (float) D, 1);
                    Bullet.Bullet_Reallo(x - 10, y, -X / (float) D, -Y / (float) D, 1);
                    Bullet.Bullet_Reallo(x + 10, y, -X / (float) D, -Y / (float) D, 1);
                    t = System.currentTimeMillis();
                }
                break;
            case 2:
            case 3:
                if ((System.currentTimeMillis() - t) > 1000) {
                    Bullet.Bullet_Reallo(x, y, -X / (float) D, -Y / (float) D, 1);
                    Bullet.Bullet_Reallo(x - 20, y, -(X - 20) / (float) D, -Y / (float) D, 1);
                    Bullet.Bullet_Reallo(x + 20, y, -(X + 20) / (float) D, -Y / (float) D, 1);
                    t=System.currentTimeMillis();
                }
                break;
        }
    }
    //edit enemy shooting

    private void setValue(){
        switch (id) {
            case 1:
                value=10+Wave;
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

    private void setPoint(){
        switch (id){
            case 1:
                break;
        }
    }
    //set enemy point base on id

    private void setHitboxRadius(){
        switch (id){
            case 1:
            case 2:
            case 3:
                hitboxRadius=20;
                break;
        }
    }
    //set enemy hitbox base on id

    private void setMove() {                  //Furthermore edit enemy move here
        X = x - x_b;
        Y = y - y_b;
        D = Math.sqrt((double) X * (double) X + (double) Y * (double) Y);
        switch (moveId) {
            case 100:
                setX_move(2*randPara.nextFloat());
                setY_move(2*randPara.nextFloat());
                if (randPara.nextFloat()<0.5) setX_move(-x_move);
                if (randPara.nextFloat()<0.5) setY_move(-y_move);
                if ((x+x_move<480)||(x+x_move>800)) setX_move(-x_move);
                if ((y+y_move<480)||(y+y_move>720)) setY_move(-y_move);
                if ((System.currentTimeMillis()-t1>Waves.getWavetime()-4000)&&(x>640)) moveId+=3;
                if ((System.currentTimeMillis()-t1>Waves.getWavetime()-4000)&&(x<640)) moveId+=13;
                break;
            case 103:
                speed=-2;
                setY_move(2*randPara.nextFloat());
                if (randPara.nextFloat()<0.5) setY_move(-y_move);
                if (x<0) State=false;
                break;
            case 113:
                speed=2;
                setY_move(2*randPara.nextFloat());
                if (randPara.nextFloat()<0.5) setY_move(-y_move);
                if (x>1280) State=false;
                break;
            case 200:
                speed=-1;
                setX_move(0);
                moveId+=4;
                break;
            case 201:
                speed=1;
                if (System.currentTimeMillis()-t1>7000) moveId+=13;
                break;
            case 204:
                if (System.currentTimeMillis()-t1>2000) moveId-=3;
                break;
            case 214:
                if (y>720) State=false;
                break;
            case 300:
                x_move=2;
                y_move=0;
                y_move=2*randPara.nextFloat();
                if (randPara.nextFloat()<0.5) y_move=-y_move;
                if ((y+y_move<480)||(y+y_move>720)) y_move=-y_move;
                if(x>1280) State=false;
                break;
        }
        switch (moveId%10) {  // Default and simple move(don't edit here)
            case 1: //stay still
                setX_move(0);
                setY_move(0);
                break;
            case 2: //spin and keep distance
                setX_move(((Y-X*0.1f)*speed/(float)D));
                setY_move(((-X-Y*0.1f)*speed/(float)D));
                break;
            case 3: //move horizontal
                setX_move(speed);
                break;
            case 4: //move vertical
                setY_move(speed);
                break;
        }
    }
    //Furthermore edit enemy move here
}