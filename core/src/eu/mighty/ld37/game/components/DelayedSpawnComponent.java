package eu.mighty.ld37.game.components;

import com.badlogic.ashley.core.Component;
import eu.mighty.ld37.game.Defaults;

public class DelayedSpawnComponent implements Component {
	public float timeToSpawn = Defaults.RESPAWN_TIME;
}
