package game.demo;


import UI.GameOverScreen;
import UI.MainClass;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import UI.PlayerData;

public class MyGdxGame implements Screen {

	//Player player; // null obj which not consist any method. --> it just save a variable __> different from c++.
	// In later we have to player = new Player() in somewhere--> but not remove new Player()

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

	//	Texture sprite_bullet;
//	//Input in; // interface class --> abstract class --> we cannot call obj of this class.
	public MyGdxGame(MainClass mainClass){
		stage = new Stage();

		playerData = new PlayerData();

		label_point.setText(GameOverScreen.score);
		label_point.setFontScale(2f);
		label_point.setPosition(0,Gdx.graphics.getHeight()-50);
		stage.addActor(label_point);

		this.mainClass = mainClass;
		assets.load();
		player.create();
		background.create();
		background.resize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

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
			if(player.fire()){
				player.Bullet_Call();
			}


			if(Waves.Wave < bossWave)
			{
				Waves.Wave_Come();

				Enemy.render();
				Enemy.fire();
				Enemy.checkCollision();
			}

			Bullet.render();

			if(Waves.Wave == bossWave)
			{
				boss.getPlayerPos(player.x, player.y);
				boss.render();
				boss.shootRocket();
				boss.checkCollision();
				boss.checkCollisionWithPlayer();
				Enemy.killAll();
			}

			player.checkCollisionwthBullet();
			player.checkCollisionwthEnemy();


//			Gdx.app.log("FPS", Integer.toString(Gdx.graphics.getFramesPerSecond()));
			System.out.println(Waves.Wave);

			label_point.setText(GameOverScreen.score);

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