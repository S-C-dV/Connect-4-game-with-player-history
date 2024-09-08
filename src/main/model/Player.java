package model;

import java.util.ArrayList;

// player with identity (isPlayer1), own coins, checks winning condition
public class Player {

    private boolean win;
    private final String player;

    private final ArrayList<ArrayList<Integer>> loCols;

    private ArrayList<Integer> col0;
    private ArrayList<Integer> col1;
    private ArrayList<Integer> col2;
    private ArrayList<Integer> col3;
    private ArrayList<Integer> col4;
    private ArrayList<Integer> col5;
    private ArrayList<Integer> col6;

    // EFFECT: creates player with isPlayer1 as true or false.
    public Player(String isPlayer1) {
        this.player = isPlayer1;

        this.col0 = new ArrayList<Integer>();
        this.col1 = new ArrayList<Integer>();
        this.col2 = new ArrayList<Integer>();
        this.col3 = new ArrayList<Integer>();
        this.col4 = new ArrayList<Integer>();
        this.col5 = new ArrayList<Integer>();
        this.col6 = new ArrayList<Integer>();

        this.loCols = new ArrayList<ArrayList<Integer>>();
        this.loCols.add(col0);
        this.loCols.add(col1);
        this.loCols.add(col2);
        this.loCols.add(col3);
        this.loCols.add(col4);
        this.loCols.add(col5);
        this.loCols.add(col6);

    }

    // REQUIRES: 0<= col <= 6. 0<= row <= 5.
    // MODIFIES: this
    // EFFECTS: adds a new "coin" to the column specified
    public void addCoin(int col, int row) {
        loCols.get(col).add(row);
        eventAddedCoin(col);
    }

    // MODIFIES: EventLog
    // EFFECTS: creates an event and logs it that this player added a coin to the specified column
    private void eventAddedCoin(int col) {
        EventLog.getInstance().logEvent(new Event(player + " added a coin to column " + col));
    }

    // REQUIRES: 0<= col <= 6. 0<= row <= 5.
    // MODIFIES: this
    // EFFECTS: checks if the input coin location leads to a win.
    public void checkWin(int col, int row) {
        if (checkVerticalWin(col, row) || checkHorizontalWin(col, row) || checkUpDiagonalWin(col, row)
                || checkDownDiagonalWin(col, row)) {
            this.win = true;
            eventWin();
        }
    }

    // MODIFIES: EventLog
    // EFFECT: creates event and logs it that this player won the game
    private void eventWin() {
        EventLog.getInstance().logEvent(new Event(player + " won the game!"));
    }


    // REQUIRES: 0 <= col <= 6. 0 <= row <= 5.
    //           -1 <= colChange <= 1, -1 <= rowChange <= 1
    // EFFECTS: counts the number of consecutive coins in direction given
    // eg. countInDirection(2, 3, 1, -1) for right/downward coin count as of coin at point (2,3) on grid
    private int countInDirection(int col, int row, int colChange, int rowChange) {
        int currentRow = row + rowChange;
        int count = 0;

        for (int i = col + colChange; (0 <= i) && (i <= 6); i += colChange) {
            if ((currentRow < 0) || (currentRow > 5)) {
                return count;
            } else if (loCols.get(i).contains(currentRow)) {
                count++;
                currentRow += rowChange;
            } else {
                return count;
            }
        }
        return count;
    }


    // REQUIRES: 0<= col <= 6. 0<= row <= 5.
    // EFFECT: returns true if 3+ horizontally adjacent coins to location inputted
    private boolean checkHorizontalWin(int col, int row) {
        int countRight = countInDirection(col, row, 1, 0);
        int countLeft = countInDirection(col, row, -1, 0);

        return ((countLeft + countRight) >= 3);
    }

    // REQUIRES: 0<= col <= 6. 0<= row <= 5.
    // EFFECT: returns true if 3+ vertically adjacent coins to location inputted
    private boolean checkVerticalWin(int col, int row) {
        int countUp = countInDirection(col, row, 0, 1);
        int countDown = countInDirection(col, row, 0, -1);

        return ((countUp + countDown) >= 3);
    }

    // REQUIRES: 0<= col <= 6. 0<= row <= 5.
    // EFFECT: returns true if 3+ downward-diagonally adjacent coins to location inputted
    private boolean checkDownDiagonalWin(int col, int row) {
        int countRight = countInDirection(col, row, 1, -1);
        int countLeft = countInDirection(col, row, -1, 1);

        return ((countLeft + countRight) >= 3);
    }

    // REQUIRES: 0<= col <= 6. 0<= row <= 5.
    // EFFECT: returns true if 3+ upward-diagonally adjacent coins to location inputted
    private boolean checkUpDiagonalWin(int col, int row) {
        int countRight = countInDirection(col, row, 1, 1);
        int countLeft = countInDirection(col, row, -1, -1);

        return ((countLeft + countRight) >= 3);
    }


    // public methods for testing purposes

    // REQUIRES: 0<= col <= 6. 0<= row <= 5.
    // EFFECTS: tests horizontal win
    public boolean testingHorizontal(int col, int row) {
        return checkHorizontalWin(col, row);
    }

    // REQUIRES: 0<= col <= 6. 0<= row <= 5.
    // EFFECTS: tests vertical win
    public boolean testingVertical(int col, int row) {
        return checkVerticalWin(col, row);
    }

    // EFFECTS: tests upward-diagonal win
    public boolean testingDiagonalUp(int col, int row) {
        return checkUpDiagonalWin(col, row);
    }

    // REQUIRES: 0<= col <= 6. 0<= row <= 5.
    // EFFECTS: tests downward-diagonal win
    public boolean testingDiagonalDown(int col, int row) {
        return checkDownDiagonalWin(col, row);
    }

    // REQUIRES: 0<= col <= 6. 0<= row <= 5.
    // EFFECTS: counts consecutive coins in specified direction
    public int testingCountConsecutiveCoins(int col, int row, int colChange, int rowChange) {
        return countInDirection(col, row, colChange, rowChange);
    }

    // EFFECT: returns list of all the columns
    public int getGridSize() {
        return this.loCols.size();
    }

    // REQUIRES: 0 <= col <= 6
    // EFFECTS: returns specified column size
    public int getColSize(int col) {
        return loCols.get(col).size();
    }

    // REQUIRES: 0 <= col <= 6. index is an index that exists in the column list
    // EFFECT: returns the value at given column and index
    public int getColIndexed(int col, int index) {
        return loCols.get(col).get(index);
    }



    // setters and getters

    // EFFECT: returns win
    public boolean getWin() {
        return this.win;
    }

    // EFFECT: returns isPlayer1
    public boolean getPlayer() {
        return this.player.equals("Player 1");
    }
}

