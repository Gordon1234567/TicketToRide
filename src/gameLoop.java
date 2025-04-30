import java.util.HashMap;
import java.util.Scanner;

public class gameLoop {


    public gameLoop(){

    }

// We will have to find what exceptions will be thrown if any and account for those
    public void loop() {
        Scanner scan = new Scanner(System.in);

        // Set how many players there will be
        System.out.println("Welcome rail tycoon to Ticket To Ride!");
        System.out.println("The Ohio valley is fit to be the industrial heartland of the country, it's up to you to bring the steam and steel to this land");
        System.out.println();
        System.out.println("How many capitalists will be playing?");
        int numPlayers = scan.nextInt();
        Hand[] player = new Hand[numPlayers];

        // create and shuffle a deck
        cardDeck deck = new cardDeck();
        deck.shuffle();

        routDeck routDeck = new routDeck();
        routDeck.shuffle();

        Board board = new Board();

        board.addCardToCardRow((card)deck.draw());
        board.addCardToCardRow((card)deck.draw());
        board.addCardToCardRow((card)deck.draw());
        board.addCardToCardRow((card)deck.draw());
        board.addCardToCardRow((card)deck.draw());

        for (int i = 0; i < numPlayers; i++) {
            player[i] = new Hand();
            player[i].pickupCards(deck);
            player[i].setPlayerNumber(i+1);
        }

        int playerNum = 0;

        while(true){
            System.out.println("___________________________________________________________________");
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
            System.out.println("The card row has");
            System.out.println(board.printCardRow());
            System.out.println("Player " + (playerNum + 1) + " would you like to:" +
                    "\n1 - Pick up 2 random cards" +
                    "\n2 - Place trains" +
                    "\n3 - Check your points, trains, and routs " +
                    "\n4 - Take a rout card" +
                    "\n5 - Pick up 1 card form the card row");

            while(true) {

                int choice = scan.nextInt();

                if (choice == 1) {
                    player[playerNum].pickupCards(deck);
                    System.out.println("Your new hand " + player[playerNum].toString());
                    break;

                }else if (choice == 2) {
                    System.out.println("Where would you like your train to start?");
                    String input1 = scan.next();

                    // Allows for String inputs
                    int location1 = 0;
                    try{
                        location1 = Integer.parseInt(input1);
                    }catch(NumberFormatException e){
                        location1 = inputReceiver(input1);
                    }

                    System.out.println("Where would you like your train to end?");
                    String input2 = scan.next();
                    int location2 = 0;
                    try{
                        location2 = Integer.parseInt(input2);
                    }catch(NumberFormatException e){
                        location2 = inputReceiver(input2);
                    }
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
                    System.out.println(player[playerNum].checkTrains() + " trains remaining");
                    System.out.println(player[playerNum].toStringRout());
                    System.out.println("What else?");

                } else if (choice == 4) {
                    player[playerNum].pickupRoutCard(routDeck);
                    System.out.println("Your new hand " + player[playerNum].toStringRout());
                    break;
                }else if(choice == 5){
                    System.out.println("What color card do you want");
                    scan.nextLine();
                    String cardColor = scan.nextLine();
                    boolean run = true;
                    for (int i = 0; i < 5; i++){
                        if (board.getCardFromCardRow(i).getColor().equals(cardColor)){
                            player[playerNum].pickupCard(board.getCardFromCardRow(i));
                            board.removeCardFromCardRow(board.getCardFromCardRow(i));
                            board.addCardToCardRow((card)deck.draw());
                            run = false;
                            break;
                        }
                    }
                    if (run) {
                        System.out.println("There is no card with that color, what would you like to do");
                    }else break;
                }  else{

                    System.out.println("Not an option");
                }
            }
            player[playerNum].checkRouts();
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
                System.out.println("Line already built");
                return false;

                // then checks if the player has enough trains left
            } else if(x.getTrains() < railroad.getTrains()){
                System.out.println("You don't have enough trains for that");
                return false;

                // Then checks if the player has enough cards of specified color in their hand
            } else if (railroad.getColor().equals("gray")) {
                return true;
            } else if (x.hasNumCards(railroad.getColor(), railroad.getTrains())) {
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
                System.out.println("Please input the correct the right way, lower numbered location first");
                return false;
        }
    }

    /**
     * This will allow the user to input city names or String
     * versions of numbers and have it still work
     * @param input
     * @return
     */
    public int inputReceiver(String input){
        HashMap<String, Integer> m = new HashMap<>();

        m.put("one",1);
        m.put("One",1);
        m.put("Gary",1);
        m.put("gary",1);

        m.put("Two",2);
        m.put("two",2);
        m.put("Toledo",2);
        m.put("toledo",2);

        m.put("Three",3);
        m.put("three",3);
        m.put("Indianapolis",3);
        m.put("indianapolis",3);

        m.put("Four",4);
        m.put("four",4);
        m.put("Dayton",4);
        m.put("dayton",4);

        m.put("Five",5);
        m.put("five",5);
        m.put("Columbus",5);
        m.put("columbus",5);

        m.put("Six",6);
        m.put("six",6);
        m.put("Cleveland",6);
        m.put("cleveland",6);

        m.put("Seven",7);
        m.put("seven",7);
        m.put("Cincinnati",7);
        m.put("cincinnati",7);

        m.put("Eight",8);
        m.put("eight",8);
        m.put("Charleston",8);
        m.put("charleston",8);

        m.put("Nine",9);
        m.put("nine",9);
        m.put("Erie",9);
        m.put("erie",9);

        m.put("Ten",10);
        m.put("ten",10);
        m.put("Pittsburgh",10);
        m.put("pittsburgh",10);

        try{
            return m.get(input);
        }catch(Exception e) {
            return 0;
        }
    }
}
