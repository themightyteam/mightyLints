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
import eu.mighty.ld37.game.components.AIBulletComponent;
import eu.mighty.ld37.game.components.BulletComponent;
import eu.mighty.ld37.game.components.CollidableComponent;
import eu.mighty.ld37.game.components.ExhaustComponent;
import eu.mighty.ld37.game.components.HasWeaponComponent;
import eu.mighty.ld37.game.components.MovementComponent;
import eu.mighty.ld37.game.components.PlayerComponent;
import eu.mighty.ld37.game.components.TextureComponent;
import eu.mighty.ld37.game.components.TransformComponent;
import eu.mighty.ld37.game.logic.MovementLogic;

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

	private MovementLogic moveLogic = 
			new MovementLogic();
	
	@Override
	public void processEntity(Entity entity, float deltaTime) {
		// System.out.println("Entering UserControlledSystem's processEntity");

		
		MovementComponent movement = mm.get(entity);
		TransformComponent transform = tm.get(entity);
		HasWeaponComponent weaponed = wm.get(entity);

		boolean spaceKeyPressed = Gdx.input.isKeyPressed(Keys.SPACE);
		boolean leftKeyPressed = (Gdx.input.isKeyPressed(Keys.LEFT) || 
				Gdx.input.isKeyPressed(Keys.A));
		boolean rightKeyPressed = (Gdx.input.isKeyPressed(Keys.RIGHT)
				|| Gdx.input.isKeyPressed(Keys.D));
		boolean upKeyPressed = (Gdx.input.isKeyPressed(Keys.UP) || 
				Gdx.input.isKeyPressed(Keys.W));
		boolean downKeyPressed = (Gdx.input.isKeyPressed(Keys.DOWN) || 
				Gdx.input.isKeyPressed(Keys.S));
	
		this.moveLogic.doMovement((PooledEngine) this.getEngine(), entity, deltaTime, 
				mm, tm, wm, 
				spaceKeyPressed, leftKeyPressed, rightKeyPressed, 
				upKeyPressed, downKeyPressed);
		

		
		
	}
}
