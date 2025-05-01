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
    line J;
    line K;
    line L;
    line M;

    private ArrayList<card> cardRow = new ArrayList<>();

    /**
     * A record of the colored cards being used
     */


    /**
     * lines will hold all the lines on the board
     */

    private ArrayList<line> lines = new ArrayList<>();

    /**
     * This will create a Board using a given ArrayList of lines
     */
    public Board(){

        A = new line("&",2,1,2);
        B = new line("blue",4,1,3);
        C = new line("green",3,2,4);
        D = new line("red",1,3,4);
        E = new line("&",5,4,5);
        F = new line("green",2,3,7);
        G = new line("blue",3,6,5);
        H = new line("green",1,5,8);
        I = new line("red",6,7,8);
        J = new line("red",4,5,9);
        K = new line("&",1,9,10);
        L = new line("green",1,5,10);
        M = new line("blue",2,8,10);
        lines.add(A);
        lines.add(B);
        lines.add(C);
        lines.add(D);
        lines.add(E);
        lines.add(F);
        lines.add(G);
        lines.add(H);
        lines.add(I);
        lines.add(J);
        lines.add(K);
        lines.add(L);
        lines.add(M);

    }

    /**
     * This will print the Board to the console
     */
    public void printBoard(){
        // The ASCII board that will change whenever a line is filled
        System.out.println(
                "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" +
                        "\n{  (1)--" + A.toString() + "--(2)          (6)      (9)} 1-Gary         10-Pittsburgh" +
                        "\n{   |        |            |     _/ | } 2-Toledo       Train lines are in the form [number, letter]" +
                        "\n{  " + B.toString() + "       " + C.toString() + "           " + G.toString() + "   " + J.toString() + "  " + K.toString() + " } 3-Indianapolis b is blue, g is green, r is red" +
                        "\n{   |        |            | _/     | } 4-Dayton       & means you can use any cards" +
                        "\n{  (3)--" + D.toString() + "--(4)----" + E.toString() + "----(5)--" + L.toString() + "-(10)} 5-Columbus" +
                        "\n{   |                     |      _/  } 6-Cleveland" +
                        "\n{  " + F.toString() + "                     " + H.toString() + "   " + M.toString() + "    } 7-Cincinnati" +
                        "\n{   |                     |  _/      } 8-Charleston" +
                        "\n{  (7)-------" + I.toString() + "----------(8)/        } 9-Erie" +
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

    public card getCardFromCardRow(int i){
        return cardRow.get(i);
    }

    public void addCardToCardRow(card a){
        cardRow.add(a);
    }

    public void removeCardFromCardRow(card a){
        cardRow.remove(a);
    }

    public String printCardRow(){
        StringBuilder print = new StringBuilder();
        for (int i = 0; i < cardRow.size(); i++){
            print.append(cardRow.get(i).getColor()+", ");
        }
        print.deleteCharAt(print.length()-1);
        print.deleteCharAt(print.length()-1);
        return print.toString();
    }
}
