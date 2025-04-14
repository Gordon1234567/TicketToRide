import java.util.ArrayList;
import java.util.HashSet;

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

    private HashSet<line> lines = new HashSet<>(5);
    // ^This may work as a hashSet^ instead of an arrayList

    /**
     * This will create a Board using a given ArrayList of lines
     * @param lineSet is the set of lines
     */
    private Board(HashSet<line> lineSet){
        lines = lineSet;
        // 25 width in white spaces
        this.board = """
                ~~~~~~~~~~~~~~~~~~~~~~~~~~~
                {                         }\s
                {                         }\s
                {                         }
                {                         }
                {                         }
                {                         }""";
    }

    /**
     * This will check if a line is full
     * @param l is the line we want to check
     * @return if the line is full
     */
    public boolean isLineFull(line l){
        return l.getFull();
    }

    /**
     * This will print the Board to the console
     */
    public void printBoard(){

    }
}
