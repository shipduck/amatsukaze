package moe.shipduck.amatsukaze.android;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

import moe.shipduck.amatsukaze.Basic3D;
import moe.shipduck.amatsukaze.MyGdxGame;

public class LiveWallpaperScreen implements Screen {
	ApplicationListener myGame;

	public LiveWallpaperScreen(final Game game) {
		//this.myGame = new MyGdxGame();
		this.myGame = new Basic3D();
		this.myGame.create();
	}

	@Override
	public void dispose() {
		myGame.dispose();
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
	}

	@Override
	public void pause() {
		myGame.pause();
	}

	@Override
	public void render(float delta) {
		myGame.render();
	}

	@Override
	public void resize(int width, int height) {
		myGame.resize(width, height);

	}

	@Override
	public void resume() {
		myGame.resume();
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}
}
