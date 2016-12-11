package eu.mighty.ld37.game.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
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

//				if ((Math.abs(transformi.pos.x - transformj.pos.x) < collidablei.collidable_zone.getWidth())
//					&&(Math.abs(transformi.pos.y - transformj.pos.y) < collidablei.collidable_zone.getHeight())) {
				if (areColliding(transformi.pos, transformj.pos, collidablei.collidable_zone, collidablej.collidable_zone)) {
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
								System.out.println("Goal from team " + teami);
								if (teami.team == Defaults.FRIEND_TEAM) {
									sl.goalFriendTeam();
								} else {
									sl.goalEnemyTeam();
								}
							}
						} else if (goali != null && csj != null) {
							teami = teamM.get(entityi);
							teamj = teamM.get(entityj);
							if (teami.team != teamj.team) {
								System.out.println("Goal from team " + teamj);
								if (teamj.team == Defaults.FRIEND_TEAM) {
									sl.goalFriendTeam();
								} else {
									sl.goalEnemyTeam();
								}
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


	private boolean areColliding(Vector3 pos1, Vector3 pos2, Rectangle rect1, Rectangle rect2) {
//		return ((Math.abs(pos1.x - pos2.x) < Math.min(rect1.getWidth(), rect2.getWidth()))
//				&&(Math.abs(pos1.y - pos2.y) < Math.min(rect1.getHeight(), rect2.getHeight())));
		rect1.setPosition(pos1.x, pos1.y);
		rect2.setPosition(pos2.x, pos2.y);

		return rect1.overlaps(rect2);

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
					if (health.health <= 0) {
						System.out.println("Ship of team " + team.team + " destroyed by missile");
						if (team.team == Defaults.FRIEND_TEAM) {
							sl.pointFriendTeam();
						} else {
							sl.pointEnemyTeam();
						}
					}
					//System.out.println("Hurt in team " + team.team + " by missile");
				}
			}
		}
	}
}


