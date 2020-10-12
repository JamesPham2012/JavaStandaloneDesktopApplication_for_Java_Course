package game.demo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.lang.Math;
import java.util.Vector;
import game.demo.Bullet;

public class MyGdxGame extends ApplicationAdapter {

	//Player player; // null obj which not consist any method. --> it just save a variable __> different from c++.
	// In later we have to player = new Player() in somewhere--> but not remove new Player()
	Player player = new Player();
	Vector<Bullet> bullet_arr = new Vector<>();
	SpriteBatch batch;
	Texture plane;
	Background background = new Background();

	//	Texture sprite_bullet;
//	//Input in; // interface class --> abstract class --> we cannot call obj of this class.
	@Override// we override function create in ApplicationAdapter.
	public void create() {
		background.create();
		background.resize(1080,720);
		player.create();
		for(int i=0;i<bullet_arr.size();i++){
			bullet_arr.elementAt(i).create();
		}
	}
	public void render(){

		Gdx.gl.glClearColor(0, 0, 0, 255);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		background.render();
		player.render_player();
		if(player.fire()){
			player.Bullet_Call(bullet_arr);
		}
		Bullet.render_bullet(bullet_arr);
		Gdx.app.log("FPS", Integer.toString(Gdx.graphics.getFramesPerSecond()));
	}
	public void dispose(){
		player.dispose();
		System.out.println("out");
	}
}


