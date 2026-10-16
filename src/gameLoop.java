import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class gameLoop {


    public gameLoop(){

    }

    /**
     * This is the main method running the game
     * intakes the number of players then makes a deck
     * runs through each players turn, first printing the board
     * then asking them which option they would like to use
     * with taking cards and placing trains ending their turn
     * when the deck runs out a new one is reshuffled in.
     * this loop continues until a player runs out of trains.
     */
    public void loop() {
        Scanner scan = new Scanner(System.in);

        // Set how many players there will be
        System.out.println("Welcome rail tycoon to Ticket To Ride!");
        System.out.println("The Ohio valley is fit to be the industrial heartland of the country, it's up to you to bring the steam and steel to this land");
        System.out.println();
        System.out.println("How many capitalists will be playing? 1-3 players allowed");

        int numPlayers = 0;
        boolean going = true;
        while (going) {
            going = false;
            try {

                numPlayers = scan.nextInt();
                if (numPlayers < 1 || numPlayers > 3){
                    System.out.println("Not an option");
                    going = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Not an option");
                going = true;
                scan.nextLine();
            }

        }
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
            player[i].setUpHand();
            player[i].pickupCards(deck);
            player[i].setPlayerNumber(i+1);
        }

        int playerNum = 0;

        while(true){
            System.out.println("___________________________________________________________________");
            board.printBoard();

            if(player[playerNum].getTrains() == 0 || !canAnyoneBuild(board, player)){
                if (player[playerNum].getTrains() == 0) {
                    System.out.println("The game has ended because " + (playerNum + 1) + " has no more trains");
                } else {
                    System.out.println("The game has ended because no player has enough trains for an unclaimed line");
                }
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

            System.out.println("\nPlayer " +(playerNum + 1) + "s turn");
            System.out.println(player[playerNum].toString());

            System.out.println("\nwould you like to:" +
                    "\n1 - Pick up 2 random cards" +
                    "\n2 - Place trains" +
                    "\n3 - Check your points, trains, and routs " +
                    "\n4 - Take a rout card" +
                    "\n5 - Pick up 1 card from the card row");

            while(true) {
                int choice = 10;
                boolean go = true;
                while (go) {
                    go = false;
                    try {

                        choice = scan.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("Not an option");
                        go = true;
                        scan.nextLine();
                    }
                }

                if (choice == 1) {
                    if (deck.getRemaining() >= 2 ) {
                        player[playerNum].pickupCards(deck);
                        System.out.println(player[playerNum].toString());
                        break;
                    }else{
                        System.out.println("There are no more cards in the deck, what would you like to do");
                        System.out.println("\nwould you like to:" +
                                "\n1 - Pick up 2 random cards" +
                                "\n2 - Place trains" +
                                "\n3 - Check your points, trains, and routs " +
                                "\n4 - Take a rout card" +
                                "\n5 - Pick up 1 card form the card row");
                    }

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
                    scan.nextLine();
                    int location2 = 0;
                    try{
                        location2 = Integer.parseInt(input2);
                    }catch(NumberFormatException e){
                        location2 = inputReceiver(input2);
                    }
                    line a = findLine(board, location1, location2);
                    if (a == null) {
                        System.out.println("Choose two different cities joined directly on the board.");
                        System.out.println("\nwould you like to:" +
                                "\n1 - Pick up 2 random cards" +
                                "\n2 - Place trains" +
                                "\n3 - Check your points, trains, and routs " +
                                "\n4 - Take a rout card" +
                                "\n5 - Pick up 1 card from the card row");
                        continue;
                    }

                    boolean gotPlayed = playTrain(a , player[playerNum], board, scan);
                    if (gotPlayed) {break;}

                } else if (choice == 3) {
                    System.out.println(player[playerNum].checkPoints() + " points");
                    System.out.println(player[playerNum].getTrains() + " trains remaining");
                    System.out.println(player[playerNum].toStringRout());
                    System.out.println("What else?");

                } else if (choice == 4) {
                    if (player[playerNum].howManyRoutCards() == 3){
                        System.out.println("You can only have 3 rout cards at a time");
                        System.out.println("\nwould you like to:" +
                                "\n1 - Pick up 2 random cards" +
                                "\n2 - Place trains" +
                                "\n3 - Check your points, trains, and routs " +
                                "\n4 - Take a rout card" +
                                "\n5 - Pick up 1 card form the card row");
                    } else if (routDeck.getRemaining() == 0) {
                        System.out.println("There are no more rout cards");
                        System.out.println("\nwould you like to:" +
                                "\n1 - Pick up 2 random cards" +
                                "\n2 - Place trains" +
                                "\n3 - Check your points, trains, and routs " +
                                "\n4 - Take a rout card" +
                                "\n5 - Pick up 1 card form the card row");
                    } else {
                        player[playerNum].pickupRoutCard(routDeck);
                        System.out.println("Your new hand " + player[playerNum].toStringRout());
                        break;
                    }
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
                        System.out.println("\nwould you like to:" +
                                "\n1 - Pick up 2 random cards" +
                                "\n2 - Place trains" +
                                "\n3 - Check your points, trains, and routs " +
                                "\n4 - Take a rout card" +
                                "\n5 - Pick up 1 card form the card row");
                    }else break;
                }  else{

                    System.out.println("Not an option");
                }
            }
            player[playerNum].checkRouts();
            if(playerNum < numPlayers-1){
                playerNum++;
            } else { playerNum = 0;}

            if(deck.getRemaining() < 2){
                cardDeck newDeck = new cardDeck();
                newDeck.shuffle();
                deck = newDeck;
            }
        }

    }

    /**
     * This will be given the line that the player wants to play, it will try to play trains there
     * @param railroad is the line the player wants to play on
     */
    public boolean playTrain(line railroad, Hand x, Board board) {
        return playTrain(railroad, x, board, new Scanner(System.in));
    }

    // Reuse the turn's scanner so it cannot buffer input away from color prompts.
    private boolean playTrain(line railroad, Hand x, Board board, Scanner scan) {
        /**
         * This is a boolean so that if the player doesn't meet
         * the requirements for the line chosen, the player's turn
         * will continue to loop.
         */

            // first checks if the line is full
            if (railroad == null || railroad.getColor() == null) {
                System.out.println("Choose two different cities joined directly on the board.");
                return false;
            } else if (railroad.getFull()) {
                System.out.println("Line already built");
                return false;

                // then checks if the player has enough trains left
            } else if(x.getTrains() < railroad.getTrains()){
                System.out.println("You don't have enough trains for that");
                return false;

                // Then checks if the player has enough cards of specified color in their hand
            } else if (railroad.getColor().equals("&") && x.getTotalCards() >= railroad.getTrains()) {
                System.out.println("choose what colors you will use for the & track you need to choose " + railroad.getTrains() + " cards");
                String color="";
                for (int i = 0; i < railroad.getTrains(); i++){
                    System.out.println("what card do you want to use: " + "[" + (i+1) + "-"+railroad.getTrains() + "]");
                    System.out.println(x.toString());
                    boolean runfast = true;
                    while (runfast) {
                        runfast = false;

                        color = scan.next();
                        if (!color.equals("red") && !color.equals("blue") && !color.equals("green")){
                            System.out.println("that is not a color try again");
                            runfast = true;
                        } else if (x.getHand().get(color) == 0) {
                            System.out.println("You don't have that color card");
                            runfast = true;
                        }
                    }
                    railroad.setColorForGray(color);
                    board.playTrains(x, railroad);
                }
                x.addGroup(railroad.getLocations());
                x.addPoints(railroad.getPoints());
                return true;
            } else if (x.hasNumCards(railroad.getColor(), railroad.getTrains())) {
                board.playTrains(x, railroad);
                x.addGroup(railroad.getLocations());
                System.out.println("That's "+ railroad.getPoints() + " points");
                return true;

            } else {
                System.out.println("You don't have the required cards");
                return false;
            }
    }

    /** Finds a direct track between two distinct, valid cities. */
    public line findLine(Board board, int start, int end) {
        if (start < 1 || start > 10 || end < 1 || end > 10 || start == end) {
            return null;
        }
        for (int i = 0; i < board.getLength(); i++) {
            line track = board.getLine(i);
            if (track.getLocations().contains(start) && track.getLocations().contains(end)) {
                return track;
            }
        }
        return null;
    }

    /** Checks train capacity, since players can continue drawing missing cards. */
    public boolean canAnyoneBuild(Board board, Hand[] players) {
        for (int i = 0; i < board.getLength(); i++) {
            line track = board.getLine(i);
            if (!track.getFull()) {
                for (Hand player : players) {
                    // Cards can still be drawn; remaining trains determine feasibility.
                    if (player.getTrains() >= track.getTrains()) return true;
                }
            }
        }
        return false;
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
        m.put("1",1);

        m.put("Two",2);
        m.put("two",2);
        m.put("Toledo",2);
        m.put("toledo",2);
        m.put("2",2);

        m.put("Three",3);
        m.put("three",3);
        m.put("Indianapolis",3);
        m.put("indianapolis",3);
        m.put("3",3);

        m.put("Four",4);
        m.put("four",4);
        m.put("Dayton",4);
        m.put("dayton",4);
        m.put("4",4);

        m.put("Five",5);
        m.put("five",5);
        m.put("Columbus",5);
        m.put("columbus",5);
        m.put("5",5);

        m.put("Six",6);
        m.put("six",6);
        m.put("Cleveland",6);
        m.put("cleveland",6);
        m.put("6",6);

        m.put("Seven",7);
        m.put("seven",7);
        m.put("Cincinnati",7);
        m.put("cincinnati",7);
        m.put("7",7);

        m.put("Eight",8);
        m.put("eight",8);
        m.put("Charleston",8);
        m.put("charleston",8);
        m.put("8",8);

        m.put("Nine",9);
        m.put("nine",9);
        m.put("Erie",9);
        m.put("erie",9);
        m.put("9",9);

        m.put("Ten",10);
        m.put("ten",10);
        m.put("Pittsburgh",10);
        m.put("pittsburgh",10);
        m.put("10",10);

        try{
            return m.get(input);
        }catch(Exception e) {
            return 0;
        }
    }
}
