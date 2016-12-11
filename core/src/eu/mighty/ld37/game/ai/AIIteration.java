package eu.mighty.ld37.game.ai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import eu.mighty.ld37.game.Defaults;
import eu.mighty.ld37.game.components.AIBulletComponent;
import eu.mighty.ld37.game.components.AIRelevantComponent;
import eu.mighty.ld37.game.components.AIShipComponent;
import eu.mighty.ld37.game.components.BulletComponent;
import eu.mighty.ld37.game.components.PlayerComponent;
import eu.mighty.ld37.game.components.TeamComponent;
import eu.mighty.ld37.game.components.TransformComponent;

public class AIIteration {

	HashMap<Integer, Entity> entityMap;
	ArrayList <Integer> friendTeamList;
	ArrayList <Integer> enemyTeamList;
	ArrayList <Integer> bulletList;
	ArrayList<Integer> goalFriendTeamList;
	ArrayList<Integer> goalEnemyTeamList;
	ArrayList<Integer> scoreFriendTeamList;
	ArrayList<Integer> scoreEnemyTeamList;
	FlatMapProcessor mapProcessor;	
	private ComponentMapper<AIRelevantComponent> aiMapper;
	private ComponentMapper<AIBulletComponent> aiBulletMapper;
	private ComponentMapper<AIShipComponent> aiShipMapper;
	private ComponentMapper<BulletComponent> bulletMapper;
	private ComponentMapper<PlayerComponent> playerMapper;
	private ComponentMapper<TeamComponent> teamMapper;
	private ComponentMapper<TransformComponent> transformMapper;
	int currentIt;

	
	public AIIteration()
	{
		
	}
	public void update(
			int currentIt,
			FlatMapProcessor mapProcessor,
			HashMap<Integer, Entity> entityMap, 
			ArrayList<Integer> friendTeamList, 
			ArrayList<Integer> enemyTeamList, 
			ArrayList<Integer> bulletList,
			ArrayList<Integer> goalFriendTeamList,
			ArrayList<Integer> goalEnemyTeamList,
			ArrayList<Integer> scoreFriendTeamList,
			ArrayList<Integer> scoreEnemyTeamList)
	{
		this.currentIt = currentIt;
		this.mapProcessor = mapProcessor;
		this.entityMap = entityMap;
		this.friendTeamList = friendTeamList;
		this.enemyTeamList = enemyTeamList;
		this.bulletList = bulletList;
		this.goalFriendTeamList = goalFriendTeamList;
		this.goalEnemyTeamList = goalEnemyTeamList;
		this.scoreFriendTeamList = scoreFriendTeamList;
		this.scoreEnemyTeamList = scoreEnemyTeamList;

		//Obtain the mappers
		this.aiMapper = ComponentMapper.getFor(AIRelevantComponent.class);
		this.aiBulletMapper = ComponentMapper.getFor(AIBulletComponent.class);
		this.aiShipMapper = ComponentMapper.getFor(AIShipComponent.class);
		this.bulletMapper = ComponentMapper.getFor(BulletComponent.class);
		this.teamMapper = ComponentMapper.getFor(TeamComponent.class);
		this.transformMapper = ComponentMapper.getFor(TransformComponent.class);
		this.playerMapper = ComponentMapper.getFor(PlayerComponent.class);

	}
	
	public TargetObject obtainRandomRegion(int idObject)
	{
		int idMin = -1;
		float xPos = new Float(Math.random()*Defaults.mapWidth);
		float yPos = new Float(Math.random()*Defaults.mapHeight);
		
		int nextRegion = this.mapProcessor.obtainCurrentRegion(xPos, 
			yPos);
		
		return new TargetObject(idMin, nextRegion);
	}
	
	public int getDecisionIt()
	{
		return currentIt;
	}
	
	public TargetObject obtainClosestTeamMateGoalSquare(int idObject)
	{
		int nextRegion = -1;
		int idMin = -1;
		
		if (this.entityMap.containsKey(idObject))
		{
			//Pick the team
			Entity entity = this.entityMap.get(idObject);
			
			TeamComponent teamComp = this.teamMapper.get(entity);
			
			ArrayList<Integer> myList;
			if (teamComp.team == Defaults.FRIEND_TEAM)
			{
				myList = this.goalFriendTeamList;
			}
			else
			{
				myList = this.goalEnemyTeamList;
			}
			
			idMin = this.obtainIdClosestDistance(idObject, myList);
			if (idMin != -1)
			{
				
				Vector3 myPos = this.transformMapper.get(this.entityMap.get(idObject)).pos;		
				
				//Obtain the region of this node
				 nextRegion = this.mapProcessor.obtainCurrentRegion(myPos.x, 
							myPos.y);
			}
			else
			{
				//TODO - initialize things (probably not needed)
			}
		}
		return new TargetObject(idMin, nextRegion);
	}
	
	
	public TargetObject obtainClosestGoalEnemySquare(int idObject)
	{
		int nextRegion = -1;
		int idMin = -1;
		
		if (this.entityMap.containsKey(idObject))
		{
			//Pick the team
			Entity entity = this.entityMap.get(idObject);
			
			TeamComponent teamComp = this.teamMapper.get(entity);
			
			ArrayList<Integer> myList;
			if (teamComp.team == Defaults.FRIEND_TEAM)
			{
				myList = this.goalEnemyTeamList;
			}
			else
			{
				myList = this.goalFriendTeamList;
			}
			
			idMin = this.obtainIdClosestDistance(idObject, myList);
			
			if (idMin != -1)
			{
				
				Vector3 myPos = this.transformMapper.get(this.entityMap.get(idObject)).pos;		
				
				//Obtain the region of this node
				 nextRegion = this.mapProcessor.obtainCurrentRegion(myPos.x, 
							myPos.y);
			}
			else
			{
				//TODO - initialize things (probably not needed)
			}
		}
		return new TargetObject(idMin, nextRegion);
	}
	

	public TargetObject obtainClosestTeamMateSquare(int idObject)
	{
		int nextRegion = -1;
		int idMin = -1;
		
		if (this.entityMap.containsKey(idObject))
		{
			//Pick the team
			Entity entity = this.entityMap.get(idObject);
			
			TeamComponent teamComp = this.teamMapper.get(entity);
			
			ArrayList<Integer> myList;
			if (teamComp.team == Defaults.FRIEND_TEAM)
			{
				myList = this.friendTeamList;
			}
			else
			{
				myList = this.enemyTeamList;
			}
			
			idMin = this.obtainIdClosestDistance(idObject, myList);
			if (idMin != -1)
			{
				Vector3 myPos = this.transformMapper.get(this.entityMap.get(idObject)).pos;		
				
				//Obtain the region of this node
				 nextRegion = this.mapProcessor.obtainCurrentRegion(myPos.x, 
							myPos.y);
			}
			else
			{
				//TODO - initialize things (probably not needed)
			}
		}
		return new TargetObject(idMin,nextRegion);
	}
	
	public TargetObject obtainClosestEnemySquare(int idObject)
	{
		int nextRegion = -1;
		int idMin = -1;
		
		if (this.entityMap.containsKey(idObject))
		{
			//Pick the team
			Entity entity = this.entityMap.get(idObject);
			
			TeamComponent teamComp = this.teamMapper.get(entity);
			
			ArrayList<Integer> myList;
			if (teamComp.team == Defaults.FRIEND_TEAM)
			{
				myList = this.enemyTeamList;
			}
			else
			{
				myList = this.friendTeamList;
			}
			
			idMin = this.obtainIdClosestDistance(idObject, myList);
			
			if (idMin != -1)
			{
				
				Vector3 myPos = this.transformMapper.get(this.entityMap.get(idObject)).pos;		
				
				//Obtain the region of this node
				 nextRegion = this.mapProcessor.obtainCurrentRegion(myPos.x, 
							myPos.y);
			}
			else
			{
				//TODO - initialize things (probably not needed)
			}
		}
		return new TargetObject(idMin, nextRegion);
	}
	
	
	int obtainIdClosestDistance(int idObject, List<Integer> othersList)
	{
		double minCost = Double.MAX_VALUE;
		int idMin = -1;
		
		Vector3 myPos = this.transformMapper.get(this.entityMap.get(idObject)).pos;
		Vector2 my2DPos = new Vector2(myPos.x, myPos.y);		
		
		for (Integer otherId : othersList)
		{
			if (idMin != otherId)
			{
				Vector3 otherPos = this.transformMapper.get(this.entityMap.get(otherId)).pos;
				Vector2 other2DPos = new Vector2(otherPos.x, otherPos.y);
				
				double distance = this.getDistance(my2DPos, other2DPos);
				
				if (minCost > distance)
				{
					minCost = distance;
					idMin = otherId;
				}		
				
			}		
		}
		return idMin;
	}
	
	double getDistance(Vector2 myPos, Vector2 otherPos)
	{
	
		double estimatedCost = Math.min(0.5 * (Math.pow(myPos.x - otherPos.x, 2) +
			Math.pow(myPos.y - otherPos.y, 2)), 
			0.5 * (Math.pow(myPos.x - otherPos.x + Defaults.mapWidth,2) +
			Math.pow(myPos.y - otherPos.y, 2)));
		
		return estimatedCost;
	}	
	
	//Getters and setters
	public ArrayList<Integer> getFriendTeamList() {
		return friendTeamList;
	}

	public void setFriendTeamList(ArrayList<Integer> friendTeamList) {
		this.friendTeamList = friendTeamList;
	}

	public ArrayList<Integer> getEnemyTeamList() {
		return enemyTeamList;
	}

	public void setEnemyTeamList(ArrayList<Integer> enemyTeamList) {
		this.enemyTeamList = enemyTeamList;
	}

	public ArrayList<Integer> getBulletList() {
		return bulletList;
	}

	public void setBulletList(ArrayList<Integer> bulletList) {
		this.bulletList = bulletList;
	}

	public ComponentMapper<AIRelevantComponent> getAiMapper() {
		return aiMapper;
	}

	public void setAiMapper(ComponentMapper<AIRelevantComponent> aiMapper) {
		this.aiMapper = aiMapper;
	}

	public ComponentMapper<AIBulletComponent> getAiBulletMapper() {
		return aiBulletMapper;
	}

	public void setAiBulletMapper(ComponentMapper<AIBulletComponent> aiBulletMapper) {
		this.aiBulletMapper = aiBulletMapper;
	}

	public ComponentMapper<AIShipComponent> getAiShipMapper() {
		return aiShipMapper;
	}

	public void setAiShipMapper(ComponentMapper<AIShipComponent> aiShipMapper) {
		this.aiShipMapper = aiShipMapper;
	}

	public ComponentMapper<BulletComponent> getBulletMapper() {
		return bulletMapper;
	}

	public void setBulletMapper(ComponentMapper<BulletComponent> bulletMapper) {
		this.bulletMapper = bulletMapper;
	}

	public ComponentMapper<PlayerComponent> getPlayerMapper() {
		return playerMapper;
	}

	public void setPlayerMapper(ComponentMapper<PlayerComponent> playerMapper) {
		this.playerMapper = playerMapper;
	}

	public ComponentMapper<TeamComponent> getTeamMapper() {
		return teamMapper;
	}

	public void setTeamMapper(ComponentMapper<TeamComponent> teamMapper) {
		this.teamMapper = teamMapper;
	}

	public ComponentMapper<TransformComponent> getTransformMapper() {
		return transformMapper;
	}

	public void setTransformMapper(
			ComponentMapper<TransformComponent> transformMapper) {
		this.transformMapper = transformMapper;
	}


}
