package edu.neu.coe.info6205.mcts.tictactoe;

import edu.neu.coe.info6205.mcts.core.Game;
import edu.neu.coe.info6205.mcts.core.Move;
import edu.neu.coe.info6205.mcts.core.Node;
import edu.neu.coe.info6205.mcts.core.State;
import edu.neu.coe.info6205.mcts.dotsandboxes.DotsAndBoxes;
import edu.neu.coe.info6205.mcts.dotsandboxes.DotsAndBoxesNode;

import java.util.Collection;
import java.util.Random;
import java.util.Scanner;

/**
 * Class to represent a Monte Carlo Tree Search for TicTacToe.
 */
public class MCTS<G extends Game > {

    public static void main(String[] args) {
//        MCTS mcts = new MCTS(new DotsAndBoxesNode(new DotsAndBoxes().start()));
////        System.out.println(mcts.root.state().boxPosition().render());
//        Node<DotsAndBoxes>root=mcts.root;
//
////        Node<DotsAndBoxes> result= new DotsAndBoxesNode(root.state());
//        for(int i=0;i<500;i++){
//            Node<DotsAndBoxes> child=selectionNode(root);
//            if(child!=null){
//                child.simulateRandom();
//                backPropagateBox(child);
//            }
//
//        }
//        Node<DotsAndBoxes> child=root.selectChild();
//        System.out.println(child.state().boxPosition().render());
        startDotAndBoxes();
//        System.out.println(result.state().boxPosition().render());
//        System.out.println(n1.state().boxPosition().render());
//        startTicTacToe();
        // This is where you process the MCTS to try to win the game.
    }
    public static void startDotAndBoxes(){
        MCTS mcts = new MCTS(new DotsAndBoxesNode(new DotsAndBoxes().start()));
        Node<DotsAndBoxes> root = mcts.root;

        while(!root.state().isTerminal()) {
            root = new DotsAndBoxesNode(takeMoveDB(root, 1));
            if(root.state().isTerminal()){
                break;
            }
            for (int i = 0; i < 1000; i++) {
//            if fully expanded then select or add child
                Node<DotsAndBoxes> temp = selectionNode(root);
                if (temp != null ) {
                    temp.simulateRandom();
                    backPropagateBox(temp);
                }

//            simulate from the node which was given by last lines(will return a temp node)
//            call backpropagate from the temp node(note don't add parent to temp node)

            }
//            Position computerMovePosition=root.selectChild().state().position();

            Node<DotsAndBoxes> child=root.selectChild();
            System.out.println(child.state().boxPosition().render());
            if(!root.state().isTerminal()){
                root= new DotsAndBoxesNode(makeComputerMoveDB(child));
            }else{
                break;
            }


        }
        int winner= root.state().player()-1;
        System.out.println("Winner:"+winner);
    }


    public static void startTicTacToe(){
        MCTS mcts = new MCTS(new TicTacToeNode(new TicTacToe().new TicTacToeState()));
        Node<TicTacToe> root = mcts.root;

        while(!root.state().isTerminal()) {
            root = new TicTacToeNode(takeMove(root, 1));
            if(root.state().isTerminal()){
                break;
            }
            for (int i = 0; i < 1000; i++) {
//            if fully expanded then select or add child
                Node<TicTacToe> temp = selection(root);
                if (temp != null ) {
                    temp.simulateRandom();
                    backpropagate(temp);
                }

//            simulate from the node which was given by last lines(will return a temp node)
//            call backpropagate from the temp node(note don't add parent to temp node)

            }
//            Position computerMovePosition=root.selectChild().state().position();

            Node<TicTacToe> child=root.selectChild();
            System.out.println(child.state().position().render());
            if(!root.state().isTerminal()){
                root= new TicTacToeNode(makeComputerMove(child,child.state().position()));
            }else{
                break;
            }


        }
        int winner= root.state().player()-1;
        System.out.println("Winner:"+winner);
    }

    public static Node<DotsAndBoxes> selectionNode(Node<DotsAndBoxes> root){
        if(root.isFullyExpanded() && !root.state().isTerminal()){
            return selectionNode(root.selectChild());
        }else if(root.state().winner().isPresent()){
            return root;
        }
        return root.addChild();
    }

    public static Node<TicTacToe> selection(Node<TicTacToe> root){
        if(root.isFullyExpanded() && !root.state().isTerminal()){
            return selection(root.selectChild());
        }else if(root.state().winner().isPresent()){
            return root;
        }

        return root.addChild();


    }

    public static void backPropagateBox(Node<DotsAndBoxes> node){
        Node<DotsAndBoxes>leafNode=node;
        int tempWins= leafNode.wins();
        while(node.getParent()!=null){
            tempWins=-tempWins;
            node.getParent().addWins(tempWins);
            node.getParent().addPlayouts(1);
            node= node.getParent();
        }
    }
    public static void backpropagate(Node<TicTacToe> node){
        Node<TicTacToe>leafNode = node;
        int tempWins= leafNode.wins();
        while(node.getParent()!=null){
            tempWins=-tempWins;
            node.getParent().addWins(tempWins);
            node.getParent().addPlayouts(1);
            node= node.getParent();
        }
    }

    public MCTS(Node<G> root) {
        this.root = root;
    }
    public static State takeMove(Node<TicTacToe> root,int player){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter i and j:");
        int i=sc.nextInt();
        int j=sc.nextInt();
        TicTacToe.TicTacToeMove userMove = new TicTacToe.TicTacToeMove(player,i,j);
        return  root.state().next(userMove);
    }
    public static State takeMoveDB(Node<DotsAndBoxes> root,int player){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter i and j:");
        int i=sc.nextInt();
        int j=sc.nextInt();
        String direction=sc.next();
        DotsAndBoxes.DotsAndBoxesMove userMove = new DotsAndBoxes.DotsAndBoxesMove(i,j,direction,player);
        State<DotsAndBoxes>movePlayed =root.state().next(userMove);
        while (player!= movePlayed.player()){
             sc = new Scanner(System.in);
            System.out.println("Enter i and j:");
             i=sc.nextInt();
             j=sc.nextInt();
             direction=sc.next();
             userMove = new DotsAndBoxes.DotsAndBoxesMove(i,j,direction,player);
            movePlayed =root.state().next(userMove);
        }
        return movePlayed;
    }

    public static State makeComputerMove(Node<TicTacToe> root,Position position){
        return new TicTacToe(). new TicTacToeState(root.state().position());
//        return null;
    }
    public static State makeComputerMoveDB(Node<DotsAndBoxes> root){
        return new DotsAndBoxes(). new DotsAndBoxesState(root.state().boxPosition());
//        return null;
    }
    private final Node<G> root;

    public Node<G> getRoot() {
        return root;
    }
}