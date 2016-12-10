package eu.mighty.ld37.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import com.badlogic.gdx.Gdx;
import eu.mighty.ld37.game.Defaults;
import eu.mighty.ld37.game.components.MovementComponent;
import eu.mighty.ld37.game.components.TransformComponent;

public class MovementSystem extends IteratingSystem {
	static final float FRUSTUM_WIDTH = Gdx.graphics.getWidth();
	static final float FRUSTUM_HEIGHT = Gdx.graphics.getHeight();

	private ComponentMapper<TransformComponent> tm = ComponentMapper.getFor(TransformComponent.class);
	private ComponentMapper<MovementComponent> mm = ComponentMapper.getFor(MovementComponent.class);
	
	public MovementSystem() {
		super(Family.all(TransformComponent.class, MovementComponent.class).get());
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
		// System.out.println("Entering MovementSystem's processEntity");
		TransformComponent transform = tm.get(entity);
		MovementComponent movement = mm.get(entity);

		transform.pos.x += movement.velocity.x * deltaTime;
		transform.pos.y += movement.velocity.y * deltaTime;

		// Wrap around
		transform.pos.x = transform.pos.x % Defaults.mapWidth;
		if (transform.pos.x < 0) {
			transform.pos.x = transform.pos.x + Defaults.mapWidth;
		}

		// Y limits
		if (transform.pos.y > FRUSTUM_HEIGHT*2.5) transform.pos.y = FRUSTUM_HEIGHT*2.5f;
		if (transform.pos.y < 0) transform.pos.y = 0;

		/*
		 * System.out.println("Speed.x -> " +
		 * String.valueOf(movement.velocity.x) + " Accel.x -> " +
		 * String.valueOf(movement.accel.x));
		 */
		// System.out.println("transform.pos.x " +
		// String.valueOf(transform.pos.x));
	}
}
