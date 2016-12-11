package eu.mighty.ld37.game.logic;

import eu.mighty.ld37.game.Defaults;

/**
 * Created by eLeDe on 11/12/2016.
 */
public class ScoreLogic {

    private int pointsFriendTeam = 0;
    private int pointsEnemyTeam = 0;

    public ScoreLogic() {}

    public void pointFriendTeam() {
        this.pointsFriendTeam += Defaults.SCORE_DESTROY;
    }

    public void pointEnemyTeam() {
        this.pointsEnemyTeam += Defaults.SCORE_DESTROY;
    }

    public void goalFriendTeam() {
        this.pointsFriendTeam += Defaults.SCORE_GOAL;
    }

    public void goalEnemyTeam() {
        this.pointsEnemyTeam += Defaults.SCORE_GOAL;
    }

    public int getPointsFriendTeam() {
        return pointsFriendTeam;
    }

    public int getPointsEnemyTeam() {
        return pointsEnemyTeam;
    }
}
