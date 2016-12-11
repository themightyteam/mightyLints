package ai.decision.decisiontree;


/**
 * Example class of a decision tree node 
 * 
 * Min and max value act as conditions of testValue (the value to be tested)
 * 
 * @author hector
 *
 */
public class FloatNode extends BranchNode {

	float minValue;
	float maxValue;
	float testValue;

	public FloatNode(
			DecisionTreeNode trueNode,
			DecisionTreeNode falseNode,
			float minValue, 
			float maxValue)
	{
		
		super(trueNode, falseNode);
		
		this.minValue = minValue;
		this.maxValue = maxValue;

	}


	@Override
	public DecisionTreeNode getBranch() 
	{
		
		if ((this.testValue >= this.minValue) &&
				(this.testValue <= this.maxValue))
		{
			return this.trueNode;
		}
		else return this.falseNode;
		
	}


	//Getters and setters
	public float getMinValue() {
		return minValue;
	}


	public void setMinValue(float minValue) {
		this.minValue = minValue;
	}


	public float getMaxValue() {
		return maxValue;
	}


	public void setMaxValue(float maxValue) {
		this.maxValue = maxValue;
	}


	public float getTestValue() {
		return testValue;
	}


	public void setTestValue(float testValue) {
		this.testValue = testValue;
	}
	
	
	



}
