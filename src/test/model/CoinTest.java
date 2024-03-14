package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// test for Coin class
public class CoinTest {

    private Coin coin1;
    private Coin coin2;

    @BeforeEach
    public void setup() {
        coin1 = new Coin(3,true);
        coin2 = new Coin(0, false);
    }

    //constructor
    @Test
    public void testConstructor() {
        assertTrue(coin1.getPlayer());
        assertEquals(3, coin1.getRow());

        assertFalse(coin2.getPlayer());
        assertEquals(0, coin2.getRow());
    }

    //coin representation
    @Test
    public void testCoinRepresentation() {
        assertEquals("|x", coin1.coinRepresentation());

        assertEquals("|o", coin2.coinRepresentation());
    }

    @Test
    public void testGetPlayer() {
        assertTrue(coin1.getPlayer());
        assertFalse(coin2.getPlayer());
    }

    @Test
    public void testGetRow() {
        assertEquals(3, coin1.getRow());
        assertEquals(0, coin2.getRow());
    }
}
