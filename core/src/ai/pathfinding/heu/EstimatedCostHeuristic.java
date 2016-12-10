package ai.pathfinding.heu;

public abstract class EstimatedCostHeuristic 
{
	public abstract double obtainEstimatedCost(int currentNode, int targetNode);
	
}
