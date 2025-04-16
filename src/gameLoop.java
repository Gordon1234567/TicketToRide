import java.util.Scanner;

public class gameLoop {


    public gameLoop(){

    }

// We will have to find what exceptions will be thrown if any and account for those
    public void loop() {
        Scanner scan = new Scanner(System.in);

        // Set how many players there will be
        System.out.println("How many people are playing?");
        int numPlayers = scan.nextInt();
        Hand[] player = new Hand[numPlayers];

        // create and shuffle a deck
        Deck deck = new Deck();
        deck.shuffle();

        Board board = new Board();

        for (int i = 0; i < numPlayers; i++) {
            player[i] = new Hand();
            player[i].pickupCards(deck);
        }

        int playerNum = 1;
        while(true){
            board.printBoard();

            System.out.println("Player " + playerNum + " would you like to:" +
                    "\n1 - Pick up cards" +
                    "\n2 - Place trains" +
                    "\n3 - Check your points" +
                    "\n4 - Check your trains");
            int choice = scan.nextInt();

            if(choice == 1){
                player[playerNum].pickupCards(deck);
            }else{
                System.out.println("Where would you like to place Trains?");
            }
            break;
        }

    }
    /**
     * This will ask the player if they want to pick up cards or place trains,
     * They can also choose to check their points and how many trains they have left
     * @return
     */

    /**
     * This will be given the line that the player wants to play, it will try to play trains there
     * @param line is the line the player wants to play on
     */
    public void playTrain(String line, Hand x){

    }
}
