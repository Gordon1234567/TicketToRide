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

    private ArrayList<line> lines = new ArrayList<>(7);
    // ^This may work as a hashSet^ instead of an arrayList

    /**
     * This will create a Board using a given ArrayList of lines
     * @param lineSet is the set of lines
     */
    private Board(ArrayList<line> lineSet){
        lines = lineSet;

        this.board = """
                ~~~~~~~~~~~~~~~~~~~~~~~~~~~
                {  1------2          6    }
                {  |      |          |    }
                {  |      |          |    }
                {  |      |          |    }
                {  3------4----------5    }
                {         |               }
                {         |               }
                {         |               }
                {         6               }
                ~~~~~~~~~~~~~~~~~~~~~~~~~~~""";
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
