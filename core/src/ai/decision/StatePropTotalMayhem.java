package ai.decision;

public class StatePropTotalMayhem extends StateProp {

	{
		this.propGoToOtherBase = 0;
		this.propGoToMyBase = 0.3/(1 - this.propGoToOtherBase);
		this.propGoToRandomSquareInZone = 0.1/
				(( 1- this.propGoToOtherBase) *
						(1 -this.propGoToMyBase));
		this.propGoToRandomSquare = 0/
				(( 1- this.propGoToOtherBase) *
						(1-this.propGoToMyBase) * 
						(1- this.propGoToRandomSquareInZone));
		this.propGoToClosestEnemy = 0.20/
				((1-this.propGoToOtherBase) *
						(1-this.propGoToMyBase) * 
						(1- this.propGoToRandomSquareInZone) *
						(1- this.propGoToRandomSquare)
						);
		this.propGoToClosestEnemyWithFlag = 0.3/
				((1-this.propGoToOtherBase) *
						(1-this.propGoToMyBase) * 
						(1- this.propGoToRandomSquareInZone) *
						(1- this.propGoToRandomSquare) *
						(1- this.propGoToClosestEnemy));
		this.propGoToClosestTeammate = 0.05/
				((1-this.propGoToOtherBase) *
						(1-this.propGoToMyBase) * 
						(1- this.propGoToRandomSquareInZone) *
						(1- this.propGoToRandomSquare) *
						(1- this.propGoToClosestEnemy)*
						(1 - this.propGoToClosestEnemyWithFlag)
				
				);
		this.propGoToClosestTeamMateWithFlag = 0.05/
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
