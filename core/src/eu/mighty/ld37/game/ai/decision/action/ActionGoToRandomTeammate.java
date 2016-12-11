package eu.mighty.ld37.game.ai.decision.action;

import ai.decision.decisiontree.DecisionTreeNode;
import eu.mighty.ld37.game.ai.AIIteration;
import eu.mighty.ld37.game.ai.TargetObject;

public class ActionGoToRandomTeammate  extends ActionFSMNode {

	public ActionGoToRandomTeammate(int idObject, AIIteration aiIteration) {
		super(idObject, aiIteration);

		this.nextNode = -1;
		this.nextIdShip = -1;
	}

	@Override
	public DecisionTreeNode makeDecision() {

		TargetObject target = this.aiIteration.obtainRandomTeammateSquare(this.idObject);
		
		this.nextNode = target.getNextNode();
		this.nextIdShip = target.getNextIdShip();

		return this;
	}

}
