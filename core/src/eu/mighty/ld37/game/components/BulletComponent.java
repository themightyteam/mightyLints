package eu.mighty.ld37.game.components;

import com.badlogic.ashley.core.Component;

import eu.mighty.ld37.game.Defaults;

public class BulletComponent implements Component {
	public float timeOfLifeLeft = Defaults.TIME_OF_LIFE_BASIC_MISSILE_SEC;
}
