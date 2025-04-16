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

    private HashSet<line> lines = new HashSet<>(7);
    // ^This may work as a hashSet^ instead of an arrayList

    /**
     * This will create a Board using a given ArrayList of lines
     */
    private Board(){
        lines.add(new line("red",2));

        this.board = """
                ~~~~~~~~~~~~~~~~~~~~~~~~~~~
                {  1------2          6    } 1-Toledo
                {  |      |          |    } 2-Cleveland
                {  |      |          |    } 3-Dayton
                {  |      |          |    } 4-Columbus
                {  3------4----------5    } 5-Pittsburgh
                {  |                      } 6-Erie
                {  |                      } 7-Cincinnati
                {  |                      }
                {  7                      }
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
