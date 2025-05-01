import java.lang.reflect.Array;
import java.util.*;
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
    private ArrayList<rout> routs = new ArrayList<>();


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

    private ArrayList<HashSet<Integer>> groups = new ArrayList<>();

    /**
     * creates an empty hand
     */
    public Hand(){

    }

    public int getTotalCards(){
        return hand.get("red") + hand.get("blue") + hand.get("green");
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public void addGroup(HashSet<Integer> group) {
        if (groups.isEmpty()){
            groups.add(group);
        }else {
            ArrayList<HashSet<Integer>> delete = new ArrayList<>();
            HashSet<Integer> combinedGroup = group;
            for (int i = 0; i < groups.size(); i++){
                HashSet<Integer> a = groups.get(i);
                if (hasSame(a,combinedGroup)){
                    combinedGroup = setSame(a,combinedGroup);
                    delete.add(groups.get(i));
                }
            }
            for (int i = 0; i < delete.size(); i++){
                groups.remove(delete.get(i));
            }
            groups.add(combinedGroup);
        }

    }


    public boolean hasSame(HashSet<Integer> a, HashSet<Integer> b){
        for (Integer e : a){
            if (b.contains(e)){
                return true;
            }
        }
        return false;
    }

    public HashSet<Integer> setSame(HashSet<Integer> a, HashSet<Integer> b) {
        for (Integer e : a){
            if (!b.contains(e)){
                b.add(e);
            }
        }
        return b;
    }

    public void pickupRoutCard(routDeck theDeck){
        rout one = (rout) theDeck.draw();
        routs.add(one);
    }
    public int howManyRoutCards(){
        return routs.size();
    }

    /**
     * This adds 2 cards into the players hand
     */
    public void pickupCards(cardDeck theDeck){
        if(theDeck.getRemaining() == 0 || theDeck.getRemaining() == 1){
            System.out.println("No more Cards in the deck");
        }else {
            card one = (card) theDeck.draw();
            int currentCount = hand.getOrDefault(one.getColor(), 0);
            hand.put(one.getColor(), currentCount + 1);
            card two = (card) theDeck.draw();
            int currentCount2 = hand.getOrDefault(two.getColor(), 0);
            hand.put(two.getColor(), currentCount2 + 1);
        }
    }

    public void pickupCard(card a){
        int currentCount = hand.getOrDefault(a.getColor(), 0);
        hand.put(a.getColor(), currentCount + 1);
    }

    public void addPoints(int i){
        points+=i;
    }
    /**
     * will remove a specified number of cards of the specified color
     * will add points to the player
     * will then subtract number of cards needed from trains
     */
    public void placeTrains(line x) {

        if (!x.getColor().equals("&")) {
            int cardsRemoved = x.getTrains();

            // Removes the first instance of a card of a specific color the specified number of times
            // It's removing all of that color right now

            hand.put(x.getColor(), hand.get(x.getColor()) - cardsRemoved);

            points = points + x.getPoints();
            trains = trains - x.getTrains();
        }else {
            hand.put(x.getColor(), hand.get(x.getColor()) - 1);
        }
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

    public String toStringRout(){
        StringBuilder print = new StringBuilder();
        for (int i = 0; i < routs.size(); i++) {
            print.append("You have rout from " + routs.get(i).getPoint1() + " to " + routs.get(i).getPoint2() + "\n" );
        }

        return print.toString();
    }

    public void checkRouts(){
        ArrayList<rout> deleate = new ArrayList<>();
        for (int i = 0; i < routs.size(); i++){
            for (int j = 0; j < groups.size(); j++){
                if (groups.get(j).contains(routs.get(i).getPoint1()) && groups.get(j).contains(routs.get(i).getPoint2())){
                    System.out.println("You have completed your rout form " + routs.get(i).getPoint1() + " to " + routs.get(i).getPoint2() + " gaining " + routs.get(i).getPoints() + "  points");
                    points+=routs.get(i).getPoints();
                    deleate.add(routs.get(i));
                }
            }
        }
        for (int i = 0; i < deleate.size(); i++){
            routs.remove(deleate.get(i));
        }
    }

    // this will tell if a hand contains the specified number of a specific color of card
    // But I can't get it working
    public boolean hasNumCards(String color, int frequency) {
        return hand.getOrDefault(color, 0) >= frequency;
    }


}
