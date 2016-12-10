package eu.mighty.ld37.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import eu.mighty.ld37.game.components.BackgroundComponent;
import eu.mighty.ld37.game.components.MovementComponent;
import eu.mighty.ld37.game.components.PlayerComponent;

public class ParallaxSystem extends IteratingSystem {
	private ComponentMapper<PlayerComponent> playerM;
	private ComponentMapper<BackgroundComponent> backgroundM;
	private ComponentMapper<MovementComponent> movementM;
	private Array<Entity> processQueue;

	public ParallaxSystem() {
		super(Family.one(PlayerComponent.class, BackgroundComponent.class).get());

		playerM = ComponentMapper.getFor(PlayerComponent.class);
		backgroundM = ComponentMapper.getFor(BackgroundComponent.class);
		movementM = ComponentMapper.getFor(MovementComponent.class);

		processQueue = new Array<Entity>();
	}

	@Override
	public void update(float deltaTime) {
		// System.out.println("Entering ParallaxSystem's update");
		super.update(deltaTime);

		Vector2 playerVelocity = null;
		for (Entity entity : processQueue) {
			PlayerComponent pl = playerM.get(entity);

			if (pl == null) {
				continue;
			}

			MovementComponent m = movementM.get(entity);
			playerVelocity = m.velocity;
			break;
		}

		for (Entity entity : processQueue) {
			BackgroundComponent bg = backgroundM.get(entity);

			if (bg == null) {
				continue;
			}
			MovementComponent m = movementM.get(entity);
			BackgroundComponent b = backgroundM.get(entity);

			m.velocity.x = playerVelocity.x * b.parallaxVelocityFactor * -1;
			//m.velocity.y = playerVelocity.y * b.parallaxVelocityFactor * -1;
			m.velocity.y = 0;
		}
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		// System.out.println("Entering ParallaxSystem's processEntity");
		processQueue.add(entity);
	}

}
