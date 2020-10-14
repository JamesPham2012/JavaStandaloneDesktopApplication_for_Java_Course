package game.demo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.lang.Math;
import java.util.Random;
import java.util.Vector;
import game.demo.Bullet;

public class MyGdxGame extends Game {

	//Player player; // null obj which not consist any method. --> it just save a variable __> different from c++.
	// In later we have to player = new Player() in somewhere--> but not remove new Player()
	Player player = new Player();
	Vector<Bullet> bullet_arr = new Vector<>();
	Vector<Enemy> enemy_arr = new Vector<>();
	Background background = new Background();
	Assets assets = new Assets();
	Random e=new Random();

	//	Texture sprite_bullet;
//	//Input in; // interface class --> abstract class --> we cannot call obj of this class.
	@Override// we override function create in ApplicationAdapter.
	public void create() {
		assets.load();
		background.create();
		background.resize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		player.create();
		bullet_arr.addElement(new Bullet(120,240,1));
		for(int i=0;i<bullet_arr.size();i++){
			bullet_arr.elementAt(i).create();
		}

		init_Enemy(30);
		for(int i=0;i<enemy_arr.size();i++){
			enemy_arr.elementAt(i).setParam();

		}

	}
	public void init_Enemy(int am){
		for (int i=1;i<=am;i++)	{
			enemy_arr.addElement(new Enemy(e.nextInt(1200),e.nextInt(720)));
		}

	};
	public void render(){

		Gdx.gl.glClearColor(0, 0, 0, 255);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		background.render();
		player.render_player();
		if(player.fire()){
			player.Bullet_Call(bullet_arr);
		}

		for (int i=0;i< enemy_arr.size();i++){
			enemy_arr.elementAt(i).render_enemy();
			if(enemy_arr.elementAt(i).Autoshoot()){
				enemy_arr.elementAt(i).Bullet_Call(bullet_arr);
			}
		}
		Bullet.render_bullet(bullet_arr);
		Gdx.app.log("FPS", Integer.toString(Gdx.graphics.getFramesPerSecond()));
	}
	public void dispose(){
		player.dispose();
		System.out.println("out");
	}
}


