package ai.decision.decisiontree;

import java.util.Random;

/**
 * 
 * Example of a random decision.
 * 
 * It stores the state selected and returns always the same decision
 * if the knowledge state did not change (e.g. the branch is traversed
 * in consecutive frames)
 * 
 * @author hector
 *
 */
public class RandomNode extends BranchNode {

	//Variable to store last decision
	DecisionTreeNode lastDecision;
	
	Random generator;
	
	//Previous iteration of the decision tree algorithm 
	//(an internal counter - it should be provided in the internal knowledge of the player)
	
	int lastDecisionIt = Integer.MIN_VALUE;
	
	//FIXME: provide this value in the knowledge world class (input to the decision tree)
	int newIt;

	
	public RandomNode(DecisionTreeNode trueNode, DecisionTreeNode falseNode, float testValue) {
		super(trueNode, falseNode);
		
		//Init the random number generator
		this.generator = new Random();
		
	}

	@Override
	public DecisionTreeNode getBranch() {
		// TODO Auto-generated method stub
		
		if (this.lastDecision != null)
			if (this.lastDecisionIt + 1 == newIt)
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
		
		return this.lastDecision;
	}

	//Getters and setters
	public int getNewIt() {
		return newIt;
	}

	public void setNewIt(int newIt) {
		this.newIt = newIt;
	}
	


}
