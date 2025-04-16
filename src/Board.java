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
        line oneThree = new line("blue",4);
        line twoFour = new line("green",3);
        line threeFour = new line("red",1);
        line fourFive = new line("blue",5);
        line threeSeven = new line("green",2);
        line fiveSix = new line("green",3);
        line fiveEight = new line("green",1);
        line sevenEight = new line("red",6);
        lines.add(oneTwo);
        lines.add(oneThree);
        lines.add(twoFour);
        lines.add(threeFour);
        lines.add(fourFive);
        lines.add(threeSeven);
        lines.add(fiveSix);
        lines.add(fiveEight);
        lines.add(sevenEight);


        this.board =
                "~~~~~~~~~~~~~~~~~~~~~~~~~~~" +
                "\n{  1--" + oneTwo.toString() + "--2          6    } 1-Toledo" +
                "\n{  |      |          |    } 2-Cleveland" +
                "\n{ " + oneThree.toString() + "     " + twoFour.toString() + "         " + fiveSix.toString() + "    } 3-Dayton" +
                "\n{  |      |          |    } 4-Columbus" +
                "\n{  3--" + threeFour.toString() + "--4----" + fourFive.toString() + "----5    } 5-Pittsburgh" +
                "\n{  |                 |    } 6-Erie" +
                "\n{ " + threeSeven.toString() + "                " + fiveEight.toString() + "    } 7-Cincinnati" +
                "\n{  |                 |    } 8-Wheeling" +
                "\n{  7-------" + sevenEight.toString() + "--------8    }" +
                "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~";
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
