package ai.decision.decisiontree.alien;

import ai.decision.AlienFSMDecisor;

public  abstract class ActionFSMNode extends DecisionTreeFSMNode {

	int nextNode;

	public ActionFSMNode(AlienFSMDecisor alienFSM)
	{
		super(alienFSM);
	}
	
	public int getNextNode() {
		return nextNode;
	}

	public void setNextNode(int nextNode) {
		this.nextNode = nextNode;
	}

	
	
}
