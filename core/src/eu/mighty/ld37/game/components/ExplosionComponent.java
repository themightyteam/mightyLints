package eu.mighty.ld37.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;

public class ExplosionComponent implements Component {
	public ParticleEffect pe_explosion = null;
	public boolean destroyed = false;
}
