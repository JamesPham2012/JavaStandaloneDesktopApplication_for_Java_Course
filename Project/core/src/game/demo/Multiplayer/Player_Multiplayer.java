package game.demo.Multiplayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import game.demo.Assets;
import game.demo.GameObj;

import java.util.Vector;

public class Player_Multiplayer extends GameObj {
    private int loaded=0;
    private long t=System.currentTimeMillis()-200;
    private float varyDistance;
    private long rapidity;
    private boolean controllable = true; // to distinguish player and coop_player--> coop_player is false
    private boolean fire; // Fire and shift_left variables are on/off buttons for coop_player.
    private boolean shift_left;
    private Texture texture;
    public void create(){
        texture =  Assets.texture_plane;
        scale = 0.4f;
        hitboxRadius=10;
        State=true;
        setId(1);
    }

    public void setTexture(Texture texture){
        this.texture = texture;
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
    public void setX(float x){
        this.x = x;
    }
    public void setControllable(boolean controllable){
        this.controllable = controllable;
    }
    public void setY(float y){
        this.y = y;
    }
    public void setExecute(boolean execute){
        this.State = !execute;
    }

    public boolean fire(){
        if(controllable){
            if (Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_LEFT)) Bullet_Multiplayer.resetfakebase();
            if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
                if (System.currentTimeMillis() - t > rapidity) {
                    t = System.currentTimeMillis();
                    loaded = 1;
                } else {
                    loaded = 0;
                }
                if (loaded==1) return true;
                else return false;

            }
            else return false;
        }
        else{
            if(shift_left) Bullet_Multiplayer.resetfakebase();
            if(fire){
                if (System.currentTimeMillis() - t > rapidity) {
                    t = System.currentTimeMillis();
                    loaded = 1;
                } else {
                    loaded = 0;
                }
                if (loaded==1) return true;
                else return false;
            }
            else return false;
        }

    }
    public void setFire(boolean fire){
        this.fire = fire;
    }


    public void render_player (SpriteBatch batch) { // loop
        if (State) {
            batch.draw(texture, (int) (x - 20), (int) (y -20),
                    40, 40);
        }
        input();

    }
    public void dispose () {
        batch.dispose();
    }

    public static void checkCollision(Vector<Bullet_Multiplayer> bullet_arr){
        for (int i=0;i<bullet_arr.size();i++) {
            if ((!bullet_arr.elementAt(i).isDed())&&
                    (bullet_arr.elementAt(i).id*MultiplayerGame.player.id<0)&&
                    (Math.sqrt(Math.pow((double)bullet_arr.elementAt(i).getX()-MultiplayerGame.player.x,2.0)+
                            Math.pow((double)bullet_arr.elementAt(i).getY()-MultiplayerGame.player.y,2.0))<
                            (double)bullet_arr.elementAt(i).hitboxRadius+MultiplayerGame.player.hitboxRadius)){
                                    MultiplayerGame.player.Execute();
            }
        }
    }

    public boolean Execute(){
        State=false;
        System.out.println("Dead");
        return true;
    }

    /* ----------------------------------------------------------------------
     * ----------------------------------------------------------------------
     * --------------------------Edit - add more-----------------------------
     * --------------------------Player's data-------------------------------
     * ----------------------------------------------------------------------
     * ----------------------------------------------------------------------*/

    public boolean input(){
        if(controllable&&State){ // we just control our player not coop_player.
            float xPrevious = x;
            float yPrevious = y;
            switch (id) {
                case -1:
                    if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                        varyDistance=1;
                        rapidity=50;
                    }
                    else {
                        varyDistance=5;
                        rapidity=100;
                    }
                    break;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) x-=varyDistance;
            if (x<0) x=0;
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) x+=varyDistance;
            if (x>Gdx.graphics.getWidth()) x=Gdx.graphics.getWidth();
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) y+=varyDistance;
            if (y>Gdx.graphics.getHeight()) y=Gdx.graphics.getHeight();
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) y-=varyDistance;
            if (y<0) y=0;
            if(x==xPrevious&&y==yPrevious){
                return false;
            }
            else{
                return true;
            }
        }
        else{
            return false;
        }

    }

    public void Bullet_Call(Vector<Bullet_Multiplayer> bullet_arr) {              //Furthermore edit here
        switch (Math.abs(id)) {
            case 0:
            {
                Bullet_Multiplayer.Bullet_Reallo(bullet_arr,getX(), getY(), 0, 30, 0);
                Bullet_Multiplayer.Bullet_Reallo(bullet_arr,getX(), getY(), 0, -30, 0);
                Bullet_Multiplayer.Bullet_Reallo(bullet_arr,getX(), getY(), 30, 0, 0);
                Bullet_Multiplayer.Bullet_Reallo(bullet_arr,getX(), getY(), -30, 0, 0);
                break;
            }
            case 1:
                /*     bullet_arr.addElement(new Bullet(getX(), getY(), 0, 30, 1));*/
            {
                if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                    Bullet_Multiplayer.Bullet_Reallo(bullet_arr,getX(), getY(), 0, 30, -3);
                    Bullet_Multiplayer.Bullet_Reallo(bullet_arr,getX(), getY(), -5, 29, -3);
                    Bullet_Multiplayer.Bullet_Reallo(bullet_arr,getX(), getY(), 5, 29, -3);
                    Bullet_Multiplayer.Bullet_Reallo(bullet_arr,getX(), getY(), 0, 25, -3);
                    Bullet_Multiplayer.Bullet_Reallo(bullet_arr,getX(), getY(), -10, 28, -3);
                    Bullet_Multiplayer.Bullet_Reallo(bullet_arr,getX(), getY(), 10, 28, -3);
                }
                else {
                    Bullet_Multiplayer.Bullet_Reallo(bullet_arr,getX(), getY(), 0, 30, -1);
                    Bullet_Multiplayer.Bullet_Reallo(bullet_arr,getX()-3, getY(), -2, 29, -1);
                    Bullet_Multiplayer.Bullet_Reallo(bullet_arr,getX()+3, getY(), 2, 29, -1);
                    Bullet_Multiplayer.Bullet_Reallo(bullet_arr,getX()-5, getY(), -5, 28, -1);
                    Bullet_Multiplayer.Bullet_Reallo(bullet_arr,getX()+5, getY(), 5, 28, -1);
                }
            }
            break;
        }
    }
}