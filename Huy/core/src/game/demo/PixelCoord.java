package game.demo;

public class PixelCoord {
    public float relativeX;
    public float relativeY;
    public int location_Bullet = -99;
    public int location_Target= -99;
    public PixelCoord(float x, float y){
        this.relativeX=x;
        this.relativeY=y;
    }

    public PixelCoord(float x_Draw,float y_Draw, PixelCoord Hitbox_Element){
        this.relativeX=x_Draw + Hitbox_Element.relativeX;
        this.relativeY=y_Draw + Hitbox_Element.relativeY;

    }

    public PixelCoord(PixelCoord Hitbox_Element){
        this.relativeX=Hitbox_Element.relativeX;
        this.relativeY=Hitbox_Element.relativeY;
    }
    public PixelCoord(float x_Draw,float y_Draw, int location_Bullet){
        this.relativeX=x_Draw ;
        this.relativeY=y_Draw ;
        this.location_Bullet=location_Bullet;
    }
    public PixelCoord(float x_Draw,float y_Draw, int location_Bullet,int location_Target){
        this.relativeX=x_Draw ;
        this.relativeY=y_Draw ;
        this.location_Bullet=location_Bullet;
        this.location_Target=location_Target;
    }

    public boolean VicinityBullet(PixelCoord another){
        return (Math.abs(this.relativeX-another.relativeX)<=3)&&(Math.abs(this.relativeY-another.relativeY)<=3);
    }
}
