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

<<<<<<< Updated upstream:Project/core/src/game/demo/MyGdxGame.java
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
=======
import com.badlogic.gdx.graphics.glutils.FileTextureData;
>>>>>>> Stashed changes:Game/core/src/game/demo/MyGdxGame.java
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


	static Vector<PixelCoord> Sonar_Player=new Vector<>();//generic relative position
	static Vector<PixelCoord> Sonar_Bullet=new Vector<>();//generic relative position
	static Vector<PixelCoord> Sonar_Enemy=new Vector<>();//generic relative position
	static Vector<PixelCoord> Player_Pixel=new Vector<>();//id 1, ally . Under consideration: Transfer to class Player
	static Vector<PixelCoord> Bullet_Ally_Pixel=new Vector<>();//id 1, ally
	static Vector<PixelCoord> Enemy_Pixel=new Vector<>();// id 2, enemy. Under transfer consideration: Transfer to class Enemy
	static Vector<PixelCoord> Bullet_Oppress_Pixel=new Vector<>();// id 2, enemy


	boolean pauseGame = false;
	MainClass mainClass;
	int z1=0;

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
		player.create(assets.texture_plane.getWidth(),assets.texture_plane.getHeight());
		background.create();
		background.resize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());



		/*------------------------------------------------------------------------------------------------------------*/
		/*--------------------------------newly added 23/10/2020------------------------------------------------------*/
		Player_Pixel.clear();
		Bullet_Ally_Pixel.clear();
		Enemy_Pixel.clear();
		Bullet_Oppress_Pixel.clear();
		Sonar_Player.clear();
		Sonar_Enemy.clear();
		Sonar_Bullet.clear();
		int z1=0;
		String path = ((FileTextureData)assets.texture_plane.getTextureData()).getFileHandle().path();
		String path2 = ((FileTextureData)assets.texture_bullet.getTextureData()).getFileHandle().path();
		String path3 = ((FileTextureData)assets.texture_enemy.getTextureData()).getFileHandle().path();
		Collision tor=new Collision();
		int[][] Sonar_P=tor.Materialize(path,1,256,32);
		int[][] Sonar_B=tor.Materialize(path2,2,22,32);
		int[][] Sonar_E=tor.Materialize(path3,3,256,32);
		//start collision model outline for generic relative position from drawing position\
		System.out.println(Sonar_Player.size());
		int Counter=0;
		for (int i=0;i<Sonar_P.length;i++){//each line, or in other word, Y
			for(int j=0;j<Sonar_P[1].length;j++){//each collum or in other word, X
				if (Sonar_P[i][j]==1){
					Sonar_Player.addElement(new PixelCoord(j,Sonar_P.length-i-1));
//					System.out.println("Left " + Sonar_Player.lastElement().relativeX + " Height " +Sonar_Player.lastElement().relativeY);
					Counter++;
					System.out.print("1,");
				}
				if(Sonar_P[i][j]==0){
					System.out.print("0,");
				}
			}
			System.out.println();
		}
		System.out.println(Counter);
		for (int i=0;i<Sonar_B.length;i++){//each line, or in other word, Y
			for (int j=0;j<Sonar_B[1].length;j++){//each collum or in other word, X
				if(Sonar_B[i][j]==1){
					Sonar_Bullet.addElement((new PixelCoord(j,Sonar_B.length-i-1)));
					System.out.print("1,");
				}
				if(Sonar_B[i][j]==0){
					System.out.print("0,");
				}
			}
			System.out.println();
		}
		for (int i=255;i>=0;i--){
			for(int j=0;j<256;j++){
				if (Sonar_E[i][j]==1){
					Sonar_Enemy.addElement(new PixelCoord(i,255-j));
//					System.out.print("1,");
				}
				if(Sonar_E[i][j]==0){
//					System.out.print("0,");
				}
			}
//			System.out.println();
		}



		//end collision model outline for generic relative position from drawing position
		/*------------------------------------------------------------------------------------------------------------*/


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
			player.render_player(Sonar_Player,Player_Pixel);
			if(player.fire()){
				player.Bullet_Call(bullet_arr);
			}
			Waves.Wave_Come(enemy_arr);
			Enemy.render(enemy_arr,Sonar_Enemy,Enemy_Pixel);
			Enemy.fire(enemy_arr,bullet_arr);
			Bullet.render(player,bullet_arr,Sonar_Bullet,Bullet_Ally_Pixel,Bullet_Oppress_Pixel);
			/*player.checkCollision(bullet_arr);
			Enemy.checkCollision(enemy_arr,bullet_arr);*/
//			Gdx.app.log("FPS", Integer.toString(Gdx.graphics.getFramesPerSecond()));


			label_point.setText(point);
			stage.act();
			stage.draw();

			if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
				mainClass.setMenuScreen();
			}
			if(!player.State){
				mainClass.setMenuScreen();
				Waves.reset();
				Wave=0;
			}
<<<<<<< Updated upstream:Project/core/src/game/demo/MyGdxGame.java
=======
			//trigger for checking needed, examination
			/*System.out.println("Size of Sonar Bullet " + Sonar_Bullet.size());
			System.out.println(Sonar_Enemy.size()+"  "+Sonar_Player.size());*/
loop:		for (int i=0;i<Bullet_Oppress_Pixel.size();i++){
				for(int j=0;j<Player_Pixel.size();j++){
					if(Player_Pixel.elementAt(j).Vicinity(Bullet_Oppress_Pixel.elementAt(i))){
						System.out.println(Player_Pixel.elementAt(j).relativeX + " "+ Bullet_Oppress_Pixel.elementAt(i).relativeX);
						player.Execute();
						break loop;
					}
				}
			}

			if (Gdx.input.isKeyJustPressed(Input.Keys.Y)){
				for(int i=1;i<Player_Pixel.size();i++){
					System.out.println("Right "+ Player_Pixel.elementAt(i).relativeX + " Height "+ Player_Pixel.elementAt(i).relativeY);
				}
			}

			Player_Pixel.clear();//size=0
			Bullet_Ally_Pixel.clear();
			Enemy_Pixel.clear();
			Bullet_Oppress_Pixel.clear();
>>>>>>> Stashed changes:Game/core/src/game/demo/MyGdxGame.java

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