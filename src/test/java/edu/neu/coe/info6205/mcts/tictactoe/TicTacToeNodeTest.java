package edu.neu.coe.info6205.mcts.tictactoe;

import edu.neu.coe.info6205.mcts.core.Node;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TicTacToeNodeTest {

    @Test
    public void winsAndPlayouts() {
        TicTacToe.TicTacToeState state = new TicTacToe().new TicTacToeState(Position.parsePosition("X . 0\nX O .\nX . 0", TicTacToe.X));
        TicTacToeNode node = new TicTacToeNode(state);
        assertTrue(node.isLeaf());
        assertEquals(1, node.wins());
        assertEquals(1, node.playouts());
    }

    @Test
    public void state() {
        TicTacToe.TicTacToeState state = new TicTacToe().new TicTacToeState();
        TicTacToeNode node = new TicTacToeNode(state);
        assertEquals(state, node.state());
    }

    @Test
    public void white() {
        TicTacToe.TicTacToeState state = new TicTacToe().new TicTacToeState();
        TicTacToeNode node = new TicTacToeNode(state);
        assertTrue(node.white());
    }

    @Test
    public void children() {
        TicTacToe.TicTacToeState state = new TicTacToe().new TicTacToeState(Position.parsePosition("X . 0\nX O .\nX . 0", TicTacToe.X));
        TicTacToeNode node = new TicTacToeNode(state);
        while (!node.isFullyExpanded()){
            node.addChild();
        }
//        node.selectChild();
        assertEquals(node.children().size(),3);
        // no tests yet

    }

    @Test
    public void simulateRandom(){
        TicTacToe.TicTacToeState state = new TicTacToe().new TicTacToeState(Position.parsePosition("X . 0\nX O .\nX . 0", TicTacToe.X));
        TicTacToeNode node = new TicTacToeNode(state);
        node.simulateRandom();
        assertEquals(true,node.state().isTerminal());
    }
    @Test
    public void addChild() {
        TicTacToe.TicTacToeState state = new TicTacToe().new TicTacToeState(Position.parsePosition("X X 0\n0 O .\nX X 0", TicTacToe.X));
        TicTacToeNode node = new TicTacToeNode(state);
        node.addChild();
        assertTrue(node.children().size()==1);
        // no tests yet
    }

    @Test
    public void backPropagate() {
        // no tests yet
        TicTacToe.TicTacToeState state = new TicTacToe().new TicTacToeState(Position.parsePosition("X X 0\n0 O .\nX X 0", TicTacToe.X));
        TicTacToeNode root = new TicTacToeNode(state);
        MCTS mcts = new MCTS(root);
        Node<TicTacToe> node2=MCTS.selection(root);
        node2.simulateRandom();
        MCTS.backpropagate(node2);
//      0 wins in this case and root has move of player 1, so the win of node2 (1)will go to root as -1
        assertEquals(-node2.wins(),root.wins());

    }

    @Test
    public void isLeaf(){
        TicTacToe.TicTacToeState state = new TicTacToe().new TicTacToeState(Position.parsePosition("X X 0\n0 O .\nX X 0", TicTacToe.X));
        TicTacToeNode node = new TicTacToeNode(state);
        assertEquals(false,node.isLeaf());
        while(!node.isFullyExpanded()){
            node.simulateRandom();
            node.addChild();
        }
        Node<TicTacToe> nodeChild= node.selectChild();
        assertTrue(nodeChild.isLeaf());


    }
}