package ai.pathfinding.commons;

public class NodeRecord 
{
	int idNode;
	Connection conn;
	double costSoFar;
	double estimatedTotalCost;

	/**
	 * 
	 * Constructor
	 * 
	 * @param idNode : id of the node
	 * @param conn : connection reaching this node
	 * @param costSoFar : the accumulated cost to reach this node
	 * @param estimatedTotalCost : estimation of the cost from this node to the target
	 */
	public NodeRecord(
			int idNode,
			Connection conn,
			double costSoFar,
			double estimatedTotalCost)
	{
		this.idNode = idNode;
		this.conn = conn;
		this.costSoFar = costSoFar;
		this.estimatedTotalCost = estimatedTotalCost;

	}
	
	public NodeRecord(
			int idNode)
			{
		this.idNode = idNode;
		this.conn = null;
		this.costSoFar = 0.0;
		this.estimatedTotalCost = 0.0;
	}

	


	//Getters amd setters
	public int getIdNode() {
		return idNode;
	}

	public void setIdNode(int idNode) {
		this.idNode = idNode;
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public double getCostSoFar() {
		return costSoFar;
	}

	public void setCostSoFar(double costSoFar) {
		this.costSoFar = costSoFar;
	}

	public double getEstimatedTotalCost() {
		return estimatedTotalCost;
	}

	public void setEstimatedTotalCost(double estimatedTotalCost) {
		this.estimatedTotalCost = estimatedTotalCost;
	}




}
