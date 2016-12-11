package eu.mighty.ld37.game.ai.decision.action;

import eu.mighty.ld37.game.ai.AIIteration;
import eu.mighty.ld37.game.ai.decision.DecisionTreeFSMNode;

public  abstract class ActionFSMNode extends DecisionTreeFSMNode {

	int nextNode;
	int nextIdShip;

	public ActionFSMNode( int idObject, AIIteration aiIteration)
	{
		super(idObject, aiIteration);
	}
	
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
