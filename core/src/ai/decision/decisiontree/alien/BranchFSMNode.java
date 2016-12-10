package ai.decision.decisiontree.alien;

import ai.decision.AlienFSMDecisor;
import ai.decision.decisiontree.DecisionTreeNode;

public abstract class BranchFSMNode extends DecisionTreeFSMNode {

	//The branch taken if the test is true
	DecisionTreeNode trueNode;
	//The branch taken if the test is false
	DecisionTreeNode falseNode;
	
	public BranchFSMNode(AlienFSMDecisor alienFSM,DecisionTreeNode trueNode,
			DecisionTreeNode falseNode ) {
		super(alienFSM);

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
