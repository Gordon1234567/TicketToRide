import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;


/**
 * This will contain all the information not just on a player's cards,
 * but on that players other stats as well
 */
public class Hand {


    /**
     * the cards someone has in their hand
     */
   // private ArrayList<card> hand = new ArrayList<>();
   // Trying to make hand a hashMap
    private HashMap<String,Integer> hand = new HashMap<>();

    /**
     * the points accumulated
     */
    private int points = 0;

    /**
     * the trains remaining to a player
     */
    private int trains = 15;

    /**
     * will hold what player number they are
     */
    private int playerNumber;

    /**
     * creates an empty hand
     */
    public Hand(){

    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    /**
     * This adds 2 cards into the players hand
     */
    public void pickupCards(Deck theDeck){
        card one = theDeck.draw();
        int currentCount = hand.getOrDefault(one.getColor(),0);
        hand.put(one.getColor(),currentCount + 1);
        card two = theDeck.draw();
        int currentCount2 = hand.getOrDefault(two.getColor(),0);
        hand.put(two.getColor(),currentCount2 + 1);
    }

    /**
     * will remove a specified number of cards of the specified color
     * will add points to the player
     * will then subtract number of cards needed from trains
     */
    public void placeTrains(line x) {

        int cardsRemoved = x.getTrains();

        // Removes the first instance of a card of a specific color the specified number of times
        for (int i = 0; i < cardsRemoved; i++) {
            hand.remove(x.getColor());
        }
        points = points + x.getPoints();
        trains = trains - x.getTrains();
    }

    /**
     * this returns the players number of points
     * @return the number of points
     */
    public int getPoints(){
        return points;
    }

    /**
     * this gets the number of trains the player has
     * @return the number of trains
     */
    public int getTrains(){
        return trains;
    }

    /**
     * Shows how many points they have
     */
    public int checkPoints(){
        return points;
    }

    /**
     * Check how many trains they have
     */
    public int checkTrains(){
        return trains;
    }

    /**
     * this gives the players hand
     * @return a ArrayList of cards for the players hand
     */
    public HashMap<String,Integer> getHand() {
        return hand;
    }

    public String toString() {
        StringBuilder print = new StringBuilder();
        for ( String key : hand.keySet() ) {
            print.append("You have " + hand.get(key) + " " + key + ", ");
        }
        print.deleteCharAt(print.length()-1);
        print.deleteCharAt(print.length()-1);
        return print.toString();

    }

    // this will tell if a hand contains the specified number of a specific color of card
    // But I can't get it working
    public boolean hasNumCards(String color, int frequency) {
        return hand.getOrDefault(color, 0) >= frequency;
    }
}
