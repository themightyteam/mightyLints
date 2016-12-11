package eu.mighty.ld37.game.components;

import sun.reflect.generics.tree.Tree;
import ai.pathfinding.commons.PredictedPath;

import com.badlogic.ashley.core.Component;

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
	
	Tree decisionTree;
	
	
}
