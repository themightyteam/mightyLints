package eu.mighty.ld37.game.components;

import com.badlogic.ashley.core.Component;
import eu.mighty.ld37.game.Defaults;

/**
 * 
 * This component is added for those ships which are allowed to score 
 * 
 * @author hmightypirate
 *
 */


public class CanScoreComponent  implements Component {
    public float timeToScore = Defaults.TIME_TO_RESCORE;
    public boolean canScore = true;

}
