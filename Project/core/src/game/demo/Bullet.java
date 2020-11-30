package game.demo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Vector;
import java.lang.Math;

public class Bullet extends GameObj {
    private SpriteBatch batch;
    private long t=0l;
    protected int Source_ID_Collision=0;
    private static Bullet fakebase=new Bullet(0,0,0,0);
    public static Vector<Bullet> bullet_arr=new Vector<>();

    private Bullet(float x_c, float y_c, float xfromhost, float yfromhost, int Id, int CollisionID) {
        x = x_c + xfromhost;
        x_b = x_c;
        y = y_c + yfromhost;
        y_b = y_c;
        moveId = Id*100;
        id=Id;
        Source_ID_Collision=CollisionID;
        State=true;
        t=System.currentTimeMillis();
    }

    private Bullet(float x_c, float y_c, int Id,int CollisionID) {
        x = x_c;
        x_b = x_c;
        y = y_c;
        y_b = y_c;
        id = Id;
        Source_ID_Collision=CollisionID;
    }

    public static void resetfakebase(){
        fakebase.x_b=MyGdxGame.player.getX();
        fakebase.y_b=MyGdxGame.player.getY();
    }

    public void setX_move(float x_move) { this.x_move = x_move; }

    public void setY_move(float y_move) {
        this.y_move = y_move;
    }

    private void create() {
        batch = new SpriteBatch();
    }

    private void renders(GameObj playerObject,int location_of_Bullet,Vector<PixelCoord> Pixel1,Vector<PixelCoord> Pixel2) { // loop
        batch.begin();
        batch.draw(Assets.texture_bullet, (x- (Assets.texture_bullet.getWidth()/2)),(y- (Assets.texture_bullet.getHeight()/2)));
        if (this.isAlly()){
            for(int i=0;i< Enemy.enemy_arr.size();i++){
                if ((this.distanceto(Enemy.enemy_arr.elementAt(i))<(Enemy.enemy_arr.elementAt(i).getSonarRange()+this.getSonarRange()))||(this.distanceto2(Enemy.enemy_arr.elementAt(i))<(Enemy.enemy_arr.elementAt(i).getSonarRange()+this.getSonarRange()))){
                    Pixel1.addElement(new PixelCoord(x- (Assets.texture_bullet.getWidth()*scale/2),y- (Assets.texture_bullet.getHeight()*scale/2),location_of_Bullet,i));
                }
            }
        }
        if (!this.isAlly()){
            if ((this.distanceto(playerObject)<(playerObject.getSonarRange()+this.getSonarRange()))||(this.distanceto2(playerObject)<(playerObject.getSonarRange()+this.getSonarRange()))){
                Pixel2.addElement(new PixelCoord(x- (Assets.texture_bullet.getWidth()*scale/2),y- (Assets.texture_bullet.getHeight()*scale/2),location_of_Bullet));
            }
        }        batch.end();
        y += y_move;
        x += x_move;
        if (System.currentTimeMillis()-t>10000){
            State=false;
        }
    }
    protected boolean isAlly(){
        return (this.Source_ID_Collision==1);
    }
    public static void render(Player player,Vector<PixelCoord> Pixel1,Vector<PixelCoord> Pixel2) {
        for (int i = 0; i < bullet_arr.size(); i++) {
            if (bullet_arr.elementAt(i).State) {
                bullet_arr.elementAt(i).setMove();
                bullet_arr.elementAt(i).renders(player,i,Pixel1,Pixel2);
            }
        }
    }

    public void dispose() {
        batch.dispose();
    }

    public static void Bullet_Reallo(float x_c, float y_c, float x_offset, float y_offset,int id,int Collision_ID) {
        int BulletGen = 0;
        loop:     while (BulletGen == 0) {
            // need change to replace DED value with ALIVE value in order for the vector not to be too long, waste of memory
            for (int i = 0; i < bullet_arr.size(); i++) {                                                                           //there exists at least an element in the array
                if (bullet_arr.elementAt(i).isDed()) {                                    // there is 1 dead bullet
                    bullet_arr.elementAt(i).Revive(x_c, y_c, x_offset,y_offset,id,Collision_ID);//revive it as a new bullet
                    BulletGen = 1;

                    /*Gdx.app.log("Log", "Bullet number "+i+" revived");*/
                    continue loop;
                }
            }
            // if the code get here, there is NO dead bullet in the array
            bullet_arr.addElement(new Bullet(x_c, y_c,x_offset,y_offset,id,Collision_ID));// create a new bullet
            bullet_arr.lastElement().setParam();
            bullet_arr.lastElement().create();
            BulletGen = 1;
        }
    }

    public boolean isDed() {
        return !State;
    }

    private void Revive(float x_c,float y_c, float x_offset, float y_offset,int id,int Collision_Id){
        this.x_b=x_c;
        this.y_b=y_c;
        this.x=x_c+x_offset;
        this.y=y_c+y_offset;
        this.id=id;
        this.moveId=id*100;
        this.State=true;
        setHitboxRadius();
        setValue();
        Source_ID_Collision = Collision_Id;
        t=System.currentTimeMillis();
    }

    public void Execute(){
        State=false;
        id=0;
    }

    /* ----------------------------------------------------------------------
     * ----------------------------------------------------------------------
     * --------------------------Edit - add more-----------------------------
     * -------------------Bullets' hitbox, value, orbit----------------------
     * ----------------------------------------------------------------------
     * ----------------------------------------------------------------------*/

    private void setHitboxRadius() {
        switch (id){
            case -1:
            case -2:
            case -3:
            case 1:
                hitboxRadius=((float)Assets.texture_bullet.getHeight()+(float)Assets.texture_bullet.getWidth())/4;
                break;
        }
    }

    private void setValue(){
        value=1;
    }

    private void setMove() {                  //Furthermore edit bullet orbit here
        X = x - x_b;
        Y = y - y_b;
        D = Math.sqrt((double) X * (double) X + (double) Y * (double) Y);
        switch (moveId) {
            case -100://straight
                speed=10;
                moveId=1;
                break;
            case -200://spin around host
                speed=10/(float)Math.sqrt(D);
                moveId=2;
                break;
            case -300:
                x_b=fakebase.x_b;
                y_b=fakebase.y_b;
                moveId=-100;
                break;
            case 100:
                speed=3;
                moveId+=1;
                break;
        }
        switch (moveId%10) {  // Default and simple move
            case 1: //straight
                setX_move(speed * X / (float) D);
                setY_move(speed * Y / (float) D);
                if((x>1280)||(x<0)||(y<0)||(y>720)) Execute();
                break;
            case 2: //spin and keep distance
                setX_move(((Y-X*0.1f)*speed/(float)D));
                setY_move(((-X-Y*0.1f)*speed/(float)D));
                if (System.currentTimeMillis()-t>5000) Execute();
                break;
            case 3: //spin and spread
                setX_move(((Y+X*0.3f)*speed/(float)D));
                setY_move(((-X+Y*0.3f)*speed/(float)D));
                break;
        }
    }
}