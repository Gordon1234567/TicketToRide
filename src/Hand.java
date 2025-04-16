import java.util.ArrayList;


/**
 * This will contain all the information not just on a player's cards,
 * but on that players other stats as well
 */
public class Hand {


    /**
     * the cards someone has in their hand
     */
    private ArrayList<card> hand = new ArrayList<>();

    /**
     * the points accumulated
     */
    private int points = 0;

    /**
     * the trains remaining to a player
     */
    private int trains = 15;

    /**
     * creates an empty hand
     */
    public Hand(){

    }

    /**
     * This adds 2 cards into the players hand
     */
    public void pickupCards(Deck theDeck){
        hand.add(theDeck.draw());
        hand.add(theDeck.draw());
    }

    /**
     * will remove a specified number of cards of the specified color
     * will likely throw an exception if those cards aren't in hand
     * will then subtract number of cards needed from trains
     */
    public void placeTrains(){
    }

    /**
     * this adds points to the player
     */
    public void addPoints(){
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
    public ArrayList<card> getHand() {
        return hand;
    }

    public String toString() {

        ArrayList<String> cards = new ArrayList<>();

        for (int i = 0; i < hand.size(); i++) {
            cards.add(hand.get(i).getColor());
        }

        return cards.toString();
    }
}
