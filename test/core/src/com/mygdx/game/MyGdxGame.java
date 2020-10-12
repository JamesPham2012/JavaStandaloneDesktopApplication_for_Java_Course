package com.mygdx.game;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

import java.util.Vector;

public class MyGdxGame extends ApplicationAdapter {

	//Player player; // null obj which not consist any method. --> it just save a variable __> different from c++.
	// In later we have to player = new Player() in somewhere--> but not remove new Player()
	Player player = new Player();
	Vector<Bullet> bullet_arr = new Vector<>();
	Enemy enemy = new Enemy();

	//	Texture sprite_bullet;
//	//Input in; // interface class --> abstract class --> we cannot call obj of this class.

	@Override// we override function create in ApplicationAdapter.
	public void create() {
		//
		player.create();
		enemy.create();
		bullet_arr.addElement(new Bullet(player));
		bullet_arr.firstElement().exist=false;
		bullet_arr.firstElement().create();
	}
	public void render(){

		Gdx.gl.glClearColor(0, 0, 10, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		player.render_player();
		enemy.render_enemy();

		if(player.RectangleCollision(enemy)){
			player.existFalse();
		}

		if(player.fire() && player.getExist()) {
			int flag = 0;
	loop:		while(flag==0){
				for (int i = 0; i < bullet_arr.size(); i++) {
					if (bullet_arr.elementAt(i).getExist() == false) {
						bullet_arr.elementAt(i).revise(player.getX(), player.getY());
						Gdx.app.log("TEST", "revived bullet at " + i);
						break loop;
					}
				}
				bullet_arr.addElement(new Bullet(player));
				//System.out.println(player.getX());
				bullet_arr.lastElement().create();
				Gdx.app.log("TEST", "Created bullet new bullet");
				flag=1;
			}


		}


		
		for(int i=0;i<bullet_arr.size();i++){

			if (bullet_arr.elementAt(i).yObject>720) {
				bullet_arr.elementAt(i).existFalse();
			}
			if (bullet_arr.elementAt(i).getExist()) bullet_arr.elementAt(i).render_bullet();
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


