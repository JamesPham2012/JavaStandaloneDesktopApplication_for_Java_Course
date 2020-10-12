package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
;
import com.badlogic.gdx.utils.Timer;
import java.util.Random;
import java.util.Vector;
public class Launcher extends ApplicationAdapter {
	SpriteBatch batch;
	Texture BG;

//Gdx.graphics.getWidth(),Gdx.graphics.getHeight()
	Random e=new Random();
	Player player=new Player();
	Vector<Bullet> bullet_arr = new Vector<>();
	Vector<Enemy>  Enemy_arr = new Vector<>();
	@Override
	public void create () {

		BG=new Texture("BG1.jpg");
		player.setParam();
		player.create();
		if (bullet_arr.size()==0){
			bullet_arr.addElement(new Bullet(122,-1,true,false));
			bullet_arr.firstElement().modelGenerator();
			bullet_arr.firstElement().setParam();
			bullet_arr.firstElement().create();
		}

		init_Enemy(30);
		for(int i=0;i<Enemy_arr.size();i++){
			Enemy_arr.elementAt(i).setParam();
			Enemy_arr.elementAt(i).create();
		}

	}
	public void init_Enemy(int am){
		for (int i=1;i<=am;i++)	{
			Enemy_arr.addElement(new Enemy(e.nextInt(1200),e.nextInt(720)));
		}

	};
	@Override
	public void render () {
		batch = new SpriteBatch();
		Gdx.gl.glClearColor(1, 1, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);  //ánh trăng là nghệ thuật lừa dối
		batch.begin();
		batch.draw(BG,0,0);
		batch.end();
		player.create();
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
						bullet_arr.elementAt(i).create();//pick a model to create the render img
						/*Gdx.app.log("Log", "Bullet number "+i+" revived");*/
						continue loop;
		 			}
		 		}
				// if the code get here, there is NO dead bullet in the array
				bullet_arr.addElement(new Bullet(player.getX(),player.getY(),true));// create a new bullet
				bullet_arr.lastElement().modelGenerator();//start rendering image for it
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
					bullet_arr.lastElement().modelGenerator();
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
		Gdx.app.log("FPS", Integer.toString(Gdx.graphics.getFramesPerSecond()));

		batch.dispose();
		player.dispose();




	}


	@Override
	public void dispose () {
		batch.dispose();// seems like delete of C
		BG.dispose();
		Gdx.app.log("Termination", "Game Terminated");
	}
}
