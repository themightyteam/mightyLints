package eu.mighty.ld37.game.components;

import com.badlogic.ashley.core.Component;

public class AIComponent implements Component {
	//TODO: put AI information here
	//Unique id of the shp
	public int idShip;
	
	//Indication if the ship has a current active path
	public boolean hasActivePath;
	
	//Target node of the pathfinding algorithm (if any)
	public int idTargetNode;
	
	
	
	
	
}
