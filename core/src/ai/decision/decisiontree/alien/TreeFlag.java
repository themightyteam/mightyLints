package ai.decision.decisiontree.alien;

import ai.decision.AlienFSMDecisor;
import ai.decision.StateProp;
import ai.decision.decisiontree.DecisionTreeNode;

public class TreeFlag 
{
	
	DecisionTreeNode rootNode;
	
	
	public TreeFlag(StateProp prop,AlienFSMDecisor alienFSM,
			int timeOut)
	{
		
		RandomBranchFSMNodeWithTimeout branch1 = new RandomBranchFSMNodeWithTimeout(	
				alienFSM,
				new ActionGoToClosestTeammate(alienFSM), 
				new ActionGoToClosestTeammateWithFlag(alienFSM),
				prop.getPropGoToClosestTeammate(), 
				timeOut );

		
		RandomBranchFSMNodeWithTimeout branch2 = new RandomBranchFSMNodeWithTimeout(	
				alienFSM,
				new ActionGoToClosestEnemyWithFlag(alienFSM), 
				branch1,
				prop.getPropGoToClosestEnemyWithFlag(), 
				timeOut );

		RandomBranchFSMNodeWithTimeout branch3 = new RandomBranchFSMNodeWithTimeout(	
				alienFSM,
				new ActionGoToClosestEnemy(alienFSM), 
				branch2,
				prop.getPropGoToClosestEnemy(), 
				timeOut );
		

		RandomBranchFSMNodeWithTimeout branch4 = new RandomBranchFSMNodeWithTimeout(	
				alienFSM,
				new ActionGoToRandomSquare(alienFSM), 
				branch3,
				prop.getPropGoToRandomSquare(), 
				timeOut );

		
		RandomBranchFSMNodeWithTimeout branch5 = new RandomBranchFSMNodeWithTimeout(	
				alienFSM,
				new ActionGottoRandomSquareInZone(alienFSM), 
				branch4,
				prop.getPropGoToRandomSquareInZone(), 
				timeOut );
	
		RandomBranchFSMNodeWithTimeout branch6 = new RandomBranchFSMNodeWithTimeout(	
				alienFSM,
				new ActionGoToMyBase(alienFSM), 
				branch5,
				prop.getPropGoToMyBase(), 
				timeOut );

		RandomBranchFSMNodeWithTimeout branch7 = new RandomBranchFSMNodeWithTimeout(	
				alienFSM,
				new ActionGoToOtherBase(alienFSM), 
				branch6,
				prop.getPropGoToOtherBase(), 
				timeOut );
		
		ConditionFlagBranch branch8 = new ConditionFlagBranch(
				alienFSM,
				new ActionGoToMyBase(alienFSM),
				branch7
				);

		this.rootNode = branch8;


	}


	public DecisionTreeNode getRootNode() {
		return rootNode;
	}


	public void setRootNode(DecisionTreeNode rootNode) {
		this.rootNode = rootNode;
	}
	
	
	
}

