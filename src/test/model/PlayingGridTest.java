package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// tests for PlayingGrid
public class PlayingGridTest extends CompletedPlayingGridTest {

    PlayingGrid game;

    @BeforeEach
    public void beforeEach() {
        game = new PlayingGrid();
        super.game = this.game;
    }

    //test constructor
    @Test
    public void testConstructor() {
        super.testConstructor();
    }

    //addCoinToCol
    @Test
    public void testAddCoinToCol() {
        game.addCoinToCol(0,true);
        assertEquals(1, game.getColSize(0));
        Coin coin1 = game.getColIndexed(0,0);

        assertEquals(0, coin1.getRow());
        assertEquals(true, coin1.getPlayer());


        game.addCoinToCol(0,false);
        assertEquals(2, game.getColSize(0));
        Coin coin2 = game.getColIndexed(0,1);

        assertEquals(1, coin2.getRow());
        assertEquals(false, coin2.getPlayer());
    }

    // test add coin to reach draw count
    @Test
    public void testAddCoinToColDraw() {
        game.setPlays(41);
        game.addCoinToCol(1, true);

        assertTrue(game.isGameDraw());
        assertTrue(game.getGameOver());
    }

    // getStatus
    @Test
    public void testGetStatusFalse() {
        game.setGameOver(false);

        assertFalse(game.getGameOver());
    }

    @Test
    public void testGetStatusTrue() {
        game.setGameOver(true);

        assertTrue(game.getGameOver());
    }



    //makeGridCompleted
    @Test
    public void testMakeGridCompleted() {
        game.addCoinToCol(4, true);
        game.addCoinToCol(1, false);
        game.addCoinToCol(0, true);
        game.addCoinToCol(4, false);
        game.setWinner(game.getPlayerRepresentation(true));

        CompletedPlayingGrid completed = game.makeGridCompleted();

        assertEquals(1, completed.getColSize(0));
        assertEquals(1, completed.getColSize(1));
        assertEquals(0, completed.getColSize(2));
        assertEquals(0, completed.getColSize(3));
        assertEquals(2, completed.getColSize(4));
        assertEquals(0, completed.getColSize(5));
        assertEquals(0, completed.getColSize(6));
        assertTrue(completed.isPlayer1Winner());

    }


}