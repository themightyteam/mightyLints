package ai.decision;

public class StatePropStatusQuo extends StateProp {

	public StatePropStatusQuo()
	{
		this.propGoToOtherBase = 0.4;
		this.propGoToMyBase = 0.15/(1 - this.propGoToOtherBase);
		this.propGoToRandomSquareInZone = 0.15/
				(( 1- this.propGoToOtherBase) *
						(1 -this.propGoToMyBase));
		this.propGoToRandomSquare = 0.10/
				(( 1- this.propGoToOtherBase) *
						(1-this.propGoToMyBase) * 
						(1- this.propGoToRandomSquareInZone));
		this.propGoToClosestEnemy = 0.10/
				((1-this.propGoToOtherBase) *
						(1-this.propGoToMyBase) * 
						(1- this.propGoToRandomSquareInZone) *
						(1- this.propGoToRandomSquare)
						);
		this.propGoToClosestEnemyWithFlag = 0/
				((1-this.propGoToOtherBase) *
						(1-this.propGoToMyBase) * 
						(1- this.propGoToRandomSquareInZone) *
						(1- this.propGoToRandomSquare) *
						(1- this.propGoToClosestEnemy));
		this.propGoToClosestTeammate = 0.10/
				((1-this.propGoToOtherBase) *
						(1-this.propGoToMyBase) * 
						(1- this.propGoToRandomSquareInZone) *
						(1- this.propGoToRandomSquare) *
						(1- this.propGoToClosestEnemy)*
						(1 - this.propGoToClosestEnemyWithFlag)
				
				);
		this.propGoToClosestTeamMateWithFlag = 0/
				((1-this.propGoToOtherBase) *
						(1-this.propGoToMyBase) * 
						(1- this.propGoToRandomSquareInZone) *
						(1- this.propGoToRandomSquare) *
						(1- this.propGoToClosestEnemy)*
						(1 - this.propGoToClosestEnemyWithFlag)	*
						(1- this.propGoToClosestEnemyWithFlag )
				);
	}

}
