package ai.decision.decisiontree.alien;

import ai.decision.AlienFSMDecisor;
import ai.decision.decisiontree.DecisionTreeNode;

public class ActionGoToClosestTeammateWithFlag  extends ActionFSMNode {
	
	public ActionGoToClosestTeammateWithFlag(AlienFSMDecisor alienFSM) {
		super(alienFSM);
	
		this.nextNode = -1;
	}

	@Override
	public DecisionTreeNode makeDecision() {

		this.nextNode = this.alienFSM.obtainClosestTeamMateSquare();
		
		return this;
	}

	
	
}
