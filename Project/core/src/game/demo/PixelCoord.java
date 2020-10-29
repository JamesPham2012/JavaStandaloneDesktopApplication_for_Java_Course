package game.demo;

public class PixelCoord {
    public float relativeX;
    public float relativeY;
    public float Proximity=999;
    public PixelCoord(float x, float y){
        this.relativeX=x;
        this.relativeY=y;
    }

    public PixelCoord(float x_Draw,float y_Draw, PixelCoord Hitbox_Element){
        this.relativeX=x_Draw + Hitbox_Element.relativeX;
        this.relativeY=y_Draw + Hitbox_Element.relativeY;

    }


    public boolean Vicinity(PixelCoord another){
        return (Math.abs(this.relativeX-another.relativeX)<=2)&&(Math.abs(this.relativeY-another.relativeY)<=2);
    }
}
