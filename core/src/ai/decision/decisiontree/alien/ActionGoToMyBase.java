package ai.decision.decisiontree.alien;

import ai.decision.AlienFSMDecisor;
import ai.decision.decisiontree.DecisionTreeNode;

public class ActionGoToMyBase extends ActionFSMNode
{
	
	public ActionGoToMyBase(AlienFSMDecisor alienFSM) {
		super(alienFSM);

	}

	@Override
	public DecisionTreeNode makeDecision() {
		// TODO Auto-generated method stub
		this.nextNode = alienFSM.obtainMyBaseSquare();
		
		return this;
	}
	
	
}
