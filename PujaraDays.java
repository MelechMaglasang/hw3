import java.util.List;
import java.util.ArrayList;
import java.util.Random;
/** A Lemonade Stand game player that is extremely attached to the idea of being difficult.
  * 
  * @author MD, BR
  */
public class PujaraDays implements Bot {
    private List<Integer> historyP0 = new ArrayList<>();
    private List<Integer> historyP1 = new ArrayList<>();
    private List<Integer> historyP2 = new ArrayList<>();
    private int roundCount = 0;
    private Random generator = new Random();

    private int randomizePosition() {
        return generator.nextInt(12) + 1;
    }

    private int scoreRound(int action1, int action2, int action3) {
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


    public int getNextMove(int player1LastMove, int player2LastMove) {
        int utility = -1;
        int newPos = -1;
        // , utility, newPos;

        if (this.roundCount > 0) {
          //Check utility based on previous
          utility = scoreRound(this.historyP0.get(this.historyP0.size() - 1), this.historyP1.get(this.historyP1.size() - 1), this.historyP2.get(this.historyP2.size() - 1));
          if (utility < 8) {
            newPos = (this.historyP0.get(this.historyP0.size() - 1) + 4) % 12;
            if (newPos == 0) {
                newPos = 12;
            }
            newPos = randomizePosition();

          }
          else{
              newPos = this.historyP0.get(this.historyP0.size() - 1);
          }
        }
        else {
          newPos = randomizePosition();
        }
        this.roundCount++;
        this.historyP0.add(newPos);
        this.historyP1.add(player1LastMove);
        this.historyP2.add(player2LastMove);

        return newPos;
    }
}