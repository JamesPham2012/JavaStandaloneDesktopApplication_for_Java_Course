package game.demo;

import UI.MainClass;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.lang.Math;
import java.util.Vector;
import game.demo.Bullet;

public class MyGdxGame implements Screen {

	//Player player; // null obj which not consist any method. --> it just save a variable __> different from c++.
	// In later we have to player = new Player() in somewhere--> but not remove new Player()
	static Player player = new Player();
	Assets assets = new Assets();
	Background background = new Background();
	static int Wave=0;

	boolean pauseGame = false;
	MainClass mainClass;

	//	Texture sprite_bullet;
//	//Input in; // interface class --> abstract class --> we cannot call obj of this class.
	public MyGdxGame(MainClass mainClass){
		this.mainClass = mainClass;
		assets.load();
		player.create();
		background.create();
		background.resize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
	}
	@Override
	public void show() {
		pauseGame=false;
		System.out.println("Show");
	}
	public void render(float delta){


		if(!pauseGame){
			Gdx.gl.glClearColor(0, 0, 0, 255);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			background.render();
			player.render_player();
			if(player.fire()){
				player.Bullet_Call();
			}
			/*if(Waves.Wave_Come()){
				Waves.Wave_Call(enemy_arr,Wave);
			}*/
			Waves.Wave_Come();
			Enemy.render();
			Enemy.fire();
			Bullet.render();
//			player.checkCollisionwthBullet();
			player.checkCollisionwthEnemy();
			Enemy.checkCollision();
			Item.render();

			Gdx.app.log("FPS", Integer.toString(Gdx.graphics.getFramesPerSecond()));
			System.out.println(Wave);

			if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
				mainClass.setMenuScreen();
			}
			if(!player.State){
				mainClass.setMenuScreen();
				Waves.reset();
				Wave=0;
			}
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
		System.out.println("out");
	}
}