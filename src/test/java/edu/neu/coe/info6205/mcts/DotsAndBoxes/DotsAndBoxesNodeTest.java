package edu.neu.coe.info6205.mcts.DotsAndBoxes;

import edu.neu.coe.info6205.mcts.core.State;
import edu.neu.coe.info6205.mcts.dotsandboxes.BoxPosition;
import edu.neu.coe.info6205.mcts.dotsandboxes.DotsAndBoxes;
import edu.neu.coe.info6205.mcts.dotsandboxes.DotsAndBoxesNode;
import io.cucumber.java.eo.Do;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DotsAndBoxesNodeTest {
    @Test
    public void state(){
        String input = "·--·--·  ·\n" +
                "|p1|     |\n" +
                "·--·  ·  ·\n" +
                "|     |   \n" +
                "·--·--·  ·\n"+
                "|p0|     |\n"+
                "·--·  ·  ·";
        DotsAndBoxes.DotsAndBoxesState state= new DotsAndBoxes().new DotsAndBoxesState(BoxPosition.parseBoxPosition(input,3));
        DotsAndBoxesNode node= new DotsAndBoxesNode(state);
        assertEquals(state, node.state());
    }

    @Test
    public void winsAndPlayouts(){
        String input = "·--·--·--·\n" +
                "|p1|p0|p1|\n" +
                "·--·--·--·\n" +
                "|p0|p1|p0|\n" +
                "·--·--·--·\n"+
                "|p0|p0|p0|\n"+
                "·--·--·--·";
        DotsAndBoxes.DotsAndBoxesState state= new DotsAndBoxes().new DotsAndBoxesState(BoxPosition.parseBoxPosition(input,3));
        DotsAndBoxesNode node= new DotsAndBoxesNode(state);
        System.out.println(node.state().boxPosition().render());
        assertEquals(true,node.isLeaf());
        assertEquals(1, node.wins());
        assertEquals(1, node.playouts());
    }


}
