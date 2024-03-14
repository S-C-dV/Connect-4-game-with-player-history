package persistence;

import exceptions.EmptyHistoryException;
import model.CompletedPlayingGrid;
import model.GameHistory;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// JSON-related methods inspired by provided phase 2 example project :
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonReaderTest {

    @Test
    public void testReadFileNotExist() {
        JsonReader reader = new JsonReader("./data/notRealFile.json");
        try {
            GameHistory gh = reader.read();
            fail("no file exists, should throw exception");
        } catch (IOException ioe) {

        }
    }

    @Test
    public void testReadEmptyFile() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyHistory.json");
        try {
            GameHistory gh = reader.read();
            assertTrue(gh.getHistory().isEmpty());
            try {
                gh.findP1WinProportions();
                fail("should have thrown empty history exception");
            } catch (EmptyHistoryException e) {
                // passes
            }
        } catch (IOException ioe) {
            fail("file exists, shouldn't throw exception");
        }
    }
//
//    @Test
//    void testReaderGeneralWorkRoom() {
//        JsonReader reader = new JsonReader("./data/testReaderGeneralWorkRoom.json");
//        try {
//            WorkRoom wr = reader.read();
//            assertEquals("My work room", wr.getName());
//            List<Thingy> thingies = wr.getThingies();
//            assertEquals(2, thingies.size());
//            checkThingy("needle", Category.STITCHING, thingies.get(0));
//            checkThingy("saw", Category.WOODWORK, thingies.get(1));
//        } catch (IOException e) {
//            fail("Couldn't read from file");
//        }

    @Test
    public void testReadRegularFile() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralHistory.json");
        try {
            GameHistory gh = reader.read();
            assertEquals(3, gh.getHistorySize());
            testCorrectHistoryRegularFile(gh);
            try {
                assertEquals(0.0, gh.findP1WinProportions());
            } catch (EmptyHistoryException ehe) {
                fail("history isn't empty");
            }
        } catch (IOException ioe) {
            fail("path exists, shouldn't fail");
        }
    }

    private void testCorrectHistoryRegularFile(GameHistory gh) {
        // test grid 1
        CompletedPlayingGrid grid1 = gh.indexList(0);
        assertEquals(0, grid1.getColSize(0));
        assertEquals(4, grid1.getColSize(4));
        assertEquals(2, grid1.getColSize(5));
        assertFalse(grid1.getWinner());
        assertFalse(grid1.getColIndexed(4,0).getPlayer());

        // test grid 2
        CompletedPlayingGrid grid2 = gh.indexList(1);
        assertEquals(0, grid2.getColSize(0));
        assertEquals(0, grid2.getColSize(4));
        assertEquals(0, grid2.getColSize(5));
        assertFalse(grid2.getWinner());

        // test grid 3
        CompletedPlayingGrid grid3 = gh.indexList(2);
        assertEquals(1, grid3.getColSize(0));
        assertEquals(6, grid3.getColSize(3));
        assertEquals(0, grid3.getColSize(5));
        assertTrue(grid3.getColIndexed(3,0).getPlayer());
        assertFalse(grid3.getColIndexed(3,1).getPlayer());
        assertFalse(grid3.getWinner());
    }

}
