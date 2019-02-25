import java.util.*;

/** A Lemonade Stand game player that is #slow
  * 
  * @author MM
  */
  public class SlowBot implements Bot {
    /*
      Plays a stick style strategy can move if no one takes advantage of it and moves across it
      If it is a sucker of the match it will occupy the spot of one of the players forcing it to make a decision

    */
    private ArrayList<Integer> player1History = new ArrayList<Integer>(); 
    private ArrayList<Integer> player2History = new ArrayList<Integer>();
    private ArrayList<Integer> selfHistory = new ArrayList<Integer>();


    private boolean areAcross(int player1LastMove, int player2LastMove){
      if ( (player1LastMove - 6) % 12 == player2LastMove){
        return true;

      }
      return false; 
    }

    private double findGamma(ArrayList<Integer> playerHistory){
      
    }
    
    public int getNextMove(int player1LastMove, int player2LastMove) {
        //Build History
        player1History.add(player1LastMove);
        player2History.add(player2LastMove);

        // System.out.println(this.player1History.size());

        // Signal that this can be a stationary player so start off stationary
        if (this.player1History.size() < 5){
          this.selfHistory.add(6);
          return 6;
        }

        //Rate player1 based on history
        sPlayer1 = 

        //Rate player2 based on history


        //Collaborate with a player




        return 11;
    }
}
