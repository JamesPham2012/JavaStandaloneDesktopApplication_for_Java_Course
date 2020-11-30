package game.demo;


import UI.GameOverScreen;
import UI.MainClass;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import java.util.Vector;

import com.badlogic.gdx.graphics.glutils.FileTextureData;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import UI.PlayerData;

public class MyGdxGame implements Screen {

	public static int point=0;

	static Player player = new Player();
	Assets assets = new Assets();

	static Boss boss;

	Background background = new Background();

	Label label_point = new Label("0",new Skin(Gdx.files.internal("skin/Textfield.json")));

	static int Wave=0;
	private Stage stage;

	Sound sound = Gdx.audio.newSound(Gdx.files.internal("Audio/ZA-WARUDO.mp3"));;

	boolean pauseGame = false;
	MainClass mainClass;

	PlayerData playerData;

	public int bossWave = 10;

	static Vector<PixelCoord> Sonar_Player=new Vector<>();//generic relative position
	static Vector<PixelCoord> Sonar_Enemy=new Vector<>();//generic relative position
	static Vector<PixelCoord> Bullet_Ally_Pixel=new Vector<>();//generic relative position
	static Vector<PixelCoord> Bullet_Oppress_Pixel=new Vector<>();//generic relative position

	//	Texture sprite_bullet;
//	//Input in; // interface class --> abstract class --> we cannot call obj of this class.
	public MyGdxGame(MainClass mainClass){
		stage = new Stage();

		playerData = new PlayerData();

		label_point.setText(point);
		label_point.setFontScale(2f);
		label_point.setPosition(0,Gdx.graphics.getHeight()-50);
		stage.addActor(label_point);

		this.mainClass = mainClass;
		assets.load();
		player.create(assets.texture_plane.getWidth(),assets.texture_plane.getHeight());
		background.create();
		background.resize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());



		Bullet_Oppress_Pixel.clear();
		Bullet_Ally_Pixel.clear();
		Sonar_Player.clear();
		Sonar_Enemy.clear();


		int z1=0;
		String path = ((FileTextureData)assets.texture_plane.getTextureData()).getFileHandle().path();
		String path2 = ((FileTextureData)assets.texture_bullet.getTextureData()).getFileHandle().path();
		String path3 = ((FileTextureData)assets.texture_enemy.getTextureData()).getFileHandle().path();
		Collision tor=new Collision();
		int[][] Sonar_P=tor.Materialize(path,1,Assets.texture_plane.getWidth(),32);
		int[][] Sonar_B=tor.Materialize(path2,2,Assets.texture_bullet.getWidth(),32);
		int[][] Sonar_E=tor.Materialize(path3,3,Assets.texture_enemy.getWidth(),32);
		//start collision model outline for generic relative position from drawing position\
		for (int i=0;i<Sonar_P.length;i++){//each line, or in other word, Y
			for(int j=0;j<Sonar_P[1].length;j++){//each collum or in other word, X
				if (Sonar_P[i][j]==1){
					Sonar_Player.addElement(new PixelCoord(j,Sonar_P.length-i-1));
//					System.out.println("Left " + Sonar_Player.lastElement().relativeX + " Height " +Sonar_Player.lastElement().relativeY);;
//					System.out.print("1,");
				}
				if(Sonar_P[i][j]==0){
//					System.out.print("0,");
				}
			}
//			System.out.println();
		}
//		System.out.println(Counter);

		for (int i=0;i<Sonar_E.length;i++){
			for(int j=0;j<Sonar_E[1].length;j++){
				if (Sonar_E[i][j]==1){
					Sonar_Enemy.addElement(new PixelCoord(j,Sonar_E.length-i-1));
				}
				if(Sonar_E[i][j]==0){
				}
			}
		}

		boss = new Boss();
	}

	@Override
	public void show() {
		pauseGame=false;
		System.out.println("Show");
	}

	public void render(float delta){
		if(!pauseGame){

			background.render();

			player.render_player();
			player.setHitbox(Sonar_Player);
			if(player.fire()){
				player.Bullet_Call();
			}
			Waves.Wave_Come();

			Enemy.render(Sonar_Enemy);
			Enemy.fire();

			Bullet.render(player,Bullet_Ally_Pixel,Bullet_Oppress_Pixel);

			if(Waves.Wave == bossWave)
			{
				boss.getPlayerPos(player.x, player.y);
				boss.render();
				boss.shootRocket();
				boss.checkCollision();
				boss.checkCollisionWithPlayer();
				Enemy.killAll();
			}

			for (int i=0;i<Enemy.enemy_arr.size();i++) {
				Enemy.enemy_arr.elementAt(i).ChangeTextureValue();
				Enemy.enemy_arr.elementAt(i).setHitbox(Sonar_Enemy);
			}
			Bullet.render(player,Bullet_Ally_Pixel,Bullet_Oppress_Pixel);
			player.Collision(Bullet_Oppress_Pixel,Bullet.bullet_arr);//works
			for(int j=0;j<Enemy.enemy_arr.size();j++) {
				if (!Enemy.enemy_arr.elementAt(j).isDed()) {
					Enemy.enemy_arr.elementAt(j).Colixong(Bullet_Ally_Pixel);
				}
			}

			Bullet_Ally_Pixel.clear();
			Bullet_Oppress_Pixel.clear();
			Item.render();

			System.out.println(Waves.Wave);

			label_point.setText(point);


			stage.act();
			stage.draw();


			if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
				pauseGame = true;
				long id = sound.play(1f);
				sound.setPitch(id, 1f);
				sound.setLooping(id,false);
				mainClass.setPauseScreen();
			}

			if(!player.State){
				mainClass.setGameOverScreen();
				Waves.reset();
				Bullet.bullet_arr.clear();
				Enemy.enemy_arr.clear();
				GameOverScreen.score = point;
				point = 0;
				player.x = 0;
				player.y = 0;
				Wave=0;
			}
			if(!boss.State)
			{
				mainClass.setWinScreen();
				Waves.reset();
				player.x = 0;
				player.y = 0;
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
		boss.dispose();
		System.out.println("out");
	}
}