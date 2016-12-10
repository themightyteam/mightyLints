package ai.decision.decisiontree.alien;

import java.util.Random;

import ai.decision.AlienFSMDecisor;
import ai.decision.decisiontree.DecisionTreeNode;

public class RandomBranchFSMNode extends BranchFSMNode {

	//Variable to store last decision
	DecisionTreeNode lastDecision;
	
	Random generator;
	
	//Previous iteration of the decision tree algorithm 
	//(an internal counter - it should be provided in the internal knowledge of the player)
	
	int lastDecisionIt = Integer.MIN_VALUE;
	
	//Branch threshold
	double threshold;
	
	
	public RandomBranchFSMNode(AlienFSMDecisor alienFSM,
			DecisionTreeNode trueNode, DecisionTreeNode falseNode, double threshold) {
		super(alienFSM, trueNode, falseNode);
		
		this.threshold = threshold;
		
		//Init the random number generator
		this.generator = new Random();
		
		
	}

	@Override
	public DecisionTreeNode getBranch() {

		if (this.lastDecision != null)
			if (this.lastDecisionIt + 1 == this.alienFSM.getDecisionIt())
			{
				this.lastDecisionIt = this.alienFSM.getDecisionIt();
				return this.lastDecision;
			}
		
		//No valid previous decision, calculate a new one
		double nextRandom = generator.nextDouble();
		
		if (nextRandom < this.threshold)
		{
			this.lastDecision = this.trueNode;	
		}
		else
			this.lastDecision = this.falseNode;
		
		this.lastDecisionIt = this.alienFSM.getDecisionIt();
		
		return this.lastDecision;
		
	}

}
