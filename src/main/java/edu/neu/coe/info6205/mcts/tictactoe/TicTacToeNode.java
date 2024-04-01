package edu.neu.coe.info6205.mcts.tictactoe;

import edu.neu.coe.info6205.mcts.core.Move;
import edu.neu.coe.info6205.mcts.core.Node;
import edu.neu.coe.info6205.mcts.core.State;

import java.util.*;

public class TicTacToeNode implements Node<TicTacToe> {

    /**
     * @return true if this node is a leaf node (in which case no further exploration is possible).
     */
    public boolean isLeaf() {
        return state().isTerminal();
    }

    /**
     * @return the State of the Game G that this Node represents.
     */
    public State<TicTacToe> state() {
        return state;
    }

    /**
     * Method to determine if the player who plays to this node is the opening player (by analogy with chess).
     * For this method, we assume that X goes first so is "white."
     * NOTE: this assumes a two-player game.
     *
     * @return true if this node represents a "white" move; false for "black."
     */
    public boolean white() {
        return state.player() == state.game().opener();
    }

    /**
     * @return the children of this Node.
     */
    public Collection<Node<TicTacToe>> children() {
        return children;
    }
    public void simulateRandom(){
        int currentPlayer = this.state.player();
        int levelPlayer = 1-currentPlayer;
        State<TicTacToe>newState=this.state;
        while(!newState.isTerminal()){
            newState=newState.next(newState.chooseMove(currentPlayer));
            currentPlayer=1-currentPlayer;
        }
        currentPlayer=1-currentPlayer;
        Node<TicTacToe> tempNode = new TicTacToeNode(newState);
        this.updatePlayouts(tempNode.playouts());
        if(newState.winner().isPresent()){
            this.updateWins(levelPlayer==currentPlayer?tempNode.wins():-tempNode.wins());
        }
        else{
            this.updateWins(0);
        }


    }
//    /**
//     * Method to add a child to this Node.
//     *
//     * @param state the State for the new chile.
//     */
    public Node<TicTacToe> addChild() {
//        will add a child and simulate a random game
        Node<TicTacToe>newNode=null;
        for (Iterator<Move<TicTacToe>> it = this.state.moveIterator(state.player()); it.hasNext(); )
        {
            State<TicTacToe> newState = this.state.next(it.next());
            if(this.children().isEmpty()){
                newNode=new TicTacToeNode(newState);
                newNode.updateParent(this);
                children.add(newNode);

                break;
            } else{
                if(!it.hasNext()){
                    this.isFullyExpanded=true;
                }
                if(!this.children.stream().anyMatch(node->node.state().position().equals(newState.position()))){
                    newNode=new TicTacToeNode(newState);
                    newNode.updateParent(this);
                    children.add(newNode);
                    break;
                }

            }


        }
        return newNode;
//        State<TicTacToe> randomState=simulateRamdom(state);
//        Node<TicTacToe> randomNode= new TicTacToeNode(randomState);
//        Node<TicTacToe> newNode = new TicTacToeNode(state);
//        newNode.updateWins(randomNode.wins());
//        newNode.updatePlayouts(randomNode.playouts());
//        newNode.updateParent(this);
//        children.add(newNode);
//        return newNode;
    }

    public Node<TicTacToe> selectChild() {
//        if children empty return same node
        double bestScore = Double.NEGATIVE_INFINITY;
        List<Node<TicTacToe>> bestChild = new ArrayList<>();
        double uctScore;
        for (Node<TicTacToe> child : children) {
//            if(child.state().isTerminal()){
//                uctScore=Double.NEGATIVE_INFINITY;
//            }else{
                uctScore = calculateUCTScore((TicTacToeNode) child);
//            }
            if (uctScore > bestScore) {
                bestScore = uctScore;
                bestChild.clear();
                bestChild.add(child);
            } else if (uctScore==bestScore) {
                bestChild.add(child);
            }
        }

        return bestChild.get(new Random().nextInt(bestChild.size()));
    }
    private double calculateUCTScore(TicTacToeNode child) {
        double exploitationTerm = ((double) child.wins() / child.playouts());
        double explorationTerm = 2 * Math.sqrt(Math.log(playouts()) / child.playouts());
        return exploitationTerm + explorationTerm;
    }
    /**
     * This method sets the number of wins and playouts according to the children states.
     */
    public void backPropagate() {
        playouts = 0;
        wins = 0;
        for (Node<TicTacToe> child : children) {
            wins += child.wins();
            playouts += child.playouts();
        }
    }
    public void updateWins(int wins){
        this.wins=wins;
    }
    public void addWins(int wins){
        this.wins+=wins;
    }
    public void addPlayouts(int playouts){
        this.playouts+=playouts;
    }
    public void updateParent(Node<TicTacToe> node){
        this.parent=node;
    }
    public void updatePlayouts(int playouts){
        this.playouts=playouts;
    }
    /**
     * @return the score for this Node and its descendents a win is worth 2 points, a draw is worth 1 point.
     */
    public int wins() {
        return wins;
    }

    /**
     * @return the number of playouts evaluated (including this node). A leaf node will have a playouts value of 1.
     */
    public int playouts() {
        return playouts;
    }
    public boolean isFullyExpanded() {
        return isFullyExpanded;
    }
    public Node<TicTacToe> getParent(){
        return this.parent;
    }

    public TicTacToeNode(State<TicTacToe> state) {
        this.state = state;
        children = new ArrayList<>();
        initializeNodeData();
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

    private final State<TicTacToe> state;
    private final ArrayList<Node<TicTacToe>> children;

    private Node<TicTacToe> parent;
    private boolean isFullyExpanded;
    private int wins;
    private int playouts;
}