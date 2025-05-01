import java.util.HashSet;

/**
 * This will make a line witch will keep track of what color and how many trains are needed to build a line.
 * It also keeps track of the lines point value, and if the line has been played yet.
 */
public class line {

    /**
     * The color of cards needed
     */
    private String color;

    private String colorForGray;
    /**
     * the number of cards needed
     */
    private int trains;
    /**
     * the number of points the line is worth
     */
    private int points;
    /**
     * whether the line has been filled or not
     */
    private boolean full;
    /**
     * tells which player owns the train
     */
    private int owner;

    private HashSet<Integer> locations = new HashSet<>();

    public line(){

    }

    public void setColorForGray(String colorForGray) {
        this.colorForGray = colorForGray;
    }

    public String getColorForGray() {
        return colorForGray;
    }

    /**
     * This will make a line with a color and number of trains needed to build it.
     * It also calculates the number of points the line is worth based on the length.
     * @param Color is the color card needed to build the line
     * @param Trains is the number of trains needed to build the line
     */
    public line(String Color, int Trains, int first, int second){
        color = Color;
        trains = Trains;
        if(Trains == 1){points = 1;}
        else if(Trains == 2){points = 2;}
        else if(Trains == 3){points = 4;}
        else if(Trains == 4){points = 7;}
        else if(Trains == 5){points = 10;}
        else if(Trains == 6){points = 15;}
        full = false;
        locations.add(first);
        locations.add(second);
    }

    public HashSet<Integer> getLocations(){
        return locations;
    }

    /**
     * This gives the number of trains needed to build the line
     * @return the number of trains needed
     */
    public int getTrains(){
        return trains;
    }

    /**
     * This gives the number of points a line is worth
     * @return the number of points
     */
    public int getPoints(){
        return points;
    }

    /**
     * Returns the color needed to build the line
     * @return the color
     */
    public String getColor(){
        return color;
    }

    /**
     * returns if a line is full
     * @return if it's full
     */
    public boolean getFull(){
        return full;
    }

    /**
     * This sets if the line is full
     */
    public void setFull(){
        full = true;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public String toString() {
        char firstChar = color.charAt(0);
        if(full) {
            return ("P" + owner);
            // I want to make this show the player occupying the space
        }else{
            return ("" + trains + firstChar);
        }
    }
}
