package ai.decision.decisiontree;


/**
 * 
 * This class implements the basic functionality of a node
 * 
 * It should be extended by other class which will implement
 * 	the conditions inside each node.
 * 
 * @author hector
 *
 */
public abstract class BranchNode extends DecisionTreeNode {

	//The branch taken if the test is true
	DecisionTreeNode trueNode;
	//The branch taken if the test is false
	DecisionTreeNode falseNode;

	public BranchNode(DecisionTreeNode trueNode,
			DecisionTreeNode falseNode)
	{
		this.trueNode = trueNode;
		this.falseNode = falseNode;
	}

	@Override
	public DecisionTreeNode makeDecision() 
	{

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
