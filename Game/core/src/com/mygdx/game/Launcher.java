package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
;
import java.util.Vector;
public class Launcher extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;

//Gdx.graphics.getWidth(),Gdx.graphics.getHeight()

	Player player=new Player();
	Vector<Bullet> bullet_arr = new Vector<>();
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("Maehwa_icon.png");
		player.setParam();
		player.create();
		for(int i=0;i<bullet_arr.size();i++){
			bullet_arr.elementAt(i).create();
		}
		Gdx.app.log("Creation", "Object Created");

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		player.render_player();
		if(player.Shoot()){
			Gdx.app.log("Creation", "Bullet Created");
			bullet_arr.addElement(new Bullet(player.getX(),player.getY(),1));
			bullet_arr.lastElement().setParam();
			create();

		}
		for(int i=0;i<bullet_arr.size();i++){
			bullet_arr.elementAt(i).render_bullet();
		}
	}






	@Override
	public void dispose () {
		batch.dispose();// seems like delete of C
		img.dispose();// seems like delete of C


		Gdx.app.log("Termination", "Game Terminated");
	}
}
