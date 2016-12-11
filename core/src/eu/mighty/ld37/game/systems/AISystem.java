package eu.mighty.ld37.game.systems;

import java.util.ArrayList;
import java.util.HashMap;

import ai.decision.decisiontree.DecisionTreeNode;
import ai.pathfinding.AStar;
import ai.pathfinding.commons.PathfindingGraph;
import ai.pathfinding.heu.EstimatedCostHeuristic;
import ai.pathfinding.heu.EuclideanDistanceHeuristic;
import ai.world.AIWorld;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import eu.mighty.ld37.game.Defaults;
import eu.mighty.ld37.game.ai.AIIteration;
import eu.mighty.ld37.game.ai.FlatMapProcessor;
import eu.mighty.ld37.game.ai.decision.action.ActionFSMNode;
import eu.mighty.ld37.game.ai.decision.tree.TreeBludgeoner;
import eu.mighty.ld37.game.ai.decision.tree.TreeGoalkeeper;
import eu.mighty.ld37.game.ai.decision.tree.TreeScorer;
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

	private int currentAIId = 1;

	private int currentIt;
	
	AIIteration nextIteration;

	public AISystem(){	
		this(Family.one(AIShipComponent.class, AIBulletComponent.class).one(BulletComponent.class, 
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
		this.aiWorld = new AIWorld(this.mapProcessor, "zoning", "pathfinding");
		//this.aiWorld.aStarFullTest();

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
		this.nextIteration = new AIIteration();
		
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
			PlayerComponent playerComp = this.playerMapper.get(entity);
			
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
		this.nextIteration.update(
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
		
		EstimatedCostHeuristic heuristic = 
				new EuclideanDistanceHeuristic( this.aiWorld.getNodeList());
	
		
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
					if ( aiShip.currentPath != null)
					{
						continue;
					}
					else
					{
						//Take a new decision
					
						DecisionTreeNode decision = aiShip.decisionTree.getRootNode().makeDecision();
						
						if (decision instanceof ActionFSMNode)
						{
							ActionFSMNode targetNode = (ActionFSMNode) decision;
							
							aiShip.idTargetNode = targetNode.getNextNode();
						}
						
					
						//If path is null then recalculate the path
						if (aiShip.idTargetNode != aiShip.currentRegion && aiShip.idTargetNode != -1)
						{
							
							System.out.println("NEW TARGET NODE "+ aiShip.idTargetNode);
							System.out.println("NEW CURRENT REGION "+aiShip.currentRegion);
							//System.out.println("CURRENT REGION "+ aiShip.currentRegion);
								//System.out.println("TARGET NODE "+ aiShip.idTargetNode);
									
							aiShip.currentPath = aStar.pathfindAStar( new PathfindingGraph(this.aiWorld.getGraphMap()), 
									aiShip.currentRegion, 
									aiShip.idTargetNode, 
									heuristic);
							
							if (aiShip.currentPath.getPredConn().size() > 0)
								aiShip.idExpectedNode = aiShip.currentPath.getPredConn().get(0).getSinkNodeId();
						}
						
						//A new decision must be performed
						//TODO
					}
				}		
			}
		}
		//Delete the map with the entities in the current slot
		this.entityMap.clear();
		
		this.currentIt = this.currentIt + 1;
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {

		//Obtain the AI component
		AIShipComponent aiShipComp = this.aiShipMapper.get(entity);
		AIBulletComponent aiBulletComp = this.aiBulletMapper.get(entity);

		if (aiShipComp != null)
		{
			//Check if it is the first time seeing this object
			if (aiShipComp.idAIObject == 0)
			{
				//Initialize stuff here
				aiShipComp.idAIObject = this.currentAIId;
				aiShipComp.idTargetNode = -1;
				//Create the new decision system
				
				GoalComponent goalShipComp = this.goalMapper.get(entity);
				CanScoreComponent scoreShipComp = this.scoreMapper.get(entity);
				
				if (goalShipComp != null)
				{
					aiShipComp.decisionTree = new TreeGoalkeeper(aiShipComp.idAIObject,
							this.nextIteration,
							Defaults.DECISION_TIMEOUT);
				} else if (scoreShipComp != null)
				{
					
					//FIXME: change for a more convenient tree
					aiShipComp.decisionTree = new TreeScorer(aiShipComp.idAIObject,
							this.nextIteration,
							Defaults.DECISION_TIMEOUT);
				}
				else
				{
					
					//FIXME: change for a more convenient tree
					aiShipComp.decisionTree = new TreeBludgeoner(aiShipComp.idAIObject,
							this.nextIteration,
							Defaults.DECISION_TIMEOUT);
				}		
				
				this.currentAIId += 1;
				//System.out.println("XXXXXXXx CURENT AI "+this.currentAIId);
			}
			else
			{
				//System.out.println("I KNOW THIS GUY  "+this.currentAIId);
			}
			this.entityMap.put(aiShipComp.idAIObject, entity);

		}
		if (aiBulletComp != null)
		{
			if (aiBulletComp.idAIObject == 0)
			{
				//Initialize stuff here
				aiBulletComp.idAIObject = this.currentAIId;
				this.currentAIId += 1;
				

				//System.out.println("XXXXXXXx CURENT AI "+this.currentAIId);
			}
			else
			{
				//System.out.println("I KNOW THIS GUY  "+this.currentAIId);
			}
			
			this.entityMap.put(aiBulletComp.idAIObject, entity);	
		}
	}
}
