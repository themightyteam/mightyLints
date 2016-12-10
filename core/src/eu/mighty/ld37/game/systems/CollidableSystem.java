package eu.mighty.ld37.game.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import eu.mighty.ld37.game.components.CollidableComponent;
import eu.mighty.ld37.game.components.ExplosionComponent;
import eu.mighty.ld37.game.components.TeamComponent;
import eu.mighty.ld37.game.components.TransformComponent;

public class CollidableSystem extends EntitySystem {
	private ComponentMapper<CollidableComponent> cm = ComponentMapper.getFor(CollidableComponent.class);
	private ComponentMapper<TransformComponent> tm = ComponentMapper.getFor(TransformComponent.class);
	private ComponentMapper<ExplosionComponent> em = ComponentMapper.getFor(ExplosionComponent.class);
	private ComponentMapper<TeamComponent> teamM = ComponentMapper.getFor(TeamComponent.class);

	private ImmutableArray<Entity> entities;

	public CollidableSystem() {}

	@Override
	public void addedToEngine(Engine engine) {
		entities = engine.getEntitiesFor(Family.all(CollidableComponent.class, TransformComponent.class).get());
	}


	@Override
	public void update(float deltaTime) {
		// System.out.println("Entering MovementSystem's processEntity");
		super.update(deltaTime);

		Entity entityi, entityj;
		TransformComponent transformi, transformj;
		CollidableComponent collidablei, collidablej;
		ExplosionComponent explosioni, explosionj;
		TeamComponent teami, teamj;

		for (int i=0; i<entities.size(); i++) {

			entityi = entities.get(i);
			transformi = tm.get(entityi);
			collidablei = cm.get(entityi);

			for (int j=i+1; j<entities.size(); j++) {
				entityj = entities.get(j);
				transformj = tm.get(entityj);
				collidablej = cm.get(entityj);

				if ((Math.abs(transformi.pos.x - transformj.pos.x) < collidablei.collidable_zone.getWidth())
					&&(Math.abs(transformi.pos.y - transformj.pos.y) < collidablei.collidable_zone.getHeight())) {
					explosioni = em.get(entityi);
					explosionj = em.get(entityj);
					if (explosioni != null) {
						explosioni.destroyed = true;
						teami = teamM.get(entityi);
						if (teami != null) System.out.println("Equipo " + teami.team + " pierde nave");
					}
					if (explosionj != null) {
						explosionj.destroyed = true;
						teamj = teamM.get(entityj);
						if (teamj != null) System.out.println("Equipo " + teamj.team + " pierde nave");
					}

				}



			}

		}

	}
}

