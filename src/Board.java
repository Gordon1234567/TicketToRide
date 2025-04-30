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
    line A;
    line B;
    line C;
    line D;
    line E;
    line F;
    line G;
    line H;
    line I;

    /**
     * A record of the colored cards being used
     */
    private card [] assignedCard = {new card("red"), new card("blue"),new card ("green")};

    /**
     * lines will hold all the lines on the board
     */

    //private HashMap<Integer,line> lines = new HashMap<>(7);
    // ^This may work as a hashSet^ instead of an arrayList
    private ArrayList<line> lines = new ArrayList<>();

    /**
     * This will create a Board using a given ArrayList of lines
     */
    public Board(){
        // a large portion of the work will be writing code so that
        // the user can input a variety of answers to
        // "Where would you like to place trains?" and still get it to apply.
        A = new line(assignedCard[0].getColor(),2,1,2);
        B = new line(assignedCard[1].getColor(),4,1,3);
        C = new line(assignedCard[2].getColor(),3,2,4);
        D = new line(assignedCard[0].getColor(),1,3,4);
        E = new line(assignedCard[1].getColor(),5,4,5);
        F = new line(assignedCard[2].getColor(),2,3,7);
        G = new line(assignedCard[2].getColor(),3,6,5);
        H = new line(assignedCard[2].getColor(),1,5,8);
        I = new line(assignedCard[0].getColor(),6,7,8);
        lines.add(A);
        lines.add(B);
        lines.add(C);
        lines.add(D);
        lines.add(E);
        lines.add(F);
        lines.add(G);
        lines.add(H);
        lines.add(I);

    }

    /**
     * This will print the Board to the console
     */
    public void printBoard(){
        // The ASCII board that will change whenever a line is filled
        System.out.println(
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
                        "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }

    /**
     * retrieves the line so you can use line methods
     * @param l
     * @return
     */
    public line getLine(int l){
        return lines.get(l);
    }

    public int getLength(){
        return lines.size();
    }

    public void playTrains(Hand x, line railroad){
        x.placeTrains(railroad);
        railroad.setFull();
        railroad.setOwner(x.getPlayerNumber());
    }
}
