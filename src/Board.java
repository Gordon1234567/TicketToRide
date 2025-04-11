import java.util.ArrayList;

/**
 * This class will print the board to the user,
 * It will take in a ArrayList of lines and use that to construct the board
 */
public class Board {
    /**
     * board will keep track of what the bard looks like for the user
     */
    private String board;
    /**
     * lines will hold all the lines on the board
     */
    private ArrayList<line> lines;

    /**
     * This will create a Board using a given ArrayList of lines
     * @param lines is the list of lines
     */
    private Board(ArrayList<line> lines){

    }

    /**
     * This will check if a line is full
     * @param l is the line we want to check
     * @return if the line is full
     */
    public boolean isLineFull(line l){
        return true;
    }

    /**
     * This will print the Board to the console
     */
    public void printBoard(){

    }
}
