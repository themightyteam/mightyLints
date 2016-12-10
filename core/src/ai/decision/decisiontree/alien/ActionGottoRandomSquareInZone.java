package ai.decision.decisiontree.alien;

import ai.decision.AlienFSMDecisor;

public class ActionGottoRandomSquareInZone extends RandomActionNode 
{

	public ActionGottoRandomSquareInZone(AlienFSMDecisor alienFSM) {
		super(alienFSM);
	}

	@Override
	public int obtainNextNode() {

		return this.nextNode = this.alienFSM.obtainRandomSquareInMyZone();
	}

}
