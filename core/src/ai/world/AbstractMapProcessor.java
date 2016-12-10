package ai.world;

import java.util.List;

import ai.movement.commons.PathNode;

public abstract class AbstractMapProcessor {

	float tileWidth;
	float tileHeight;
	
	/**
	 * 
	 * 
	 * 
	 * @param zoneLayer
	 * @return
	 */
	public abstract List<PathNode> obtainZoning(String zoneLayer);

	/**
	 * 
	 * 
	 * 
	 * @param pathLayer
	 * @return
	 */
	public abstract List<PathNode> obtainPathFinding(String pathLayer);
	
	
}
