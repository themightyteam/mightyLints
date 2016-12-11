package eu.mighty.ld37.game.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;

import eu.mighty.ld37.game.Defaults;
import eu.mighty.ld37.game.components.*;
import eu.mighty.ld37.game.logic.ScoreLogic;

public class CollidableSystem extends EntitySystem {

	private ImmutableArray<Entity> entities;
	private ScoreLogic sl;

	public CollidableSystem(ScoreLogic sl) {
		this.sl = sl;
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
		ShipComponent shipi, shipj;
		CanScoreComponent csi, csj;
		GoalComponent goali, goalj;

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
					shipi = entityi.getComponent(ShipComponent.class);
					shipj = entityj.getComponent(ShipComponent.class);
					csi = entityi.getComponent(CanScoreComponent.class);
					csj = entityj.getComponent(CanScoreComponent.class);
					goali = entityi.getComponent(GoalComponent.class);
					goalj = entityj.getComponent(GoalComponent.class);
					if (shipi != null && shipj != null) {
						if (csi != null && goalj != null) {
							sl.goalFriendTeam();
						} else if (goali != null && csj != null) {
							sl.goalEnemyTeam();
						} else {
							if (hurti != null) {
								hurti.hurted = true;
								teami = entityi.getComponent(TeamComponent.class);
								if (teami != null) {
									if (entityi.getComponent(HealthComponent.class) != null) {
										entityi.getComponent(HealthComponent.class).health -= 10;
										System.out.println("Hurt in team: " + teami.team);
									}
								}
							}
							if (hurtj != null) {
								hurtj.hurted = true;
								teamj = entityj.getComponent(TeamComponent.class);
								if (teamj != null) {
									if (entityj.getComponent(HealthComponent.class) != null) {
										entityj.getComponent(HealthComponent.class).health -= 10;
										System.out.println("Hurt in team: " + teamj.team);
									}
								}
							}
						}
					} else {
						if (shipi == null) {
							makeHurtWithMissile(entityj);
						} else {
							makeHurtWithMissile(entityi);
						}
					}
				}
			}
		}
	}


	private void makeHurtWithMissile(Entity entity) {
		HurtComponent hurt = entity.getComponent(HurtComponent.class);
		if (hurt != null) {
			hurt.hurted = true;
			TeamComponent team = entity.getComponent(TeamComponent.class);
			if (team != null) {
				HealthComponent health = entity.getComponent(HealthComponent.class);
				if (health != null) {
					health.health -= Defaults.MISSILE_HURT;
					System.out.println("Hurt in team " + team.team + " by missile");
				}
			}
		}
	}

}


