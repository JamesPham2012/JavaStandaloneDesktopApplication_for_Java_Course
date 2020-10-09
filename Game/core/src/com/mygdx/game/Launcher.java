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
			bullet_arr.addElement(new Bullet(122,-1,true,false));
			bullet_arr.firstElement().setParam();
			bullet_arr.firstElement().create();
		}
		init_Enemy(5);
		for(int i=0;i<Enemy_arr.size();i++){
			Enemy_arr.elementAt(i).setParam();
			Enemy_arr.elementAt(i).create();
		}

	}
	public void init_Enemy(int am){
		for (int i=1;i<=am;i++)	{
			Enemy_arr.addElement(new Enemy(X_Var[i-1],Y_Var[i-1]));
		}

	};
	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		player.render_player();
		int BulletGen=0;
		int counter1;
		if(player.Manual_Shoot())
	loop:	while (BulletGen==0){
				// need change to replace DED value with ALIVE value in order for the vector not to be too long, waste of memory
		 		for(int i=0;i<bullet_arr.size();i++)
		 		{                                                                           //there exists at least an element in the array
		 			if(bullet_arr.elementAt(i).isDed()){                                    // there is 1 dead bullet
		 				boolean res = bullet_arr.elementAt(i).Revive(player.getX(),player.getY(),true);//revive it as a new bullet
						BulletGen=1;
						/*Gdx.app.log("Log", "Bullet number "+i+" revived");*/
						continue loop;
		 			}
		 		}
				// if the code get here, there is NO dead bullet in the array

				bullet_arr.addElement(new Bullet(player.getX(),player.getY(),true));
				bullet_arr.lastElement().setParam();
				bullet_arr.lastElement().create();
				BulletGen=1;
//				Gdx.app.log("Log", "Bullet number "+bullet_arr.size()+" created");

			}
			/*bullet_arr.addElement(new Bullet(player.getX(),player.getY(),1));
			bullet_arr.lastElement().setParam();*/
		// create MUST be in here to allow the data deletion to continue
		int counter=0;


		for (int i=0;i< Enemy_arr.size();i++){
			if(Enemy_arr.elementAt(i).State){
				Enemy_arr.elementAt(i).render_enemy();
			}
		}

		for(int j=0;j<Enemy_arr.size();j++){
			int BulletGen2=0;
			if (Enemy_arr.elementAt(j).Autoshoot()){
		loop:		while (BulletGen2==0){
					// need change to replace DED value with ALIVE value in order for the vector not to be too long, waste of memory
					for(int i=0;i<bullet_arr.size();i++)
					{                                                                           //there exists at least an element in the array
						if(bullet_arr.elementAt(i).isDed()){                                    // there is 1 dead bullet
							boolean res = bullet_arr.elementAt(i).Revive(Enemy_arr.elementAt(j).getX(),Enemy_arr.elementAt(j).getY(),false);//revive it as a new bullet with a new source
							BulletGen2=1;
							bullet_arr.elementAt(i).create();

							/*Gdx.app.log("Log", "Bullet number "+i+" revived");*/
							continue loop;
						}
					}
					// if the code get here, there is NO dead bullet in the array

					bullet_arr.addElement(new Bullet(Enemy_arr.elementAt(j).getX(),Enemy_arr.elementAt(j).getY(),false));
					bullet_arr.lastElement().setParam();
					bullet_arr.lastElement().create();
					BulletGen2=1;
			}
		}}
		for(int i=0;i<bullet_arr.size();i++){
			if(bullet_arr.elementAt(i).isDed()) {
				counter++;
				continue;
			}
			else {bullet_arr.elementAt(i).render_bullet(); // skip all dead elements
			}}


		Gdx.app.log("Used", Integer.toString(bullet_arr.size()-counter));
		Gdx.app.log("Size", Integer.toString(bullet_arr.size()));


	}
	@Override
	public void dispose () {
		batch.dispose();// seems like delete of C



		Gdx.app.log("Termination", "Game Terminated");
	}
}
