package ai.decision.decisiontree.alien;

import ai.decision.AlienFSMDecisor;
import ai.decision.decisiontree.DecisionTreeNode;

public class ActionGoToClosestEnemy  extends ActionFSMNode {

	
	public ActionGoToClosestEnemy(AlienFSMDecisor alienFSM) {
		super(alienFSM);
	
		this.nextNode = -1;
	}

	@Override
	public DecisionTreeNode makeDecision() {

		this.nextNode = this.alienFSM.obtainClosestEnemySquare();
		
		return this;
	}

	
	
}
