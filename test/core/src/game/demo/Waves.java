package game.demo;

import java.util.Random;
import java.util.Vector;

public class Waves {
    private static Random waveid=new Random();
    private static long wavetime=5000;
    private static long t=System.currentTimeMillis();
    private static int gotonext;

    public static boolean Wave_Come(){
        if (System.currentTimeMillis() - t > wavetime) {
            t = System.currentTimeMillis();
            gotonext = 1;
        } else {
            gotonext = 0;
        }
        if (gotonext==1) return true;
        else return false;
    }

    public static void Wave_Call(Vector<Enemy> enemy_arr, Vector<Bullet> bullet_arr, int Wave) {
        int id=waveid.nextInt();
        switch (id){
            case 1:
                wavetime=0;
                Enemy.Enemy_Reallo(enemy_arr,0,0,0,0);//this is just an example
                break;
        }
    }
}
