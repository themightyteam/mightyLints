package eu.mighty.ld37.game.components;

import com.badlogic.ashley.core.Component;

import eu.mighty.ld37.game.Defaults;

public class HasWeaponComponent implements Component {
	public int weaponType = Defaults.TYPE_WEAPON_BASIC_MISSILE;
	public float timeToRearm = Defaults.TIME_TO_REARM_WEAPON_BASIC_MISSILE_SEC;
}
