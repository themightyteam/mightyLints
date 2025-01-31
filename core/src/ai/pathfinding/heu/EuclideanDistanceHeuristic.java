package ai.pathfinding.heu;

import java.util.HashMap;
import java.util.Map;

import eu.mighty.ld37.game.Defaults;
import ai.world.NodeRepresentations;

public class EuclideanDistanceHeuristic extends EstimatedCostHeuristic
{
	Map<Integer, NodeRepresentations> nodeList;
	

	public EuclideanDistanceHeuristic()
	{
		this.nodeList = new HashMap<Integer, NodeRepresentations>();
	}
	
	public EuclideanDistanceHeuristic(Map<Integer, NodeRepresentations> nodeList)
	{
		this.nodeList = nodeList;
	}

	@Override
	public double obtainEstimatedCost(int currentNode, int targetNode) {
		// TODO Auto-generated method stub
		
		double estimatedCost = Double.MAX_VALUE;
		
		try
		{
		
			NodeRepresentations current = this.nodeList.get(currentNode);
			NodeRepresentations target = this.nodeList.get(targetNode);
		
	
		
		estimatedCost = Math.min(0.5 * (Math.pow(current.getX() - target.getX(), 2) +
				Math.pow(current.getY() - target.getY(), 2)), 
				0.5 * (Math.pow(current.getX() - target.getX() + Defaults.mapWidth,2) +
				Math.pow(current.getY() - target.getY(), 2)));
		
		} 
		catch(Exception e)
		{
			System.out.println("XXXX Current node "+currentNode);
			System.out.println("XXXX Target node "+targetNode);
		}
		
		return estimatedCost;
	}
	
	

}
