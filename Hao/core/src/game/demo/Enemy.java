package game.demo;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Vector;

public class Enemy extends GameObj {

    long timer1=System.currentTimeMillis();
    public int Loaded=1;
    public  Enemy(int x,int y){
        this.x=x;
        this.y=y;
        batch = new SpriteBatch();
        setScale(0.3f);
    }

    public void render_enemy(){
        batch.begin();
        batch.draw(Assets.texture_enemy, (int)(x- (Assets.texture_enemy.getWidth()*scale/2)),(int)(y- (Assets.texture_enemy.getHeight()*scale/2)),Assets.texture_enemy.getWidth()*scale,Assets.texture_enemy.getHeight()*scale);
        batch.end();
    }

    public boolean Autoshoot(){  //reload time 0.5 sec
        if (System.currentTimeMillis()-timer1>200){
            timer1=System.currentTimeMillis();
            Loaded=1;
        }else {Loaded=0;}

        if (Loaded==1){
            return true;
        }
        else return false;
    }

    public void Bullet_Call(Vector<Bullet> bullet_arr){
        Bullet.Bullet_Reallo(bullet_arr,getX(), getY(), 0, -30, 1);
    }

}
