package persistence;

import model.Coin;
import model.CompletedPlayingGrid;
import model.GameHistory;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

// JSON-related methods inspired by provided phase 2 example project :
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonReader {

    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads history from file and returns it fully parsed into a GameHistory object
    // throws IOException if an error occurs reading data from file
    public GameHistory read() throws IOException {
        String jsData = readFile(source);
        JSONObject jsObject = new JSONObject(jsData);
        return parseHistory(jsObject);
    }

    // EFFECTS: reads source file as string and returns it
    // throws IOException if an error occurs reading data from file
    private String readFile(String source) throws IOException {
        StringBuilder content = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> content.append(s));
        }
        return content.toString();
    }

    // EFFECT: returns parsed GameHistory from JSONObject
    private GameHistory parseHistory(JSONObject jsonObject) {
        GameHistory history = new GameHistory();
        addHistories(history, jsonObject);
        return history;
    }

    // MODIFIES: history
    // EFFECT: parses list of games from JSONObject and adds them to history
    private void addHistories(GameHistory history, JSONObject jsonObject) {
        JSONArray listGames = jsonObject.getJSONArray("history");
        for (Object json : listGames) {
            JSONObject jsonGrid = (JSONObject) json;
            addParsedGrid(history, jsonGrid);
        }
    }

    // MODIFIES: history
    // EFFECT: parses single game from JSONObject and adds it to history
    private void addParsedGrid(GameHistory history, JSONObject jsonGrid) {
        boolean isPlayer1Winner = jsonGrid.getBoolean("isPlayer1Winner");
        JSONArray onlyJsonGrid = jsonGrid.getJSONArray("grid");

        CompletedPlayingGrid parsedGrid = gridFromJsonArray(onlyJsonGrid);
        parsedGrid.setWinner(isPlayer1Winner);

        history.addGame(parsedGrid);
    }

    // EFFECT: parses a full game grid from JSONArray and returns it.
    private CompletedPlayingGrid gridFromJsonArray(JSONArray jsonGrid) {
        CompletedPlayingGrid parsedGrid = new CompletedPlayingGrid();
        int index = 0;
        for (Object jsCol : jsonGrid) {
            JSONArray jsonColumn = (JSONArray) jsCol;
            ArrayList<Coin> goodDataColumn = jsColToListCoin(jsonColumn);
            parsedGrid.setColumn(index, goodDataColumn);
            index++;
        }
        return parsedGrid;
    }

    // EFFECT: parses a game column from JSONArray and returns it
    private ArrayList<Coin> jsColToListCoin(JSONArray jsCol) {
        ArrayList<Coin> col = new ArrayList<>();
        for (Object jsCoin : jsCol) {
            JSONObject jsonCoin = (JSONObject) jsCoin;
            Coin coin = jsCoinToDataCoin(jsonCoin);
            col.add(coin);
        }
        return col;
    }

    // EFFECT: parses a Coin object from JSONObject and returns it.
    private Coin jsCoinToDataCoin(JSONObject jsCoin) {
        int row = jsCoin.getInt("row");
        boolean isPlayer1 = jsCoin.getBoolean("isPlayer1");

        return new Coin(row, isPlayer1);
    }
}