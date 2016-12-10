package ai.pathfinding.commons;

public class Connection 
{
	
	int idConn;
	int sourceNodeId;
	int sinkNodeId;
	double cost; 
	
	
	
	public Connection(
			int idConn,
			int sourceNodeId,
			int sinkNodeId,
			double cost
			)
	{
		
		this.idConn = idConn;
		this.sourceNodeId = sourceNodeId;
		this.sinkNodeId = sinkNodeId;
		this.cost = cost;
		
	}

	//Getters and setters
	public int getIdConn() {
		return idConn;
	}

	public void setIdConn(int idConn) {
		this.idConn = idConn;
	}

	public int getSourceNodeId() {
		return sourceNodeId;
	}

	public void setSourceNodeId(int sourceNodeId) {
		this.sourceNodeId = sourceNodeId;
	}

	public int getSinkNodeId() {
		return sinkNodeId;
	}

	public void setSinkNodeId(int sinkNodeId) 
	{
		this.sinkNodeId = sinkNodeId;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) 
	{
		this.cost = cost;
	}

	
	
}
