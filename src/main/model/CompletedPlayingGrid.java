package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static java.lang.String.valueOf;

// JSON-related methods inspired by provided phase 2 example project :
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class CompletedPlayingGrid {

    protected String winner;

    protected ArrayList<Coin> col0;
    protected ArrayList<Coin> col1;
    protected ArrayList<Coin> col2;
    protected ArrayList<Coin> col3;
    protected ArrayList<Coin> col4;
    protected ArrayList<Coin> col5;
    protected ArrayList<Coin> col6;

    protected ArrayList<ArrayList<Coin>> loCols;

    // EFFECT: creates CompletedPlayingGrid with no winner yet and empty columns.
    public CompletedPlayingGrid() {
        col0 = new ArrayList<Coin>();
        col1 = new ArrayList<Coin>();
        col2 = new ArrayList<Coin>();
        col3 = new ArrayList<Coin>();
        col4 = new ArrayList<Coin>();
        col5 = new ArrayList<Coin>();
        col6 = new ArrayList<Coin>();

        this.loCols = new ArrayList<ArrayList<Coin>>();
        this.loCols.add(col0);
        this.loCols.add(col1);
        this.loCols.add(col2);
        this.loCols.add(col3);
        this.loCols.add(col4);
        this.loCols.add(col5);
        this.loCols.add(col6);
    }

    // REQUIRES: 0<= row <= 5.
    // EFFECT: prints the game row with x for player 1 and o for player 2 coins.
    public String printRow(int row) {
        String rowCoins = valueOf(row).concat(" ");
        for (int column = 0; column <= 6; column++) {
            if (loCols.get(column).size() > row) {
                Coin chip = loCols.get(column).get(row);
                rowCoins = rowCoins.concat(chip.coinRepresentation());
            } else {
                rowCoins = rowCoins.concat("| ");
            }
        }
        return rowCoins.concat("|");
    }

    // REQUIRES: 0 <= col <= 6. getColSize(col) < 6
    // MODIFIES: this
    // EFFECT: adds new Coin to grid at specified column.
    public void addCoinToCol(int col, boolean isPlayer1) {
        int row = getColSize(col);
        Coin coin = new Coin(row, isPlayer1);
        loCols.get(col).add(coin);
    }

    // JSON methods inspired by provided phase 2 example project :
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

    // EFFECT: creates JSONArray representing the inputted column
    private JSONArray columnToJson(ArrayList<Coin> list) {
        JSONArray jsArray = new JSONArray();
        for (Coin c : list) {
            jsArray.put(c.coinToJson());
        }
        return jsArray;
    }

    // JSON methods inspired by provided phase 2 example project :
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

    // REQUIRES: this.isPlayer1Winner !null.
    // EFFECT: creates JSONObject of entire grid with winner.
    public JSONObject fullGridToJson() {
        JSONObject jsFullGrid = new JSONObject();

        JSONArray jsGrid = new JSONArray();
        for (ArrayList<Coin> col : this.loCols) {
            jsGrid.put(this.columnToJson(col));
        }

        jsFullGrid.put("winner", this.winner);
        jsFullGrid.put("grid", jsGrid);

        return jsFullGrid;
    }

    // setters and getters

    // REQUIRES: 0<= col <= 6.
    // EFFECT: returns columns size
    public int getColSize(int col) {
        return loCols.get(col).size();
    }

    // REQUIRES: winner is "Player 1" "Player 2" "neither"
    // MODIFIES: this
    // EFFECT: set the winner
    public void setWinner(String winner) {
        this.winner = winner;
    }

    // REQUIRES: 0<= col <= 6
    // MODIFIES: this
    // EFFECT: modifies the indexed column with given list of coins
    public void setColumn(int index, ArrayList<Coin> column) {
        ArrayList<Coin> col = this.loCols.get(index);
        col.addAll(column);
    }

    // for test purposes

    // EFFECT: returns size of grid
    public int getGridWidth() {
        return loCols.size();
    }

    // REQUIRES: 0<= col <= 6. index exists in the list of specified column
    // EFFECT: returns coin present at specified column and column list index
    public Coin getColIndexed(int col, int index) {
        return loCols.get(col).get(index);
    }


    // REQUIRES: a winner has been appointed --> setWinner(boolean) has been called before.
    // EFFECT: returns boolean winner.
    public String getWinner() {
        return this.winner;
    }

    public boolean isPlayer1Winner() {
        return this.winner.equals("Player 1");
    }

    public boolean isPlayer2Winner() {
        return this.winner.equals("Player 2");
    }

    //EFFECT: returns the column at given integer
    public ArrayList<Coin> getCol(int col) {
        return this.loCols.get(col);
    }

    public static String getPlayerRepresentation(boolean turn) {
        if (turn) {
            return "Player 1";
        } else {
            return "Player 2";
        }
    }

    public static String getDrawRepresentation() {
        return "neither";
    }
}
