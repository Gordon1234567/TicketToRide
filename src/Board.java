import java.util.ArrayList;
import java.util.HashMap;
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
     * A record of the colored cards being used
     */
    private card [] assignedCard = {new card("red"), new card("blue"),new card ("green")};

    /**
     * lines will hold all the lines on the board
     */

    private HashMap<Integer,line> lines = new HashMap<>(7);
    // ^This may work as a hashSet^ instead of an arrayList

    /**
     * This will create a Board using a given ArrayList of lines
     */
    public Board(){
        // a large portion of the work will be writing code so that
        // the user can input a variety of answers to
        // "Where would you like to place trains?" and still get it to apply.
        line A = new line(assignedCard[0].getColor(),2);
        line B = new line(assignedCard[1].getColor(),4);
        line C = new line(assignedCard[2].getColor(),3);
        line D = new line(assignedCard[0].getColor(),1);
        line E = new line(assignedCard[1].getColor(),5);
        line F = new line(assignedCard[2].getColor(),2);
        line G = new line(assignedCard[2].getColor(),3);
        line H = new line(assignedCard[2].getColor(),1);
        line I = new line(assignedCard[0].getColor(),6);
        lines.put(12,A);
        lines.put(13,B);
        lines.put(24,C);
        lines.put(34,D);
        lines.put(45,E);
        lines.put(37,F);
        lines.put(56,G);
        lines.put(58,H);
        lines.put(78,I);

        // The ASCII board that will change whenever a line is filled
        this.board =
                "~~~~~~~~~~~~~~~~~~~~~~~~~~~" +
                "\n{  (1)--" + A.toString() + "--(2)          (6)   } 1-Toledo" +
                "\n{   |        |            |    } 2-Cleveland" +
                "\n{  " + B.toString() + "       " + C.toString() + "           " + G.toString() + "    } 3-Dayton" +
                "\n{   |        |            |    } 4-Columbus" +
                "\n{  (3)--" + D.toString() + "--(4)----" + E.toString() + "----(5)   } 5-Pittsburgh" +
                "\n{   |                     |    } 6-Erie" +
                "\n{  " + F.toString() + "                     " + H.toString() + "   } 7-Cincinnati" +
                "\n{   |                     |    } 8-Wheeling" +
                "\n{  (7)-------" + I.toString() + "----------(8)   }" +
                "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~";
    }

    /**
     * This will print the Board to the console
     */
    public void printBoard(){
        // Ugh! I can't figure out why it isn't printing out the new board, it's updating the lines to full properly
        System.out.println(board);
    }

    /**
     * retrieves the line so you can use line methods
     * @param l
     * @return
     */
    public line getLine(int l){
        return lines.get(l);
    }

    public void playTrains(Hand x, int l){
        x.placeTrains(lines.get(l));
        lines.get(l).setFull();
        lines.get(l).setOwner(x.getPlayerNumber());
    }
}
