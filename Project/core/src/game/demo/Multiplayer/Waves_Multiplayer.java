package game.demo.Multiplayer;
import java.lang.Object;
import java.util.Vector;

public class Waves_Multiplayer {
    private static long wavetime=0;
    private static long t=System.currentTimeMillis();
    //    private static long t1=System.currentTimeMillis();
    private static boolean Loadwave;
    private static int enemies=0;
    private static int id;
    private static boolean isAllEGone;
    private static Object obj=new Object();

    public static void Wave_Come(Vector<Enemy_Multiplayer> enemy_arr) {
        if (System.currentTimeMillis() - t > wavetime) {
            t = System.currentTimeMillis();
            Loadwave = true;
            MultiplayerGame.Wave++;
            id=MultiplayerGame.RandomBound(2,4);
            enemies=0;

        }
        if (System.currentTimeMillis() - t <= wavetime){
            if (Loadwave) {
                Wave_Call(enemy_arr, MultiplayerGame.Wave);
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

    private static void Wave_Call(Vector<Enemy_Multiplayer> enemy_arr, int Wave) {
        switch (id){
            case 1:
                wavetime=20000;
                for (int i=0; i<(Wave+1)/2; i++){

                    Enemy_Multiplayer.Enemy_Reallo(enemy_arr,(float)(MultiplayerGame.RandomBound(320,480)), (float)(MultiplayerGame.RandomBound(120,560)), 1, Wave);//this is just an example
                }
                Loadwave=false;
                break;
            case 2:
                wavetime=10000;
                for (int i=0; i<8; i++){
                    Enemy_Multiplayer.Enemy_Reallo(enemy_arr,(float)80+160*i,720,2,Wave);
                }
                Loadwave=false;
                break;
            case 3:
                wavetime=10000;
                if (System.currentTimeMillis()-t>1000){
                    Enemy_Multiplayer.Enemy_Reallo(enemy_arr, 0, (float) (MultiplayerGame.RandomBound(160,560)), 3, Wave);
                    t=System.currentTimeMillis();
                    enemies++;
                }
                if (enemies==Wave) Loadwave=false;
                break;
        }
    }
}