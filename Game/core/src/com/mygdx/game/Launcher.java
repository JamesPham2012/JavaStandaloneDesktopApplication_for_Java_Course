package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
;
import java.util.Random;
import java.util.Vector;
public class Launcher extends ApplicationAdapter {
	SpriteBatch batch;
	Texture BG;

//Gdx.graphics.getWidth(),Gdx.graphics.getHeight()

	Player player=new Player();
	Vector<Bullet> bullet_arr = new Vector<>();
	Vector<Enemy>  Enemy_arr = new Vector<>();

	@Override
	public void create () {
		batch = new SpriteBatch();
		player.setParam();
		player.create();
		Random e= new Random();
		for(int i=0;i<bullet_arr.size();i++){
			bullet_arr.elementAt(i).create();
		}
		BG=new Texture("BG1.jpg");
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.draw(BG,0,0);
		player.render_player();

		if(player.Manual_Shoot()){// need change to replace DED value with ALIVE value in order for the vector not to be too long, waste of memory
			/*for(int i=0;i<bullet_arr.size();i++){
				if ((i==0)||(i==1)){
					bullet_arr.addElement(new Bullet(player.getX(),player.getY(),1));
					bullet_arr.lastElement().setParam();
				}
				else {
					if(bullet_arr.elementAt(i).isDed()){
						bullet_arr.elementAt(i).Reallocate(player.getX(),player.getY(),1);
					}
					if (i== bullet_arr.lastIndexOf(bullet_arr)){
						bullet_arr.addElement(new Bullet(player.getX(),player.getY(),1));
						bullet_arr.lastElement().setParam();
					}
				}
			}*/
			bullet_arr.addElement(new Bullet(player.getX(),player.getY(),1));
			bullet_arr.lastElement().setParam();
			create();
		}



		for(int i=0;i<bullet_arr.size();i++){
			if(bullet_arr.elementAt(i).isDed()) {
				bullet_arr.removeElementAt(i);
				i--;
			}
			else bullet_arr.elementAt(i).render_bullet();
		}


		Gdx.app.log("Size", Integer.toString(bullet_arr.size()));
		Gdx.app.log("Frame", String.valueOf(Gdx.graphics.getFramesPerSecond()));
		super.render();
	}
	@Override
	public void dispose () {
		batch.dispose();// seems like delete of C
		BG.dispose();// seems like delete of C


		Gdx.app.log("Termination", "Game Terminated");
	}
}
