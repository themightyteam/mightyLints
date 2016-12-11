package eu.mighty.ld37.game.ai.decision.tree;

import eu.mighty.ld37.game.ai.AIIteration;
import eu.mighty.ld37.game.ai.decision.RandomBranchFSMNodeWithTimeout;
import eu.mighty.ld37.game.ai.decision.action.ActionGoToClosestEnemyGoal;
import eu.mighty.ld37.game.ai.decision.action.ActionGoToClosestTeammate;
import eu.mighty.ld37.game.ai.decision.action.ActionWanderRandomRegion;

public class TreeScorer extends BrainTree {

	public TreeScorer(int myId, AIIteration aiIteration,
			int timeOut)
	{
			super(myId, aiIteration, timeOut);
						
			//Wander to a different region 
			RandomBranchFSMNodeWithTimeout branch1 = new RandomBranchFSMNodeWithTimeout(	
					myId, aiIteration,
					new ActionGoToClosestTeammate(myId, aiIteration), 
					new ActionWanderRandomRegion(myId, aiIteration),
					0.2, 
					timeOut );

			RandomBranchFSMNodeWithTimeout branch2 = new RandomBranchFSMNodeWithTimeout(	
					myId, aiIteration,
					branch1, 
					new ActionGoToClosestEnemyGoal(myId, aiIteration),
					0.3, 
					timeOut );
			
			this.rootNode = branch2;
		
	}
	
}
