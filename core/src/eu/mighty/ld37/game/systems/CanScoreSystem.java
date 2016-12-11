package eu.mighty.ld37.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import eu.mighty.ld37.game.Defaults;
import eu.mighty.ld37.game.components.BulletComponent;
import eu.mighty.ld37.game.components.CanScoreComponent;

public class CanScoreSystem extends IteratingSystem {
	private ComponentMapper<CanScoreComponent> csm = ComponentMapper.getFor(CanScoreComponent.class);

	public CanScoreSystem() {
		super(Family.all(CanScoreComponent.class).get());
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
		CanScoreComponent cs = csm.get(entity);

		if (!cs.canScore) {
			cs.timeToScore -= deltaTime;
			if (cs.timeToScore < 0) {
				cs.canScore = true;
				cs.timeToScore = Defaults.TIME_TO_RESCORE;
			}
		}
	}
}
