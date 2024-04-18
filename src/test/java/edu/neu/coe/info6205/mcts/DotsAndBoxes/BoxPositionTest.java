package edu.neu.coe.info6205.mcts.DotsAndBoxes;

import edu.neu.coe.info6205.mcts.dotsandboxes.BoxPosition;
import edu.neu.coe.info6205.mcts.tictactoe.Position;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoxPositionTest {



    @Test
    public void testRender() {
        String grid = "·--·--·  ·\n" +
                "|p1|     |\n" +
                "·--·  ·  ·\n" +
                "|     |   \n" +
                "·--·--·  ·\n"+
                "|p0|     |\n"+
                "·--·  ·  ·\n";
        BoxPosition target = BoxPosition.parseBoxPosition(grid, 3);

        assertEquals(grid, target.render());
    }

    @Test
    public void testFull() {
        assertFalse(BoxPosition.parseBoxPosition("·--·--·  ·\n" +
                "|p1|     |\n" +
                "·--·  ·  ·\n" +
                "|     |   \n" +
                "·--·--·  ·\n"+
                "|p0|     |\n"+
                "·--·  ·  ·\n", 3).full());
        assertTrue(BoxPosition.parseBoxPosition("·--·--·--·\n" +
                "|p1|p0|p1|\n" +
                "·--·--·--·\n" +
                "|p0|p1|p0|\n" +
                "·--·--·--·\n"+
                "|p0|p1|p0|\n"+
                "·--·--·--·\n", 3).full());
    }

    @Test
    public void testWinner0() {
        String grid = "·--·--·--·\n" +
                "|p1|p0|p1|\n" +
                "·--·--·--·\n" +
                "|p0|p1|p0|\n" +
                "·--·--·--·\n"+
                "|p0|p1|p0|\n"+
                "·--·--·--·\n";
        BoxPosition target = BoxPosition.parseBoxPosition(grid, 3);
        assertEquals(Integer.valueOf(0),target.winner().get());
    }

    @Test
    public void testWinner1() {
        String grid = "·--·--·--·\n" +
                "|p1|p1|p1|\n" +
                "·--·--·--·\n" +
                "|p0|p1|p0|\n" +
                "·--·--·--·\n"+
                "|p1|p1|p0|\n"+
                "·--·--·--·\n";
        BoxPosition target = BoxPosition.parseBoxPosition(grid, 3);
        assertEquals(Integer.valueOf(1),target.winner().get());
    }

    @Test
    public void testWinner3() {
        String grid = "·--·--·--·\n" +
                "|p1|p0|p1|\n" +
                "·--·--·--·\n" +
                "|p0|p1|p0|\n" +
                "·--·--·--·\n"+
                "|p0|p1|   \n"+
                "·--·--·--·\n";
        BoxPosition target = BoxPosition.parseBoxPosition(grid, 3);
        assertFalse(target.winner().isPresent());
    }

    @Test
    public void testMove1() {
        String grid = "·--·--·--·\n" +
                "|p1|p0|p1|\n" +
                "·--·--·--·\n" +
                "|p0|p1|p0|\n" +
                "·--·--·--·\n"+
                "|p0|p1|   \n"+
                "·--·--·--·\n";
        BoxPosition target = BoxPosition.parseBoxPosition(grid, 3);
        BoxPosition moved = target.move(2, 2, "right",0);
        BoxPosition expected = BoxPosition.parseBoxPosition("·--·--·--·\n" +
                "|p1|p0|p1|\n" +
                "·--·--·--·\n" +
                "|p0|p1|p0|\n" +
                "·--·--·--·\n"+
                "|p0|p1|p0| \n"+
                "·--·--·--·\n", 3);
        assertEquals(expected, moved);
    }
}
