package eu.mighty.ld37.game.screen;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import eu.mighty.ld37.MightyLD37Game;
import eu.mighty.ld37.game.Defaults;
import eu.mighty.ld37.game.components.*;
import eu.mighty.ld37.game.listeners.AudioListener;
import eu.mighty.ld37.game.systems.BulletSystem;
import eu.mighty.ld37.game.systems.CollidableSystem;
import eu.mighty.ld37.game.systems.HealthSystem;
import eu.mighty.ld37.game.systems.MovementSystem;
import eu.mighty.ld37.game.systems.ParallaxSystem;
import eu.mighty.ld37.game.systems.RenderingSystem;
import eu.mighty.ld37.game.systems.UserControlledSystem;

public class BattleScreen implements Screen {

	private PooledEngine entityEngine;
	private MightyLD37Game game;

	public BattleScreen(MightyLD37Game mightyLD37Game) {
		this.game = mightyLD37Game;
	}

	@Override
	public void show() {
		this.entityEngine = new PooledEngine();

		this.entityEngine.addSystem(new UserControlledSystem());
		this.entityEngine.addSystem(new MovementSystem());
		this.entityEngine.addSystem(new BulletSystem());
		this.entityEngine.addSystem(new ParallaxSystem());
		this.entityEngine.addSystem(new RenderingSystem(game.batcher));
		this.entityEngine.addSystem(new CollidableSystem());
		this.entityEngine.addSystem(new HealthSystem(game.audioClips));

		createBackgrounds();
		createPlayer();
		createFriendTeam();
		createEnemyTeam();

		AudioListener audioListener = new AudioListener(game.audioClips);
		this.entityEngine.addEntityListener(audioListener);

		this.game.musics.playTurkishMarch();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		this.entityEngine.update(delta);
	}

	private void createPlayer() {
		Entity entity = this.entityEngine.createEntity();

		PlayerComponent playerComponent = this.entityEngine
				.createComponent(PlayerComponent.class);
		ShipComponent ship = this.entityEngine
				.createComponent(ShipComponent.class);
		TextureComponent texture = this.entityEngine
				.createComponent(TextureComponent.class);
		TransformComponent position = this.entityEngine
				.createComponent(TransformComponent.class);
		MovementComponent movement = this.entityEngine
				.createComponent(MovementComponent.class);
		HasWeaponComponent weaponed = this.entityEngine
				.createComponent(HasWeaponComponent.class);
		ExhaustComponent exhaust = this.entityEngine
				.createComponent(ExhaustComponent.class);
		HurtComponent hurt = this.entityEngine
				.createComponent(HurtComponent.class);
		CollidableComponent collidable = this.entityEngine
				.createComponent(CollidableComponent.class);
		TeamComponent teamComponent = this.entityEngine
				.createComponent(TeamComponent.class);


		position.pos.set(Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 2, 0.0f);
		position.rotation = (float) Math.PI / -2;


		Texture tex = new Texture(
				Gdx.files.internal(Defaults.playerTextureFile));
		texture.region = new TextureRegion(tex, 0, 0, tex.getWidth() - 1,
				tex.getHeight() - 1);

		teamComponent.team = Defaults.FRIEND_TEAM;

		collidable.collidable_zone = new Rectangle(0, 0, tex.getWidth()-1, tex.getHeight()-1);


		exhaust.pe_left = new ParticleEffect();
		exhaust.pe_right = new ParticleEffect();
		exhaust.pe_left.load(Gdx.files.internal("exhaust_left.particle"), Gdx.files.internal(""));
		exhaust.pe_right.load(Gdx.files.internal("exhaust_right.particle"), Gdx.files.internal(""));
		exhaust.pe_left.start();
		exhaust.pe_right.start();

		hurt.pe_hurt = new ParticleEffect();
		hurt.pe_hurt.load(Gdx.files.internal("hurt.particle"), Gdx.files.internal(""));
		hurt.pe_hurt.start();

		entity.add(playerComponent);
		entity.add(ship);
		entity.add(movement);
		entity.add(position);
		entity.add(texture);
		entity.add(weaponed);
		entity.add(exhaust);
		entity.add(hurt);
		entity.add(collidable);
		entity.add(teamComponent);

		this.entityEngine.addEntity(entity);
	}

	private void createBackgrounds() {
		Texture texWoman1 = new Texture(
				Gdx.files.internal(Defaults.womanBackgroundTextureFile));
		Texture texWoman2 = new Texture(
				Gdx.files.internal(Defaults.woman2BackgroundTextureFile));
		Texture man = new Texture(
				Gdx.files.internal(Defaults.manBackgroundTextureFile));
		Texture spider = new Texture(
				Gdx.files.internal(Defaults.spiderBackgroundTextureFile));

		createBackground(Defaults.deepestBackgroundTextureFile, new Vector2(
				Defaults.windowWidth * -1, 0), new Vector2(1, 1), 2f, 0);
		createBackground(texWoman1, new Vector2(0, 0), new Vector2(3f, 3f), 1f,
				0.1f);
		createBackground(texWoman2, new Vector2(texWoman1.getWidth() * 5, 0),
				new Vector2(6, 6), 1f, 0.2f);
		createBackground(man, new Vector2(man.getWidth() * 2, 0), new Vector2(
				3, 3), 1f, -0.2f);
		createBackground(man, new Vector2(man.getWidth() * 2, 0), new Vector2(
				3, 3), 1f, -0.2f);
		createBackground(spider, new Vector2(Defaults.mapWidth * 3 / 4,
				Defaults.mapHeight - 2 * spider.getHeight()), new Vector2(0.4f,
				0.4f), 1f, -0.1f);
	}

	private void createBackground(String textureFile, Vector2 positionInScreen,
			Vector2 scale, float depth, float parallaxVelocityFactor) {
		Texture tex = new Texture(Gdx.files.internal(textureFile));
		createBackground(tex, positionInScreen, new Vector2(1, 1), depth,
				parallaxVelocityFactor);
	}

	private void createBackground(Texture tex, Vector2 positionInScreen,
			Vector2 scale, float depth, float parallaxVelocityFactor) {
		Entity entity = new Entity();

		BackgroundComponent background = this.entityEngine.createComponent(BackgroundComponent.class);
		TextureComponent texture = this.entityEngine
				.createComponent(TextureComponent.class);
		TransformComponent transform = this.entityEngine
				.createComponent(TransformComponent.class);
		MovementComponent movement = this.entityEngine.createComponent(MovementComponent.class);

		texture.region = new TextureRegion(tex, 0, 0, tex.getWidth() - 1, tex.getHeight() - 1);

		transform.pos.set(tex.getWidth() / 2 + positionInScreen.x,
				tex.getHeight() / 2 + positionInScreen.y, depth);
		transform.scale.set(scale);
		background.parallaxVelocityFactor = parallaxVelocityFactor;

		entity.add(background);
		entity.add(movement);
		entity.add(transform);
		entity.add(texture);

		this.entityEngine.addEntity(entity);
	}

	private Vector3 getRandomPosition() {
		Vector3 position = new Vector3();
		position.x = (float)Math.random() * Gdx.graphics.getWidth();
		position.y = (float)Math.random() * Gdx.graphics.getHeight();
		position.z = 0.0f;
		return position;
	}


	private Entity createShip(String textureFile, float velocity, int team) {
		Entity entity = this.entityEngine.createEntity();

		ShipComponent ship = this.entityEngine
				.createComponent(ShipComponent.class);
		TextureComponent texture = this.entityEngine
				.createComponent(TextureComponent.class);
		TransformComponent position = this.entityEngine
				.createComponent(TransformComponent.class);
		MovementComponent movement = this.entityEngine
				.createComponent(MovementComponent.class);
		movement.velocity.set(velocity, 0);
		HasWeaponComponent weaponed = this.entityEngine
				.createComponent(HasWeaponComponent.class);
		ExhaustComponent exhaust = this.entityEngine
				.createComponent(ExhaustComponent.class);
		HurtComponent hurt = this.entityEngine
				.createComponent(HurtComponent.class);
		CollidableComponent collidable = this.entityEngine
				.createComponent(CollidableComponent.class);
		TeamComponent teamComponent = this.entityEngine
				.createComponent(TeamComponent.class);
		HealthComponent health = this.entityEngine
				.createComponent(HealthComponent.class);


		position.pos.set(getRandomPosition());
		if (velocity > 0) {
			position.rotation = Defaults.PLAYER_ROTATION_HEADING_RIGHT;
		} else {
			position.rotation = Defaults.PLAYER_ROTATION_HEADING_LEFT;
		}

		Texture tex = new Texture(
				Gdx.files.internal(textureFile));
		texture.region = new TextureRegion(tex, 0, 0, tex.getWidth() - 1,
				tex.getHeight() - 1);

		collidable.collidable_zone = new Rectangle(0, 0, tex.getWidth()-1, tex.getHeight()-1);

		teamComponent.team = team;
		health.health = 1000;

		exhaust.pe_left = new ParticleEffect();
		exhaust.pe_right = new ParticleEffect();
		exhaust.pe_left.load(Gdx.files.internal("exhaust_left.particle"), Gdx.files.internal(""));
		exhaust.pe_right.load(Gdx.files.internal("exhaust_right.particle"), Gdx.files.internal(""));
		exhaust.pe_left.start();
		exhaust.pe_right.start();

		hurt.pe_hurt = new ParticleEffect();
		hurt.pe_hurt.load(Gdx.files.internal("hurt.particle"), Gdx.files.internal(""));
		hurt.pe_hurt.start();

		entity.add(ship);
		entity.add(movement);
		entity.add(position);
		entity.add(texture);
		entity.add(weaponed);
		entity.add(exhaust);
		entity.add(hurt);
		entity.add(collidable);
		entity.add(teamComponent);
		entity.add(health);

		return entity;
	}


	private void createEnemyTeam() {
		for (int i = 0; i < Defaults.NUMBER_OF_MEMBERS_IN_ONE_TEAM - 1; i++) {
			this.entityEngine.addEntity(createShip(Defaults.enemyTextureFile, 0.0f, Defaults.ENEMY_TEAM));
		}
	}

	private void createFriendTeam() {
		for (int i = 0; i < Defaults.NUMBER_OF_MEMBERS_IN_ONE_TEAM - 2; i++ ) {
			this.entityEngine.addEntity(createShip(Defaults.friendTextureFile, 0.0f, Defaults.FRIEND_TEAM));
		}
	}


	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		this.game.musics.pauseTurkishMarch();

	}

	@Override
	public void resume() {
		this.game.musics.playTurkishMarch();

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
