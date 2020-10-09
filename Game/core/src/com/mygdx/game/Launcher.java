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


//Gdx.graphics.getWidth(),Gdx.graphics.getHeight()

	Player player=new Player();
	Vector<Bullet> bullet_arr = new Vector<>();
	Vector<Enemy>  Enemy_arr = new Vector<>();





	int[] X_Var={240,400,560,720,1000};
	int[] Y_Var={360,500,400,600,170};
	@Override
	public void create () {
		batch = new SpriteBatch();
		player.setParam();
		player.create();

		if (bullet_arr.size()==0){
			bullet_arr.addElement(new Bullet(122,-1,1,false));
			bullet_arr.firstElement().create();
		}



	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		player.render_player();
		int BulletGen=0;
		if(player.Manual_Shoot())
	loop:	while (BulletGen==0){
				// need change to replace DED value with ALIVE value in order for the vector not to be too long, waste of memory


		 		for(int i=0;i<bullet_arr.size();i++)
		 		{                                                                           //there exists at least an element in the array
		 			if(bullet_arr.elementAt(i).isDed()){                                    // there is 1 dead bullet
		 				bullet_arr.elementAt(i).Revive(player.getX(),player.getY(),1);//revive it as a new bullet
						BulletGen=1;
						continue loop;
		 			}
		 		}
				// if the code get here, there is NO dead bullet in the array

				bullet_arr.addElement(new Bullet(player.getX(),player.getY(),1));
				bullet_arr.lastElement().setParam();
				bullet_arr.lastElement().create();
				BulletGen=1;

			}




			/*bullet_arr.addElement(new Bullet(player.getX(),player.getY(),1));
			bullet_arr.lastElement().setParam();*/
		// create MUST be in here to allow the data deletion to continue
		int counter=0;
		for(int i=0;i<bullet_arr.size();i++){
			if(bullet_arr.elementAt(i).isDed()) {
				counter++;
				continue;
			}
			else bullet_arr.elementAt(i).render_bullet(); // skip all dead elements
		}



		Gdx.app.log("Size", Integer.toString(bullet_arr.size()));
		Gdx.app.log("Dead", Integer.toString(counter));

	}
	@Override
	public void dispose () {
		batch.dispose();// seems like delete of C



		Gdx.app.log("Termination", "Game Terminated");
	}
}
