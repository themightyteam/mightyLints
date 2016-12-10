package eu.mighty.ld37.game.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import eu.mighty.ld37.game.components.CollidableComponent;
import eu.mighty.ld37.game.components.ExplosionComponent;
import eu.mighty.ld37.game.components.TeamComponent;
import eu.mighty.ld37.game.components.TransformComponent;

public class CollidableSystem extends EntitySystem {

	private ImmutableArray<Entity> entities;

	public CollidableSystem() {}

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
		ExplosionComponent explosioni, explosionj;
		TeamComponent teami, teamj;

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
					explosioni = entityi.getComponent(ExplosionComponent.class);
					explosionj = entityj.getComponent(ExplosionComponent.class);
					if (explosioni != null) {
						explosioni.destroyed = true;
						teami = entityi.getComponent(TeamComponent.class);
						if (teami != null) System.out.println("Equipo " + teami.team + " pierde nave");
					}
					if (explosionj != null) {
						explosionj.destroyed = true;
						teamj = entityi.getComponent(TeamComponent.class);
						if (teamj != null) System.out.println("Equipo " + teamj.team + " pierde nave");
					}
				}
			}
		}
	}
}

