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
    public Board(){
        // a large portion of the work will be writing code so that
        // the user can input a variety of answers to
        // "Where would you like to place trains?" and still get it to apply.
        line oneTwo = new line("red",2);
        line oneThree = new line("blue",3);
        line twoFour = new line("red",);
        line threeFour = new line("red",2);
        line fourfive = new line("red",2);
        line threeSeven = new line("red",2);
        line fiveSix = new line("red",2);
        line fiveEight = new line("red",2);
        line sevenEight = new line("red",2);
        lines.add(oneTwo);

        this.board = """
                ~~~~~~~~~~~~~~~~~~~~~~~~~~~
                {  1------2          6    } 1-Toledo
                {  |      |          |    } 2-Cleveland
                {  |      |          |    } 3-Dayton
                {  |      |          |    } 4-Columbus
                {  3------4----------5    } 5-Pittsburgh
                {  |                 |    } 6-Erie
                {  |                 |    } 7-Cincinnati
                {  |                 |    } 8-Wheeling
                {  7-----------------8    }
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
        System.out.println(board);
    }

}
