import java.util.*;
import java.util.ArrayList;
import java.util.HashSet;
// import java.util.Math;

/** A Lemonade Stand game player that picks moves uniformly at random.
  * 
  * @author RR
  */
public class FollowBot implements Bot {
    private Random generator = new Random();

    private HashSet<Integer> player1History = new HashSet<Integer>(); //player 1's history
    private HashSet<Integer> player2History = new HashSet<Integer>(); //player 2's history

    public boolean isStick(HashSet<Integer> history) {
      if (history.size() > 1){
        return false;
      } 
      return true;
    }

    public int getNextMove(int player1LastMove, int player2LastMove) {
        player1History.add(player1LastMove);
        player2History.add(player2LastMove);

      
        if (!isStick(player1History) && !isStick(player2History)) {
          int followee = player2LastMove;
          if (generator.nextInt(2) + 1  == 1){
            followee = player1LastMove;
          }

          int nextMove = Math.floorMod(followee - 6, 12);

          if (nextMove == 0){
            nextMove = 12;
          }
          return nextMove;
        } else {
          int followee;
          if (isStick(player1History)) {
            followee = player1LastMove;
          } else {
            followee = player2LastMove;
          }
          int nextMove = Math.floorMod(followee - 6, 12);

          if (nextMove == 0){
            nextMove = 12;
          }
 
          return nextMove;
        }
    }
    
}