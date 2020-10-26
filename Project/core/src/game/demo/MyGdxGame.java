package game.demo;

import UI.MainClass;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.awt.*;
import java.lang.Math;
import java.util.Vector;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import game.demo.Bullet;

public class MyGdxGame implements Screen {

	//Player player; // null obj which not consist any method. --> it just save a variable __> different from c++.
	// In later we have to player = new Player() in somewhere--> but not remove new Player()
	public static int point = 0;
	BitmapFont font_point;
	static Player player = new Player();
	Assets assets = new Assets();
	Vector<Bullet> bullet_arr = new Vector<>();
	Vector<Enemy> enemy_arr = new Vector<>();
	Background background = new Background();
	Label label_point = new Label("0",new Skin(Gdx.files.internal("skin/Textfield.json")));
	Skin skin;
	SpriteBatch batch;
	static int Wave=0;
	private Stage stage;


	boolean pauseGame = false;
	MainClass mainClass;

	//	Texture sprite_bullet;
//	//Input in; // interface class --> abstract class --> we cannot call obj of this class.
	public MyGdxGame(MainClass mainClass){
		stage = new Stage();
		label_point.setText(point);
		label_point.setFontScale(2f);
		label_point.setPosition(0,Gdx.graphics.getHeight()-50);
		stage.addActor(label_point);

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
				player.Bullet_Call(bullet_arr);
			}
			Waves.Wave_Come(enemy_arr);
			Enemy.render(enemy_arr);
			Enemy.fire(enemy_arr,bullet_arr);
			Bullet.render(bullet_arr);
			player.checkCollision(bullet_arr);
			Enemy.checkCollision(enemy_arr,bullet_arr);
			Gdx.app.log("FPS", Integer.toString(Gdx.graphics.getFramesPerSecond()));


			label_point.setText(point);
			stage.act();
			stage.draw();

			if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
				mainClass.setPauseScreen();
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