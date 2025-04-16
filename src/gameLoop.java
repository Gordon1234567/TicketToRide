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

            System.out.println("Your hand:");
            System.out.println(player[playerNum].toString());
            System.out.println();

            System.out.println("Player " + playerNum + " would you like to:" +
                    "\n1 - Pick up cards" +
                    "\n2 - Place trains" +
                    "\n3 - Check your points" +
                    "\n4 - Check your trains");

            while(true) {

                int choice = scan.nextInt();

                if (choice == 1) {
                    player[playerNum].pickupCards(deck);
                    break;
                } else if (choice == 2) {
                    System.out.println("Where would you like to place Trains?");
                    break;
                } else if (choice == 3) {
                    System.out.println(player[playerNum].checkPoints() + " points");
                    System.out.println("What else?");
                } else if (choice == 4) {
                    System.out.println(player[playerNum].checkTrains() + " trains remaining");
                    System.out.println("What else?");
                } else {
                    System.out.println("Not an option");
                }
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
    public void playTrain(int line, Hand x,Board board){
        try {
            if (board.isLineFull(line)) {
                System.out.println("Line already taken");
            } else {

            }
        } catch (Exception e) {
            // This will obviously be changed when we add multiple ways to recognize user input
            System.out.println("Please input in the right way");;
        }
    }
}
