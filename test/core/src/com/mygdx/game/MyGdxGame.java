package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Bullet;
import com.mygdx.game.Player;

import java.util.Vector;

public class MyGdxGame extends ApplicationAdapter {

	//Player player; // null obj which not consist any method. --> it just save a variable __> different from c++.
	// In later we have to player = new Player() in somewhere--> but not remove new Player()
	Player player = new Player();
	Vector<Bullet> bullet_arr = new Vector<>();
	Enemy enemy = new Enemy();


	SpriteBatch batch;
	Texture plane;
	Sprite sprite;

	//	Texture sprite_bullet;
//	//Input in; // interface class --> abstract class --> we cannot call obj of this class.

	@Override// we override function create in ApplicationAdapter.
	public void create() {
		//
		player.create();
		enemy.create();
	}
	public void render(){

		Gdx.gl.glClearColor(0, 0, 10, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		player.render_player();

		enemy.render_enemy();

		if(player.RectangleCollision(enemy)){
			player.existFalse();
		}

		if(player.fire() && player.getExist()){
			bullet_arr.addElement(new Bullet(player));
			bullet_arr.lastElement().create();
		}
		
		for(int i=0;i<bullet_arr.size();i++){
			bullet_arr.elementAt(i).render_bullet();

			if (bullet_arr.elementAt(i).yObject>480) {
				bullet_arr.elementAt(i).existFalse();
			}

			if(bullet_arr.elementAt(i).RectangleCollision(enemy)){
				enemy.existFalse();
				bullet_arr.elementAt(i).existFalse();
			}
		}
	}
	public void dispose(){
		player.dispose();
		enemy.dispose();
		System.out.println("out");
	}
}


