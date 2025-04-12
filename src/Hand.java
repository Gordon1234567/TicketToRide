import java.util.ArrayList;

public class Hand {

    /**
     * This will contain all the information not just on a player's cards,
     * but on that players other stats as well
     */
    // the cards someone has in their hand
    private ArrayList<card> hand = new ArrayList<>();

    // the points accumulated
    private int points = 0;

    // the trains remaining to a player
    private int trains = 15;

    public Hand(){
    // creates an empty hand
    }

    public void pickupCards(card one,card two ){
        hand.add(one);
        hand.add(two);
    }

    public void placeTrains(){
        // will remove a specified number of cards of the specified color
        // will likely throw an exception if those cards aren't in hand
        // will then subtract number of cards needed from trains
    }

    public void addPoints(){
    }

    public int getPoints(){
        return points;
    }

    public int getTrains(){
        return trains;
    }

    public ArrayList<card> getHand() {
        return hand;
    }


}
