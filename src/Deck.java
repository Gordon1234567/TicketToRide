import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

/**
 * this will be the card deck
 */
public class Deck {

    Random rand = new Random();
    /**
     * the deck. There will be an equal number of each color in the deck
     */
    protected Stack<cardInterface> deck = new Stack<>();


    /**
     * this will randomize the deck
     */
    public void shuffle(){

        for (int i = 0; i <= deck.size(); i++) {
                int randInta = rand.nextInt(0, deck.size());
                int randIntb = rand.nextInt(0, deck.size());
                swap(randInta, randIntb);
            }
    }



    /**
     * returns the top card and removes it from the deck
     * @return
     */
    public cardInterface draw(){
        // returns the top card and removes it from the deck
        cardInterface c = deck.getFirst();
        deck.removeFirst();
        return c;
    }

    /**
     * gives how many cards are in the deck
     * @return the number of cards
     */
    public int getRemaining(){
        return deck.size();
    }

    //swaps 2 cards in this.cards
    public void swap(int index1, int index2) {
        cardInterface temp = deck.get(index1);
        // this.cards[index1] = this.card[index2];
        deck.set(index1,deck.get(index2));
        // this.cards[index2] = temp
        deck.set(index2, temp);
    }

}
