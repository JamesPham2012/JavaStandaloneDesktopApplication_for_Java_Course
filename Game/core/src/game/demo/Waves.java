package game.demo;

import java.util.Random;
import java.util.Vector;

public class Waves {
    private static Random waveid=new Random();
    private static long wavetime=0;
    private static long t=System.currentTimeMillis();
    private static int gotonext;

    public static boolean Wave_Come(){
        if (System.currentTimeMillis() - t > wavetime) {
            t = System.currentTimeMillis();
            gotonext = 1;
        } else {
            gotonext = 0;
        }
        if (gotonext==1) {
            MyGdxGame.Wave++;
            return true;
        }
        else return false;
    }

    public static void reset(){
        wavetime=5000;
    }

    public static void Wave_Call(Vector<Enemy> enemy_arr, int Wave) {
        int id=0/*waveid.nextInt(1)*/;
        switch (id){
            case 0:
                wavetime=30000;
                for (int i=0; i<Wave; i++){
                    Enemy.Enemy_Reallo(enemy_arr,(float)(waveid.nextInt(320)+480), (float)(waveid.nextInt(160)+560), 1, Wave);//this is just an example
                }
                break;
        }
    }
}