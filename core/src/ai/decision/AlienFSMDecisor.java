package ai.decision;

import java.util.List;

import ai.decision.decisiontree.DecisionTreeNode;
import ai.decision.decisiontree.alien.ActionFSMNode;
import ai.decision.decisiontree.alien.TreeFlag;
import ai.decision.decisiontree.alien.TreeNoFlag;
import ai.movement.commons.PathNode;
import ai.player.AINormalPlayer;

import com.badlogic.gdx.math.Vector2;

public class AlienFSMDecisor extends BasicDecisor 
{
	//No team has the flag
	public static int STATE_STATUS_QUO = 0;

	//Enemy team has the flag
	public static int ONLY_ENEMY_ATTACK = 1;

	//Only we have the flag
	public static int ONLY_WE_ATTACK = 2;

	//Both teams have the flag
	public static int TOTAL_MAYHEM = 3;


	public static final double SHOOTING_RANGE = 5;
	
	
	//Timeout of random vars in decisor tree
	public static final int DECISOR_TICK_TIMEOUT = 100;

	/////////////////////////////
	//    State variables
	//    FIXME: Move to a new class
	//
	///////////////////////////
	
	//Current State
	int currentState;
	
	//Time in which the player reached the node
	long firstTimeInNode = 0;
	
	//Closest Enemy instance
	NormalPlayer closestEnemy = null;
	
	//Closest Teammate instance
	NormalPlayer closestTeammate = null;
	
	//Closest Teammate with flag
	NormalPlayer teamMateWithFlag = null;
	
	//Closest enemy with flag
	NormalPlayer enemyWithFlag = null;
	
	//My base position
	Vector2 myBase = null;
	
	//Enemy Base position
	Vector2 otherBase = null;

	//True if enemy team has the flag
	boolean enemyTeamHasFlag = false;
	
	//True if my team has the flag
	boolean myTeamHasFlag = false;
	
	//True if i have the flag
	boolean iHaveTheFlag = false;

	//New state transition (change in state during tick execution)
	boolean stateTransition = false;

	//Flag that activates when a node goal was reached
	boolean goalReached = false;
	
	//Stores the number of decision iterations (number of executions of the decisor tree)
	int decisionIt = 0;

	//Set of decisors (one for state)
	TreeFlag decisorStatusQuo;
	TreeFlag decisorOnlyWeAttack;
	TreeFlag decisorOnlyEnemyAttack;
	TreeFlag decisorTotalMayhem;
	
	
	public AlienFSMDecisor(AINormalPlayer aiNormalPlayer )
	{
		super(aiNormalPlayer);
		
		//Sets initial state 
		this.currentState = STATE_STATUS_QUO;
		
		//Init the decisors
		this.decisorStatusQuo = new TreeNoFlag(new StatePropStatusQuo(),
				this,
				DECISOR_TICK_TIMEOUT);
	
		this.decisorOnlyWeAttack = new TreeFlag(new StatePropOnlyWeAttack(),
				this,
				DECISOR_TICK_TIMEOUT);
		
		this.decisorOnlyEnemyAttack = new TreeNoFlag(new StatePropOnlyEnemyAttack(),
				this,
				DECISOR_TICK_TIMEOUT);

		this.decisorTotalMayhem = new TreeNoFlag(new StatePropTotalMayhem(),
				this,
				DECISOR_TICK_TIMEOUT);
		
	}



	/**
	 * 
	 * Obtains the target node over which pathfinding will be calculated
	 * 
	 * Parameters:
	 * 
	 * @param aiWorld : representation of the world (graph nodes)
	 * @param aiPlayer: current alien player
	 * 
	 * @return the node in the world graph of nodes with the target
	 * (if no change in state occurred and there was a current pathfinding in use
	 * the target node would be the same)
	 * 
	 * 
	 */
	@Override
	public int getTransition()
	{
		if (this.aiPlayer.getSquareTeam() == Player.GREEN_TEAM)
		{
			//Obtains the state/Update state variables
			this.obtainState(this.aiPlayer.getGreenTeamList(), 
					this.aiPlayer.getVioletTeamList(), this.aiPlayer);


		}
		else
		{
			//Obtains the state/Updates state variables
			this.obtainState(this.aiPlayer.getVioletTeamList(), 
					this.aiPlayer.getGreenTeamList(), this.aiPlayer);

		}

		//Check if there was a change in state (someone captured/freed the flag
		this.checkChangeInState();

		//Obtain the next target node
		return this.obtainNextTarget();
	}

	/**
	 * 
	 * Obtains next target node if function of the current state 
	 * 
	 * @return next node in state
	 */
	private int obtainNextTarget()
	{

	

		//If i have the flag, return always to base (the node where the base is located)
		if (this.iHaveTheFlag)
			return this.obtainMyBaseSquare();

		DecisionTreeNode resultNode;
		if (this.currentState ==  AlienFSMDecisor.STATE_STATUS_QUO)
		{
			resultNode = this.decisorStatusQuo.getRootNode().makeDecision();

		} else if (this.currentState ==  AlienFSMDecisor.ONLY_ENEMY_ATTACK)
		{
			resultNode = this.decisorOnlyEnemyAttack.getRootNode().makeDecision();
		}
		else if (this.currentState ==  AlienFSMDecisor.ONLY_WE_ATTACK)
		{
			resultNode = this.decisorOnlyWeAttack.getRootNode().makeDecision();
		}
		else 
		{
			resultNode = this.decisorTotalMayhem.getRootNode().makeDecision();
		}
		
		
		//Add one tick
		this.decisionIt++;
		
		//Reset ticks if goal was reached
		if (this.goalReached)
		{
			this.decisionIt = 0;
			this.goalReached = false;
		}
		
		if (resultNode instanceof ActionFSMNode)
		{
			ActionFSMNode resultAction = (ActionFSMNode) resultNode;
			
			return resultAction.getNextNode();
		}
		else return -1;

	}

	/**
	 * Obtains the base node id in the path of nodes
	 * 
	 * @return the id of the enemy base node
	 */
	public int obtainOtherBaseSquare()
	{
		if (this.otherBase != null)
		{
			int baseNode = this.aiPlayer.getAiWorld().obtainCurrentNode(this.otherBase).getIdNode();

			return baseNode;
		}

		return -1;
	}

	/**
	 * 
	 * Obtains the node of the players base
	 * 
	 * @return the node id of the player base or -1 if no base was
	 * 	specified
	 */
	public int obtainMyBaseSquare()
	{
		if (this.myBase != null)
		{
			//System.out.println("My Base "+ this.myBase.x+ " "+ this.myBase.y );

			int baseNode = this.aiPlayer.getAiWorld().obtainCurrentNode(this.myBase).getIdNode();

			return baseNode;
		}

		return -1;
	}

	public int obtainRandomSquareInMyZone()
	{
		if (this.aiPlayer.getPosition() != null)
		{
			int zone = this.aiPlayer.getAiWorld().obtainCurrentZone(this.aiPlayer.getPosition());

			if (zone > -1)
			{
				List<PathNode> pathsInZone = 
						this.aiPlayer.getAiWorld().getZonePathMap().get(zone);

				int randomPath =  (int) ( Math.floor(Math.random() * pathsInZone.size() ) );

				return pathsInZone.get(randomPath).getIdNode();
			}
		}

		return -1;
	}

	public int obtainRandomSquare()
	{
		if (this.aiPlayer.getPosition() != null)
		{
			int randomPath = (int) (Math.floor(Math.random() * this.aiPlayer.getAiWorld().getPathList().size()));

			return this.aiPlayer.getAiWorld().getPathList().get(randomPath).getIdNode();

		}

		return -1;
	}


	public int obtainClosestEnemySquare()
	{
		if (this.closestEnemy != null)
		{
			if (this.closestEnemy.getLastSeenNode() != null)

				return this.closestEnemy.getLastSeenNode().getIdNode();

		} 
		return -1;
	}

	public int obtainClosestEnemyWithFlagSquare()
	{
		if (this.enemyWithFlag != null)
		{
			if (this.enemyWithFlag.getLastSeenNode() != null)

				return this.enemyWithFlag.getLastSeenNode().getIdNode();

		} 
		return -1;
	}

	public int obtainClosestTeamMateSquare()
	{
		if (this.closestTeammate != null)
		{
			if (this.closestTeammate.getLastSeenNode() != null)

				return this.closestTeammate.getLastSeenNode().getIdNode();

		} 
		return -1;
	}

	public int obtainClosestTeamMateWithFlagSquare()
	{
		if (this.teamMateWithFlag != null)
		{
			if (this.teamMateWithFlag.getLastSeenNode() != null)

				return this.teamMateWithFlag.getLastSeenNode().getIdNode();

		} 
		return -1;
	}


	/**
	 * 
	 * Update the state of the player
	 * 
	 * @param myTeamList : teammate list
	 * @param enemyTeamList : enemy list
	 * @param aiPlayer : current player
	 */
	private void obtainState(List<Player> myTeamList, 
			List<Player> enemyTeamList, AINormalPlayer aiPlayer)
	{
		this.iHaveTheFlag = aiPlayer.isHasFlag();

		//this.closestTeammate = null;
		//this.teamMateWithFlag = null;

		this.enemyTeamHasFlag = false;
		this.myTeamHasFlag = false;

		for (Player inListPlayer : myTeamList )
		{


			if ((inListPlayer instanceof NormalPlayer ) ||
					(inListPlayer instanceof AINormalPlayer))
			{

				NormalPlayer player = (NormalPlayer) inListPlayer;	

				if (player.isHasFlag())
				{
					this.myTeamHasFlag = true;

					if (this.teamMateWithFlag == null)
					{
						this.teamMateWithFlag = player;
					}
					else
					{
						if (this.isTeammateCloser(player.getPosition(), 
								this.teamMateWithFlag.getPosition(), 
								aiPlayer.getPosition()))
						{
							this.teamMateWithFlag = player;
						}
					}
				}

				//Check if the player is the closest teammate
				if (this.closestTeammate == null)
					this.closestTeammate = player;
				else
				{
					if (this.isTeammateCloser(player.getPosition(), 
							this.closestTeammate.getPosition(), 
							aiPlayer.getPosition()))
					{
						this.closestTeammate = player;
					}
				}
			}

		}

		//Check enemy information

		//this.enemyWithFlag = null;
		//this.closestEnemy = null;


		for (Player inListPlayer : enemyTeamList )
		{

			if ((inListPlayer instanceof NormalPlayer ) ||
					(inListPlayer instanceof AINormalPlayer))
			{

				NormalPlayer player = (NormalPlayer) inListPlayer;		

				if (player.isHasFlag())
				{
					this.enemyTeamHasFlag = true;

					if (this.enemyWithFlag == null)
					{
						this.enemyWithFlag = player;

						//System.out.println(this.enemyWithFlag.getLastSeenNode().getIdNode());
					}
					else
					{
						if (this.isTeammateCloser(player.getPosition(), 
								this.enemyWithFlag.getPosition(), 
								aiPlayer.getPosition()))
						{
							this.enemyWithFlag = player;
						}
					}
				}


				//Check if the player is the closest enemy
				if (this.closestEnemy == null)
					this.closestEnemy = player;
				else
				{
					if (this.isTeammateCloser(player.getPosition(), 
							this.closestEnemy.getPosition(), 
							aiPlayer.getPosition()))
					{
						this.closestEnemy = player;
					}
				}

			}
		}	
	}

	/**
	 * 
	 * Changes the macro state (FSM State)
	 * 
	 * It flags if the transition of state occurred during this tick
	 * 
	 */
	private void checkChangeInState() 
	{

		this.stateTransition = false;
		if ((!this.enemyTeamHasFlag) && (!this.myTeamHasFlag))
		{
			if (this.currentState != AlienFSMDecisor.STATE_STATUS_QUO)
				this.stateTransition = true;
			this.currentState = AlienFSMDecisor.STATE_STATUS_QUO;
		}
		else if (this.enemyTeamHasFlag && !this.myTeamHasFlag)
		{
			if (this.currentState != AlienFSMDecisor.ONLY_ENEMY_ATTACK)	
				this.stateTransition = true;
			this.currentState = AlienFSMDecisor.ONLY_ENEMY_ATTACK;

		}
		else if (!this.enemyTeamHasFlag && this.myTeamHasFlag)
		{
			if (this.currentState != AlienFSMDecisor.ONLY_WE_ATTACK)
				this.stateTransition = true;
			this.currentState = AlienFSMDecisor.ONLY_WE_ATTACK;
		}
		else if (this.enemyTeamHasFlag && this.myTeamHasFlag)
		{
			if (this.currentState != AlienFSMDecisor.TOTAL_MAYHEM)
				this.stateTransition = true;
			this.currentState = AlienFSMDecisor.TOTAL_MAYHEM;
		}


	}


	private boolean isTeammateCloser(Vector2 position, Vector2 prevPosition, Vector2 myPosition)
	{
		if ((this.euclideanDistance(position, myPosition)) < 
				(this.euclideanDistance(prevPosition, myPosition)))
		{
			return true;
		}
		else
			return false;

	}

	private double euclideanDistance(Vector2 one, Vector2 other)
	{
		return 0.5 * Math.pow(one.x - other.x, 2 ) + Math.pow(other.y - other.y, 2);
	}


	public boolean shouldIshoot(AINormalPlayer aiPlayer)
	{
		if ((this.closestEnemy != null) && (aiPlayer.getPosition() != null))
		{
			if (this.closestEnemy.getPosition() != null)
			{
				if (Math.abs(aiPlayer.getPosition().x - 
						closestEnemy.getPosition().x) < AlienFSMDecisor.SHOOTING_RANGE)
				{
					return true;
				}
			}

		}


		return false;
	}


	public Vector2 getMyBase() {
		return myBase;
	}


	public void setMyBase(Vector2 myBase) {
		this.myBase = myBase;
	}


	public Vector2 getOtherBase() {
		return otherBase;
	}


	public void setOtherBase(Vector2 otherBase) {
		this.otherBase = otherBase;
	}

	public int getDecisionIt() {
		return decisionIt;
	}

	public void setDecisionIt(int decisionIt) {
		this.decisionIt = decisionIt;
	}



	public boolean isiHaveTheFlag() {
		return iHaveTheFlag;
	}



	public void setiHaveTheFlag(boolean iHaveTheFlag) {
		this.iHaveTheFlag = iHaveTheFlag;
	}



	public boolean isGoalReached() {
		return goalReached;
	}



	public void setGoalReached(boolean goalReached) {
		this.goalReached = goalReached;
	}



	public NormalPlayer getClosestEnemy() {
		return closestEnemy;
	}



	public void setClosestEnemy(NormalPlayer closestEnemy) {
		this.closestEnemy = closestEnemy;
	}




}
