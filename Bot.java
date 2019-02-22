
/** Declares the behavioral contract for a participant in the CSC 370 Lemonade
  * Stand game contest.
  * 
  * @author RR
  */
public interface Bot {
    
    /** Returns the next action that this bot will take. */
    public int getNextMove(int player1LastMove, int player2LastMove);
    
}
