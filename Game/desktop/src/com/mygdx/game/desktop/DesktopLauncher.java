package com.mygdx.game.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.DataConfig;
import com.mygdx.game.Launcher;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();//creating object named Config for options
		//Need to reevaluate in advance the offset back screen
		config.resizable=false;
		config.width=1280;
		config.height=720;
		config.maxNetThreads=16;
		config.title="Noodle Rocket Launcher";

		config.foregroundFPS=144;
		new LwjglApplication(new Launcher(), config);

	}
}
