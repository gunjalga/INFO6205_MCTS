package edu.neu.coe.info6205.mcts.DotsAndBoxes;

import edu.neu.coe.info6205.mcts.dotsandboxes.BoxPosition;
import org.junit.Test;

public class BoxPositionTest {

    @Test
    public void testBoxCaptured(){
        String input = "·--·--·  ·\n" +
                       "|p1|     |\n" +
                       "·--·  ·  ·\n" +
                       "|     |   \n" +
                       "·--·--·  ·\n"+
                       "|p0|     |\n"+
                       "·--·  ·  ·";
        BoxPosition boxPosition= BoxPosition.parseBoxPosition(input,3);
        System.out.println(boxPosition.render());
    }
}
