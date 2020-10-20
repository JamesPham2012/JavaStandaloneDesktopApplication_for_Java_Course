package game.demo.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import UI.MainClass;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.resizable=true;
		config.width=1280;
		config.height=720;
		config.maxNetThreads=16;
		config.title="Noodle Rocket Launcher";
		config.foregroundFPS=144;
		new LwjglApplication(new MainClass(), config);
	}
}
