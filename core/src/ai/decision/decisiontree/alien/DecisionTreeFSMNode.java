package ai.decision.decisiontree.alien;

import ai.decision.AlienFSMDecisor;
import ai.decision.decisiontree.DecisionTreeNode;

public abstract class DecisionTreeFSMNode extends DecisionTreeNode {
	
	AlienFSMDecisor alienFSM;
	
	public DecisionTreeFSMNode(AlienFSMDecisor alienFSM)
	{
		this.alienFSM = alienFSM;
	}

}
