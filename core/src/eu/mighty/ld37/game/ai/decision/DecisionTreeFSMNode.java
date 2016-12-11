package eu.mighty.ld37.game.ai.decision;

import ai.decision.decisiontree.DecisionTreeNode;
import eu.mighty.ld37.game.ai.AIIteration;

public abstract class DecisionTreeFSMNode extends DecisionTreeNode {

	
	public int idObject;
	public AIIteration aiIteration;
	
	public DecisionTreeFSMNode(int idObject, AIIteration aiIteration)
	{
		this.idObject = idObject;
		this.aiIteration = aiIteration;
	}
}
