package eu.mighty.ld37.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IteratingSystem;

import eu.mighty.ld37.game.components.BulletComponent;

public class BulletSystem extends IteratingSystem {
	private ComponentMapper<BulletComponent> bm = ComponentMapper
			.getFor(BulletComponent.class);
	
	public BulletSystem() {
		super(Family.all(BulletComponent.class).get());
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
		// System.out.println("Entering BulletSystem's processEntity");
		BulletComponent bullet = bm.get(entity);

		bullet.timeOfLifeLeft -= deltaTime;
		if (bullet.timeOfLifeLeft <= 0) {
			((PooledEngine) this.getEngine()).removeEntity(entity);
		}
	}
}
