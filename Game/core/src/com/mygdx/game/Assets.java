package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.Texture;

public class Assets {
    public static Texture texture_bullet;
    public static Texture texture_plane;
    public static Texture texture_enemy;
    public static Texture texture_item;
    public static Texture texture_boss;
    public static Texture texture_rocket;

    public void load(){
        texture_bullet = new Texture("Texture/Bullets/Bullet.png");
        texture_plane = new Texture("Texture/Player/Spacefighter.png");
        texture_enemy = new Texture("Texture/Enemy/Enemy.png");
        texture_item = new Texture("Texture/Items/Item.png");
        texture_boss = new Texture("Texture/Boss/EnemyBoss.png");
        texture_rocket = new Texture("Texture/Bullets/Rocket.png");
    }
}
