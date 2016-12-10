package ai.pathfinding.commons;

import java.util.ArrayList;
import java.util.List;

public class PredictedPath 
{
	List<Connection> predConn;
	
	public PredictedPath( List<Connection> predConn )
	{
		this.predConn = predConn;
	}

	
	//Getters and setters
	public List<Connection> getPredConn() {
		return predConn;
	}

	public void setPredConn(List<Connection> predConn) {
		this.predConn = predConn;
	}
	

	
	
}
