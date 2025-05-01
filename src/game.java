import java.util.Scanner;

/**
 * @author Gordon Schartner
 * @author Samuel Rees
 */

public class game {
    /**
     * This will just run the game
     */
    public static void main(String[] args) {
        gameLoop game = new gameLoop();
        game.loop();
    }

        /**
         * Will intake the number of players and make that many Hand objects in an array.
         * Then will run Turn in a loop for each player at a time,
         * printing board at the end of each turn while
         * checking if any each player runs out of trains at the end of their turn.
         * Once a player runs out of trains the loop ends and a winner is declared
         */
}
