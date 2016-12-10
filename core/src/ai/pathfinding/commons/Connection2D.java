package ai.pathfinding.commons;

public class Connection2D extends Connection {

	public static final double COST_JUMP = 1;
	public static final double COST_LAND = 0.6;
	public static final double COST_NORMAL = 0.8;
	
	boolean isJump;
	boolean isLanding;
	boolean isNormal;
	
	public Connection2D(int idConn, int sourceNodeId, int sinkNodeId,
			double cost, boolean isJump, boolean isLanding, boolean isNormal) 
	
	{
		super(idConn, sourceNodeId, sinkNodeId, cost);
		
		this.isJump = isJump;
		this.isNormal = isNormal;
		this.isLanding = isLanding;
		
		if (this.isJump)
			this.cost = this.cost * Connection2D.COST_JUMP;
		else if (this.isNormal)
			this.cost = this.cost * Connection2D.COST_NORMAL;
		else
			this.cost = this.cost * Connection2D.COST_LAND;
	}
	
	@Override
	public void setCost(double cost) 
	{
		if (this.isJump)
			this.cost = cost * Connection2D.COST_JUMP;
		else if (this.isNormal)
			this.cost = cost * Connection2D.COST_NORMAL;
		else
			this.cost = cost * Connection2D.COST_LAND;
	}

	
	//Getters and setters
	public boolean isJump() {
		return isJump;
	}

	public void setJump(boolean isJump) {
		this.isJump = isJump;
	}

	public boolean isLanding() {
		return isLanding;
	}

	public void setLanding(boolean isLanding) {
		this.isLanding = isLanding;
	}

	public boolean isNormal() {
		return isNormal;
	}

	public void setNormal(boolean isNormal) {
		this.isNormal = isNormal;
	}
	
	
	

}
