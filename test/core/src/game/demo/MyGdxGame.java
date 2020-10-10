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

		Gdx.gl.glClearColor(255, 0, 0, 255);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		player.render_player();
		if(player.fire()){
			bullet_arr.addElement(new Bullet(player.getX(),player.getY()+28));
			bullet_arr.addElement(new Bullet(player.getX()-10,player.getY()+28));
			bullet_arr.addElement(new Bullet(player.getX()-5,player.getY()+28));
			create();
		}
		for(int i=0;i<bullet_arr.size();i++){
			System.out.println(i);
			/*bullet_arr.elementAt(i).setX_var((bullet_arr.elementAt(i).y-bullet_arr.elementAt(i).y_b)/10);
			bullet_arr.elementAt(i).setY_var((bullet_arr.elementAt(i).x_b-bullet_arr.elementAt(i).x)/10);*/
			bullet_arr.elementAt(i).render();
			if (bullet_arr.elementAt(i).y>480) {
				bullet_arr.removeElementAt(i);
			}
		}
	}
	public void dispose(){
		player.dispose();
		System.out.println("out");
	}
}


