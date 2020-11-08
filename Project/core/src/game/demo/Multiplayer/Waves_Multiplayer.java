package game.demo.Multiplayer;


import java.lang.Object;
import java.util.Random;
import java.util.Vector;

public class Waves_Multiplayer {
    private static long wavetime=0;
    private static Random waveid=new Random();
    private static long t=System.currentTimeMillis();
    //    private static long t1=System.currentTimeMillis();
    private static boolean Loadwave;
    private static int enemies=0;
    private static int id;
    private static boolean isAllEGone;
    private static Object obj=new Object();

    public static void Wave_Come() {
        if (System.currentTimeMillis() - t > wavetime) {
            System.out.println("First if is occured");
            t = System.currentTimeMillis();
            Loadwave = true;
            MultiplayerGame.Wave++;
            if(MultiplayerGame.Wave==1){
                id=2;
            }
            else{
                int temp = MultiplayerGame.RandomBound(1,3);
                if(temp>0){
                    id=MultiplayerGame.rand;
                }
            }
            //id= 2;
            System.out.println("id of wave "+MultiplayerGame.Wave+" is: "+id);
            enemies=0;

        }
        if (System.currentTimeMillis() - t <= wavetime){
            if (Loadwave && id>0) {
                System.out.println("Loadwave");
                Wave_Call();
            }
//            if (!Loadwave) {
//                if (Enemy.isClearedAll()) {
//                    System.out.println("clearall?");
//                    wavetime=0;
//                }
//            }

        }
    }
    public static long getWavetime() {return wavetime;}
    public static void reset(){
        wavetime=0;
    }

    private static void Wave_Call() {
        int Wave = MultiplayerGame.Wave;
        switch (id){
            case 1:
                int x=0;
                int y=0;
                wavetime=10000+(long)Math.sqrt(Wave)*1000;
                if (wavetime>20000) wavetime=20000;
                if (Wave>10) Wave=10;
                Loadwave=false;
                MultiplayerGame.rand = 0;
                try{
                    int temp = MultiplayerGame.RandomBound1(320,480,Wave+1);
                    for (int i=0; i<Wave+1; i++){// randomX, randomY
                        x=MultiplayerGame.randPosition1.get(i);
                        y=MultiplayerGame.randPosition2.get(i);
                        Enemy_Multiplayer.Enemy_Reallo((float)(x), (float)(y), 1, Wave);//this is just an example
                    }
                }catch(Exception e){
                    System.out.println(e);
                }

                break;
            case 2:
                wavetime=10000;
                for (int i=0; i<8; i++){
                    Enemy_Multiplayer.Enemy_Reallo((float)80+160*i,720,2,Wave);
                }
                Loadwave=false;
                MultiplayerGame.rand=0;
                break;
            case 3:
                wavetime=10000;
                MultiplayerGame.rand=0;

                if (System.currentTimeMillis()-t>1000){
                    try{
                        int temp = MultiplayerGame.RandomBound1(160,560,(Wave/10 +1));
                        System.out.println("Size of "+temp);
                        for (int i=0;i<temp;i++) {
                            int random=MultiplayerGame.randPosition1.get(i);
                            Enemy_Multiplayer.Enemy_Reallo(0, (float) (random), 3, Wave);
                            t = System.currentTimeMillis();
                            enemies++;
                        }
                    }catch(Exception e){
                        System.out.println(e);
                    }

                }
                if ((enemies>=Wave+1)||(enemies>=30)) {
                    Loadwave=false;
                }
                break;
        }
    }

//    private static void Wave_Call() {
//        int Wave=MultiplayerGame.Wave;
//        switch (id){
//            case 1:
//                wavetime=10000+(long)Math.sqrt(Wave)*1000;
//                if (wavetime>20000) wavetime=20000;
//                if (Wave>10) Wave=10;
//                for (int i=0; i<Wave+1; i++){
//                    Enemy_Multiplayer.Enemy_Reallo((float)(waveid.nextInt(320)+480), (float)(waveid.nextInt(160)+560), 1, Wave);//this is just an example
//                }
//                Loadwave=false;
//                break;
//            case 2:
//                wavetime=10000;
//                for (int i=0; i<8; i++){
//                    Enemy_Multiplayer.Enemy_Reallo((float)80+160*i,720,2,Wave);
//                }
//                Loadwave=false;
//                break;
//            case 3:
//                wavetime=10000;
//                if (System.currentTimeMillis()-t>1000){
//                    for (int i=0;i<(Wave/10+1);i++) {
//                        Enemy_Multiplayer.Enemy_Reallo(0, (float) (waveid.nextInt(160) + 560-80*i), 3, Wave);
//                        t = System.currentTimeMillis();
//                        enemies++;
//                    }
//                }
//                if ((enemies>=Wave+1)||(enemies>=30)) {
//                    Loadwave=false;
//                }
//                break;
//        }
//    }
}