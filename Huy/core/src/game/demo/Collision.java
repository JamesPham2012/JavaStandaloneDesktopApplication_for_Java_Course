package game.demo;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Vector;

public class Collision {

//ALERT, THIS WILL CAUSE OUT OF BOUNDS OF SCREEN REAL PIXELS, WHAT IF WE HAVE THE CURSOR AT THE CORNER, WHERE ARE THE WASTED PIXEL?


    public int[][] Materialize(String name,int ID,int width_of_byte,int bpp){
        Pixmap pixmap = new Pixmap(Gdx.files.internal(name));
        ByteBuffer nativeData = pixmap.getPixels();
        byte[] byte_arr = new byte[nativeData.remaining()];
        nativeData.get(byte_arr);
        pixmap.dispose();
        int [][] result=HitboxGen(byte_arr,width_of_byte,bpp);
        return result;
    }

    public int[][] HitboxGen(byte [] byte_arr ,int width_of_byte,int bpp){
        int[] newdata=new int[byte_arr.length/(bpp/8)];
        int height_of_byte= byte_arr.length/(bpp/8)/width_of_byte;
        int[][] TransparencyMap=new int[height_of_byte+2][width_of_byte+2];//map of transparent pixels
        int[][] DensityMap=new int[height_of_byte+2][width_of_byte+2];//map of hitbox
        int[][] Hitbox=new int[height_of_byte][width_of_byte];//map of density level
        for(int i=0;i<byte_arr.length;i=i+4){
            //((byte_arr[i]==-1)&&(byte_arr[i+1]==-1)&&(byte_arr[i+2]==-1)&&(byte_arr[i+3]==0))||((byte_arr[i]==0)&&(byte_arr[i+1]==0)&&(byte_arr[i+2]==0)&&(byte_arr[i+3]==0))||((byte_arr[i]==117)&&(byte_arr[i+1]==117)&&(byte_arr[i+2]==117)&&(byte_arr[i+3]==0))
            if ((byte_arr[i+3]==0)){//transparent pixel... you say about data science, ye rite
                newdata[i/4]=0;;
            }
            else newdata[i/4]=1;
        }
        int counter=0;
        for(int i=0;i<height_of_byte;i++){
            for (int j=0;j<width_of_byte;j++){
                TransparencyMap[i+1][j+1]=newdata[counter++];
            }
        }
            /*ystem.out.println();
            for(int i=1;i<height_of_byte;i++){
                for (int j=1;j<width_of_byte;j++){
                    int BorderLeft=i-1;
                    int BorderRight=i+1;
                    int BorderUp=j-1;
                    int BorderDown=j+1;
                    if(((TransparencyMap[i][BorderDown]*TransparencyMap[i][BorderUp]*TransparencyMap[BorderRight][BorderDown]*TransparencyMap[BorderLeft][BorderUp]*TransparencyMap[BorderRight][BorderUp]*TransparencyMap[BorderLeft][BorderDown]*TransparencyMap[BorderLeft][j]*TransparencyMap[BorderLeft][j])==0)&&((TransparencyMap[i][BorderDown]+TransparencyMap[i][BorderUp]+TransparencyMap[BorderRight][BorderDown]+TransparencyMap[BorderLeft][BorderUp]+TransparencyMap[BorderRight][BorderUp]+TransparencyMap[BorderLeft][BorderDown]+TransparencyMap[BorderLeft][j]+TransparencyMap[BorderLeft][j])!=0)){
                        Hitbox[i-1][j-1]=1;
                    }else Hitbox[i-1][j-1]=0;
                }
            }    //result in 0-1 2d array of 2-pixel borderline, can be used asap, test passed for PLAYER*/
            //start another scanning process, independent of line 59 and possible alternate
        for(int i=1;i<height_of_byte+1;i++){
            for (int j=1;j<width_of_byte+1;j++){
                int BorderLeft=i-1;
                int BorderRight=i+1;
                int BorderUp=j-1;
                int BorderDown=j+1;
                DensityMap[i][j]=((TransparencyMap[i][BorderDown]+TransparencyMap[i][BorderUp]+TransparencyMap[BorderRight][BorderDown]+TransparencyMap[BorderLeft][BorderUp]+TransparencyMap[BorderRight][BorderUp]+TransparencyMap[BorderLeft][BorderDown]+TransparencyMap[BorderLeft][j]+TransparencyMap[BorderLeft][j]));
            }
            }

        int counter1=0;
        for(int i=1;i<height_of_byte+1;i++){
            for (int j=1;j<width_of_byte+1;j++){
                int BorderLeft=i-1;
                int BorderRight=i+1;
                int BorderUp=j-1;
                int BorderDown=j+1;
                if (((DensityMap[i][BorderDown]==8)||(DensityMap[i][BorderUp]==8)||(DensityMap[BorderRight][BorderDown]==8)||(DensityMap[BorderLeft][BorderUp]==8)||(DensityMap[BorderRight][BorderUp]==8)||(DensityMap[BorderLeft][BorderDown]==8)||(DensityMap[BorderLeft][j]==8)||(DensityMap[BorderLeft][j]==8))&&(DensityMap[i][j]!=8)){
                    Hitbox[i-1][j-1]=1;
                    counter1++;
                }
                else Hitbox[i-1][j-1]=0;
            }
        }
        System.out.println(counter1);
        return Hitbox;
        }
    }

