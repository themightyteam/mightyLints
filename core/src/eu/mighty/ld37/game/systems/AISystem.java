package eu.mighty.ld37.game.systems;

import java.util.ArrayList;
import java.util.HashMap;

import ai.pathfinding.AStar;
import ai.pathfinding.commons.PathfindingGraph;
import ai.pathfinding.heu.EuclideanDistanceHeuristic;
import ai.world.AIWorld;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import eu.mighty.ld37.game.Defaults;
import eu.mighty.ld37.game.ai.AIIteration;
import eu.mighty.ld37.game.ai.FlatMapProcessor;
import eu.mighty.ld37.game.components.AIBulletComponent;
import eu.mighty.ld37.game.components.AIRelevantComponent;
import eu.mighty.ld37.game.components.AIShipComponent;
import eu.mighty.ld37.game.components.BulletComponent;
import eu.mighty.ld37.game.components.CanScoreComponent;
import eu.mighty.ld37.game.components.GoalComponent;
import eu.mighty.ld37.game.components.PlayerComponent;
import eu.mighty.ld37.game.components.TeamComponent;
import eu.mighty.ld37.game.components.TransformComponent;

public class AISystem extends IteratingSystem {

	HashMap<Integer, Entity> entityMap = new HashMap<Integer, Entity>();
	FlatMapProcessor mapProcessor;	
	private AIWorld aiWorld;
	private ComponentMapper<AIRelevantComponent> aiMapper;
	private ComponentMapper<AIBulletComponent> aiBulletMapper;
	private ComponentMapper<AIShipComponent> aiShipMapper;
	private ComponentMapper<BulletComponent> bulletMapper;
	private ComponentMapper<PlayerComponent> playerMapper;
	private ComponentMapper<TeamComponent> teamMapper;
	private ComponentMapper<TransformComponent> transformMapper;
	private ComponentMapper<GoalComponent> goalMapper;
	private ComponentMapper<CanScoreComponent> scoreMapper;

	private int currentIt;

	public AISystem(){	
		this(Family.all(AIRelevantComponent.class).one(BulletComponent.class, 
				TeamComponent.class).get());
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
		
		// Init the AI
		this.aiWorld = new AIWorld(mapProcessor, "zoning", "pathfinding");

		//Obtain the mappers
		this.aiMapper = ComponentMapper.getFor(AIRelevantComponent.class);
		this.aiBulletMapper = ComponentMapper.getFor(AIBulletComponent.class);
		this.aiShipMapper = ComponentMapper.getFor(AIShipComponent.class);
		this.bulletMapper = ComponentMapper.getFor(BulletComponent.class);
		this.teamMapper = ComponentMapper.getFor(TeamComponent.class);
		this.transformMapper = ComponentMapper.getFor(TransformComponent.class);
		this.playerMapper = ComponentMapper.getFor(PlayerComponent.class);
		this.goalMapper = ComponentMapper.getFor(GoalComponent.class);
		this.scoreMapper = ComponentMapper.getFor(CanScoreComponent.class);
		this.currentIt = 0;
	}

	public void updateShipInformation(AIShipComponent aiShip,
			TransformComponent transComp)
	{
		int newRegion = this.mapProcessor.obtainCurrentRegion(transComp.pos.x, 
				transComp.pos.y);
		int newZone = this.mapProcessor.obtainCurrentZone(transComp.pos.x, 
				transComp.pos.y);

		//Check if the model has not followed the pathfinding algorithm or if the target region was reached
		if (aiShip.currentPath != null)
		{	
			if ((newRegion != aiShip.currentRegion) && (newRegion != aiShip.idExpectedNode))
			{
				//Put the path to null
				aiShip.currentPath=null;
			}
			else if (newRegion == aiShip.idExpectedNode)
			{
				aiShip.currentPath.getPredConn().remove(0);
				if (aiShip.currentPath.getPredConn().size()> 0)
				{
					aiShip.idExpectedNode = aiShip.currentPath.getPredConn().get(0).getSinkNodeId();
				}
				else
				{
					//Goal reached (current path == null)
					aiShip.currentPath=null;
				}
			}
		}

		//Update the region
		aiShip.currentRegion = newRegion;
		aiShip.currentZone = newZone;
		
		this.currentIt = this.currentIt + 1;
	}

	public void updateBulletInformation(AIBulletComponent aiBullet,
			TransformComponent transComp)
	{
		int newRegion = this.mapProcessor.obtainCurrentRegion(transComp.pos.x, 
				transComp.pos.y);
		int newZone = this.mapProcessor.obtainCurrentZone(transComp.pos.x, 
				transComp.pos.y);

		aiBullet.currentRegion = newRegion;
		aiBullet.currentZone = newZone;

	}

	@Override
	public void update(float deltaTime) {
		// System.out.println("Entering RenderingSystem's update");
		super.update(deltaTime);

		ArrayList<Integer> friendTeamList = new ArrayList<Integer>();
		ArrayList<Integer> enemyTeamList = new ArrayList<Integer>();
		ArrayList<Integer> goalFriendTeamList = new ArrayList<Integer>();
		ArrayList<Integer> goalEnemyTeamList = new ArrayList<Integer>();
		ArrayList<Integer> scoreFriendTeamList = new ArrayList<Integer>();
		ArrayList<Integer> scoreEnemyTeamList = new ArrayList<Integer>();
		ArrayList<Integer> bulletList = new ArrayList<Integer>();
		
		//processing stuff here (update parameters of ships)
		for (Integer key : this.entityMap.keySet())
		{
			//Update zone 
			Entity entity = this.entityMap.get(key);

			//Update information
			AIShipComponent aiShip = this.aiShipMapper.get(entity);
			TransformComponent transComp = this.transformMapper.get(entity);
			AIBulletComponent aiBullet = this.aiBulletMapper.get(entity);
			TeamComponent teamComp = this.teamMapper.get(entity);
			CanScoreComponent canScoreComp = this.scoreMapper.get(entity);
			GoalComponent goalComp = this.goalMapper.get(entity);
			
			if (aiShip != null)
			{
				this.updateShipInformation(aiShip, transComp);
				
				if (teamComp.team == Defaults.FRIEND_TEAM)
				{
					friendTeamList.add(aiShip.idAIObject);
					if (goalComp != null)
					{
						//It is goal 
						goalFriendTeamList.add(aiShip.idAIObject);
					}
					
					if (canScoreComp != null)
					{
						scoreFriendTeamList.add(aiShip.idAIObject);
					}
					
				}
				else
				{
					enemyTeamList.add(aiShip.idAIObject);
					if (goalComp != null)
					{
						//It is goal 
						goalEnemyTeamList.add(aiShip.idAIObject);
					}
					
					if (canScoreComp != null)
					{
						scoreEnemyTeamList.add(aiShip.idAIObject);
					}
				}
				
				
				
			}

			if (aiBullet != null)
			{
				this.updateBulletInformation(aiBullet, transComp);
				
				//Add the bullet to the list
				bulletList.add(aiBullet.idAIObject);
			}	
		}
		
		//Prepare the object for the decision system
		AIIteration nextIteration = new AIIteration(
				this.currentIt,
				this.mapProcessor,
				this.entityMap,
				friendTeamList,enemyTeamList,bulletList,
				goalFriendTeamList, goalEnemyTeamList,
				scoreFriendTeamList, scoreEnemyTeamList);

		//Perform these steps for artificial players only
		//TOOD 
		//1) if decision and path then continue
		//2) if decision and not path the path
		//3) if not decision then decision and after that path
		//4) perform the movement
		
		AStar aStar = new AStar();
		for (Integer key : this.entityMap.keySet())
		{
			//Check if this is ai controlled ship
			if (this.playerMapper.get(this.entityMap.get(key)) == null)
			{
				//TODO: If true do magic here!
				
				//Pick the ai object 
				//Update information
				AIShipComponent aiShip = this.aiShipMapper.get(this.entityMap.get(key));
				
				if (aiShip != null)
				{
					if ( aiShip.hasActiveDecision  && aiShip.currentPath != null)
					{
						continue;
					}
					else
					{	
						//A new decision must be performed
						//TODO
					}
				}
				
				//If path is null then recalculate the path
				if (aiShip.idTargetNode != aiShip.currentRegion)
				{
					aiShip.currentPath = aStar.pathfindAStar( new PathfindingGraph(aiWorld.getGraphMap()), 
							aiShip.currentRegion, 
							aiShip.idTargetNode, 
							new EuclideanDistanceHeuristic());
				}
			}
		}
		//Delete the map with the entities in the current slot
		this.entityMap.clear();
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {

		//Obtain the AI component
		AIRelevantComponent aiComp = this.aiMapper.get(entity);
		this.entityMap.put(aiComp.idAIObject, entity);

	}




}
