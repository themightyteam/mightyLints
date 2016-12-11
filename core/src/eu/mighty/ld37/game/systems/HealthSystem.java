package eu.mighty.ld37.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IteratingSystem;

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
}
