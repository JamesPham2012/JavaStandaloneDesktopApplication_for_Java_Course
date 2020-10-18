package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.graphics.Camera;
//import com.badlogic.gdx.graphics.GL20;
//import com.badlogic.gdx.graphics.OrthographicCamera;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.utils.viewport.StretchViewport;
//import com.badlogic.gdx.utils.viewport.Viewport;

public class Launcher extends ApplicationAdapter {

	Background BG = new Background();
	MainMenu mainMenu = new mainMenu();
	
	@Override 
	public void create () {
		BG.create();
		mainMenu.create();
	}

	@Override
	public void render () {
		BG.render();
		mainMenu.render();
	}

	@Override
	public void resize(int width, int height) {
		BG.resize(width, height);
	}

	@Override
	public void dispose () {
		BG.dispose();
		mainMenu.dispose();
	}
}
