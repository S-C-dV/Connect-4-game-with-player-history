package ui;

import exceptions.EmptyHistoryException;
import model.CompletedPlayingGrid;
import model.GameHistory;
import model.PlayingGrid;

// runs the game history/list of previous games
public class RunGameHistory {

    private final GameHistory history;

    // EFFECT: creates object with specified history, runs the history.
    public RunGameHistory(GameHistory history) {
        this.history = history;

        runHistory();
    }

    // EFFECT: prints out proportion of games won, and final game layouts if there are any
    private void runHistory() {
        if (generateProportions()) {
            generateGames();
        }

    }

    // EFFECT: prints proportion of games won. returns whether any games are in history
    private Boolean generateProportions() {
        try {
            float propP1 = history.findP1WinProportions();
            float propP2 = 100 - propP1;
            String statement = "Player 1 has won " + propP1 + "% of games, and Player 2 has won " + propP2
                    + "% of games.";
            System.out.println(statement);
            return true;
        } catch (EmptyHistoryException ehe) {
            System.out.println("No games have been played yet.");
            return false;
        }
    }

    // EFFECT: determines winner and call for board to be printed
    private void generateGames() {
        for (CompletedPlayingGrid pg: history.getHistory()) {
            if (pg.getWinner()) {
                System.out.println("Player 1 won this game.");
            } else {
                System.out.println("Player 2 won this game.");
            }
            printOutGame(pg);
        }
    }

    // EFFECT: prints game layout.
    private void printOutGame(CompletedPlayingGrid game) {
        System.out.println("'x' represents Player 1's coins, 'o' represents Player 2's coins.");
        for (int row = 5; row >= 0; row--) {
            String drawnRow = game.printRow(row);
            System.out.println(drawnRow);
        }
        System.out.println("   0 1 2 3 4 5 6");
    }
}
