package edu.neu.coe.info6205.mcts.dotsandboxes;

import edu.neu.coe.info6205.mcts.core.Move;
import edu.neu.coe.info6205.mcts.core.Node;
import edu.neu.coe.info6205.mcts.core.State;
import edu.neu.coe.info6205.mcts.tictactoe.MCTS;

import java.util.*;

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
        playouts=0;
        wins=0;
        for(Node<DotsAndBoxes> child:children){
            wins+=child.wins();
            playouts+=child.playouts();
        }

    }

    private double calculateUCTScore(DotsAndBoxesNode child) {
        double exploitationTerm = ((double) child.wins() / child.playouts());
        double explorationTerm = 1.4 * Math.sqrt(Math.log(playouts()) / child.playouts());
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
        double bestScore = Double.NEGATIVE_INFINITY;
        ArrayList<Node<DotsAndBoxes>> bestChild = new ArrayList<>();
        double uctScore;
        for (Node<DotsAndBoxes> child : children) {
//            if(child.state().isTerminal()){
//                uctScore=Double.NEGATIVE_INFINITY;
//            }else{
            uctScore = calculateUCTScore((DotsAndBoxesNode) child);
//            }
            if (uctScore > bestScore) {
                bestScore = uctScore;
                bestChild.clear();
                bestChild.add(child);
            } else if (uctScore==bestScore) {
                bestChild.add(child);
            }
        }

        Node<DotsAndBoxes> selectedChild= bestChild.get(new Random().nextInt(bestChild.size()));
        for (Node<DotsAndBoxes> child : bestChild) {
            int maxWins= Integer.MIN_VALUE;
            int wins = child.wins(); // Get the number of wins for the current child
            if (wins > maxWins) {
                // Update the best child and the maximum number of wins
                selectedChild = child;
                maxWins = wins;
            }
        }
//                nodeComparator.findBestMove(bestChild);
//                bestChild.get(new Random().nextInt(bestChild.size()));
//        System.out.println("Best score:");
//        System.out.println(bestScore+selectedChild.state().boxPosition().render());
        return selectedChild;
    }

    @Override
    public void updateWins(int wins) {
        this.wins=wins;
    }

    public Node<DotsAndBoxes> addChild() {
//        will add a child and simulate a random game
        Node<DotsAndBoxes> newNode=null;
        State<DotsAndBoxes>temp=new DotsAndBoxes().new DotsAndBoxesState(this.state);

        for (Iterator<Move<DotsAndBoxes>> it = temp.moveIterator(this.state.player()); it.hasNext(); )
        {
            State<DotsAndBoxes>tempCopy=new DotsAndBoxes().new DotsAndBoxesState(this.state);
            State<DotsAndBoxes> newState = tempCopy.next(it.next());

            if(this.children().isEmpty()){
                newNode=new DotsAndBoxesNode(newState);
                newNode.resetWins();
                newNode.resetPlayOuts();
                newNode.updateParent(this);
                children.add(newNode);

                break;
            } else{
                if(!it.hasNext()){
                    this.isFullyExpanded=true;
                }
                if(!this.children.stream().anyMatch
                        (node->node.state().boxPosition().
                                equals(newState.boxPosition()))){
                    newNode=new DotsAndBoxesNode(newState);
                    newNode.resetWins();
                    newNode.resetPlayOuts();
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
            if (winner.isPresent())
            {
                if(winner.get()==0||winner.get()==1){
                    wins = 1;
                }
                // if computer wins

                else wins=0;// if human wins.
            }
            else wins=0;
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
        return isFullyExpanded;
    }

    @Override
    public void simulateRandom() {
        int currentPlayer = this.state.player();
//        if(this.state.boxPosition().boxCaptured)
//            currentPlayer=1-this.state.player();
//        else{
//            currentPlayer=this.state.player();
//        }
        int levelPlayer=-1;
        if(this.state.boxPosition().boxCaptured){
            levelPlayer=currentPlayer;
        }else{
            levelPlayer=1-currentPlayer;
        }
        State<DotsAndBoxes>newState=new DotsAndBoxes().new DotsAndBoxesState(this.state);
        boolean isBoxCaptured;
        while(!newState.isTerminal()){
            isBoxCaptured=false;
            State<DotsAndBoxes> temp=(DotsAndBoxes.DotsAndBoxesState)newState.next(newState.chooseMove(currentPlayer));
            isBoxCaptured=temp.boxPosition().boxCaptured;
            if(!isBoxCaptured){
                currentPlayer=1-currentPlayer;
            }
            newState=temp;
//            if(!temp.boxPosition().boxCaptured) {
//                currentPlayer = 1 - currentPlayer;
//            }
        }
        currentPlayer=newState.winner().get();
        Node<DotsAndBoxes> tempNode = new DotsAndBoxesNode(newState);
        this.updatePlayouts(tempNode.playouts());
        if(newState.winner().isPresent()){
            this.updateWins(levelPlayer==currentPlayer?tempNode.wins():-tempNode.wins());
        }
        else{
            this.updateWins(0);
        }
    }

    @Override
    public void addWins(int wins) {
        this.wins+=wins;
    }

    @Override
    public void addPlayouts(int playouts) {
        this.playouts+=playouts;
    }

    @Override
    public void resetWins() {
        this.wins=0;
    }

    @Override
    public void resetPlayOuts() {
        this.playouts=0;
    }

    @Override
    public Node<DotsAndBoxes> getParent() {
        return this.parent;
    }

    @Override
    public void updatePlayouts(int playouts) {
        this.playouts=playouts;
    }

    private final State<DotsAndBoxes> state;
    private final ArrayList<Node<DotsAndBoxes>> children;

    private Node<DotsAndBoxes> parent;
    private boolean isFullyExpanded;
    private int wins;
    private int playouts;

    private class NodeComparator implements Comparator<DotsAndBoxesNode>{

        @Override
        public int compare(DotsAndBoxesNode o1, DotsAndBoxesNode o2) {
            return Integer.compare(o1.wins(),o2.wins());
        }

        public Node<DotsAndBoxes> findBestMove(ArrayList<Node<DotsAndBoxes>> childs){
            PriorityQueue<Node<DotsAndBoxes>> priorityQueue = new PriorityQueue<>();
            for(Node<DotsAndBoxes> child:childs){
                priorityQueue.add(child);
            }
//            while(!priorityQueue.isEmpty()){
//                Node<DotsAndBoxes> dotsAndBoxesNode = priorityQueue.poll();
//            }

            return priorityQueue.peek();
        }
    }
}
