package eu.mighty.ld37.game.ai;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;

import eu.mighty.ld37.game.components.AIBulletComponent;
import eu.mighty.ld37.game.components.AIRelevantComponent;
import eu.mighty.ld37.game.components.AIShipComponent;
import eu.mighty.ld37.game.components.BulletComponent;
import eu.mighty.ld37.game.components.PlayerComponent;
import eu.mighty.ld37.game.components.TeamComponent;
import eu.mighty.ld37.game.components.TransformComponent;

public class AIIteration {

	ArrayList <Integer> friendTeamList;
	ArrayList <Integer> enemyTeamList;
	ArrayList <Integer> bulletList;

	private ComponentMapper<AIRelevantComponent> aiMapper;
	private ComponentMapper<AIBulletComponent> aiBulletMapper;
	private ComponentMapper<AIShipComponent> aiShipMapper;
	private ComponentMapper<BulletComponent> bulletMapper;
	private ComponentMapper<PlayerComponent> playerMapper;
	private ComponentMapper<TeamComponent> teamMapper;
	private ComponentMapper<TransformComponent> transformMapper;

	public AIIteration(
			HashMap<Integer, Entity> entityMap, 
			ArrayList<Integer> friendTeamList, 
			ArrayList<Integer> enemyTeamList, 
			ArrayList<Integer> bulletList)
	{

		this.friendTeamList = friendTeamList;
		this.enemyTeamList = enemyTeamList;
		this.bulletList = bulletList;

		//Obtain the mappers
		this.aiMapper = ComponentMapper.getFor(AIRelevantComponent.class);
		this.aiBulletMapper = ComponentMapper.getFor(AIBulletComponent.class);
		this.aiShipMapper = ComponentMapper.getFor(AIShipComponent.class);
		this.bulletMapper = ComponentMapper.getFor(BulletComponent.class);
		this.teamMapper = ComponentMapper.getFor(TeamComponent.class);
		this.transformMapper = ComponentMapper.getFor(TransformComponent.class);
		this.playerMapper = ComponentMapper.getFor(PlayerComponent.class);

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
