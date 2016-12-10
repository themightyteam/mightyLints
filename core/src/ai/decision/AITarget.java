package ai.decision;

import com.badlogic.gdx.math.Vector2;

public class AITarget {
	
	Vector2 nextTarget;
	
	boolean isJump;
	
	public AITarget( Vector2 nextTarget, boolean isJump) 
	{
		this.nextTarget = nextTarget;
		this.isJump = isJump;
	}

	//Getters and setters
	public Vector2 getNextTarget() {
		return nextTarget;
	}

	public void setNextTarget(Vector2 nextTarget) {
		this.nextTarget = nextTarget;
	}

	public boolean isJump() {
		return isJump;
	}

	public void setJump(boolean isJump) {
		this.isJump = isJump;
	}
	


}
