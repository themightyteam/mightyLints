package eu.mighty.ld37.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import eu.mighty.ld37.game.Defaults;
import eu.mighty.ld37.game.components.BulletComponent;
import eu.mighty.ld37.game.components.CollidableComponent;
import eu.mighty.ld37.game.components.ExhaustComponent;
import eu.mighty.ld37.game.components.HasWeaponComponent;
import eu.mighty.ld37.game.components.MovementComponent;
import eu.mighty.ld37.game.components.PlayerComponent;
import eu.mighty.ld37.game.components.TextureComponent;
import eu.mighty.ld37.game.components.TransformComponent;

public class UserControlledSystem extends IteratingSystem {
	private ComponentMapper<MovementComponent> mm = ComponentMapper
			.getFor(MovementComponent.class);
	private ComponentMapper<TransformComponent> tm = ComponentMapper
			.getFor(TransformComponent.class);
	private ComponentMapper<HasWeaponComponent> wm = ComponentMapper
			.getFor(HasWeaponComponent.class);

	public UserControlledSystem() {
		super(Family.all(TransformComponent.class, MovementComponent.class,
				PlayerComponent.class).get());
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
		// System.out.println("Entering UserControlledSystem's processEntity");
		MovementComponent movement = mm.get(entity);
		TransformComponent transform = tm.get(entity);
		HasWeaponComponent weaponed = wm.get(entity);

		if (weaponed != null) {
			weaponed.timeToRearm -= deltaTime;
			if (Gdx.input.isKeyPressed(Keys.SPACE)) {
				if (weaponed.timeToRearm <= 0) {
					weaponed.timeToRearm = Defaults.TIME_TO_REARM_WEAPON_BASIC_MISSILE_SEC;
					if (transform.rotation == Defaults.PLAYER_ROTATION_HEADING_LEFT) {
						createMissile(transform.pos, new Vector3(
								Defaults.missileBasicSpeed * -1, 0, 0),
								transform.rotation);
					} else {
						createMissile(transform.pos, new Vector3(
								Defaults.missileBasicSpeed, 0, 0),
								transform.rotation);
					}
				}
			}
		}

		movement.accel.x = 0;
		movement.velocity.y = 0;
		
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			movement.accel.x = Defaults.MAX_ACCEL_X * -1;
			transform.rotation = Defaults.PLAYER_ROTATION_HEADING_LEFT;
		}

		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			movement.accel.x = Defaults.MAX_ACCEL_X;
			transform.rotation = Defaults.PLAYER_ROTATION_HEADING_RIGHT;
		}

		if (Gdx.input.isKeyPressed(Keys.UP)) {
			movement.velocity.y = Defaults.MAX_SPEED_Y;
		}

		if (Gdx.input.isKeyPressed(Keys.DOWN)) {
			movement.velocity.y = Defaults.MAX_SPEED_Y * -1;
		}

		// friction
		//if (movement.accel.x == 0 & movement.velocity.x != 0) {
		if (movement.velocity.x != 0) {
			if (movement.velocity.x > Defaults.MIN_VEL) {
				movement.accel.x += Defaults.MAX_FRICTION_X * -1;
			} else if (movement.velocity.x < Defaults.MIN_VEL * -1) {
				movement.accel.x += Defaults.MAX_FRICTION_X;
			} else {
				movement.velocity.x = 0;
			}
		}

		movement.velocity.x += movement.accel.x * deltaTime;
		if (movement.velocity.x > Defaults.MAX_SPEED_X)
			movement.velocity.x = Defaults.MAX_SPEED_X;
		if (movement.velocity.x < Defaults.MAX_SPEED_X * -1)
			movement.velocity.x = Defaults.MAX_SPEED_X * -1;
	}

	private void createMissile(Vector3 pos, Vector3 velocity, float rotation) {
		Entity entity = new Entity();

		BulletComponent missileComponent = ((PooledEngine) this.getEngine())
				.createComponent(BulletComponent.class);
		TextureComponent texture = ((PooledEngine) this.getEngine())
				.createComponent(TextureComponent.class);
		TransformComponent position = ((PooledEngine) this.getEngine())
				.createComponent(TransformComponent.class);
		MovementComponent movement = ((PooledEngine) this.getEngine())
				.createComponent(MovementComponent.class);
		CollidableComponent collidable = ((PooledEngine) this.getEngine())
				.createComponent(CollidableComponent.class);
		ExhaustComponent exhaust = ((PooledEngine) this.getEngine())
				.createComponent(ExhaustComponent.class);


		Texture tex = new Texture(
				Gdx.files.internal(Defaults.missileBasicTextureFile));
		texture.region = new TextureRegion(tex, 0, 0, tex.getWidth() - 1,
				tex.getHeight() - 1);

		collidable.collidable_zone = new Rectangle(0, 0, tex.getWidth()-1, tex.getHeight()-1);

		position.pos.set(pos);
		position.rotation = rotation;

		if (velocity.x > 0) {
			position.pos.x += Defaults.PLAYER_WIDTH;
		} else {
			position.pos.x -= Defaults.PLAYER_WIDTH;
		}

		exhaust.pe_left = new ParticleEffect();
		exhaust.pe_right = new ParticleEffect();
		exhaust.pe_left.load(Gdx.files.internal("exhaust_left.particle"),
				Gdx.files.internal(""));
		exhaust.pe_right.load(Gdx.files.internal("exhaust_right.particle"),
				Gdx.files.internal(""));
		exhaust.pe_left.start();
		exhaust.pe_right.start();

		movement.velocity.set(velocity.x, velocity.y);

		entity.add(missileComponent);
		entity.add(movement);
		entity.add(position);
		entity.add(texture);
		entity.add(collidable);
		entity.add(exhaust);

		this.getEngine().addEntity(entity);
	}
}
