package eu.mighty.ld37.game.systems;

import java.util.HashMap;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import eu.mighty.ld37.game.Defaults;
import eu.mighty.ld37.game.ai.FlatMapProcessor;
import eu.mighty.ld37.game.components.AIComponent;
import eu.mighty.ld37.game.components.BulletComponent;
import eu.mighty.ld37.game.components.PlayerComponent;
import eu.mighty.ld37.game.components.TeamComponent;
import eu.mighty.ld37.game.components.TransformComponent;

public class AISystem extends IteratingSystem {

	HashMap<Integer, Entity> entityMap = new HashMap<Integer, Entity>();
	FlatMapProcessor mapProcessor;	
	private ComponentMapper<AIComponent> aiMapper;	
	private ComponentMapper<BulletComponent> bulletMapper;
	private ComponentMapper<PlayerComponent> playerMapper;
	private ComponentMapper<TeamComponent> teamMapper;
	private ComponentMapper<TransformComponent> transformMapper;

	public AISystem(){	
		this(Family.all(TransformComponent.class).one(BulletComponent.class, TeamComponent.class)
				.get());
	}

	public AISystem(Family family) {
		super(family);

		//Generate the basic AI system for a star
		this.mapProcessor = new FlatMapProcessor(Defaults.mapWidth,
				Defaults.mapHeight,
				Defaults.NUM_WIDTH_ZONES,
				Defaults.NUM_HEIGHT_ZONES,
				Defaults.NUM_WIDTH_REGIONS,
				Defaults.NUM_HEIGHT_REGIONS);

		//Obtain the mappers
		this.aiMapper = ComponentMapper.getFor(AIComponent.class);
		this.bulletMapper = ComponentMapper.getFor(BulletComponent.class);
		this.teamMapper = ComponentMapper.getFor(TeamComponent.class);
		this.transformMapper = ComponentMapper.getFor(TransformComponent.class);
		this.playerMapper = ComponentMapper.getFor(PlayerComponent.class);
	}

	@Override
	public void update(float deltaTime) {
		// System.out.println("Entering RenderingSystem's update");
		super.update(deltaTime);


		//processing stuff here (update parameters)
		for (Integer key : this.entityMap.keySet())
		{
			//Update zone 
			Entity entity = this.entityMap.get(key);

			//Update information
			AIComponent aiComp = this.aiMapper.get(entity);
			TransformComponent transComp = this.transformMapper.get(entity);



			int newRegion = this.mapProcessor.obtainCurrentRegion(transComp.pos.x, transComp.pos.y);
			int newZone = this.mapProcessor.obtainCurrentZone(transComp.pos.x, transComp.pos.y);

			//Check if the model has not followed the pathfinding algorithm or if the target region was reached
			if (aiComp.currentPath != null)
			{	
				if ((newRegion != aiComp.currentRegion) && (newRegion != aiComp.idExpectedNode))
				{
					//Put the path to null
					aiComp.currentPath=null;
				}
				else if (newRegion == aiComp.idExpectedNode)
				{
					aiComp.currentPath.getPredConn().remove(0);
					if (aiComp.currentPath.getPredConn().size()> 0)
					{
						aiComp.idExpectedNode = aiComp.currentPath.getPredConn().get(0).getSinkNodeId();
					}
					else
					{
						//Goal reached (current path == null)
						aiComp.currentPath=null;
					}
				}
			}


			//Update the region
			aiComp.currentRegion = newRegion;
			aiComp.currentZone = newZone;
		}

		//Perform these steps for artificial players only
		//TOOD 
		//1) if decision and path then continue
		//2) if decision and not path the path
		//3) if not decision then decision and after that path
		//4) perform the movement
		for (Integer key : this.entityMap.keySet())
		{
			//Check if this is ai controlled ship
			if (this.playerMapper.get(this.entityMap.get(key)) == null)
			{
				
			}
		}
		




		//Delete the map with the entities in the current slot
		this.entityMap.clear();
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {

		//Obtain the AI component
		AIComponent aiComp = this.aiMapper.get(entity);
		this.entityMap.put(aiComp.idShip, entity);

	}




}
