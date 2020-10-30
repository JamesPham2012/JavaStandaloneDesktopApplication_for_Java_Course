package game.demo;

import com.badlogic.gdx.graphics.Texture;

public class Assets {
    public static Texture texture_bullet;
    public static Texture texture_plane;
    public static Texture texture_enemy;
    public static Texture texture_item;

    public void load(){
        texture_bullet = new Texture("Bullet_plane.png");
        texture_plane = new Texture("Plane.png");
        texture_enemy = new Texture("Enemy.png");
        texture_item = new Texture("Maehwa_icon.png");
    }
}
