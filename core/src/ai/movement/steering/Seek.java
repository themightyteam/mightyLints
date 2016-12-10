package ai.movement.steering;

import com.badlogic.gdx.math.Vector2;

public class Seek 
{
	
	public Vector2 seekPosition(Vector2 character, Vector2 target, Vector2 maxAcceleration)
	{
		float diffx = target.x - character.x;
		float diffy = target.y - character.y;
		
		
		return new Vector2( diffx, diffy);
	}

}
