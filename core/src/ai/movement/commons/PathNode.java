package ai.movement.commons;

import java.util.List;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class PathNode {
	
	int idNode;
	
	Vector2 referencePoint;
	
	List<Integer> connectionList;
	List<Integer> connectionJumpList;
	List<Integer> connectionLandingList;
	
	Rectangle nodeShape;
	
	
	public PathNode( int idNode, 
			Vector2 referencePoint,
			Rectangle nodeShape
			)
			{
		this.idNode = idNode;
		this.referencePoint = referencePoint;
		this.nodeShape = nodeShape;
			}


	
	//Getters and setters
	
	public List<Integer> getConnectionList() {
		return connectionList;
	}

	public void setConnectionList(List<Integer> connectionList) {
		this.connectionList = connectionList;
	}

	public List<Integer> getConnectionJumpList() {
		return connectionJumpList;
	}

	public void setConnectionJumpList(List<Integer> connectionJumpList) {
		this.connectionJumpList = connectionJumpList;
	}

	public List<Integer> getConnectionLandingList() {
		return connectionLandingList;
	}

	public void setConnectionLandingList(List<Integer> connectionLandingList) {
		this.connectionLandingList = connectionLandingList;
	}

	public Rectangle getNodeShape() {
		return nodeShape;
	}

	public void setNodeShape(Rectangle nodeShape) {
		this.nodeShape = nodeShape;
	}
	
	public int getIdNode() {
		return idNode;
	}

	public void setIdNode(int idNode) {
		this.idNode = idNode;
	}

	public Vector2 getReferencePoint() {
		return referencePoint;
	}

	public void setReferencePoint(Vector2 referencePoint) {
		this.referencePoint = referencePoint;
	}
	

	
	

}
