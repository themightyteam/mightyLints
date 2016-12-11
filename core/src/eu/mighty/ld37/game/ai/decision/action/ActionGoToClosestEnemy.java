package eu.mighty.ld37.game.ai.decision.action;

import ai.decision.decisiontree.DecisionTreeNode;
import eu.mighty.ld37.game.ai.AIIteration;


public class ActionGoToClosestEnemy  extends ActionFSMNode {

	public ActionGoToClosestEnemy(int idObject, AIIteration aiIteration) {
		super(idObject, aiIteration);

		this.nextNode = -1;
	}

	@Override
	public DecisionTreeNode makeDecision() {

		this.nextNode = this.aiIteration.obtainClosestEnemySquare(this.idObject);

		return this;
	}

}
