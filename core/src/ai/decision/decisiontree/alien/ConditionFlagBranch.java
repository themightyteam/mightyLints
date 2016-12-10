package ai.decision.decisiontree.alien;

import ai.decision.AlienFSMDecisor;
import ai.decision.decisiontree.DecisionTreeNode;

public class ConditionFlagBranch extends BranchFSMNode {

	public ConditionFlagBranch(AlienFSMDecisor alienFSM, DecisionTreeNode trueNode,
			DecisionTreeNode falseNode) {
		super(alienFSM, trueNode, falseNode);
	}

	@Override
	public DecisionTreeNode getBranch() {
		
		if (this.alienFSM.isiHaveTheFlag())
			return trueNode;
		
		return falseNode;
	}

}
