package ai.world;

public class NodeRepresentations {
	
	int idNode;
	double x;
	double y;
	
	public NodeRepresentations( int idNode, double x, double y)
	{
		this.idNode = idNode;
		this.x = x;
		this.y = y;
	}
	
	//Getters and setters
	
	
	
	public double getX() {
		return x;
	}
	public int getIdNode() {
		return idNode;
	}

	public void setIdNode(int idNode) {
		this.idNode = idNode;
	}

	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	
	

}
