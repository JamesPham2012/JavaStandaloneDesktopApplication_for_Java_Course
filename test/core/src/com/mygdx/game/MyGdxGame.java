package com.mygdx.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.giaodien.MainClass;

import java.util.Vector;

public class MyGdxGame implements Screen {

	//Player player; // null obj which not consist any method. --> it just save a variable __> different from c++.
	// In later we have to player = new Player() in somewhere--> but not remove new Player()
	private Player player = new Player();
	public Vector<Bullet> bullet_arr = new Vector<>();
	private Enemy enemy = new Enemy();
	ScreenViewport viewport = new ScreenViewport();
	Stage stage;
	boolean pauseGame = false;
	private MainClass mainClass;


	public MyGdxGame(MainClass mainClass){
		this.mainClass = mainClass;
		player.create();
		enemy.create();
		bullet_arr.addElement(new Bullet(player));
		bullet_arr.firstElement().exist=false;
		bullet_arr.firstElement().create();


	}
	@Override
	public void show() {
		pauseGame=false;
		System.out.println("Show");

	}


	@Override
	public void render(float delta){
		Gdx.gl.glClearColor(0, 0, 10, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if(!pauseGame){

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
//
//

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
	//		stage.act();
	//		stage.draw();



		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
			mainClass.setMenuScreen();
		}


	}




	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {
		System.out.println("Pause");
		pauseGame=true; // pause is 1

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {
		pauseGame=true;

	}

	public void dispose(){
		player.dispose();
		enemy.dispose();
		System.out.println("out");
	}
}


