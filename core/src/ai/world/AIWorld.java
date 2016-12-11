package ai.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ai.movement.commons.PathNode;
import ai.pathfinding.AStar;
import ai.pathfinding.commons.Connection;
import ai.pathfinding.commons.Connection2D;
import ai.pathfinding.commons.PathfindingGraph;
import ai.pathfinding.commons.PredictedPath;
import ai.pathfinding.heu.EstimatedCostHeuristic;
import ai.pathfinding.heu.EuclideanDistanceHeuristic;

import com.badlogic.gdx.math.Vector2;

import eu.mighty.ld37.game.Defaults;

public class AIWorld 
{

	//Node representations record (for A-star)
	Map< Integer, NodeRepresentations> nodeList;

	//Graph map with the connections among nodes for A-star
	HashMap<Integer, List<Connection>> graphMap;

	AbstractMapProcessor tiledProcessor;

	List<PathNode> zoneList;

	List<PathNode> pathList;

	HashMap<Integer, List<PathNode>> zonePathMap;

	public AIWorld(AbstractMapProcessor tiledProcessor, 
			String zoneLayer, 
			String pathLayer)
	{

		//Obtain zones
		this.zoneList = tiledProcessor.obtainZoning(zoneLayer);

		//Obtain paths
		this.pathList = tiledProcessor.obtainPathFinding(pathLayer);

		//Obtain the path nodes in each zone
		this.obtainPathsInZone();

		//Build the pathfinding graph
		this.obtainPathfindingGraph();


	}

	public void aStarFullTest()
	{

		System.out.println("\n\nFull Test A star ");
		AStar aStar = new AStar();

		EstimatedCostHeuristic heuristic = 
				new EuclideanDistanceHeuristic( this.nodeList);

		for (NodeRepresentations nodeSource : this.nodeList.values())
		{
			for (NodeRepresentations nodeSink : this.nodeList.values())
			{	
				PredictedPath predPath = aStar.pathfindAStar( new PathfindingGraph( this.graphMap), 
						nodeSource.getIdNode(), 
						nodeSink.getIdNode(), 
						heuristic);

				if (nodeSource.getIdNode() == nodeSink.getIdNode())
				{
					//They should be null
					if (predPath.getPredConn().size() > 0)
					{
						System.out.println("Own Path "+ nodeSource.getIdNode());


					}
				}
				else
				{
					if (predPath.getPredConn().size() == 0)
					{
						System.out.println("No Route "+ nodeSource.getIdNode()+ 
								" "+ nodeSink.getIdNode());
					}
				}	
			}	
		}	
	}


	private void obtainPathsInZone()
	{
		this.zonePathMap = new HashMap<Integer, List<PathNode>>();


		for (PathNode pathNode : this.pathList)
		{

			for (PathNode zoneNode : this.zoneList)
			{

				if (zoneNode.getNodeShape().overlaps(pathNode.getNodeShape()))
				{
					if (!this.zonePathMap.containsKey(zoneNode.getIdNode()))
					{
						this.zonePathMap.put(zoneNode.getIdNode(), 
								new ArrayList<PathNode>());
					}

					//Add the new sample to the list
					this.zonePathMap.get(zoneNode.getIdNode())
					.add( pathNode );				
				}
			}
		}

	}

	public int obtainCurrentZone(Vector2 currentPos)
	{
		int currentZone = -1;

		for (PathNode zoneNode : this.zoneList)
		{
			if ( zoneNode.getNodeShape().contains(currentPos) )
			{
				currentZone = zoneNode.getIdNode();
				break;
			}
		}

		return currentZone;

	}

	public PathNode obtainCurrentNode(Vector2 currentPos)
	{

		//Initially search in the zone

		int currentZone = -1;

		for (PathNode zoneNode : this.zoneList)
		{
			if ( zoneNode.getNodeShape().contains(currentPos) )
			{
				currentZone = zoneNode.getIdNode();
				break;
			}
		}

		if (currentZone < 0) return null;

		for (PathNode pathNode : this.zonePathMap.get(currentZone))
		{
			if (pathNode.getNodeShape().contains(currentPos))
			{
				//Square found, return pathNode
				return pathNode;

			}
		}

		//No path node found
		return null;
	}

	private void obtainPathfindingGraph()
	{

		//Init variables
		int connCounter = 0;

		this.nodeList = 
				new HashMap< Integer, NodeRepresentations>();

		this.graphMap = new HashMap<Integer, List<Connection>>();

		for ( PathNode pathNode : pathList )
		{
			NodeRepresentations nodeRecord = 
					new NodeRepresentations(pathNode.getIdNode(),
							pathNode.getReferencePoint().x,
							pathNode.getReferencePoint().y);

			this.nodeList.put(nodeRecord.getIdNode(), nodeRecord);

			List<Connection> connList = new ArrayList<Connection>();

			for (Integer jumpNode : pathNode.getConnectionJumpList())
			{

				Connection newConn = new Connection2D(
						connCounter,
						pathNode.getIdNode(),
						jumpNode,
						0.0, //Initially put the cost to 0
						true,
						false,
						false);

				//Increment the counter of connections
				connCounter++;

				connList.add(newConn);
			}

			for (Integer landNode : pathNode.getConnectionLandingList())
			{

				Connection newConn = new Connection2D(
						connCounter,
						pathNode.getIdNode(),
						landNode,
						0.0, //Initially put the cost to 0
						false,
						true,
						false);

				//Increment the counter of connections
				connCounter++;

				connList.add(newConn);
			}

			for (Integer normalNode : pathNode.getConnectionList())
			{

			
				
				Connection newConn = new Connection2D(
						connCounter,
						pathNode.getIdNode(),
						normalNode,
						Double.MAX_VALUE, //Initially put the cost to maximum
						false,
						false,
						true);

				//Increment the counter of connections
				connCounter++;

				connList.add(newConn);
			}


			this.graphMap.put( pathNode.getIdNode(), connList);
		}


		//Finally update the cost to the right value
		for (Integer key : this.graphMap.keySet())
		{
			//System.out.println("Updating connections for "+ key);
			//Loop over all the connections
			for (int i = 0; i < this.graphMap.get(key).size(); i++)
			{	
				
				Connection conn = this.graphMap.get(key).get(i);
				
				conn.setCost(this.getCost( this.nodeList.get(conn.getSourceNodeId()), 
						this.nodeList.get(conn.getSinkNodeId())));
			}
		}
	}	
	
	private double getCost(NodeRepresentations sourceNode, NodeRepresentations sinkNode)
	{	
		return Math.min(Math.abs(sourceNode.getX() - sinkNode.getX()) + Math.abs(sourceNode.getY()- sinkNode.getY()),
				Math.abs(sourceNode.getX() + Defaults.mapWidth- sinkNode.getX()) + 
				Math.abs(sourceNode.getY()- sinkNode.getY()));
	}


	//Getters and setters
	public Map<Integer, NodeRepresentations> getNodeList() {
		return nodeList;
	}

	public void setNodeList(Map<Integer, NodeRepresentations> nodeList) {
		this.nodeList = nodeList;
	}

	public HashMap<Integer, List<Connection>> getGraphMap() {
		return graphMap;
	}

	public void setGraphMap(HashMap<Integer, List<Connection>> graphMap) {
		this.graphMap = graphMap;
	}

	public HashMap<Integer, List<PathNode>> getZonePathMap() {
		return zonePathMap;
	}

	public void setZonePathMap(HashMap<Integer, List<PathNode>> zonePathMap) {
		this.zonePathMap = zonePathMap;
	}

	public List<PathNode> getPathList() {
		return pathList;
	}

	public void setPathList(List<PathNode> pathList) {
		this.pathList = pathList;
	}





}
