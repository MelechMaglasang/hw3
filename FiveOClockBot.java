
/** A Lemonade Stand game player that is extremely attached to the five o'clock
  * spot.
  * 
  * @author RR
  */
public class FiveOClockBot implements Bot {
    /** Plays the pure strategy {5}.
      * 
      * @param player1LastMove the action that was selected by Player 1 on the
      *                        last round.
      * @param player2LastMove the action that was selected by Player 2 on the
      *                        last round.
      * 
      * @return the next action to play.
      */
    public int getNextMove(int player1LastMove, int player2LastMove) {
        return 5;
    }
}
