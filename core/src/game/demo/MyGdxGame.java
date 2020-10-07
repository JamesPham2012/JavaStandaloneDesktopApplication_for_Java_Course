package game.demo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Vector;

public class MyGdxGame extends ApplicationAdapter {

	//Player player; // null obj which not consist any method. --> it just save a variable __> different from c++.
	// In later we have to player = new Player() in somewhere--> but not remove new Player()
	Player player = new Player();
	Vector<Bullet> bullet_arr = new Vector<>();

	SpriteBatch batch;
	Texture plane;
	// hello hao
	//	Texture sprite_bullet;
//	//Input in; // interface class --> abstract class --> we cannot call obj of this class.
	@Override// we override function create in ApplicationAdapter.
	public void create() {
		player.create();
		for(int i=0;i<bullet_arr.size();i++){
			System.out.println(i);
			bullet_arr.elementAt(i).create();
		}
	}
	public void render(){

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		player.render_player();
		if(player.fire()){
			bullet_arr.addElement(new Bullet(player.getX(),player.getY()));
			create();
		}
		for(int i=0;i<bullet_arr.size();i++){
			System.out.println(i);
			bullet_arr.elementAt(i).render();
		}
	}
	public void dispose(){
		player.dispose();
		System.out.println("out");
	}
}


