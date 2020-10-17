package game.demo;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Vector;

public class Enemy extends GameObj {
    private SpriteBatch batch;
    private static Texture texture;
    private long t=0;
    private long point;

    public Enemy(float x_c, float y_c, int Id, int Wave) {
        x = x_c;
        x_b = x_c;
        y = y_c;
        y_b = y_c;
        id = Id;
        setPoint();
        setValue(Wave);
    }

    public void create() {
        batch = new SpriteBatch();
        texture = new Texture("Bullet_plane.png");
    }

    public static void render(Vector<Enemy> enemy_arr) {
        for (int i = 0; i < enemy_arr.size(); i++) {
            if (enemy_arr.elementAt(i).State) {
                enemy_arr.elementAt(i).setMove();
                enemy_arr.elementAt(i).render();
            }
        }
    }
    public void setX_move(float x_move){this.x_move=x_move;}
    public void setY_move(float y_move){this.y_move=y_move;}
    public void setPoint(){
        switch (id){
            case 1:
                break;
        }
    }
    //set enemy point base on id

    public void setValue(int Wave){
        switch (id) {
        case 1:
        break;
        }
    }
    //set enemy value base on id

    public void setMove() {                  //Furthermore edit enemy move here
        X = x - x_b;
        Y = y - y_b;
        D = Math.sqrt((double) X * (double) X + (double) Y * (double) Y);
        switch (id) {
            case 1:
                break;
        }
    }
    //Furthermore edit enemy move here

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

    public void render() { // loop
        batch.begin();
        batch.draw(texture, x, y);
        batch.end();
        y += y_move;
        x += x_move;
        if (System.currentTimeMillis()-t>10000){ //replace with another condition
            State=false;
        }
    }

    public static void fire(Vector<Enemy> enemy_arr, Vector<Bullet> bullet_arr){
        for (int i=0; i<enemy_arr.size(); i++){
            enemy_arr.elementAt(i).Bullet_Call(bullet_arr);
        }
    }

    public void Bullet_Call(Vector<Bullet> bullet_arr) {              //Furthermore edit here
        switch (id) {
            case 1:
                break;
        }
    }
    //edit enemy bullet

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
        this.State=true;
        setPoint();
        setValue(Wave);
        t=System.currentTimeMillis();
    }
}
