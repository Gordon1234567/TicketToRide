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
            player[i].setPlayerNumber(i+1);
        }

        int playerNum = 0;

        while(true){
            board.printBoard();

            if(player[playerNum].getTrains() == 0){
                System.out.println("The game has ended because " + (playerNum + 1) + " has no more trains");
                int winningPlayer = 0;
                int highestNumber = 0;
                for (int i = 0; i < player.length; i++){
                    if(player[i].getPoints() > highestNumber){
                        highestNumber = player[i].getPoints();
                        winningPlayer = i + 1;
                    }

                }
                System.out.println("player " + winningPlayer + " WINS!!!!");
                break;
            }

            System.out.println("Your hand:");
            System.out.println(player[playerNum].toString());
            System.out.println();

            System.out.println("Player " + (playerNum + 1) + " would you like to:" +
                    "\n1 - Pick up cards" +
                    "\n2 - Place trains" +
                    "\n3 - Check your points" +
                    "\n4 - Check your trains");

            while(true) {

                int choice = scan.nextInt();

                if (choice == 1) {
                    player[playerNum].pickupCards(deck);
                    System.out.println("Your new hand " + player[playerNum].toString());
                    break;

                } else if (choice == 2) {
                    System.out.println("Where would you like your train to start?");
                    int location1 = scan.nextInt();
                    System.out.println("Where would you like your train to end?");
                    int location2 = scan.nextInt();
                    line a = new line();
                    for (int i = 0; i < board.getLength(); i++){
                        if (board.getLine(i).getLocations().contains(location2) && board.getLine(i).getLocations().contains(location1)){
                            a = board.getLine(i);

                        }
                    }

                    boolean gotPlayed = playTrain(a , player[playerNum], board);
                    if (gotPlayed) {break;}

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
            if(playerNum < numPlayers-1){
                playerNum++;
            } else { playerNum = 0;}
        }

    }
    /**
     * This will ask the player if they want to pick up cards or place trains,
     * They can also choose to check their points and how many trains they have left
     * @return
     */

    /**
     * This will be given the line that the player wants to play, it will try to play trains there
     * @param railroad is the line the player wants to play on
     */
    public boolean playTrain(line railroad, Hand x, Board board) {
        /**
         * This is a boolean so that if the player doesn't meet
         * the requirements for the line chosen, the player's turn
         * will continue to loop.
         */

        try {
            // first checks if the line is full
            if (railroad.getFull()) {
                System.out.println("Line already taken");
                return false;

                // then checks if the player has enough trains left
            } else if(x.getTrains() < railroad.getTrains()){
                System.out.println("You don't have enough trains for that");
                return false;

                // Then checks if the player has enough cards of specified color in their hand
            }else if (x.hasNumCards(railroad.getColor(), railroad.getTrains())) {
                // This is not working, always says you don't have the necessary cards

                board.playTrains(x, railroad);
                x.addGroup(railroad.getLocations());
                System.out.println("That's "+ railroad.getPoints() + " points");
                return true;

            } else {
                System.out.println("You don't have the required cards");
                return false;
            }
        }catch(Exception e){
                // This will obviously be changed when we add multiple ways to recognize user input
                System.out.println("Please input in the right way");
                return false;
        }
    }
}
