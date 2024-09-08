package model;


import static java.lang.String.valueOf;

// Simpler Board/Grid of a completed game
public class PlayingGrid extends CompletedPlayingGrid {

    private Boolean gameOver;
    private int plays;

    private Player player1;
    private Player player2;

    // MODIFIES: EventLog
    // EFFECT: creates a new game grid with players and list of columns, logs the start of the new game
    public PlayingGrid() {
        super();
        player1 = new Player(getPlayerRepresentation(true));
        player2 = new Player(getPlayerRepresentation(false));
        gameOver = false;
        super.winner = "neither";
        EventLog.getInstance().logEvent(new Event("New game started."));
    }

    // REQUIRES: 0<= col <= 6.
    // MODIFIES: this, player1/player2
    // EFFECT: adds coin at column into grid, and to player's grid. updates gameOver status.
    @Override
    public void addCoinToCol(int col, boolean isPlayer1) {
        int row = getColSize(col);
        super.addCoinToCol(col, isPlayer1);
        if (isPlayer1) {
            player1.addCoin(col, row);
            player1.checkWin(col, row);
            this.gameOver = player1.getWin();
        } else {
            player2.addCoin(col, row);
            player2.checkWin(col, row);
            this.gameOver = player2.getWin();
        }
        this.plays++;
        this.gameOver = (this.gameOver | isGameDraw());
    }

    public boolean isGameDraw() {
        return (this.plays >= 42);
    }

    // REQUIRES: isPlayer1Winner !null
    // EFFECT: creates a CompletedPlayingGrid from the current PlayingGrid grid.
    public CompletedPlayingGrid makeGridCompleted() {
        CompletedPlayingGrid cpg = new CompletedPlayingGrid();
        cpg.setColumn(0, this.col0);
        cpg.setColumn(1, this.col1);
        cpg.setColumn(2, this.col2);
        cpg.setColumn(3, this.col3);
        cpg.setColumn(4, this.col4);
        cpg.setColumn(5, this.col5);
        cpg.setColumn(6, this.col6);
        cpg.setWinner(winner);

        return cpg;
    }

    public String whoWon(boolean playerTurn) {
        if (isGameDraw()) {
            return getDrawRepresentation();
        } else {
            return getPlayerRepresentation(playerTurn);
        }
    }

    //setters and getters

    // EFFECT: returns whether game has ended or not
    public boolean getGameOver() {
        return this.gameOver;
    }

    // for test purposes

    // MODIFIES: this
    // EFFECT: sets the game status
    public void setGameOver(boolean status) {
        this.gameOver = status;
    }


    // MODIFIES: this
    // EFFECT: sets the number of plays
    public void setPlays(int i) {
        this.plays = i;
    }

}
