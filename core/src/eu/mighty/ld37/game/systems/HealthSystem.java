package eu.mighty.ld37.game.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IteratingSystem;

import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import eu.mighty.ld37.game.Defaults;
import eu.mighty.ld37.game.assets.AudioClips;
import eu.mighty.ld37.game.components.*;

public class HealthSystem extends IteratingSystem {
	private ComponentMapper<HealthComponent> hm = ComponentMapper.getFor(HealthComponent.class);

	public HealthSystem(AudioClips audioClips) {
		super(Family.all(HealthComponent.class).get());
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
		HealthComponent hc = hm.get(entity);

		if (hc.health <= 0) {
			TransformComponent tc = entity.getComponent(TransformComponent.class);
			createExplosion(tc.pos);
			respawnShip(entity);
			this.getEngine().removeEntity(entity);
		}
	}



	public void createExplosion(Vector3 pos) {
		Entity entity = new Entity();

		TransformComponent position = ((PooledEngine) this.getEngine())
				.createComponent(TransformComponent.class);
		ExplosionComponent explosion = ((PooledEngine) this.getEngine())
				.createComponent(ExplosionComponent.class);

		position.pos.set(pos);

		explosion.pe_explosion = new ParticleEffect();
		explosion.pe_explosion.load(Gdx.files.internal("explosion.particle"), Gdx.files.internal(""));
		explosion.pe_explosion.start();

		entity.add(position);
		entity.add(explosion);

		this.getEngine().addEntity(entity);
	}


	public void respawnShip(Entity entity) {
		Entity newEntity = new Entity();

		ImmutableArray<Component> components = entity.getComponents();
		for (Component component : components) {
			newEntity.add(component);
		}

		TransformComponent tc = newEntity.getComponent(TransformComponent.class);
		tc.pos.y = Defaults.mapHeight*2;

		HealthComponent hc = newEntity.getComponent(HealthComponent.class);
		hc.health = Defaults.HEALTH;

		DelayedSpawnComponent dc = ((PooledEngine)this.getEngine()).createComponent(DelayedSpawnComponent.class);
		newEntity.add(dc);

		System.out.println("Ship scheduled to respawn");

		this.getEngine().addEntity(newEntity);

//		ShipComponent ship = this.entityEngine
//				.createComponent(ShipComponent.class);
//		TextureComponent texture = this.entityEngine
//				.createComponent(TextureComponent.class);
//		TransformComponent position = this.entityEngine
//				.createComponent(TransformComponent.class);
//		MovementComponent movement = this.entityEngine
//				.createComponent(MovementComponent.class);
//		HasWeaponComponent weaponed = this.entityEngine
//				.createComponent(HasWeaponComponent.class);
//		ExhaustComponent exhaust = this.entityEngine
//				.createComponent(ExhaustComponent.class);
//		HurtComponent hurt = this.entityEngine
//				.createComponent(HurtComponent.class);
//		CollidableComponent collidable = this.entityEngine
//				.createComponent(CollidableComponent.class);
//		TeamComponent teamComponent = this.entityEngine
//				.createComponent(TeamComponent.class);
//		HealthComponent health = this.entityEngine
//				.createComponent(HealthComponent.class);
//
//
//		position.pos.set(getRandomPosition());
//		position.rotation = Defaults.PLAYER_ROTATION_HEADING_RIGHT;
//
//		Texture tex = new Texture(
//				Gdx.files.internal(textureFile));
//		texture.region = new TextureRegion(tex, 0, 0, tex.getWidth() - 1,
//				tex.getHeight() - 1);
//
//		collidable.collidable_zone = new Rectangle(0, 0, tex.getWidth()-1, tex.getHeight()-1);
//
//		teamComponent.team = team;
//		health.health = Defaults.HEALTH;
//
//		exhaust.pe_left = new ParticleEffect();
//		exhaust.pe_right = new ParticleEffect();
//		exhaust.pe_left.load(Gdx.files.internal("exhaust_left.particle"), Gdx.files.internal(""));
//		exhaust.pe_right.load(Gdx.files.internal("exhaust_right.particle"), Gdx.files.internal(""));
//		exhaust.pe_left.start();
//		exhaust.pe_right.start();
//
//		hurt.pe_hurt = new ParticleEffect();
//		hurt.pe_hurt.load(Gdx.files.internal("hurt.particle"), Gdx.files.internal(""));
//		hurt.pe_hurt.start();
//
//		newEntity.add(ship);
//		newEntity.add(movement);
//		newEntity.add(position);
//		newEntity.add(texture);
//		newEntity.add(weaponed);
//		newEntity.add(exhaust);
//		newEntity.add(hurt);
//		newEntity.add(collidable);
//		newEntity.add(teamComponent);
//		newEntity.add(health);

	}

}
