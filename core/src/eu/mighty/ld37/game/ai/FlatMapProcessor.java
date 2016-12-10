package eu.mighty.ld37.game.ai;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import ai.movement.commons.PathNode;
import ai.world.AbstractMapProcessor;

public class FlatMapProcessor extends AbstractMapProcessor {

	int totalWidth;
	int totalHeight;
	int numWidthZones;
	int numHeightZones;
	int numWidthRegions;
	int numHeightRegions;
	

	public FlatMapProcessor(int totalWidth, 
			int totalHeight,
			int numWidthZones,
			int numHeightZones,
			int numWidthRegions,
			int numHeightRegions)
	{
		this.totalWidth = totalWidth;
		this.totalHeight = totalHeight;
		this.numWidthZones = numWidthZones;
		this.numHeightZones = numHeightZones;
		this.numWidthRegions = numWidthRegions;
		this.numHeightRegions = numHeightRegions;
	}

	@Override
	public List<PathNode> obtainZoning(String zoneLayer) {
		List<PathNode> zoneList = new ArrayList<PathNode>();
		
		float widthZone = new Float((1.0 * this.totalWidth)/this.numWidthZones);
		float heightZone = new Float((1.0 * this.totalHeight)/this.numHeightZones);
		
		for ( int i = 0; i < this.numWidthZones; i++)
		{
			for (int j = 0; j < this.numHeightZones; j++)
			{
				int zoneId = j + i * this.numWidthZones;
				
				Rectangle zoneRect = new Rectangle();
				zoneRect.setX(i*widthZone);
				zoneRect.setY(j*heightZone);
				zoneRect.setWidth(widthZone);
				zoneRect.setHeight(heightZone);
			
				Vector2 refPoint = zoneRect.getCenter(new Vector2());
				PathNode pathNode = new PathNode(zoneId, refPoint, zoneRect);
				zoneList.add(pathNode);	
			}
		}
		
		return zoneList;
	}

	@Override
	public List<PathNode> obtainPathFinding(String pathLayer) {
		List<PathNode> pathList = new ArrayList<PathNode>();
		
		float widthZone = new Float((1.0 * this.totalWidth)/this.numWidthZones);
		float heightZone = new Float((1.0 * this.totalHeight)/this.numHeightZones);
		
		for ( int i = 0; i < this.numWidthRegions; i++)
		{
			for (int j = 0; j < this.numHeightRegions; j++)
			{
				int zoneId = j + i * this.numWidthRegions;
				
				Rectangle zoneRect = new Rectangle();
				zoneRect.setX(i*widthZone);
				zoneRect.setY(j*heightZone);
				zoneRect.setWidth(widthZone);
				zoneRect.setHeight(heightZone);
			
				Vector2 refPoint = zoneRect.getCenter(new Vector2());
				PathNode pathNode = new PathNode(zoneId, refPoint, zoneRect);
				
				List<Integer> normalList = new ArrayList<Integer>();
				List<Integer> jumpList = new ArrayList<Integer>();
				List<Integer> landingList = new ArrayList<Integer>();
				
				if (j != 0)
				{
					landingList.add(j-1);
				}
				
				if (j != this.numHeightRegions -1)
				{
					jumpList.add(this.numHeightRegions + 1);
				}
				
				//Add to the normal list oto the left
				normalList.add((i-1) % this.numWidthRegions);
				normalList.add((i+1) % this.numWidthRegions);
				
				//Add extra list to path node
				pathNode.setConnectionList(normalList);
				pathNode.setConnectionJumpList(jumpList);
				pathNode.setConnectionLandingList(landingList);
				
				pathList.add(pathNode);
			}
		}		
		
		return null;
	}
	
	
	/**
	 * Returns an id zone, given the current x,y
	 * @param x
	 * @param y
	 * @return
	 */
	public int obtainCurrentZone(float x, float y)
	{
		float widthZone = new Float((1.0 * this.totalWidth)/this.numWidthZones);
		float heightZone = new Float((1.0 * this.totalHeight)/this.numHeightZones);
		
		int i = new Float(x/widthZone).intValue();
		int j = new Float(y/heightZone).intValue();
		
		int zoneId = j + i * this.numWidthZones;
		
		return zoneId;
	}

	
	/**
	 * Returns an id region, given the current x,y
	 * @param x
	 * @param y
	 * @return
	 */
	public int obtainCurrentRegion(float x, float y)
	{
		float widthZone = new Float((1.0 * this.totalWidth)/this.numWidthRegions);
		float heightZone = new Float((1.0 * this.totalHeight)/this.numHeightRegions);
		
		int i = new Float(x/widthZone).intValue();
		int j = new Float(y/heightZone).intValue();
		
		int zoneId = j + i * this.numWidthRegions;
		
		return zoneId;
	}
	
}
