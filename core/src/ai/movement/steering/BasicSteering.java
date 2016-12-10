package ai.movement.steering;

import com.badlogic.gdx.math.Vector2;

public class BasicSteering 
{

	public static final int SEEK = 0;

	int currentState;

	Seek seekProcess;

	public BasicSteering()
	{
		this.currentState = BasicSteering.SEEK;
		this.seekProcess = new Seek();
	}

	public Vector2 updateSteering(Vector2 character, Vector2 target, Vector2 maxAcceleration)
	{

		switch(this.currentState)
		{
		case SEEK:
			return seekProcess.seekPosition(character, target, maxAcceleration);

		default:
			//Seek by default
			return seekProcess.seekPosition(character, target, maxAcceleration);

		}


	}


}
