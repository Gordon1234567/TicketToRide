import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

/** Run with JDK 22; no external test framework is required. */
public class RegressionTests {
    private static int checks;
    private static Hand player(int number) {
        Hand p = new Hand(); p.setUpHand(); p.setPlayerNumber(number);
        for (String color : List.of("red", "blue", "green")) p.getHand().put(color, 30);
        return p;
    }
    private static void check(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
        checks++;
    }
    private static void claim(gameLoop loop, Board b, Hand p, int index) {
        line track = b.getLine(index);
        if (track.getColor().equals("&")) System.setIn(new ByteArrayInputStream("red\nred\nred\nred\nred\n".getBytes(StandardCharsets.UTF_8)));
        check(loop.playTrain(track, p, b), "Track " + index + " must be claimable");
    }
    private static String console(String input) {
        InputStream beforeIn = System.in; PrintStream beforeOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        System.setOut(new PrintStream(output));
        try {
            new gameLoop().loop();
        } catch (NoSuchElementException expected) {
            // The finite input ends deliberately after the tested prompt.
        } finally { System.setIn(beforeIn); System.setOut(beforeOut); }
        return output.toString(StandardCharsets.UTF_8);
    }
    public static void main(String[] args) {
        PrintStream beforeOut = System.out; InputStream beforeIn = System.in;
        System.setOut(new PrintStream(OutputStream.nullOutputStream()));
        try {
            gameLoop loop = new gameLoop(); Board b = new Board(); Hand p = player(1);
            claim(loop, b, p, 3); claim(loop, b, p, 1); claim(loop, b, p, 2);
            check(b.getLine(2).getLocations().equals(new HashSet<>(List.of(2, 4))), "Board endpoints must stay unchanged");
            check(loop.findLine(b, 1, 2) == b.getLine(0), "Gary-Toledo must still resolve to its unclaimed track");
            check(!b.getLine(0).getFull(), "Gary-Toledo must remain unclaimed");
            // Destination connectivity still works after copying endpoint sets.
            routDeck tickets = new routDeck();
            tickets.draw(); p.pickupRoutCard(tickets); // Gary-Dayton.
            int beforePoints = p.getPoints(); p.checkRouts();
            check(p.getPoints() == beforePoints + 7 && p.howManyRoutCards() == 0, "Connected destination must score once");
            p.checkRouts(); check(p.getPoints() == beforePoints + 7, "Completed destination must not score twice");

            b = new Board(); p = player(1);
            check(loop.findLine(b, 1, 10) == null, "Disconnected cities must be rejected");
            check(loop.findLine(b, 5, 5) == null, "Same-city endpoints must be rejected");
            check(loop.findLine(b, 0, 4) == null && loop.findLine(b, 1, 11) == null, "Out-of-range endpoints must be rejected");
            check(loop.findLine(b, 4, 3) == b.getLine(3), "Reversed valid endpoints must work");
            check(!loop.playTrain(null, p, b) && !loop.playTrain(new line(), p, b), "Invalid track objects must not crash");

            int cards = p.getTotalCards(); claim(loop, b, p, 0);
            check(p.getTrains() == 13 && p.getTotalCards() == cards - 2 && p.getPoints() == 2, "Flexible track must cost two trains and cards, and score once");
            check(!loop.playTrain(b.getLine(0), p, b) && p.getTrains() == 13, "Claimed tracks must not charge twice");
            claim(loop, b, p, 1);
            check(p.getTrains() == 9 && p.getPoints() == 9, "Colored-track costs must remain correct");

            b = new Board(); Hand[] players = {player(1), player(2), player(3)};
            check(loop.canAnyoneBuild(b, players), "A new board must allow building");
            int[][] owned = {{0,2,8}, {4,1,9,7}, {10,6,12,5,3,11}};
            for (int j = 0; j < owned.length; j++) for (int i : owned[j]) claim(loop, b, players[j], i);
            check(Arrays.stream(players).allMatch(x -> x.getTrains() > 0), "Stalemate fixture must leave every player with trains");
            check(!loop.canAnyoneBuild(b, players), "An entirely claimed board must end the game");
            // A long unclaimed track alone must not keep a short-train player stuck.
            Board longOnly = new Board();
            for (int i = 0; i < longOnly.getLength(); i++) if (i != 8) longOnly.getLine(i).setFull();
            check(!loop.canAnyoneBuild(longOnly, players), "An unaffordable remaining track must also end the game");

            String invalid = console("1\n2\n1\n10\n3\n");
            check(invalid.contains("Choose two different cities") && invalid.contains("15 trains remaining"), "Disconnected input must return to a working turn menu");
            String identical = console("1\n2\n5\n5\n3\n");
            check(identical.contains("Choose two different cities") && identical.contains("15 trains remaining"), "Identical input must not spend trains");
            StringBuilder inputs = new StringBuilder("1\n");
            // Enough draws to guarantee both requested colors regardless of shuffle.
            for (int i = 0; i < 50; i++) inputs.append("1\n");
            inputs.append("2\n1\n2\nred\nblue\n3\n");
            String flexible = console(inputs.toString());
            check(flexible.contains("13 trains remaining"), "Full console flow must share its input reader with color prompts");
        } finally { System.setOut(beforeOut); System.setIn(beforeIn); }
        System.out.println("PASS: " + checks + " Java regression checks");
    }
}
