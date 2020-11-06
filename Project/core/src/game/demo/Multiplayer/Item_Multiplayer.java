package game.demo.Multiplayer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import game.demo.Assets;
import game.demo.GameObj;

import java.util.Vector;

public class Item_Multiplayer extends GameObj {

    private SpriteBatch batch;
    private long t;
    public static Vector<Item_Multiplayer> item_arr=new Vector<>();

    private Item_Multiplayer(float x_c, float y_c, int id, int moveId) {
        this.x = x_c;
        this.y = y_c;
        this.moveId = moveId;
        this.id=id;
        State=true;
        setHitboxRadius();
        setMove();
        t=System.currentTimeMillis();
    }

    public void setX_move(float x_move) { this.x_move = x_move; }

    public void setY_move(float y_move) {
        this.y_move = y_move;
    }

    public void create() {

        batch = new SpriteBatch();
        State=true;
    }

    public void renders(SpriteBatch batch) { // loop
        batch.draw(Assets.texture_item, x-10,y-10,20,20);
        y += y_move;
        x += x_move;
    }

    public static void render(SpriteBatch batch) {
        for (int i = 0; i < item_arr.size(); i++) {
            if (item_arr.elementAt(i).State) {
                item_arr.elementAt(i).setMove();
                item_arr.elementAt(i).renders(batch);
                item_arr.elementAt(i).isCollected();
            }
        }
    }

    public void dispose() {
        batch.dispose();
    }

    public static void Item_Reallo(float x_c, float y_c,int id, int moveId) {
        int BulletGen = 0;
        loop:     while (BulletGen == 0) {
            for (int i = 0; i < item_arr.size(); i++) {                                                                           //there exists at least an element in the array
                if (item_arr.elementAt(i).isDed()) {
                    item_arr.elementAt(i).Revive(x_c, y_c, id, moveId);
                    BulletGen = 1;
                    continue loop;
                }
            }
            item_arr.addElement(new Item_Multiplayer(x_c, y_c,id, moveId));
            item_arr.lastElement().setParam();
            item_arr.lastElement().create();
            BulletGen = 1;
        }
    }

    public boolean isDed() {
        return !State;
    }

    private void Revive(float x_c,float y_c, int id, int moveId){
        this.x=x_c;
        this.y=y_c;
        this.id=id;
        this.moveId=moveId;
        this.State=true;
        setHitboxRadius();
        setMove();
        t=System.currentTimeMillis();
    }

    private void setMove() {
        switch (moveId){
            case 0:
                setX_move(0);
                setY_move(-3);
                if (y<0) Execute();
                break;
            case 1:
                setX_move(0);
                setY_move(3);
                moveId+=2;
                break;
            case 2:
                setX_move(randPara.nextFloat()-0.5f);
                setY_move((float)randPara.nextInt(10));
                moveId+=1;
                break;
            case 3:
                if (System.currentTimeMillis()-t>100) moveId=0;
                break;
        }
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
        hitboxRadius=5;
    }

    private void isCollected(){
        if (Math.sqrt(Math.pow((double)x-MultiplayerGame.player.x,2.0)+
                Math.pow((double)y-MultiplayerGame.player.y,2.0))<
                (double)hitboxRadius+MultiplayerGame.player.hitboxRadius) {
            switch (id) {
                case 1:
                    MultiplayerGame.player.setPower(MultiplayerGame.player.getPower() + 0.01);
                    break;
            }
            Execute();
        }
    }

    public static void drop(float x, float y, int id){
//        switch (id) {
//
//        }
        Item_Reallo(x,y,1,1);
    }
}