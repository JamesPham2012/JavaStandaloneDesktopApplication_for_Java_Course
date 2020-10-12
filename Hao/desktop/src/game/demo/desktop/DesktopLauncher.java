package game.demo.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import game.demo.MyGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.resizable=false;
		config.width=1280;
		config.height=720;
		config.maxNetThreads=16;
		config.title="Noodle Rocket Launcher";
		config.foregroundFPS=144;
		new LwjglApplication(new MyGdxGame(), config);
	}
}
