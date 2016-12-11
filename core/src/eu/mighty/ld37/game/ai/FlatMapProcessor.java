package eu.mighty.ld37.game.ai;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import eu.mighty.ld37.game.Defaults;
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
				int zoneId = j + i * this.numHeightZones;

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
				int zoneId = j + i * this.numHeightRegions;

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
					int nextId = (j-1) + i * this.numHeightRegions;

					landingList.add(nextId);
				}

				if (j != this.numHeightRegions -1)
				{
					int nextId = (j+1) + i * this.numHeightRegions;

					jumpList.add(nextId);
				}

				//Add to the normal list to the left
				int nextId = -1;
				if (i == 0)
				{
					nextId = j + (this.numWidthRegions -1) * this.numHeightRegions;
				}
				else
				{
					nextId = j + ((i-1) % this.numWidthRegions) * this.numHeightRegions;

				}

				normalList.add(nextId );
				nextId = j + ((i+1) % this.numWidthRegions) * this.numHeightRegions;				
				normalList.add( nextId );

				//Add extra list to path node
				pathNode.setConnectionList(normalList);
				pathNode.setConnectionJumpList(jumpList);
				pathNode.setConnectionLandingList(landingList);

				//System.out.println("PATHNODE CONNECTIONS" + normalList.size());

				pathList.add(pathNode);

				System.out.println("Including node "+zoneId);
			}
		}		

		return pathList;
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


		int zoneId = j + i * this.numHeightZones;

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

		int zoneId = j + i * this.numHeightRegions;

		if (zoneId >= Defaults.MAX_NODES)
		{
			zoneId = Defaults.MAX_NODES -1;
		}

		return zoneId;
	}

}
