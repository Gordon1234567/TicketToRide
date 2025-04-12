import java.util.Stack;

public class Deck {

    // the deck. There will be an equal number of each color in the deck
    private Stack<card> deck = new Stack<>();

    private String [] colors = {"red", "blue", "green"};

    public Deck(){
        // unshuffled deck constructor
    }

    public void shuffle(){
        // planning to use my shuffle code from comp 1 with a few modifications
        // though I don't know how that will work with a Stack

        // Actually, we could make it a queue with a discard pile,
        // shuffle the discard pile and add it to the bottom of the
        // queue like people actually do with this game's deck
    }

    public card draw(){
        // returns the top two cards and removes them from the deck
        card stub = new card("Green");
        return stub;
    }

    public int getRemaining(){
        // returns the number of cards in the deck in case it is low/empty
        return 1;
    }

}
