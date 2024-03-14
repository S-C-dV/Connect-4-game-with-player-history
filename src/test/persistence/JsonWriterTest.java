package persistence;

import exceptions.EmptyHistoryException;
import model.CompletedPlayingGrid;
import model.GameHistory;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// JSON-related methods inspired by provided phase 2 example project :
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonWriterTest {

    @Test
    public void testWriterInvalidFileName() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("Expected an IOException as this isn't a legal file name to save to");
        } catch (IOException ioe) {

        }
    }

    @Test
    public void testWriteEmptyHistory() {
        try {
            GameHistory gh = new GameHistory();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyHistory.json");
            writer.open();
            writer.write(gh);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyHistory.json");
            GameHistory test = reader.read();
            assertTrue(test.getHistory().isEmpty());
        } catch (IOException ioe) {
            fail("no exception should have been thrown");
        }
    }

    @Test
    public void testWriteGeneralHistory() {
        try {
            GameHistory gh = new GameHistory();
            CompletedPlayingGrid game1 = generateGame1();
            CompletedPlayingGrid game2 = generateGame2();
            gh.addGame(game1);
            gh.addGame(game2);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralHistory.json");
            writer.open();
            writer.write(gh);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralHistory.json");
            GameHistory test = reader.read();
            assertEquals(2, test.getHistorySize());
            assertEquals(50.0, test.findP1WinProportions());
            assertFalse(test.indexList(0).getWinner());
            assertTrue(test.indexList(1).getWinner());

        } catch (IOException ioe) {
            fail("no exception should have been thrown");
        } catch (EmptyHistoryException e) {
            fail("game history isn't empty");
        }
    }

    private CompletedPlayingGrid generateGame1() {
        CompletedPlayingGrid game = new CompletedPlayingGrid();
        game.addCoinToCol(1, true);
        game.addCoinToCol(4, false);
        game.addCoinToCol(3, true);
        game.addCoinToCol(3, false);
        game.addCoinToCol(2, true);
        game.addCoinToCol(2, false);
        game.addCoinToCol(5, true);
        game.addCoinToCol(4, false);
        game.addCoinToCol(4, true);
        game.addCoinToCol(5, false);
        game.setWinner(false);

        return game;
    }

    private CompletedPlayingGrid generateGame2() {
        CompletedPlayingGrid game = new CompletedPlayingGrid();
        game.addCoinToCol(3, true);
        game.addCoinToCol(3, false);
        game.addCoinToCol(3, true);
        game.addCoinToCol(3, false);
        game.addCoinToCol(3, true);
        game.addCoinToCol(3, false);
        game.addCoinToCol(2, true);
        game.addCoinToCol(4, false);
        game.addCoinToCol(1, true);
        game.addCoinToCol(0, false);
        game.addCoinToCol(2, true);
        game.addCoinToCol(4, false);
        game.addCoinToCol(2, true);
        game.addCoinToCol(0, false);
        game.addCoinToCol(2, true);
        game.setWinner(true);

        return game;
    }
}
