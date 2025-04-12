import java.util.Stack;

/**
 * this will be the card deck
 */
public class Deck {

    /**
     * the deck. There will be an equal number of each color in the deck
     */
    private Stack<card> deck = new Stack<>();

    /**
     * this is a list of the colors in the deck
     */
    private String [] colors = {"red", "blue", "green"};

    /**
     * unshuffled deck constructor
     */
    public Deck(){

    }

    /**
     * this will randomize the deck
     */
    public void shuffle(){
        // planning to use my shuffle code from comp 1 with a few modifications
        // though I don't know how that will work with a Stack

        // Actually, we could make it a queue with a discard pile,
        // shuffle the discard pile and add it to the bottom of the
        // queue like people actually do with this game's deck
    }


    /**
     * returns the top two cards and removes them from the deck
     * @return
     */
    public card draw(){
        card stub = new card("Green");
        return stub;
    }

    /**
     * gives how many cards are in the deck
     * @return the number of cards
     */
    public int getRemaining(){
        // returns the number of cards in the deck in case it is low/empty
        return 1;
    }

}
