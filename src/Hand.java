import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Predicate;


/**
 * This will contain all the information not just on a player's cards and routs,
 * but on that players other stats as well
 */
public class Hand {

    /**
     * the cards someone has in their hand
     */
    private HashMap<String,Integer> hand = new HashMap<>();
    /**
     * the routs someone has
     */
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

    /**
     * This will keep track of what player trains are connected
     */
    private ArrayList<HashSet<Integer>> groups = new ArrayList<>();

    /**
     * creates an empty hand
     */
    public Hand(){

    }

    /**
     * sets up number of every card to be zero
     */
    public void setUpHand(){
        hand.put("red",0);
        hand.put("blue", 0);
        hand.put("green",0);
    }

    /**
     *
     * @return the total number of cards a player has
     */
    public int getTotalCards(){
        return hand.get("red") + hand.get("blue") + hand.get("green");
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;

    }

    /**
     * This takes a HashSet of a tracks destinations in the form of number, it then compares it to the hash sets the player already has,
     * if there is a common number(location of board) it will combine the Hash Sets, it will then check the combined has set to all the other hash sets the player has,
     * by the end every hash set in groups are all unique numbers in each, and so we know every number(location on board) that trains are connecting
     * @param group is the end points of the line that the player has placed on the board
     */
    public void addGroup(HashSet<Integer> group) {
        //if there are no hash sets we can just add the new one without checking anything
        if (groups.isEmpty()){
            groups.add(group);
        }else {
            //this will keep track of the hash sets that have been combined with another one and so we can delete
            ArrayList<HashSet<Integer>> delete = new ArrayList<>();
            //this is the combined hash set of all locations that are connected
            HashSet<Integer> combinedGroup = group;
            //looping though hash sets checking if any should be combined
            for (int i = 0; i < groups.size(); i++){
                HashSet<Integer> a = groups.get(i);
                //checks if the hash sets have a similar number(location)
                if (hasSame(a,combinedGroup)){
                    //if do have same location combined the two together
                    combinedGroup = setSame(a,combinedGroup);
                    //deleting the group that was combined with
                    delete.add(groups.get(i));
                }
            }

            for (int i = 0; i < delete.size(); i++){
                groups.remove(delete.get(i));
            }
            //adding the combined hash sets to the group, now we know every hash set is unique form the others and every number(location in each hash set are connected on the map by the players trains)
            groups.add(combinedGroup);
        }

    }

    /**
     *
     * @param a is a hash set
     * @param b is a hash set
     * @return if the two have a similar number
     */
    public boolean hasSame(HashSet<Integer> a, HashSet<Integer> b){
        for (Integer e : a){
            if (b.contains(e)){
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param a is a hash set
     * @param b is a hash set
     * @return is the combined hash set of the two
     */
    public HashSet<Integer> setSame(HashSet<Integer> a, HashSet<Integer> b) {
        for (Integer e : a){
            if (!b.contains(e)){
                b.add(e);
            }
        }
        return b;
    }

    /**
     * gives the player a rout card
     * @param theDeck form the rout card deck
     */
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

            hand.put(x.getColor(), hand.get(x.getColor()) - cardsRemoved);

            points = points + x.getPoints();
            trains = trains - x.getTrains();
        }else {
            hand.put(x.getColorForGray(), hand.get(x.getColorForGray()) - 1);
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

    /**
     *
     * @return the players cards in a string
     */
    public String toString() {
        StringBuilder print = new StringBuilder();
        for ( String key : hand.keySet() ) {
            print.append("You have " + hand.get(key) + " " + key + ", ");
        }
        print.deleteCharAt(print.length()-1);
        print.deleteCharAt(print.length()-1);
        return print.toString();

    }

    /**
     *
     * @return the players routs
     */
    public String toStringRout(){
        StringBuilder print = new StringBuilder();
        for (int i = 0; i < routs.size(); i++) {
            print.append("You have rout from " + routs.get(i).getPoint1() + " to " + routs.get(i).getPoint2() + "\n" );
        }

        return print.toString();
    }

    /**
     * this checks if any routs have been completed
     */
    public void checkRouts(){
        //is the rout cards that have been completed and should be deleted
        ArrayList<rout> delete = new ArrayList<>();
        //looping through the players routs
        for (int i = 0; i < routs.size(); i++){
            //checking each hash map if the two end points of the rout are contained
            for (int j = 0; j < groups.size(); j++){
                if (groups.get(j).contains(routs.get(i).getPoint1()) && groups.get(j).contains(routs.get(i).getPoint2())){
                    System.out.println("You have completed your rout form " + routs.get(i).getPoint1() + " to " + routs.get(i).getPoint2() + " gaining " + routs.get(i).getPoints() + "  points");
                    //giving player points for completing the routs
                    points+=routs.get(i).getPoints();
                    delete.add(routs.get(i));
                }
            }
        }
        for (int i = 0; i < delete.size(); i++){
            routs.remove(delete.get(i));
        }
    }

    /**
     *
     * @param color is the color of cards we are checking
     * @param frequency is the amount of that color
     * @return if the player has enough of a certain color to place a train
     */
    public boolean hasNumCards(String color, int frequency) {
        return hand.getOrDefault(color, 0) >= frequency;
    }


}
