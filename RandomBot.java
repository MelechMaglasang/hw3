
import java.util.Random;

/** A Lemonade Stand game player that picks moves uniformly at random.
  * 
  * @author RR
  */
public class RandomBot implements Bot {
    private Random generator = new Random();
    
    /** Returns an action according to the mixed strategy that picks among 
      * actions uniformly at random.
      * 
      * @param player1LastMove the action that was selected by Player 1 on the
      *                        last round.
      * @param player2LastMove the action that was selected by Player 2 on the
      *                        last round.
      * 
      * @return the next action to play.
      */
    public int getNextMove(int player1LastMove, int player2LastMove) {
        return generator.nextInt(12) + 1;
    }
    
}
