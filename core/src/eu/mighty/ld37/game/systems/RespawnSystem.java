package eu.mighty.ld37.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import eu.mighty.ld37.game.Defaults;
import eu.mighty.ld37.game.components.*;

public class RespawnSystem extends IteratingSystem {
	private ComponentMapper<DelayedSpawnComponent> dm = ComponentMapper.getFor(DelayedSpawnComponent.class);
	private int playerRole;

	public RespawnSystem(int playerRole) {
		super(Family.all(DelayedSpawnComponent.class).get());
		this.playerRole = playerRole;
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
		DelayedSpawnComponent delayed = dm.get(entity);
		//System.out.println("Time to respawn: " + delayed.timeToSpawn);
		delayed.timeToSpawn -= deltaTime;
		if (delayed.timeToSpawn <= 0) {
			if (entity.getComponent(PlayerComponent.class) == null) {
				TransformComponent tc = entity.getComponent(TransformComponent.class);
				tc.pos.set(getRandomPosition());
			} else {
				TextureComponent texture = ((PooledEngine)this.getEngine()).createComponent(TextureComponent.class);
				HealthComponent health = ((PooledEngine)this.getEngine()).createComponent(HealthComponent.class);
				CollidableComponent collidable = ((PooledEngine)this.getEngine()).createComponent(CollidableComponent.class);

				String playerTextureFile;
				switch (playerRole) {
					case Defaults.ROLE_SCORER:
						playerTextureFile = Defaults.cyanShip1TextureFile;
						break;
					case Defaults.ROLE_GOAL:
						playerTextureFile = Defaults.cyanGoalShipTextureFile;
						break;
					default:
						playerTextureFile = Defaults.playerTextureFile;
				}

				Texture tex = new Texture(
						Gdx.files.internal(playerTextureFile));
				texture.region = new TextureRegion(tex, 0, 0, tex.getWidth() - 1,
						tex.getHeight() - 1);
				collidable.collidable_zone = new Rectangle(0, 0, tex.getWidth()-1, tex.getHeight()-1);
				health.health = Defaults.HEALTH;
				entity.add(collidable);
				entity.add(health);
				entity.add(texture);
			}
			entity.remove(DelayedSpawnComponent.class);
			//System.out.println("Ship respawned!!!!");
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
