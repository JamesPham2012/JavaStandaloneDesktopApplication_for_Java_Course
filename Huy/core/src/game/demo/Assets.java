package game.demo;

import com.badlogic.gdx.graphics.Texture;

public class Assets {
    public static Texture texture_bullet;
    public static Texture texture_plane;
    public static Texture texture_enemy;

    public void load(){
        texture_bullet = new Texture("Bullet_plane.png");
        texture_plane = new Texture("Spacefighter_Small.png");
        texture_enemy = new Texture("Enemy_Small.png");
    }
}
