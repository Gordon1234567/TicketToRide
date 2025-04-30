import java.util.Random;

public class cardDeck extends Deck {
    Random rand = new Random();

    /**
     * this is a list of the colors in the deck
     */
    private String [] colors = {"red", "blue", "green"};

    /**
     * unshuffled deck constructor
     */
    public cardDeck(){
        for (int i = 0; i < 30; i++) {
            deck.add(new card(colors[0]));
        }

        for (int i = 0; i < 30; i++) {
            deck.add(new card(colors[1]));
        }

        for (int i = 0; i < 30; i++) {
            deck.add(new card(colors[2]));
        }
    }
}
