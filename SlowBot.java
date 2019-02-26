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
    //Set Variables
    private double rho = 0.5;
    private double gamma = 0.75;
    private double tol = 0.1;
    private double stickCounter = 0;
    private int sticky = 6;

    private ArrayList<Integer> player1History = new ArrayList<Integer>(); 
    private ArrayList<Integer> player2History = new ArrayList<Integer>();
    private ArrayList<Integer> selfHistory = new ArrayList<Integer>();


    // private boolean areAcross(int player1LastMove, int player2LastMove){
    //   if ( (player1LastMove - 6) % 12 == player2LastMove){
    //     return true;

    //   }
    //   return false; 
    // }


    private double findS(double bigGamma,ArrayList<Integer> selfHistory, ArrayList<Integer> otherHistory){
      int size = otherHistory.size();
      double sPlayer = 0.0;
      for (int k = 2; k < size-1; k++){
        int curr = selfHistory.get(k);

        int otherCurr = otherHistory.get(k-1);

        sPlayer += (Math.pow(this.gamma, size - 1 - k) / bigGamma) * 
        Math.pow (Math.floorMod( Math.abs(curr - otherCurr), 12), this.rho); 

      }
      sPlayer *= -1.0;

      return sPlayer;
    }

    private double findFandOther(double bigGamma,ArrayList<Integer> selfHistory, ArrayList<Integer> otherHistory){
      int size = otherHistory.size();
      double fSelfandP = 0.0;
      for (int k = 2; k < size - 1; k++){

        int curr = selfHistory.get(k);

        int otherCurr = otherHistory.get(k-1);

        fSelfandP += (Math.pow(this.gamma, size -1 - k) / bigGamma) * 
        Math.pow (Math.floorMod( Math.abs(curr - Math.floorMod( otherCurr -6, 12) ), 12), this.rho); 

      }
      fSelfandP *= -1.0;

      return fSelfandP;
    }

    //This is kinda iffy
    private double findF(double bigGamma, ArrayList<Integer> selfHistory, ArrayList<Integer> otherHistory, ArrayList<Integer> otherHistory2){
      //Self follow value
      int size = otherHistory.size();

      double f = 0.0;
      for (int k = 2; k < size-1; k++){
        int curr = selfHistory.get(k);
        int otherP1 = otherHistory.get(k-1);
        int otherP2 = otherHistory2.get(k-1);


        //Minimum across distance for both players
        double minFollow = Math.min( Math.floorMod( Math.abs(curr - Math.floorMod( otherP1 -6, 12) ), 12) ,
        Math.floorMod( Math.abs(curr - Math.floorMod( otherP2 -6, 12) ), 12) );

        f += (Math.pow(this.gamma, size -1 - k) / bigGamma) * Math.pow (  minFollow, this.rho); 
      }

      f *= -1.0;

      return f;



    }
    
    public int getNextMove(int player1LastMove, int player2LastMove) {
        //Build History
        player1History.add(player1LastMove);
        player2History.add(player2LastMove);
      

        // // Signal that this can be a stationary player so start off stationary --Stick counter basically
        if (this.stickCounter > 0){
          this.selfHistory.add(this.sticky);
          this.stickCounter -= 1;
          return this.sticky;
        }

        //Calculate bigGamma, this should be the same for all indicators
        double bigGamma = 0.0;
        
        int p1Size = player1History.size();
        for (int k = 2; k < p1Size-1; k++){
          bigGamma += Math.pow(this.gamma, p1Size -1 - k);
        }

        //Rate player1 based on history

        double sPlayer1 = this.findS(bigGamma, this.player1History, this.player1History);    
        double fSelfandp1 = this.findFandOther(bigGamma, this.selfHistory, this.player1History);
        double fPlayer1 = this.findF(bigGamma, this.player1History, this.selfHistory, this.player2History);

        //Rate player2 based on history

        double sPlayer2 = this.findS(bigGamma, this.player2History, this.player2History);  
        double fSelfandp2 = this.findFandOther(bigGamma, this.selfHistory, this.player2History);
        double fOpponents = this.findFandOther(bigGamma, this.player1History, this.player2History);
        double fPlayer2 = this.findF(bigGamma, this.player2History, this.selfHistory, this.player1History);


                  

        System.out.println("------");        
        System.out.println("sPlayer1: " + sPlayer1);
        System.out.println("sPlayer2: " + sPlayer2);
        System.out.println("fSelfandp1: " +fSelfandp1);
        System.out.println("fSelfandp2: " + fSelfandp2);
        System.out.println("fOpponents: " +fOpponents );
        System.out.println("fPlayer1: " + fPlayer1);
        System.out.println("fPlayer2: " + fPlayer2);
        




        //Play for real now

        /**
         * Player 1 has a higher stick index than its follow index and 2's stick or follow indexes
         */
        if (sPlayer1 > fPlayer1+this.tol && sPlayer1 > sPlayer2+this.tol && sPlayer1 > fPlayer2+this.tol ){
          //Sit across Player 1

          int nextMove = Math.floorMod( player1LastMove - 6, 12);
          System.out.println("sticky " + nextMove);

          // this.stickCounter = 5;
          this.sticky = nextMove;

          
          if (nextMove == 0){
            nextMove = 12;
          }
          this.selfHistory.add(nextMove);
          return nextMove;
        }

        //Set Sticky indexes
        else if (sPlayer1 > fPlayer1 + this.tol && sPlayer2 > fPlayer2+this.tol){
          //Pseudo code talks about current utility?????

          if (sPlayer1 > sPlayer2){
            int nextMove = Math.floorMod( player1LastMove - 6, 12);
            if (nextMove == 0){
              nextMove = 12;
            }
            
            this.stickCounter = 5;
            this.selfHistory.add(nextMove);

            this.sticky = nextMove;

            return this.sticky;

          }
          else{
            int nextMove = Math.floorMod( player2LastMove - 6, 12);
            if (nextMove == 0){
              nextMove = 12;
            }
            
            this.stickCounter = 5;
            this.selfHistory.add(nextMove);

            this.sticky = nextMove;

            return this.sticky;

          }
          
        }

        //is player 1 following me?
        else if (fPlayer1 > sPlayer1 + this.tol && fPlayer1 > sPlayer2 + this.tol && fPlayer1 > fPlayer2 + this.tol){
          if (fSelfandp1 > fOpponents){
            this.selfHistory.add(this.sticky);
            return this.sticky;
          }
          else{
            this.selfHistory.add(player2LastMove);
            return player2LastMove;
          }
        }





        



        this.selfHistory.add(3);
        return 3;
    }
}
