package com.mygdx.game;

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

    public static int Wave = 0;

    public static void Wave_Come() {
        if (System.currentTimeMillis() - t > wavetime) {
            t = System.currentTimeMillis();
            Loadwave = true;
            id=waveid.nextInt(4);
            enemies=0;
        }
        if (System.currentTimeMillis() - t <= wavetime){
            if (Loadwave) {
                Wave_Call();
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

    public static long getWavetime() {return wavetime;}

    public static void reset(){
        wavetime=0;
    }

    private static void Wave_Call() {
        Wave = MyGdxGame.Wave;
        switch (id){
            case 1:
                wavetime=10000+(long)Math.sqrt(Wave)*1000;
                if (wavetime>20000) wavetime=20000;
                MyGdxGame.Wave++;
                if (Wave>10)
                    Wave=10;
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
                MyGdxGame.Wave++;
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
                    MyGdxGame.Wave++;
                }
                break;
        }
    }
}
