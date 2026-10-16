# Ticket to Ride: Ohio Valley

A Java console game inspired by Ticket to Ride. Build rail connections across a custom Ohio Valley map, collect colored cards, and complete destination routes to earn points.

Created by **Gordon Schartner** and **Samuel Rees**.

## Play in your browser

[Play Ticket to Ride: Ohio Valley](https://gordon1234567.github.io/TicketToRide/)

No download or Java installation is needed. Enter 1-3 players, type answers to the terminal prompts, and press Enter. Players share one device.

## Features

- Local, turn-based play for **1–3 players** sharing a console.
- An ASCII board with **10 cities and 13 rail connections**.
- Red, blue, and green train cards, plus flexible tracks that accept any combination of colors.
- A shared row of five face-up cards.
- Destination cards that award points for connecting two cities.
- Tracking for each player's cards, points, remaining trains, and claimed connections.

## Getting started

The IntelliJ project is configured for **JDK 22**. No external libraries, Maven, or Gradle are required.

### Run in IntelliJ IDEA

1. Clone this repository or download and extract it.
2. Open the project folder in IntelliJ IDEA.
3. Set the project SDK to JDK 22 under **File → Project Structure → Project**.
4. Open `src/game.java` and run its `main` method.
5. Enter the number of players in the Run console and follow the prompts.

### Run from a terminal

With JDK 22 installed and `javac` and `java` available on your PATH, run these commands from the project folder:

```sh
javac -d out src/*.java
java -cp out game
```

## How to play

Each player starts with **15 trains and 2 random train cards**. Choose one of the numbered options when your turn begins:

| Option | Action |
| --- | --- |
| 1 | Draw two random train cards. |
| 2 | Claim a rail connection by entering its two endpoint cities. |
| 3 | View your points, remaining trains, and destination cards without ending your turn. |
| 4 | Draw a destination card; you can hold up to three unfinished destination cards. |
| 5 | Take one face-up card by entering its color. |

Drawing cards, drawing a destination card, or successfully claiming a connection ends your turn. Destination cards are checked automatically after each turn, and completed cards award points and leave your hand.

To claim a colored connection, spend the indicated number of cards of that color and have enough trains remaining. For an `&` connection, choose the individual card colors when prompted. Enter card colors in lowercase: `red`, `blue`, or `green`.

### Reading the board

- `3g` means an unclaimed connection requiring three green cards.
- `2&` means an unclaimed connection requiring two cards in any combination of colors.
- `P1` means the connection belongs to Player 1.

Enter city numbers when selecting endpoints, or use the supported city names:

| Number | City |
| --- | --- |
| 1 | Gary |
| 2 | Toledo |
| 3 | Indianapolis |
| 4 | Dayton |
| 5 | Columbus |
| 6 | Cleveland |
| 7 | Cincinnati |
| 8 | Charleston |
| 9 | Erie |
| 10 | Pittsburgh |

Choose two different cities joined directly on the board. City names are accepted in lowercase or with an initial capital.

### Scoring and game end

Claimed connections award points based on length:

| Length | Points |
| --- | --- |
| 1 | 1 |
| 2 | 2 |
| 3 | 4 |
| 4 | 7 |
| 5 | 10 |
| 6 | 15 |

Destination cards award their listed points when a player's rail network connects both endpoints. When a player reaches the start of their turn with zero trains remaining, the game ends and the player with the highest score wins.

## Code structure

All source files are in `src/` and use Java's default package.

| File | Purpose |
| --- | --- |
| `game.java` | Application entry point. |
| `gameLoop.java` | Player setup, turn menu, input handling, and game flow. |
| `Board.java` | Map, rail connections, card row, and board display. |
| `Hand.java` | Player cards, trains, scoring, and connected-city tracking. |
| `line.java` | A claimable rail connection and its cost, owner, and point value. |
| `card.java`, `cardDeck.java` | Train cards and their deck. |
| `rout.java`, `routDeck.java` | Destination cards and their deck. |
| `Deck.java`, `cardInterface.java` | Shared deck and card abstractions. |

The existing source uses `rout` for destination routes. `Turn.java` contains commented-out earlier code; active turn handling is in `gameLoop.java`.

## Implementation notes

This version uses custom rules rather than the complete board game's rules. It runs entirely in the console, with all players sharing the same display.

Flexible (`&`) connections consume one card and one train per space, with points awarded once per connection. The train-card deck is replaced with a newly shuffled deck when fewer than two cards remain. Games also end when no player has enough trains to build any unclaimed connection.

## Browser version

Open web/index.html in a browser to play the browser adaptation. It recreates the original console prompts, numbered menu, and ASCII board for 1-3 players sharing a device. Type answers and press Enter; type /restart to start over. Progress saves automatically in this browser. The browser version uses a JavaScript adaptation of the game; the original Java source remains in src/. No Java installation or server is required. Flexible tracks consume trains, and games also end when no player has enough trains for any remaining connection.

Browser saves synchronize between updated tabs. An older tab loads the latest saved game before accepting more input. If safe saving is unavailable, the terminal displays a warning and you can keep playing in that tab.

## Regression checks

With JDK 22 and Node.js on your PATH, run these commands from the project folder:

```sh
javac -d out src/*.java tests/RegressionTests.java
java -cp out RegressionTests
node tests/terminal-game.test.cjs
node tests/browser-save.test.cjs
```

The checks cover track endpoints, city validation, flexible-track costs, stalled games, console input, destination scoring, browser gameplay, and save synchronization.
