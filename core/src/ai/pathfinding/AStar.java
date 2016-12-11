package ai.pathfinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ai.pathfinding.commons.Connection;
import ai.pathfinding.commons.NodeRecord;
import ai.pathfinding.commons.PathfindingGraph;
import ai.pathfinding.commons.PredictedPath;
import ai.pathfinding.heu.EstimatedCostHeuristic;

/**
 * 
 * A completely subobtimal implementation of A-star algorithm
 * 
 * @author hector
 *
 */
public class AStar 
{

	/**
	 * 
	 * 
	 * Obtains a path among two nodes when possible, null otherwise 
	 * 
	 * @param graph : the graph in which A-star would be applied
	 * @param idStart : id of the node of the starting position
	 * @param idEnd : id of the target node
	 * @param heuristic : heuristic for the estimation of the cost from any node to target 
	 * @return the path to the target
	 */
	public PredictedPath pathfindAStar( PathfindingGraph graph, 
			int idStart, 
			int idEnd, 
			EstimatedCostHeuristic heuristic)
	{

		NodeRecord startNode = new NodeRecord(
				idStart,
				null, //no connection between the same node
				0, //Cost so far is 0
				0 + heuristic.obtainEstimatedCost(idStart, idEnd) //Cost so far is 0
				);


		//Creating auxiliar lists
		HashMap<Integer,NodeRecord> openNodeList = new HashMap<Integer, NodeRecord>();
		HashMap<Integer, NodeRecord> closeNodeList = new HashMap<Integer, NodeRecord>();

		//Adding the start node initially
		openNodeList.put(startNode.getIdNode(), startNode);

		NodeRecord currentNode = startNode;
		
		//Loop indifinetly till no open nodes exist
		while(true)
		{
			if (openNodeList.isEmpty()) break;
			
			int idSelect = this.obtainSmallestElement(openNodeList);
			
			//Pick the node from the list
			
			currentNode = openNodeList.get(idSelect);
			
			//If the end is reached terminate
			if (currentNode.getIdNode() == idEnd ) break;
			
			//Get the connections of the node
			List<Connection> connList = graph.obtainConnections(currentNode.getIdNode());
			
			//Loop through connections
			for (Connection conn : connList)
			{
				int nextStepIdNode = conn.getSinkNodeId();
				
				//Get the real cost to node
				double endNodeCost = currentNode.getCostSoFar() +
						conn.getCost();
				
				double endNodeFormerEstimationCost;
				
				NodeRecord endNodeRecord;
				
				if (closeNodeList.containsKey(nextStepIdNode))
				{
					//Here we find the record in the closed list
					//corresponding to the endNode.
					endNodeRecord = closeNodeList.get(nextStepIdNode);
					
					//If the cost of the new connection is bigger than 
					// the former calculation skip
					if (endNodeRecord.getCostSoFar() <= endNodeCost)
						continue;
					
					// Otherwise remove it from the closed list
					closeNodeList.remove(nextStepIdNode);
					
					
					// We can use the node’s old cost values
					//to calculate its heuristic without calling
					//the possibly expensive heuristic function
					endNodeFormerEstimationCost = endNodeRecord.getEstimatedTotalCost() - 
							endNodeRecord.getCostSoFar();
					
				}
				//Currently it is in the open node list
				else if (openNodeList.containsKey(nextStepIdNode))
				{
					//Here we find the record in the open list
					//corresponding to the endNode.
					endNodeRecord = openNodeList.get(nextStepIdNode);
					
					//If the cost of the new connection is bigger than 
					// the former calculation skip
					if (endNodeRecord.getCostSoFar() <= endNodeCost)
						continue;
					
					// We can use the node’s old cost values
					//to calculate its heuristic without calling
					//the possibly expensive heuristic function
					endNodeFormerEstimationCost = endNodeRecord.getEstimatedTotalCost() - 
							endNodeRecord.getCostSoFar();
					
				}
				//Otherwise we know we’ve got an unvisited
				//node, so make a record for it
				else
				{
				
					endNodeRecord = new NodeRecord(nextStepIdNode);	
					endNodeFormerEstimationCost = heuristic.obtainEstimatedCost(nextStepIdNode, idEnd);
						
				}
				
				
				//We are here if we need to update the node
				//Update the cost, estimate and connection
				endNodeRecord.setCostSoFar(endNodeCost);
				endNodeRecord.setConn(conn);
				endNodeRecord.setEstimatedTotalCost(endNodeCost + endNodeFormerEstimationCost);
				
				//Add it to the open list
				openNodeList.put(endNodeRecord.getIdNode(), endNodeRecord);
				
				
				
				
			}
			
			// We have finished looking at the connections for
			// the current node, so add it to the closed list
			// and remove it from the open list
			
			openNodeList.remove(currentNode.getIdNode());
			closeNodeList.put(currentNode.getIdNode(), currentNode);
				

		}
		
		//Current node != target (no solution found)
		if (currentNode.getIdNode() != idEnd)
		{
			return null;
		}


		List<Connection> predConn = new ArrayList<Connection>();
		
		//Obtain the path
		
		while (currentNode.getIdNode() != idStart)
		{
			predConn.add(0, currentNode.getConn());
			//If the node is in the connection it must be in the closed list 
			// (all but the last one: the target node)
			currentNode = closeNodeList.get( currentNode.getConn().getSourceNodeId());
			
		}
		
		PredictedPath predPath = new PredictedPath( predConn );
		
		return predPath;
	}

	private int obtainSmallestElement(Map<Integer, NodeRecord> openNodeList)
	{
		double minimumCost = Double.MAX_VALUE;
		int idSelect = -1;
		
		for (Integer key  : openNodeList.keySet())
		{
			if (openNodeList.get(key).getEstimatedTotalCost() < minimumCost)
			{
				minimumCost = openNodeList.get(key).getEstimatedTotalCost();
				idSelect = key;
			}
		}
		return idSelect;

	}


}
