package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// all tests for Player
public class PlayerTest {

    private Player player;

    @BeforeEach
    public void beforeEach() {
        player = new Player(true);
    }

    private void addCoinsToRow(int row) {

        for (int i = 0; i <= 6; i++) {
            player.addCoin(i, row);
        }
    }

    //Player
    @Test
    public void testPlayer() {
        assertEquals(7, player.getGridSize());
        assertTrue(player.getIsPlayer1());
        assertFalse(player.getWin());

        assertEquals(0,player.getColSize(0));
        assertEquals(0,player.getColSize(1));
        assertEquals(0,player.getColSize(2));
        assertEquals(0,player.getColSize(3));
        assertEquals(0,player.getColSize(4));
        assertEquals(0,player.getColSize(5));
    }

    // addCoin
    // bottom cols
    @Test
    public void testAddCoinBottom() {
        addCoinsToRow(0);

        assertEquals(0, player.getColIndexed(0,0)); // first col
        assertEquals(1, player.getColSize(0));

        assertEquals(0, player.getColIndexed(3,0));
        assertEquals(1, player.getColSize(3));

        assertEquals(0, player.getColIndexed(4,0));
        assertEquals(1, player.getColSize(4));

        assertEquals(0, player.getColIndexed(5,0));
        assertEquals(1, player.getColSize(5));

        assertEquals(0, player.getColIndexed(6,0));  // last col
        assertEquals(1, player.getColSize(6));
    }

    // middle-middle
    @Test
    public void testAddCoinMiddle() {
        for (int i = 0; i <= 2; i++) {
            addCoinsToRow(i);
        }
        assertEquals(2, player.getColIndexed(0,2)); // first col
        assertEquals(3, player.getColSize(0));

        assertEquals(2, player.getColIndexed(3,2));
        assertEquals(3, player.getColSize(3));

        assertEquals(2, player.getColIndexed(4,2));
        assertEquals(3, player.getColSize(4));

        assertEquals(2, player.getColIndexed(5,2));
        assertEquals(3, player.getColSize(5));

        assertEquals(2, player.getColIndexed(6,2)); // last col
        assertEquals(3, player.getColSize(6));
    }


    // top cols
    @Test
    public void testAddCoinTop() {
        for (int i = 0; i <= 5; i++) {
            addCoinsToRow(i);
        }
        assertEquals(5, player.getColIndexed(0,5)); // first col
        assertEquals(6, player.getColSize(0));

        assertEquals(5, player.getColIndexed(3,5));
        assertEquals(6, player.getColSize(3));

        assertEquals(5, player.getColIndexed(4,5));
        assertEquals(6, player.getColSize(4));

        assertEquals(5, player.getColIndexed(5,5));
        assertEquals(6, player.getColSize(5));

        assertEquals(5, player.getColIndexed(6,5)); // last col
        assertEquals(6, player.getColSize(6));
    }

    // checkWin
    @Test
    public void testCheckWinNotEvenClose() {
        player.addCoin(3, 0);
        player.checkWin(3, 0);
        assertFalse(player.getWin());

        player.addCoin(4, 1);
        player.checkWin(4, 1);
        assertFalse(player.getWin());

        player.addCoin(6, 5);
        player.checkWin(6, 5);
        assertFalse(player.getWin());
    }

    //horizontal
    // middle
    @Test
    public void testCheckWinHorizontalInRange() {
        player.addCoin(2, 3);
        player.addCoin(3, 3);
        player.addCoin(4, 3);

        player.checkWin(2, 3);
        assertFalse(player.getWin());

        player.checkWin(3, 3);
        assertFalse(player.getWin());

        player.checkWin(4, 3);
        assertFalse(player.getWin());

        player.addCoin(5, 3);
        player.checkWin(5, 3);
        assertTrue(player.getWin());
    }

    // 5+ coins
    @Test
    public void testCheckWinHorizontal5Coins() {
        player.addCoin(3, 5);
        player.addCoin(4, 5);
        player.addCoin(5, 5);
        player.addCoin(6, 5);
        player.addCoin(2, 5);

        player.checkWin(4, 5);
        assertTrue(player.getWin());
    }

    //vertical
    // middle
    @Test
    public void testCheckWinVerticalMiddle() {
        player.addCoin(2, 2);
        player.addCoin(2, 3);
        player.addCoin(2, 4);

        player.checkWin(2, 2);
        assertFalse(player.getWin());

        player.addCoin(2, 1);
        player.checkWin(2, 3);
        assertTrue(player.getWin());
    }

    // diagonal up
    // middle
    @Test
    public void testCheckWinDiagonalUpMiddle() {
        player.addCoin(2, 2);
        player.addCoin(3, 3);
        player.addCoin(4, 4);

        player.checkWin(2, 2);
        assertFalse(player.getWin());

        player.addCoin(5, 5);
        player.checkWin(4, 4);
        assertTrue(player.getWin());
    }

    // diagonal down
    @Test
    public void testCheckWinDiagonalDownMiddle() {
        player.addCoin(1, 4);
        player.addCoin(2, 3);
        player.addCoin(3, 2);

        player.checkWin(1, 4);
        assertFalse(player.getWin());

        player.addCoin(4, 1);
        player.checkWin(3, 2);
        assertTrue(player.getWin());
    }

    // countInDirection

    @Test
    public void testCountInDirectionFewCoins() {
        player.addCoin(3, 0);
        player.addCoin(4, 1);
        player.addCoin(5, 2);
        player.addCoin(5,0);
        player.addCoin(3,1);

        int horRight = player.testingCountConsecutiveCoins(3, 1, 1, 0);
        assertEquals(1, horRight);

        int horLeft = player.testingCountConsecutiveCoins(4, 1, -1, 0);
        assertEquals(1, horLeft);

        int vertUp = player.testingCountConsecutiveCoins(3, 0, 0, 1);
        assertEquals(1, vertUp);

        int vertDown = player.testingCountConsecutiveCoins(4, 1, 0, -1);
        assertEquals(0, vertDown);

        int diagUpRight = player.testingCountConsecutiveCoins(5, 2, 1,1);
        assertEquals(0, diagUpRight);

        int diagUpLeft = player.testingCountConsecutiveCoins(5, 2, -1, -1);
        assertEquals(2, diagUpLeft);

        int diagDownRight = player.testingCountConsecutiveCoins(4, 1, 1, -1);
        assertEquals(1, diagDownRight);

        int diagDownLeft = player.testingCountConsecutiveCoins(4, 1, -1, 1);
        assertEquals(0, diagDownLeft);

    }

    @Test
    // non-consecutive coins
    public void testCountInDirectionNotConsecutive() {
        addCoinsToRow(0);
        addCoinsToRow(2);
        addCoinsToRow(3);
        player.addCoin(4,5);
        player.addCoin(0,5);
        player.addCoin(1,5);

        int countUp = player.testingCountConsecutiveCoins(3, 0, 0, 1);
        assertEquals(0, countUp);

        int countDown = player.testingCountConsecutiveCoins(3, 3, 0, -1);
        assertEquals(1, countDown);

        int countRight = player.testingCountConsecutiveCoins(0, 5, 1, 0);
        assertEquals(1, countRight);

        int countLeft = player.testingCountConsecutiveCoins(4, 5, -1, 0);
        assertEquals(0, countLeft);


    }
    // left-right boundary
    @Test
    public void testCountInDirectionBoundarySides() {
        addCoinsToRow(2);

        int countRight = player.testingCountConsecutiveCoins(3, 2, 1, 0);
        assertEquals(3, countRight);

        int countLeft = player.testingCountConsecutiveCoins(4, 2, -1, 0);
        assertEquals(4, countLeft);
    }
    // top-bottom boundary
    @Test
    public void testCountInDirectionBoundaryTopBot() {
        for (int i = 0; i <= 5; i++) {
            player.addCoin(4, i);
        }

        int countUp = player.testingCountConsecutiveCoins(4, 2, 0, 1);
        assertEquals(3, countUp);

        int countDown = player.testingCountConsecutiveCoins(4, 4, 0, -1);
        assertEquals(4, countDown);
    }

    //checkHorizontalWin
    // win
    @Test
    public void testCheckHorizontalWinTrue() {
        addCoinsToRow(2);
        assertTrue(player.testingHorizontal(2,2));
    }
    // not win
    @Test
    public void testCheckHorizontalWinFalse() {
        for (int i = 2; i <= 4; i++) {
            player.addCoin(i, 1);
        }
        assertFalse(player.testingHorizontal(2,1));
    }

    //checkVerticalWin
    // win
    @Test
    public void testCheckVerticalWinTrue() {
        for (int i = 2; i <= 5; i++) {
            player.addCoin(6, i);
        }
        assertTrue(player.testingVertical(6,3));
    }
    // not win
    @Test
    public void testCheckVerticalWinFalse() {
        player.addCoin(0,2);
        player.addCoin(0,3);

        assertFalse(player.testingVertical(0,2));
    }
    //checkUpDiagonalWin
    // win
    @Test
    public void testCheckUpDiagonalWinTrue() {
        for (int i = 2; i <= 5; i++) {
            player.addCoin(i, i);
        }
        assertTrue(player.testingDiagonalUp(3,3));
    }
    // not win
    @Test
    public void testCheckUpDiagonalWinFalse() {
        for (int i = 2; i <= 4; i++) {
            player.addCoin((i - 1), i);
        }

        assertFalse(player.testingDiagonalUp(3,4));
    }
    //checkDownDiagonalWin
    // win
    @Test
    public void testCheckDownDiagonalWinTrue() {
        for (int i = 2; i <= 5; i++) {
            player.addCoin(i, (5 - i));
        }
        assertTrue(player.testingDiagonalDown(3,2));
    }
    // not win
    @Test
    public void testCheckDownDiagonalWinFalse() {
        for (int i = 2; i <= 4; i++) {
            player.addCoin((i - 1), (6 - i));
        }
        assertFalse(player.testingDiagonalDown(2,3));
    }

}
