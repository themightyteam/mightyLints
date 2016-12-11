package eu.mighty.ld37.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector3;
import eu.mighty.ld37.game.Defaults;
import eu.mighty.ld37.game.components.DelayedSpawnComponent;
import eu.mighty.ld37.game.components.TransformComponent;

public class RespawnSystem extends IteratingSystem {
	private ComponentMapper<DelayedSpawnComponent> dm = ComponentMapper.getFor(DelayedSpawnComponent.class);

	public RespawnSystem() {
		super(Family.all(DelayedSpawnComponent.class).get());
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
		DelayedSpawnComponent delayed = dm.get(entity);
		System.out.println("Time to respawn: " + delayed.timeToSpawn);
		delayed.timeToSpawn -= deltaTime;
		if (delayed.timeToSpawn <= 0) {
			TransformComponent tc = entity.getComponent(TransformComponent.class);
			tc.pos.set(getRandomPosition());
			entity.remove(DelayedSpawnComponent.class);
			System.out.println("Ship respawned!!!!");
		}

	}

	private Vector3 getRandomPosition() {
		Vector3 position = new Vector3();
		position.x = (float)Math.random() * Defaults.mapWidth;
		position.y = (float)Math.random() * Defaults.mapHeight;
		position.z = 0.0f;
		return position;
	}

}
