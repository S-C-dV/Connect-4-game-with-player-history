package ui;

import model.CompletedPlayingGrid;
import model.PlayingGrid;

import java.util.Scanner;

// active Connect 4 board game with both players' coins.
public class RunPlayingGrid {

    private Boolean stillPlaying;
    private Boolean player1Turn;
    private Boolean gameSave;

    private Scanner input;

    private PlayingGrid currentGame;

    // EFFECT: starts a game
    public RunPlayingGrid() {
        this.player1Turn = true;
        this.gameSave = false;

        currentGame = new PlayingGrid();

        runGame();
    }

    // MODIFIES: this
    // EFFECT: initialises scanner and commands, runs the game
    private void runGame() {
        this.stillPlaying = true;
        String command;

        initialise();

        while (this.stillPlaying) {
            gameMenu();
            command = input.next();

            if (command.equals("w")) {
                processWInput();
            } else {
                processCommand(command);
            }
        }
        endGameMenu();
        command = input.next();
        command = command.toLowerCase();
        processEndGameCommand(command);
    }


    // MODIFIES: this
    // EFFECT: initialises scanner and first grid printing
    private void initialise() {
        input = new Scanner(System.in);

        printOutGame();
        System.out.println("Player 1's turn. Choose a column (0-6), or withdraw (w).");

        input.useDelimiter("\n");
    }

    // EFFECT: prints options menu for game commands
    private void gameMenu() {
        String playerName;
        if (player1Turn) {
            playerName = "Player 1";
        } else {
            playerName = "Player 2";
        }
        System.out.println("Choose an unfilled column (0-6), " + playerName + ", or withdraw (w).");
    }

    // MODIFIES: this
    // EFFECT: Processes changes to PlayingGrid when a player has won.
    private void processWInput() {
        currentGame.setWinner(currentGame.getPlayerRepresentation(!player1Turn));
        this.stillPlaying = false;
        if (player1Turn) {
            System.out.println("Player 2 wins!");
        } else {
            System.out.println("Player 1 wins!");
        }
    }

    // EFFECT: prints out options menu for after game has ended
    private void endGameMenu() {
        System.out.println("Choose option:");
        System.out.println("\ts -> Save game to history and return to menu");
        System.out.println("\tany letter -> Don't save game to history and return to menu");
    }

    // EFFECT: prints out the game grid.
    private void printOutGame() {
        System.out.println("'x' represents Player 1's coins, 'o' represents Player 2's coins.");
        for (int row = 5; row >= 0; row--) {
            String drawnRow = currentGame.printRow(row);
            System.out.println(drawnRow);
        }
        System.out.println("   0 1 2 3 4 5 6");
    }

    // MODIFIES: this, player1/player2, currentGame
    // EFFECT: processes the inputted mid-game command
    private void processCommand(String command) throws NumberFormatException {
        try {
            int col = Integer.parseInt(command);
            if (col <= 6 && col >= 0) {
                if (currentGame.getColSize(col) >= 6) {
                    System.out.println("Invalid column choice, try again.");
                } else {
                    addCoinByCol(col);
                    printOutGame();
                }
            } else {
                System.out.print("Invalid column choice, try again.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid column choice, try again.");
        }
    }

    // MODIFIES: this
    // EFFECT: if input is "s", changes gameSave to true to be saved.
    private void processEndGameCommand(String command) {
        this.gameSave = (command.equals("s"));
    }

    // REQUIRES: 0 <= col <= 6.
    // MODIFIES: this, currentGame, player1/player2
    // EFFECT: adds coin to column and handles a possible win
    private void addCoinByCol(int col) {
        currentGame.addCoinToCol(col, player1Turn);
        if (currentGame.getGameOver()) {
            processWin();
        }
        this.player1Turn = !player1Turn; // by end of game, don't care who this is so add even for win
    }

    // MODIFIES: this
    // EFFECT: processes win for the game
    private void processWin() {
        currentGame.setWinner(currentGame.getPlayerRepresentation(player1Turn));
        this.stillPlaying = false;
        System.out.println(currentGame.getPlayerRepresentation(player1Turn) + " wins!");
    }

    // REQUIRES: stillPlaying is false / the game is over and there is a winner
    // EFFECT: creates a completed game grid without the additional, now unnecessary, data associated
    public CompletedPlayingGrid makeCompletedGame() {
        return currentGame.makeGridCompleted();
    }


    // setters and getters

    // EFFECT: gets game save status
    public boolean getSave() {
        return this.gameSave;
    }
}


