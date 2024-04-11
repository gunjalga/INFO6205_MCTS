package edu.neu.coe.info6205.mcts.dotsandboxes;

import edu.neu.coe.info6205.mcts.core.Game;
import edu.neu.coe.info6205.mcts.core.Move;
import edu.neu.coe.info6205.mcts.core.State;
import edu.neu.coe.info6205.mcts.tictactoe.Position;
import edu.neu.coe.info6205.mcts.tictactoe.TicTacToe;


import java.util.*;

public class DotsAndBoxes implements Game<DotsAndBoxes> {

    public static final int X = 1;
    public static final int O = 0;
    public static final int blank = -1;

    class DotsAndBoxesState implements State<DotsAndBoxes>{
        private final BoxPosition position;

        public DotsAndBoxesState(BoxPosition boxPosition){
            this.position=boxPosition;
        }
        @Override
        public DotsAndBoxes game() {
            return DotsAndBoxes.this;
        }

        @Override
        public boolean isTerminal() {
            return position.full();
        }

//        @Override
        public Position position() {
            return null;
        }

        @Override
        public BoxPosition boxPosition() {
            return this.position;
        }

        @Override
        public int player() {
            return switch (position.last) {
                case 0, -1 -> X;
                case 1 -> O;
                default -> blank;
            };
        }

        @Override
        public Optional<Integer> winner() {
            return Optional.empty();
        }

        @Override
        public Random random() {
            return null;
        }

        @Override
        public Collection<Move<DotsAndBoxes>> moves(int player) {
            if (player == position.last) throw new RuntimeException("consecutive moves by same player: " + player);
            List<Box> moves = position.moves(player);
            ArrayList<Move<DotsAndBoxes>> list = new ArrayList<>();
            System.out.println(list);
//            for (Box[] coordinates : moves) list.add(new TicTacToe.TicTacToeMove(player, coordinates[0], coordinates[1]));
            return null;
        }

        @Override
        public State<DotsAndBoxes> next(Move<DotsAndBoxes> move) {
            return null;
        }
    }

    static class DotsAndBoxesMove implements Move<DotsAndBoxes>{

        @Override
        public int player() {
            return player;
        }

        public DotsAndBoxesMove(int i,int j, String direction, int player){
            this.player=player;
        }
        private final int player;
    }
    @Override
    public State<DotsAndBoxes> start() {
        return null;
    }

    @Override
    public int opener() {
        return 0;
    }

    public static void main(String[] args) {
        BoxPosition p = BoxPosition.startingPostion();
//        System.out.println(p.render());
        BoxPosition p1=p.move(1,1,"left",0);
//        System.out.println(p1.render());

        BoxPosition p2=p1.move(1,1,"right",1);
        BoxPosition p3=p2.move(1,1,"top",0);
        BoxPosition p4=p3.move(1,1,"bottom",0);
        System.out.println(p4.render());

        List<Box> moves=p4.moves(1);
        System.out.println(moves.size());
    }

    static BoxPosition startingPosition() {
        return BoxPosition.startingPostion();
    }
}
