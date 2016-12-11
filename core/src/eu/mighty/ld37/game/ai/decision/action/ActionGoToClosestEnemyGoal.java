package eu.mighty.ld37.game.ai.decision.action;

import ai.decision.decisiontree.DecisionTreeNode;
import eu.mighty.ld37.game.ai.AIIteration;

public class ActionGoToClosestEnemyGoal extends ActionFSMNode {

	public ActionGoToClosestEnemyGoal(int idObject, AIIteration aiIteration) {
		super(idObject, aiIteration);

		this.nextNode = -1;
	}

	@Override
	public DecisionTreeNode makeDecision() {

		this.nextNode = this.aiIteration.obtainClosestGoalEnemySquare(this.idObject);

		return this;
	}

}
