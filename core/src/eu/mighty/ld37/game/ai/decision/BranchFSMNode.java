package eu.mighty.ld37.game.ai.decision;

import ai.decision.decisiontree.DecisionTreeNode;
import eu.mighty.ld37.game.ai.AIIteration;

public abstract class BranchFSMNode extends DecisionTreeFSMNode {

	//The branch taken if the test is true
	DecisionTreeNode trueNode;
	//The branch taken if the test is false
	DecisionTreeNode falseNode;
	
	public BranchFSMNode(int idObject, AIIteration aiIteration,
			DecisionTreeNode trueNode,
			DecisionTreeNode falseNode ) {
		super(idObject, aiIteration);

		this.trueNode = trueNode;
		this.falseNode = falseNode;		
		
	}

	@Override
	public DecisionTreeNode makeDecision() {


		DecisionTreeNode newBranch = this.getBranch();

		return newBranch.makeDecision();
	}

	public abstract DecisionTreeNode getBranch();

	//Getters and setters
	public DecisionTreeNode getTrueNode() {
		return trueNode;
	}

	public void setTrueNode(DecisionTreeNode trueNode) {
		this.trueNode = trueNode;
	}

	public DecisionTreeNode getFalseNode() {
		return falseNode;
	}

	public void setFalseNode(DecisionTreeNode falseNode) {
		this.falseNode = falseNode;
	}
	
	

	
}
