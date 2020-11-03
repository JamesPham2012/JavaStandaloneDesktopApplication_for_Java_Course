package game.demo;
import com.badlogic.gdx.Gdx;

import java.lang.Object;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

public class Waves {
    private static Random waveid=new Random();
    private static long wavetime=0;
    private static long t=System.currentTimeMillis();
    //    private static long t1=System.currentTimeMillis();
    private static boolean Loadwave;
    private static int enemies=0;
    private static int id;
    private static boolean isAllEGone;
    private static Object obj=new Object();

    public static void Wave_Come(Vector<Enemy> enemy_arr) {
        if (System.currentTimeMillis() - t > wavetime) {
            t = System.currentTimeMillis();
            Loadwave = true;
            MyGdxGame.Wave++;
            id=waveid.nextInt(3);
            enemies=0;
        }
        if (System.currentTimeMillis() - t <= wavetime){
            if (Loadwave) {
                Wave_Call(enemy_arr, MyGdxGame.Wave);
            }
            /*if (!Loadwave){
                isAllEGone=false;
                for (int i=0;i<enemy_arr.size();i++){
                    if (!enemy_arr.elementAt(i).State) {
                        isAllEGone=true;
                        break;
                    }
                }
                if (isAllEGone) t=System.currentTimeMillis()-wavetime+500;
            }*/
        }
    }

    public static void reset(){
        wavetime=0;
    }

    private static void Wave_Call(Vector<Enemy> enemy_arr, int Wave) {
        switch (id){
            case 1:
                wavetime=20000;
                for (int i=0; i<(Wave+1)/2; i++){
                    Enemy.Enemy_Reallo(enemy_arr,(float)(waveid.nextInt(320)+480), (float)(waveid.nextInt(160)+560), 1, Wave);//this is just an example
                }
                Loadwave=false;
                break;
            case 2:
                wavetime=10000;
                for (int i=0; i<8; i++){
                    Enemy.Enemy_Reallo(enemy_arr,(float)(80+160*i)*Gdx.graphics.getWidth()/1280,720* Gdx.graphics.getHeight()/720,2,Wave);
                }
                Loadwave=false;
                break;
            case 3:
                wavetime=10000;
                if (System.currentTimeMillis()-t>1000){
                    Enemy.Enemy_Reallo(enemy_arr, 0, (float) (waveid.nextInt(160) + 560), 3, Wave);
                    t=System.currentTimeMillis();
                    enemies++;
                }
                if (enemies==Wave) Loadwave=false;
                break;
        }
    }
}