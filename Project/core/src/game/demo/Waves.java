package game.demo;
import game.demo.Multiplayer.Enemy_Multiplayer;

import java.lang.Object;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

public class Waves {
    private static Random waveid=new Random();
    private static long wavetime=0;
    private static long t=System.currentTimeMillis();
    private static boolean Loadwave;
    private static int enemies=0;
    private static int id;
    private static boolean isAllEGone;
    private static Object obj=new Object();

    public static void Wave_Come() {
        if (System.currentTimeMillis() - t > wavetime) {
            t = System.currentTimeMillis();
            Loadwave = true;
            MyGdxGame.Wave++;
            id=waveid.nextInt(4);
            enemies=0;
        }
        if (System.currentTimeMillis() - t <= wavetime){
            if (Loadwave) {
                Wave_Call();
            }
//            // Khi chua giet het thi ra wave moi.
            if (!Loadwave) {
                if (Enemy.isClearedAll()) {
                    wavetime=0;
                }
            }
        }
    }

    public static long getWavetime() {return wavetime;}

    public static void reset(){
        wavetime=0;
    }

    private static void Wave_Call() {
        int Wave=MyGdxGame.Wave;
        switch (id){
            case 1:
                wavetime=10000+(long)Math.sqrt(Wave)*1000;
                if (wavetime>20000) wavetime=20000;
                if (Wave>10) Wave=10;
                for (int i=0; i<Wave+1; i++){
                    Enemy.Enemy_Reallo((float)(waveid.nextInt(320)+480), (float)(waveid.nextInt(160)+560), 1, Wave);//this is just an example
                }
                Loadwave=false;
                break;
            case 2:
                wavetime=10000;
                for (int i=0; i<8; i++){
                    Enemy.Enemy_Reallo((float)80+160*i,720,2,Wave);
                }
                Loadwave=false;
                break;
            case 3:
                wavetime=10000;
                if (System.currentTimeMillis()-t>1000){
                    for (int i=0;i<(Wave/10+1);i++) {
                        Enemy.Enemy_Reallo(0, (float) (waveid.nextInt(160) + 560-80*i), 3, Wave);
                        t = System.currentTimeMillis();
                        enemies++;
                    }
                }
                if ((enemies>=Wave+1)||(enemies>=30)) {
                    Loadwave=false;
                }
                break;
        }
    }
}