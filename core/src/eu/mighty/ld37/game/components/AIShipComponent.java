package eu.mighty.ld37.game.components;

import ai.pathfinding.commons.PredictedPath;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

import eu.mighty.ld37.game.ai.decision.tree.BrainTree;

public class AIShipComponent extends AIRelevantComponent implements Component {
	//TODO: put AI information here
	//Unique id of the shp
	public int idAIObject;
	
	//Indication if the ship has a current active path
	public boolean hasActivePath;
	
	//Target node of the pathfinding algorithm (if any)
	public int idTargetNode;
	
	//Expected next node in the pathfinding algorithm
	public int idExpectedNode;
	
	//Active path
	public PredictedPath currentPath;
	
	//Active Decision in the decision engine
	public boolean hasActiveDecision;

	//Time when a tiemout decision was taken
	public int decisionIt;
	
	public int targetShipId;
	
	public Vector2 nextObjPos;
	
	public BrainTree decisionTree;
	
	
}
