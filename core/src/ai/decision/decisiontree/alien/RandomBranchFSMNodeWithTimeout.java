package ai.decision.decisiontree.alien;

import ai.decision.AlienFSMDecisor;
import ai.decision.decisiontree.DecisionTreeNode;

public class RandomBranchFSMNodeWithTimeout extends RandomBranchFSMNode 
{

	//The timeout is measured in number of iterations of the decision tree
	int timeOut;

	//Signals in which iteration the state should be recalculated
	int timeOutIt;

	public RandomBranchFSMNodeWithTimeout(AlienFSMDecisor alienFSM,
			DecisionTreeNode trueNode, DecisionTreeNode falseNode,
			double threshold, int timeOut) {
		super(alienFSM, trueNode, falseNode, threshold);

		this.timeOut = timeOut;

	}


	@Override
	public DecisionTreeNode getBranch() {


		if (this.lastDecision != null)
			if (this.lastDecisionIt + 1 == this.alienFSM.getDecisionIt())
				if (this.lastDecisionIt + 1 < this.timeOutIt)
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
		this.timeOutIt = this.lastDecisionIt + this.timeOut;

		return this.lastDecision;

	}
}
