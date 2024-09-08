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
        for (CompletedPlayingGrid pg: history) {
            if (pg.isPlayer1Winner()) {
                countP1++;
            }
        }
        int total = history.size();
        return (countP1 * 100 / total);
    }

    public float findP2WinProportions() throws EmptyHistoryException {
        if (history.isEmpty()) {
            throw new EmptyHistoryException();
        }
        int countP2 = 0;
        for (CompletedPlayingGrid pg: history) {
            if (pg.isPlayer2Winner()) {
                countP2++;
            }
        }
        int total = history.size();
        return (countP2 * 100 / total);
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

    //EFFECT: returns list of games won by specified player
    public ArrayList<CompletedPlayingGrid> getWinnerGames(String player) {
        ArrayList<CompletedPlayingGrid> editedHistory = new ArrayList<>();
        for (CompletedPlayingGrid game : this.history) {
            if (player.equals(game.getWinner())) {
                editedHistory.add(game);
            }
        }
        addEvent(player);
        return editedHistory;
    }

    // MODIFIES: EventLog
    // EFFECTS: logs event that you're filtering for a specific winner
    private void addEvent(String player) {
        EventLog.getInstance().logEvent(new Event("Filtering history for games won by " + player));
    }

    // MODIFIES: this, EventLog
    // EFFECTS: adds a game to history, logs the event
    public void addGame(CompletedPlayingGrid pg) {
        EventLog.getInstance().logEvent(new Event("Game added to history"));
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
