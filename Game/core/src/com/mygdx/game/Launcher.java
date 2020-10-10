package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MyGdxGame extends ApplicationAdapter {

	//Screen
	private Camera camera;
	private Viewport viewport;

	//Graphics
	SpriteBatch batch;
	Texture background;

	//Timing
	private int backgroundOffset;

	//World parameters
	private final int WORLD_WIDTH = 72;
	private final int WORLD_HEIGHT = 128;

	public MyGdxGame()
	{
		camera = new OrthographicCamera();
		viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
		backgroundOffset = 0;
	}

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("Background.png");
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
		batch.setProjectionMatrix(camera.combined);
	}

	@Override
	public void render () {
		batch.begin();
		
		backgroundOffset++;
		if(backgroundOffset % WORLD_HEIGHT == 0)
			backgroundOffset = 0;

		batch.draw(background, 0, -backgroundOffset, WORLD_WIDTH, WORLD_HEIGHT);
		batch.draw(background, 0, -backgroundOffset+WORLD_HEIGHT, WORLD_WIDTH, WORLD_HEIGHT);

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		background.dispose();
	}
}
