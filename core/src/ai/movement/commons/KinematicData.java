package ai.movement.commons;

import com.badlogic.gdx.math.Vector2;

public class KinematicData 
{
	Vector2 position;	
	float orientation;
	Vector2 velocity;
	float rotation;
	
	public KinematicData(Vector2 position, 
			float orientation, 
			Vector2 velocity,
			float rotation)
	{
		this.position = position;
		this.orientation = orientation;
		this.velocity = velocity;
		this.rotation = rotation;
	}
}
