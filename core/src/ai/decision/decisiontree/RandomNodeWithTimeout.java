package ai.decision.decisiontree;

public class RandomNodeWithTimeout extends RandomNode {

	//The timeout is measured in number of iterations of the decision tree
	int timeOut;

	//Signals in which iteration the state should be recalculated
	int timeOutIt;

	public RandomNodeWithTimeout(DecisionTreeNode trueNode,
			DecisionTreeNode falseNode, float testValue, int timeOut) {
		super(trueNode, falseNode, testValue);

		this.timeOut = timeOut;
	}

	@Override
	public DecisionTreeNode getBranch() {
		// TODO Auto-generated method stub

		if (this.lastDecision != null)
			if (this.lastDecisionIt + 1 == newIt)
				if (this.lastDecisionIt + 1 < this.timeOutIt)
				{
					this.lastDecisionIt = newIt;
					return this.lastDecision;
				}


		//No valid previous decision, calculate a new one
		Boolean isTrueNode = generator.nextBoolean();

		if (isTrueNode)
		{
			this.lastDecision = this.trueNode;	
		}
		else
			this.lastDecision = this.falseNode;

		this.lastDecisionIt = newIt;
		this.timeOutIt = this.lastDecisionIt + this.timeOut;

		return this.lastDecision;
	}

}
