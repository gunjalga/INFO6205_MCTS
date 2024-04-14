package edu.neu.coe.info6205.mcts.dotsandboxes;

import edu.neu.coe.info6205.mcts.core.Move;
import edu.neu.coe.info6205.mcts.core.Node;
import edu.neu.coe.info6205.mcts.core.State;
import edu.neu.coe.info6205.mcts.tictactoe.TicTacToe;
import edu.neu.coe.info6205.mcts.tictactoe.TicTacToeNode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

public class DotsAndBoxesNode implements Node<DotsAndBoxes>{


    public boolean isLeaf() {
        return state().isTerminal();
    }

    public State<DotsAndBoxes> state() {
        return state;
    }

    @Override
    public boolean white() {
        return false;
    }

    public Collection<Node<DotsAndBoxes>> children() {
        return children;
    }

    @Override
    public void backPropagate() {

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

    @Override
    public Node<DotsAndBoxes> selectChild() {
        return null;
    }

    @Override
    public void updateWins(int wins) {

    }

    public Node<DotsAndBoxes> addChild() {
//        will add a child and simulate a random game
        Node<DotsAndBoxes> newNode=null;
        for (Iterator<Move<DotsAndBoxes>> it = this.state.moveIterator(state.player()); it.hasNext(); )
        {
            State<DotsAndBoxes> newState = this.state.next(it.next());
            if(this.children().isEmpty()){
                newNode=new DotsAndBoxesNode(newState);
                newNode.updateParent(this);
                children.add(newNode);

                break;
            } else{
                if(!it.hasNext()){
                    this.isFullyExpanded=true;
                }
                if(!this.children.stream().anyMatch(node->node.state().position().equals(newState.position()))){
                    newNode=new DotsAndBoxesNode(newState);
                    newNode.updateParent(this);
                    children.add(newNode);
                    break;
                }

            }


        }
        return newNode;
//
    }

    private void initializeNodeData() {
        if (isLeaf()) {
            playouts = 1;
            Optional<Integer> winner = state.winner();
            if (winner.isPresent()&&winner.get()==0)
                wins = 1; // if computer wins

            else wins=0;// if human wins.
        }
    }

    public DotsAndBoxesNode(State<DotsAndBoxes> state) {
        this.state = state;
        children = new ArrayList<>();
        initializeNodeData();
    }

    public void updateParent(Node<DotsAndBoxes> node){
        this.parent=node;
    }

    @Override
    public boolean isFullyExpanded() {
        return false;
    }

    @Override
    public void simulateRandom() {

    }

    @Override
    public void addWins(int wins) {

    }

    @Override
    public void addPlayouts(int playouts) {

    }

    @Override
    public Node<DotsAndBoxes> getParent() {
        return null;
    }

    @Override
    public void updatePlayouts(int playouts) {

    }

    private final State<DotsAndBoxes> state;
    private final ArrayList<Node<DotsAndBoxes>> children;

    private Node<DotsAndBoxes> parent;
    private boolean isFullyExpanded;
    private int wins;
    private int playouts;
}
