package model;

import org.json.JSONObject;

// JSON methods inspired by provided phase 2 example project :
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

//  Connect 4 coin with row and player
public class Coin {

    private final int row;
    private final boolean isPlayer1;

    // EFFECT: creates a coin with associated player and row
    public Coin(int row, boolean player) {
        this.row = row;
        this.isPlayer1 = player;
    }

    // EFFECT: creates representation of coin with x if coin belongs to player 1,
    //         o if coin belongs to player 2.
    public String coinRepresentation() {
        if (isPlayer1) {
            return "|x";
        } else {
            return "|o";
        }
    }

    // JSON methods inspired by provided phase 2 example project :
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

    // EFFECT: creates JSONObject of coin
    public JSONObject coinToJson() {
        JSONObject json = new JSONObject();
        json.put("row", this.row);
        json.put("isPlayer1", this.isPlayer1);
        return json;
    }

    // setters and getters

    // EFFECT: returns player as boolean
    public boolean getPlayer() {
        return this.isPlayer1;
    }

    // EFFECT: returns the coin row
    public int getRow() {
        return this.row;
    }
}
