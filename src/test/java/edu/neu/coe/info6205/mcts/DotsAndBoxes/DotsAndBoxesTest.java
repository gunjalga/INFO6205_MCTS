package edu.neu.coe.info6205.mcts.DotsAndBoxes;

import edu.neu.coe.info6205.mcts.core.State;
import edu.neu.coe.info6205.mcts.dotsandboxes.DotsAndBoxes;
import edu.neu.coe.info6205.mcts.tictactoe.TicTacToe;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class DotsAndBoxesTest {

    @Test
    public void runGame() {
        long seed = 42;
        DotsAndBoxes target = new DotsAndBoxes(seed); // games run here will all be deterministic.
        State<DotsAndBoxes> state = target.runGame();
        Optional<Integer> winner = state.winner();
        if (winner.isPresent()) assertEquals(Integer.valueOf(0), winner.get());
        else fail("no winner");
    }
}
