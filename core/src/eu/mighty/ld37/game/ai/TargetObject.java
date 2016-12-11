package eu.mighty.ld37.game.ai;

public class TargetObject {

	int nextNode;
	int nextIdShip;
	
	public TargetObject(int nextNode, int nextIdShip)
	{
		this.nextNode = nextNode;
		this.nextIdShip = nextIdShip;
	}

	//Getters and setters
	public int getNextNode() {
		return nextNode;
	}

	public void setNextNode(int nextNode) {
		this.nextNode = nextNode;
	}

	public int getNextIdShip() {
		return nextIdShip;
	}

	public void setNextIdShip(int nextIdShip) {
		this.nextIdShip = nextIdShip;
	}
	
	
}
