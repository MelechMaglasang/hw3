
import java.util.Arrays;

/** Judge for running a single match between three Lemonade Stand bots.
  * 
  * @author RR
  */
public class Arbiter {

    private int roundsCompleted;

    // In the following arrays, indices {0, 1, 2} correspond to data for 
    // players 1, 2 and 3 respectively. 
    private Bot[] players; // the bots that will be acting for the players
    private int[] lastRound; // actions chosen by players on last round
    private int[] score; // total scores of each player thus far
    
    /** Intializes a new match between three specified bots.
      * 
      * @param player1 the first bot.
      * @param player2 the second bot.
      * @param player3 the third bot.
      */
    public Arbiter(Bot player1, Bot player2, Bot player3) {
        this.roundsCompleted = 0;
        this.players = new Bot[3];
        this.players[0] = player1;
        this.players[1] = player2;
        this.players[2] = player3;
        this.lastRound = new int[3];
        this.score = new int[3];
    }
    
    /** Returns the current score of this match.
      * 
      * @return an array where the first element contains the total score
      * for player 1, the second element is the total score of player 2, and
      * the third element is the total score of player 3.
      */
    public int[] getCurrentScore() {
        return Arrays.copyOf(this.score, this.score.length);
    }
    
    /** Scores the current round from the perspective of the first player.
      * 
      * @param action1 the action taken by the first player.
      * @param action2 the action taken by the second player.
      * @param action3 the action taken by the third player.
      * 
      * @return the payoff for player 1 on this round.
      */
    public int scoreRound(int action1, int action2, int action3) {
        if ((action1 == action2) && (action1 == action3))
            return 8; // three-way tie
        else if ((action1 == action2) || (action1 == action3)) {
            return 6; // two-way tie
        }
        else {
            int score = 0;
            int i = action1;
            while ((i != action2) && (i != action3)) { // score clockwise
                i = (i % 12) + 1;
                score += 1;
            }
            i = action1;
            while ((i != action2) && (i != action3)) { // score anti-clockwise
                i = (i-1 > 0) ? i-1 : 12;
                score += 1;
            }
            return score;
        }
    }
    
    /** Plays a single round between players 1, 2 and 3. */
    public void runRound() {
        int[] actions = new int[3];
        
        if (this.roundsCompleted == 0) {
            // For very first round, we pretend that all players went on
            // position 12 on the last round
            for (int i = 0; i < 3; i++) {
                actions[i] = players[i].getNextMove(12, 12);
                if ((actions[i] > 12) || (actions[i] < 1)) {
                    throw new 
                        IllegalArgumentException("Player " + i + " returned " +
                                                 "an illegal action " + 
                                                 actions[i]);
                }
            }
        }
        else {
            // Pass in opponents' last move
            for (int i = 0; i < 3; i++) {
                actions[i] = players[i].getNextMove(this.lastRound[(i+1)%3],
                                                    this.lastRound[(i+2)%3]);
                if ((actions[i] > 12) || (actions[i] < 1)) {
                    throw new 
                        IllegalArgumentException("Player " + i + " returned " +
                                                 "an illegal action " + 
                                                 actions[i]);
                }
            }
        }
        
        // Score the round and update last played actions
        for (int i = 0; i < 3; i++) {
            this.score[i] += scoreRound(actions[i], 
                                        actions[(i+1)%3], 
                                        actions[(i+2)%3]);
            this.lastRound[i] = actions[i];
        }
        
        this.roundsCompleted++;
    }
}