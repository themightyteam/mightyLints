package ai.pathfinding.commons;

import java.util.HashMap;
import java.util.List;

public class PathfindingGraph 
{

	HashMap<Integer, List<Connection>> graphMap;
	
	
	public PathfindingGraph()
	{
		this.graphMap = new HashMap<Integer, List<Connection>>();
	}

	public PathfindingGraph( HashMap<Integer, List<Connection>> graphMap)
	{
		this.graphMap = graphMap;
	}
	
	/**
	 * 
	 * Obtains the connections having idNode as source
	 * 
	 * @param idNode : the id of the node
	 * @return
	 */
	public List<Connection> obtainConnections(int idNode)
	{
		
		if (this.graphMap.containsKey( idNode ))
		{
			return this.graphMap.get( idNode );
		}
		
		return null;
	}
	
}
