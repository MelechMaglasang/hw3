
/** Program that runs a Lemonade Stand tournament between the three named
  * players.
  * 
  * @author RR
  */
public class Tournament {
 
    public static void main(String[] args) {
        if (args.length != 4) {
            System.out.print("Usage: java Tournament <player1> <player2> ");
            System.out.println("<player3> <n>");
            System.out.println("where <player1> = class name of first bot.");
            System.out.println("      <player2> = class name of second bot.");
            System.out.println("      <player3> = class name of third bot.");
            System.out.println("      <n>       = number of rounds to play.");
            System.out.println("Example:");
            System.out.print("java Tournament RandomBot FiveOClockBot ");
            System.out.println("RandomBot 10000");
            System.exit(-1);
        }
        
        // Determine number of rounds to run
        int numRounds = 0;
        try {
            numRounds = Integer.parseInt(args[3]);
        }
        catch (Exception e) {
            System.out.println("Error: invalid value for num rounds.");
            System.exit(-1);
        }
        
        // Instantiate players
        Bot[] players = {null, null, null};
        try {
            for (int i = 0; i < 3; i++)
                players[i] = (Bot)Class.forName(args[i]).newInstance();
        }
        catch (Exception e) {
            System.out.println("Error: one or more named bots could not be " +
                               "loaded.");
            System.out.println("Double check that your bots have been " + 
                               "compiled and that the .class files are in " + 
                               "the current directory.");
            System.exit(-1);
        }
        
        // Run tournament
        Arbiter judge = new Arbiter(players[0], players[1], players[2]);
        for (int i = 0; i < numRounds; i++)
            judge.runRound();
        
        // Print mean scores
        int[] scores = judge.getCurrentScore();
        for (int i = 0; i < 3; i++)
            System.out.println(args[i] + ": " + (scores[i]/(double)numRounds));
    }
    
}
