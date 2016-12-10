package ai.decision;


public class StateProp 
{
	
	public static final int GOTOOTHERBASE = 0;
	public static final int GOTOMYBASE = 1;
	public static final int GOTORANDOMSQUAREZONE = 2;
	public static final int GOTORANDOMSQUARE = 3;
	public static final int GOTOCLOSESTENEMY = 4;
	public static final int GOTOCLOSESTENEMYWITHFLAG = 5;
	public static final int GOTOCLOSESTTEAMMATE = 6;
	public static final int GOTOCLOSESTTEAMMATEWITHFLAG = 7;
	
	double propGoToOtherBase;
	double propGoToMyBase;
	double propGoToRandomSquareInZone;
	double propGoToRandomSquare;
	double propGoToClosestEnemy;
	double propGoToClosestEnemyWithFlag;
	double propGoToClosestTeammate;
	double propGoToClosestTeamMateWithFlag;

	public int getNextState()
	{
		double nextStateProp = Math.random();
		
		if (nextStateProp <= this.propGoToOtherBase)
		{
			return StateProp.GOTOOTHERBASE;
		}
		else if (nextStateProp <= this.propGoToMyBase)
		{
			return StateProp.GOTOMYBASE;
		}
		else if (nextStateProp <= this.propGoToRandomSquareInZone)
		{
			return StateProp.GOTORANDOMSQUAREZONE;
		}
		else if (nextStateProp <= this.propGoToRandomSquare)
		{
			return StateProp.GOTORANDOMSQUARE;
		}
		else if (nextStateProp <= this.propGoToClosestEnemy)
		{
			return StateProp.GOTOCLOSESTENEMY;
		}
		else if (nextStateProp <= this.propGoToClosestEnemyWithFlag)
		{
			return StateProp.GOTOCLOSESTENEMYWITHFLAG;
		}
		else if (nextStateProp <= this.propGoToClosestTeammate)
		{
			return StateProp.GOTOCLOSESTTEAMMATE;
		}
		else 
		{
			return StateProp.GOTOCLOSESTTEAMMATEWITHFLAG;
		}
		
	}

	
	
	//Getters and setters
	public double getPropGoToOtherBase() {
		return propGoToOtherBase;
	}

	public void setPropGoToOtherBase(double propGoToOtherBase) {
		this.propGoToOtherBase = propGoToOtherBase;
	}

	public double getPropGoToMyBase() {
		return propGoToMyBase;
	}

	public void setPropGoToMyBase(double propGoToMyBase) {
		this.propGoToMyBase = propGoToMyBase;
	}

	public double getPropGoToRandomSquareInZone() {
		return propGoToRandomSquareInZone;
	}

	public void setPropGoToRandomSquareInZone(double propGoToRandomSquareInZone) {
		this.propGoToRandomSquareInZone = propGoToRandomSquareInZone;
	}

	public double getPropGoToRandomSquare() {
		return propGoToRandomSquare;
	}

	public void setPropGoToRandomSquare(double propGoToRandomSquare) {
		this.propGoToRandomSquare = propGoToRandomSquare;
	}

	public double getPropGoToClosestEnemy() {
		return propGoToClosestEnemy;
	}

	public void setPropGoToClosestEnemy(double propGoToClosestEnemy) {
		this.propGoToClosestEnemy = propGoToClosestEnemy;
	}

	public double getPropGoToClosestEnemyWithFlag() {
		return propGoToClosestEnemyWithFlag;
	}

	public void setPropGoToClosestEnemyWithFlag(double propGoToClosestEnemyWithFlag) {
		this.propGoToClosestEnemyWithFlag = propGoToClosestEnemyWithFlag;
	}

	public double getPropGoToClosestTeammate() {
		return propGoToClosestTeammate;
	}

	public void setPropGoToClosestTeammate(double propGoToClosestTeammate) {
		this.propGoToClosestTeammate = propGoToClosestTeammate;
	}

	public double getPropGoToClosestTeamMateWithFlag() {
		return propGoToClosestTeamMateWithFlag;
	}

	public void setPropGoToClosestTeamMateWithFlag(
			double propGoToClosestTeamMateWithFlag) {
		this.propGoToClosestTeamMateWithFlag = propGoToClosestTeamMateWithFlag;
	}


}
