import java.util.Scanner;

public class game {
    /**
     * This will just run the game
     */

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("How many people are playing?");
        int numPlayers = scan.nextInt();
        Hand[] player = new Hand[numPlayers];

        Deck deck = new Deck();
        deck.shuffle();

        for (int i = 0; i < numPlayers; i++) {
            player[i] = new Hand();
            player[i].pickupCards(deck);
        }


        int playerNum = 1;
        while(true){
            System.out.println("Player " + playerNum + " would you like to:" +
                    "\n1 - Pick up cards" +
                    "\n2 - Place trains" +
                    "\n3 - Check your points" +
                    "\n4 - Check your trains");

            int decision = scan.nextInt();
            break;
        }

        /**
         * Will intake the number of players and make that many Hand objects in an array.
         * Then will run Turn in a loop for each player at a time,
         * printing board at the end of each turn while
         * checking if any each player runs out of trains at the end of their turn.
         * Once a player runs out of trains the loop runs for each other player
         * one more time and then gives a winner based on who has the most points
         */
    }
}
