package ai.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ai.pathfinding.AStar;
import ai.pathfinding.commons.Connection;
import ai.pathfinding.commons.PathfindingGraph;
import ai.pathfinding.commons.PredictedPath;
import ai.pathfinding.heu.EstimatedCostHeuristic;
import ai.pathfinding.heu.EuclideanDistanceHeuristic;
import ai.world.NodeRepresentations;

public class PathfindingTest {

	
	public static void main(String [] args)
	{
		
		//Create nodes
		NodeRepresentations node0 = new NodeRepresentations( 0, 0, 0);
		NodeRepresentations node1 = new NodeRepresentations( 1, 20, 20);
		NodeRepresentations node2 = new NodeRepresentations( 2, 40, 40);
		NodeRepresentations node3 = new NodeRepresentations( 3, 60, 50);	
		
		Map< Integer, NodeRepresentations> nodeList = 
				new HashMap< Integer, NodeRepresentations>();
		
		nodeList.put( node0.getIdNode(), node0);
		nodeList.put( node1.getIdNode(), node1);
		nodeList.put( node2.getIdNode(), node2);
		nodeList.put( node3.getIdNode(), node3);
		
		//Create heuristic
		EstimatedCostHeuristic heuristic = new EuclideanDistanceHeuristic( nodeList);
		
		
		
		//Create connections
		
		//Node 0
		Connection conn01 = new Connection( 0, 
				node0.getIdNode(),
				node1.getIdNode(), 
				40);

		Connection conn02 = new Connection( 2, 
				node0.getIdNode(),
				node2.getIdNode(), 
				70);
		
		List<Connection> connNode0List = new ArrayList<Connection>();
		connNode0List.add(conn01);
		connNode0List.add(conn02);
		
		//Node1
		Connection conn10 = new Connection( 10, 
				node1.getIdNode(),
				node0.getIdNode(), 
				70);

		Connection conn12 = new Connection( 12, 
				node1.getIdNode(),
				node2.getIdNode(), 
				70);
		
		List<Connection> connNode1List = 
				new ArrayList<Connection>();
		
		connNode1List.add(conn10);
		connNode1List.add(conn12);
		
		//Node2
		Connection conn23 = new Connection( 23, 
				node2.getIdNode(),
				node3.getIdNode(), 
				50);

		Connection conn21 = new Connection( 21, 
				node2.getIdNode(),
				node1.getIdNode(), 
				20);
		
		List<Connection> connNode2List = 
				new ArrayList<Connection>();
		
		connNode2List.add(conn23);
		connNode2List.add(conn21);
		
		
		
		//Create the pathfinding graph
		HashMap<Integer, List<Connection>> graphMap = 
				new HashMap<Integer, List<Connection>>();
		
		graphMap.put( node0.getIdNode(), connNode0List);
		graphMap.put( node1.getIdNode(), connNode1List);
		graphMap.put( node2.getIdNode(), connNode2List);
		
		PathfindingGraph graph = new PathfindingGraph( graphMap );
		
		//Create a_star instance
		AStar aStar = new AStar();
		
		PredictedPath predPath = aStar.pathfindAStar(graph, 
				node0.getIdNode(), 
				node3.getIdNode(),
				heuristic);
		
		if (predPath == null)
		{
			System.out.println("No route to target found");
		}
		else
		{
			for (Connection conn : predPath.getPredConn())
			{
				System.out.println("Connection SRC " + 
						Integer.toString(conn.getSourceNodeId()) +
						" DST " +
						Integer.toString(conn.getSinkNodeId())
						);
			}
		}
		
	}
	
}
