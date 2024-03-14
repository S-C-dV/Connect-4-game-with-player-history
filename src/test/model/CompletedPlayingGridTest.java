package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class CompletedPlayingGridTest {

    protected CompletedPlayingGrid game;

    @BeforeEach
    public void setup() {
        game = new CompletedPlayingGrid();
    }


    //test constructor
    @Test
    public void testConstructor() {

        assertEquals(0, game.getColSize(0));
        assertEquals(0, game.getColSize(1));
        assertEquals(0, game.getColSize(2));
        assertEquals(0, game.getColSize(3));
        assertEquals(0, game.getColSize(4));
        assertEquals(0, game.getColSize(5));
        assertEquals(0, game.getColSize(6));

        assertEquals(7, game.getGridWidth());
    }

    //printRow
    @Test
    public void testPrintRow() {
        game.addCoinToCol(1,true);
        game.addCoinToCol(4,false);
        game.addCoinToCol(2,true);
        game.addCoinToCol(1,false);
        game.addCoinToCol(5,true);
        game.addCoinToCol(4,false);
        game.addCoinToCol(4,true);
        game.addCoinToCol(1,false);

        assertEquals("4 | | | | | | | |",game.printRow(4));
        assertEquals("0 | |x|x| |o|x| |",game.printRow(0));
        assertEquals("1 | |o| | |o| | |",game.printRow(1));
        assertEquals("2 | |o| | |x| | |",game.printRow(2));
    }

    // setWinner
    @Test
    public void testSetWinnerP1() {
        game.setWinner(true);

        assertTrue(game.getWinner());
    }

    @Test
    public void testSetWinnerP2() {
        game.setWinner(false);

        assertFalse(game.getWinner());
    }



    @Test
    public void testSetColumn() {
        Coin c1 = new Coin(0, true);
        Coin c2 = new Coin(1, false);
        Coin c3 = new Coin(2, true);

        ArrayList<Coin> testList = new ArrayList<>();
        testList.add(c1);
        testList.add(c2);
        testList.add(c3);

        assertEquals(0, game.getColSize(3));
        game.setColumn(3, testList);

        assertEquals(c1, game.getColIndexed(3,0));
        assertEquals(c2, game.getColIndexed(3,1));
        assertEquals(c3, game.getColIndexed(3,2));

    }

    //addcointocol
    @Test
    public void testAddCoinToCol() {
        game.addCoinToCol(2, true);

        assertEquals(1, game.getColSize(2));
    }
}
