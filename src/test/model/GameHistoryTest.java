package model;

import exceptions.EmptyHistoryException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class GameHistoryTest {

    GameHistory history;

    PlayingGrid game0;
    PlayingGrid game1;
    PlayingGrid game2;

    @BeforeEach
    public void setup() {
        history = new GameHistory();

        game0 = new PlayingGrid();
        game1 = new PlayingGrid();
        game2 = new PlayingGrid();

        game0.setWinner(true);
        game1.setWinner(false);
        game2.setWinner(true);
    }

    //constructor
    @Test
    public void testConstructor() {
        assertEquals(0, history.getHistorySize());
    }

    //add Game
    @Test
    public void testAddGame() {
        assertEquals(0, history.getHistorySize());

        history.addGame(game0);
        assertEquals(1, history.getHistorySize());
        assertEquals(game0, history.indexList(0));

        history.addGame(game1);
        assertEquals(2, history.getHistorySize());
        assertEquals(game0, history.indexList(0));
        assertEquals(game1, history.indexList(1));
    }

    @Test
    public void testIndexList() {
        history.addGame(game2);
        history.addGame(game0);
        history.addGame(game1);

        assertEquals(game2, history.indexList(0));
        assertEquals(game0, history.indexList(1));
        assertEquals(game1, history.indexList(2));
    }

    // test findP1WinProportions
    @Test
    public void testFindP1WinProportionsGetExcept() {
        try {
            double prop = history.findP1WinProportions();
            fail("no exception thrown when was expected");
        } catch (EmptyHistoryException ehe) {

        }
    }

    @Test
    public void testFindP1WinProportionsNoExcept0Wins() {
        history.addGame(game1);
        double prop;
        try {
            prop = history.findP1WinProportions();
            assertEquals(0, prop);
        } catch (EmptyHistoryException ehe) {
            fail("exception thrown when not expected");
        }
    }

    @Test
    public void testFindP1WinProportionsNoExcept50PWin() {
        history.addGame(game1);
        history.addGame(game2);
        double prop;
        try {
            prop = history.findP1WinProportions();
            assertEquals(50, prop);
        } catch (EmptyHistoryException ehe) {
            fail("exception thrown when not expected");
        }
    }

    @Test
    public void testFindP1WinProportionsNoExcept66PWin() {
        history.addGame(game1);
        history.addGame(game0);
        history.addGame(game2);
        double prop;
        try {
            prop = history.findP1WinProportions();
            assertEquals(66, prop);
        } catch (EmptyHistoryException ehe) {
            fail("exception thrown when not expected");
        }
    }

    @Test
    public void testFindP1WinProportionsNoExcept100PWin() {
        history.addGame(game0);
        history.addGame(game2);
        double prop;
        try {
            prop = history.findP1WinProportions();
            assertEquals(100, prop);
        } catch (EmptyHistoryException ehe) {
            fail("exception thrown when not expected");
        }
    }

    @Test
    public void testGetHistory() {
        assertTrue(history.getHistory().isEmpty());
        ArrayList<PlayingGrid> test = new ArrayList<PlayingGrid>();
        assertEquals(test, history.getHistory());

        history.addGame(game0);
        test.add(game0);
        assertEquals(1, history.getHistory().size());
        assertEquals(test, history.getHistory());
    }

}
