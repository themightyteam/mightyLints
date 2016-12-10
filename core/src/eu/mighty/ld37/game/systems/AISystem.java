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
import eu.mighty.ld37.game.components.TransformComponent;

public class AISystem extends IteratingSystem {

	HashMap<Integer, Entity> entityMap = new HashMap<Integer, Entity>();
	FlatMapProcessor mapProcessor;	
	private ComponentMapper<AIComponent> aiMapper;	
	private ComponentMapper<BulletComponent> bulletMapper;
	private ComponentMapper<PlayerComponent> playerMapper;

	public AISystem(){	
		this(Family.all(TransformComponent.class).one(BulletComponent.class, PlayerComponent.class)
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
	}

	@Override
	public void update(float deltaTime) {
		// System.out.println("Entering RenderingSystem's update");
		super.update(deltaTime);

		ArrayList<Integer> 
		
		//TODO: processing stuff here
		for (Integer key : this.entityMap.keySet())
		{
			
			
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
