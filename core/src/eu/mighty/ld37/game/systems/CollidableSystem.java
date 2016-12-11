package eu.mighty.ld37.game.systems;

import com.badlogic.ashley.core.*;
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

		ComponentMapper<CollidableComponent> cm = ComponentMapper.getFor(CollidableComponent.class);
		ComponentMapper<TransformComponent> tm = ComponentMapper.getFor(TransformComponent.class);
		ComponentMapper<HurtComponent> hm = ComponentMapper.getFor(HurtComponent.class);
		ComponentMapper<ShipComponent> sm = ComponentMapper.getFor(ShipComponent.class);
		ComponentMapper<CanScoreComponent> csm = ComponentMapper.getFor(CanScoreComponent.class);
		ComponentMapper<GoalComponent> gm = ComponentMapper.getFor(GoalComponent.class);
		ComponentMapper<TeamComponent> teamM = ComponentMapper.getFor(TeamComponent.class);


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
			transformi = tm.get(entityi);
			collidablei = cm.get(entityi);

			for (int j=i+1; j<entities.size(); j++) {

				entityj = entities.get(j);
				transformj = tm.get(entityj);
				collidablej = cm.get(entityj);

				if ((Math.abs(transformi.pos.x - transformj.pos.x) < collidablei.collidable_zone.getWidth())
					&&(Math.abs(transformi.pos.y - transformj.pos.y) < collidablei.collidable_zone.getHeight())) {
					hurti = hm.get(entityi);
					hurtj = hm.get(entityj);
					shipi = sm.get(entityi);
					shipj = sm.get(entityj);
					csi = csm.get(entityi);
					csj = csm.get(entityj);
					goali = gm.get(entityi);
					goalj = gm.get(entityj);

					if (shipi != null && shipj != null) {
						if (csi != null && goalj != null) {
							teami = teamM.get(entityi);
							teamj = teamM.get(entityj);
							if (teami.team != teamj.team) {
								sl.goalFriendTeam();
							}
						} else if (goali != null && csj != null) {
							teami = teamM.get(entityi);
							teamj = teamM.get(entityj);
							if (teami.team != teamj.team) {
								sl.goalEnemyTeam();
							}
						} else {
							if (hurti != null) {
								hurti.hurted = true;
								teami = teamM.get(entityi);
								if (teami != null) {
									if (entityi.getComponent(HealthComponent.class) != null) {
										entityi.getComponent(HealthComponent.class).health -= 10;
										System.out.println("Hurt in team: " + teami.team);
									}
								}
							}
							if (hurtj != null) {
								hurtj.hurted = true;
								teamj = teamM.get(entityj);
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
							this.getEngine().removeEntity(entityi);
						} else {
							makeHurtWithMissile(entityi);
							this.getEngine().removeEntity(entityj);
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


