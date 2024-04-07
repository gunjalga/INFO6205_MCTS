package edu.neu.coe.info6205.mcts.dotsandboxes;

import edu.neu.coe.info6205.mcts.core.Node;
import edu.neu.coe.info6205.mcts.core.State;
import edu.neu.coe.info6205.mcts.tictactoe.TicTacToeNode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class DotsAndBoxesNode {


    public boolean isLeaf() {
        return state().isTerminal();
    }

    public State<DotsAndBoxes> state() {
        return state;
    }

    public Collection<Node<DotsAndBoxes>> children() {
        return children;
    }

    private double calculateUCTScore(DotsAndBoxesNode child) {
        double exploitationTerm = ((double) child.wins() / child.playouts());
        double explorationTerm = 2 * Math.sqrt(Math.log(playouts()) / child.playouts());
        return exploitationTerm + explorationTerm;
    }

    public int playouts() {
        return playouts;
    }

    public int wins() {
        return wins;
    }

    private void initializeNodeData() {
        if (isLeaf()) {
            playouts = 1;
            Optional<Integer> winner = state.winner();
            if (winner.isPresent())
                wins = 1; // CONSIDER check that the winner is the correct player. We shouldn't need to.

            else wins=0;// a draw.
        }
    }

    public DotsAndBoxesNode(State<DotsAndBoxes> state) {
        this.state = state;
        children = new ArrayList<>();
        initializeNodeData();
    }

    private final State<DotsAndBoxes> state;
    private final ArrayList<Node<DotsAndBoxes>> children;

    private Node<DotsAndBoxes> parent;
    private boolean isFullyExpanded;
    private int wins;
    private int playouts;
}
