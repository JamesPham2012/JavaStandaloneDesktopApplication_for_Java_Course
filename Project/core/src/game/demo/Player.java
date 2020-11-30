package game.demo;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Vector;

public class Player extends GameObj{
    private SpriteBatch batch;
    private int loaded=0;
    private long t=System.currentTimeMillis()-200;
    private long rapidity;
    public static double power;

    public void create(int width, int height){
        scale = 0.4f;
        batch = new SpriteBatch();
        hitboxRadius=10;
        State=true;
        Texture_Width=width;
        Texture_Height=height;
        setId(1);
    }

    public void setId(int id){
        this.id=-id;
    }
    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }
    public boolean fire(){
        if (Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_LEFT)) Bullet.resetfakebase();
        if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
            if (System.currentTimeMillis() - t > rapidity) {
                t = System.currentTimeMillis();
                return true;
            } return false;

        }
        else return false;

    }

    public void render_player () { // loop
        if (State) {
            batch.begin();
            batch.draw(Assets.texture_plane, (x - 20), (y - 20), 40, 40);
            batch.end();
        }
        input();

    }
    public void Collision(Vector<PixelCoord> Hail,Vector<Bullet> Barr){
        loop:  for(int i=0;i<Pixel.size();i++){
            for(int j=0;j<Hail.size();j++){
                if(Pixel.elementAt(i).VicinityBullet(Hail.elementAt(j))){
                    Barr.elementAt(Hail.elementAt(j).location_Bullet).Execute();
                    this.Execute();
                    break loop;
                }
            }
        }
    }
    public void dispose () {
        batch.dispose();
    }

    public static void checkCollisionwthBullet(){
        for (int i=0;i<Bullet.bullet_arr.size();i++) {
            if ((Bullet.bullet_arr.elementAt(i).State)&&
                    (Bullet.bullet_arr.elementAt(i).id*MyGdxGame.player.id<0)&&
                    (Math.sqrt(Math.pow((double)Bullet.bullet_arr.elementAt(i).getX()-MyGdxGame.player.x,2.0)+
                            Math.pow((double)Bullet.bullet_arr.elementAt(i).getY()-MyGdxGame.player.y,2.0))<
                            (double)Bullet.bullet_arr.elementAt(i).hitboxRadius+MyGdxGame.player.hitboxRadius)){
                MyGdxGame.player.Execute();
            }
        }
    }

    public static void checkCollisionwthEnemy(){
        for (int i=0;i<Enemy.enemy_arr.size();i++) {
            if ((Enemy.enemy_arr.elementAt(i).State)&&
                    (Math.sqrt(Math.pow((double)Enemy.enemy_arr.elementAt(i).getX()-MyGdxGame.player.x,2.0)+
                            Math.pow((double)Enemy.enemy_arr.elementAt(i).getY()-MyGdxGame.player.y,2.0))<
                            (double)Enemy.enemy_arr.elementAt(i).hitboxRadius+MyGdxGame.player.hitboxRadius)){
                MyGdxGame.player.Execute();
            }
        }
    }

    public void Execute(){
        State=false;
    }
    public double getPower() { return power; }
    public void setPower(double power) { this.power=power; }

    /* ----------------------------------------------------------------------
     * ----------------------------------------------------------------------
     * --------------------------Edit - add more-----------------------------
     * --------------------------Player's data-------------------------------
     * ----------------------------------------------------------------------
     * ----------------------------------------------------------------------*/

    public void input(){
        switch (id) {
            case 0:
            case -1:
                if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                    speed=1;
                    rapidity=50;
                }
                else {
                    speed=5;
                    rapidity=100;
                }
                break;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) x-=speed;
        if (x<0) x=0;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) x+=speed;
        if (x>Gdx.graphics.getWidth()) x=Gdx.graphics.getWidth();
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) y+=speed;
        if (y>Gdx.graphics.getHeight()) y=Gdx.graphics.getHeight();
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) y-=speed;
        if (y<0) y=0;
    }

    public void Bullet_Call() {              //Furthermore edit here
        switch (Math.abs(id)) {
            case 0:
            {
                Bullet.Bullet_Reallo(getX(), getY(), 0, 30, -2,1);
                Bullet.Bullet_Reallo(getX(), getY(), 0, -30, -2,1);
                Bullet.Bullet_Reallo(getX(), getY(), 30, 0, -2,1);
                Bullet.Bullet_Reallo(getX(), getY(), -30, 0, -2,1);
                break;
            }
            case 1:
                /*     bullet_arr.addElement(new Bullet(getX(), getY(), 0, 30, 1));*/
            {
                if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                    Bullet.Bullet_Reallo(getX(), getY(), 0, 30, -3,1);
                    Bullet.Bullet_Reallo(getX(), getY(), -5, 29, -3,1);
                    Bullet.Bullet_Reallo(getX(), getY(), 5, 29, -3,1);
                    Bullet.Bullet_Reallo(getX(), getY(), 0, 25, -3,1);
                    Bullet.Bullet_Reallo(getX(), getY(), -10, 28, -3,1);
                    Bullet.Bullet_Reallo(getX(), getY(), 10, 28, -3,1);
                }
                else {
                    Bullet.Bullet_Reallo(getX(), getY(), 0, 30, -1,1);
                    Bullet.Bullet_Reallo(getX()-3, getY(), -2, 29, -1,1);
                    Bullet.Bullet_Reallo(getX()+3, getY(), 2, 29, -1,1);
                    Bullet.Bullet_Reallo(getX()-5, getY(), -5, 28, -1,1);
                    Bullet.Bullet_Reallo(getX()+5, getY(), 5, 28, -1,1);
                }
            }
            break;
        }
    }
}