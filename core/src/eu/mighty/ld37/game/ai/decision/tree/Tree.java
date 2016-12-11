package eu.mighty.ld37.game.ai.decision.tree;

import eu.mighty.ld37.game.ai.AIIteration;
import ai.decision.decisiontree.DecisionTreeNode;

public abstract class Tree {
	
	DecisionTreeNode rootNode;
	int myId;
	AIIteration aiIteration;
	int timeOut;
	
	public Tree(int myId, AIIteration aiIteration,
	int timeOut)
	{
		this.myId = myId;
		this.aiIteration = aiIteration;
		this.timeOut = timeOut;
	}

}
