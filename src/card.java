/**
 * I think this class is done
 *
 * This class will make cards, each can be a different color
 */
public class card {
    /**
     * color is the color of the card
     */
    private String color;

    /**
     * This makes a card setting the color
     * @param Color is the color
     */
    public card(String Color){
        color = Color;
    }

    /**
     * This gives the color of the card
     * @return the color
     */
    public String getColor(){
        return color;
    }
}
