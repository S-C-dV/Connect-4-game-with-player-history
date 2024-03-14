package model;

import exceptions.EmptyHistoryException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


// JSON-related methods inspired by provided phase 2 example project :
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// list of previous games
public class GameHistory {

    private ArrayList<CompletedPlayingGrid> history;

    // EFFECT: creates an empty list of games.
    public GameHistory() {
        this.history = new ArrayList<CompletedPlayingGrid>();
    }

    // EFFECT: returns rounded down percent of games won by player 1.
    //          will throw EmptyHistoryException if now games have been played yet/loGames is empty
    public float findP1WinProportions() throws EmptyHistoryException {
        if (history.isEmpty()) {
            throw new EmptyHistoryException();
        }
        int countP1 = 0;
        int countP2 = 0;
        for (CompletedPlayingGrid pg: history) {
            if (pg.getWinner()) {
                countP1++;
            } else {
                countP2++;
            }
        }
        int total = countP1 + countP2;
        return (countP1 * 100 / total);
    }


    // JSON-related methods inspired by provided phase 2 example project :
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

    // EFFECT: creates JsonObject of game history.
    public JSONObject turnHistoryToJson() {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonHistory = new JSONArray();

        for (CompletedPlayingGrid grid : this.history) {
            jsonHistory.put(grid.fullGridToJson());
        }

        jsonObject.put("history", jsonHistory);
        return jsonObject;
    }

    // MODIFIES: this
    // EFFECT adds a game to history
    public void addGame(CompletedPlayingGrid pg) {
        history.add(pg);
    }

    // EFFECT: returns the list of Games/history
    public ArrayList<CompletedPlayingGrid> getHistory() {
        return this.history;
    }

    // EFFECT: returns a specific game
    public CompletedPlayingGrid indexList(int index) {
        return history.get(index);
    }

    // EFFECT: returns the size of the list of games
    public int getHistorySize() {
        return history.size();
    }
}
