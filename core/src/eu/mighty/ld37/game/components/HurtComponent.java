package eu.mighty.ld37.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;

public class HurtComponent implements Component {
	public ParticleEffect pe_hurt = null;
	public boolean hurted = false;
}
