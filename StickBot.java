import java.util.ArrayList;
import java.util.Random;

/** A Lemonade Stand game player that is extremely attached to the five o'clock
  * spot.
  * 
  * @author RR
  */
  public class StickBot implements Bot {

    //Arbiter judge = new Arbiter(players[0], players[1], players[2]);

    private static ArrayList<Integer> player1Moves = new ArrayList<Integer>();
    private static ArrayList<Integer> player2Moves = new ArrayList<Integer>();
    private static ArrayList<Integer> myMoves = new ArrayList<Integer>();
    double round = 0;
    double avgutil = 0;
    double totalutil = 0.0;

    private Random generator = new Random();
    private int move = 0;

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

        round ++; 
        //if we have not made a move, get a random starting point
        if(move == 0){
            move = generator.nextInt(12) + 1;
            myMoves.add(move);
            player1Moves.add(player1LastMove);
            player2Moves.add(player2LastMove);

            return move;
        }
        int utility = scoreRound(move, player1LastMove, player2LastMove);
        totalutil += utility;
        avgutil = (totalutil) / (round);

        if (avgutil < 7 && round > 30){
          move = (move + 1) % 12;
          if (move == 0) {
            move = 1;
          }
        }

        
        player1Moves.add(player1LastMove);
        player2Moves.add(player2LastMove);
        myMoves.add(move);
        return move;
    }

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
}
