package eu.mighty.ld37.game.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;

import eu.mighty.ld37.game.components.*;

public class CollidableSystem extends EntitySystem {

	private ImmutableArray<Entity> entities;

	public CollidableSystem() {
	}

	@Override
	public void addedToEngine(Engine engine) {
		entities = engine.getEntitiesFor(Family.all(CollidableComponent.class).get());
	}


	@Override
	public void update(float deltaTime) {

		super.update(deltaTime);

		Entity entityi, entityj;
		TransformComponent transformi, transformj;
		CollidableComponent collidablei, collidablej;
		HurtComponent hurti, hurtj;
		TeamComponent teami, teamj;
		BulletComponent bulleti, bulletj;

		for (int i = 0; i < entities.size(); i++) {

			entityi = entities.get(i);
			transformi = entityi.getComponent(TransformComponent.class);
			collidablei = entityi.getComponent(CollidableComponent.class);

			for (int j=i+1; j<entities.size(); j++) {

				entityj = entities.get(j);
				transformj = entityj.getComponent(TransformComponent.class);
				collidablej = entityj.getComponent(CollidableComponent.class);

				if ((Math.abs(transformi.pos.x - transformj.pos.x) < collidablei.collidable_zone.getWidth())
					&&(Math.abs(transformi.pos.y - transformj.pos.y) < collidablei.collidable_zone.getHeight())) {
					hurti = entityi.getComponent(HurtComponent.class);
					hurtj = entityj.getComponent(HurtComponent.class);
					bulleti = entityi.getComponent(BulletComponent.class);
					bulletj = entityj.getComponent(BulletComponent.class);
					//playeri = entityi.getComponent(PlayerComponent.class);
					//playerj = entityj.getComponent(PlayerComponent.class);
					if (hurti != null) {
						hurti.hurted = true;
						teami = entityi.getComponent(TeamComponent.class);
						if (teami != null) {
							if (entityi.getComponent(HealthComponent.class) != null) {
								entityi.getComponent(HealthComponent.class).health -= 10;
								System.out.println("Daño en equipo: " + teami.team);
							}
						}
					}
					if (hurtj != null) {
						hurtj.hurted = true;
						teamj = entityj.getComponent(TeamComponent.class);
						if (teamj != null) {
							if (entityj.getComponent(HealthComponent.class) != null) {
								entityj.getComponent(HealthComponent.class).health -= 10;
								System.out.println("Daño en equipo: " + teamj.team);
							}
						}
					}
				}
			}
		}
	}
}

