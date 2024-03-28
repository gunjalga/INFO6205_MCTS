package edu.neu.coe.info6205.mcts.tictactoe;

import edu.neu.coe.info6205.mcts.core.Move;
import edu.neu.coe.info6205.mcts.core.Node;
import edu.neu.coe.info6205.mcts.core.State;

import java.util.Collection;
import java.util.Scanner;

/**
 * Class to represent a Monte Carlo Tree Search for TicTacToe.
 */
public class MCTS {

    public static void main(String[] args) {
        MCTS mcts = new MCTS(new TicTacToeNode(new TicTacToe().new TicTacToeState()));
        Node<TicTacToe> root = mcts.root;

        root=new TicTacToeNode(takeMove(root,1));
        root=new TicTacToeNode(takeMove(root,0));
        root=new TicTacToeNode(takeMove(root,1));
        for(int i=0;i<2000;i++) {
//            Node<TicTacToe> root1 = root.selectChild();
//            if fully expanded then select or add child
            Node<TicTacToe>temp=selection(root);
            if(temp!=null)
            {temp.simulateRandom();
            backpropagate(temp);}

//            simulate from the node which was given by last lines(will return a temp node)
//            call backpropagate from the temp node(note don't add parent to temp node)

        }
        System.out.println(root.selectChild().state().position().render());
        // This is where you process the MCTS to try to win the game.
    }

    public static Node<TicTacToe> selection(Node<TicTacToe> root){
        if(root.isFullyExpanded()){
            return selection(root.selectChild());
        }

        return root.addChild();


    }
    public static void backpropagate(Node<TicTacToe> node){
        while(node.getParent()!=null){

            node.getParent().addWins(node.wins());
            node.getParent().addPlayouts(1);
            node= node.getParent();
        }
    }

    public MCTS(Node<TicTacToe> root) {
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
    private final Node<TicTacToe> root;
}