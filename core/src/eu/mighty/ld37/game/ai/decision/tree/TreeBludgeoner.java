package eu.mighty.ld37.game.ai.decision.tree;

import eu.mighty.ld37.game.ai.AIIteration;
import eu.mighty.ld37.game.ai.decision.RandomBranchFSMNodeWithTimeout;
import eu.mighty.ld37.game.ai.decision.action.ActionGoToClosestTeammate;
import eu.mighty.ld37.game.ai.decision.action.ActionWanderRandomRegion;

public class TreeBludgeoner extends BrainTree {

	public TreeBludgeoner(int myId, AIIteration aiIteration,
			int timeOut)
	{
			super(myId, aiIteration, timeOut);
						
			//Wander to a different region 
			RandomBranchFSMNodeWithTimeout branch1 = new RandomBranchFSMNodeWithTimeout(	
					myId, aiIteration,
					new ActionGoToClosestTeammate(myId, aiIteration), 
					new ActionWanderRandomRegion(myId, aiIteration),
					0.3, 
					timeOut );

			this.rootNode = branch1;
		
	}
	
}
