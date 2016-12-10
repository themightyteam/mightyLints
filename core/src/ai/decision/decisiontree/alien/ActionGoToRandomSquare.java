package ai.decision.decisiontree.alien;

import ai.decision.AlienFSMDecisor;
import ai.decision.decisiontree.DecisionTreeNode;

public class ActionGoToRandomSquare extends RandomActionNode {


	public ActionGoToRandomSquare(AlienFSMDecisor alienFSM) {
		super(alienFSM);

	}

	@Override
	public int obtainNextNode() {
			
		this.nextNode = this.alienFSM.obtainRandomSquare();
		
		return this.nextNode;
	}

}
