package eu.mighty.ld37.game.logic;

/**
 * Created by eLeDe on 11/12/2016.
 */
public class ScoreLogic {

    private int pointsFriendTeam = 0;
    private int pointsEnemyTeam = 0;

    public ScoreLogic() {}

    public void goalFriendTeam() {
        this.pointsFriendTeam++;
    }

    public void goalEnemyTeam() {
        this.pointsEnemyTeam++;
    }

    public int getPointsFriendTeam() {
        return pointsFriendTeam;
    }

    public int getPointsEnemyTeam() {
        return pointsEnemyTeam;
    }
}
