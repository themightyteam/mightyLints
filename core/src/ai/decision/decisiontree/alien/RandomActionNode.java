package ai.decision.decisiontree.alien;

import java.util.Random;

import ai.decision.AlienFSMDecisor;
import ai.decision.decisiontree.DecisionTreeNode;

public abstract class RandomActionNode extends ActionFSMNode {


	
	Random generator;
	
	
	//Previous iteration of the decision tree algorithm 
	//(an internal counter - it should be provided in the internal knowledge of the player)
	
	int lastDecisionIt = Integer.MIN_VALUE;
	
	public RandomActionNode(AlienFSMDecisor alienFSM) {
		super(alienFSM);

		//Init the random number generator
		this.generator = new Random();
		
	}
	
	public abstract int obtainNextNode();

	@Override
	public DecisionTreeNode makeDecision() {

		if (this.nextNode != -1)
		{
			if (this.lastDecisionIt + 1 == this.alienFSM.getDecisionIt())
			{
				this.lastDecisionIt = this.alienFSM.getDecisionIt();
				
			}
		}
		else
		{
			this.nextNode = this.obtainNextNode();
			
			this.lastDecisionIt = this.alienFSM.getDecisionIt();
		}
				
		
		return this;
			
	}

}
