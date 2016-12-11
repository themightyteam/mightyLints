package eu.mighty.ld37.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import eu.mighty.ld37.MightyLD37Game;
import eu.mighty.ld37.game.Defaults;

public class LintSelectionScreen extends DefaultScreen implements Screen {

	SpriteBatch batch;
	Texture img;
	Sprite spr;
	OrthographicCamera cam;
	StretchViewport sv;

	int waitFramesForHandle = Defaults.SCREEN_INITIAL_WAIT_TIME;

	public LintSelectionScreen(MightyLD37Game game) {
		super(game);

		this.cam = new OrthographicCamera();
		this.sv = new StretchViewport(Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight(), this.cam);
		this.cam.position.set(Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 2, 0);

		this.batch = new SpriteBatch();
		this.img = new Texture("screenImages/lintSelection.png");
		this.spr = new Sprite(this.img);
		this.spr.setPosition(0, 0);
		this.spr.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		this.waitFramesForHandle -= 1;
		if (this.waitFramesForHandle <= 0)
			handleInput();

		batch.setProjectionMatrix(this.cam.combined);

		this.batch.begin();
		this.spr.draw(batch);
		this.batch.end();
	}

	@Override
	public void resize(int width, int height) {
		this.sv.update(width, height);
	}

	private void handleInput() {
		int role = -1;
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			role = Defaults.ROLE_SCORER;
		} else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			role = Defaults.ROLE_SHOOTER;
		} else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			role = Defaults.ROLE_GOAL;
		}

		if (role != -1) {
			this.mightyGame.setScreen(new BattleScreen(this.mightyGame, role));
		}
	}

}
