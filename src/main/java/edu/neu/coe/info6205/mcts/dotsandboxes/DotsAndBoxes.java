package edu.neu.coe.info6205.mcts.dotsandboxes;

import edu.neu.coe.info6205.mcts.core.Game;
import edu.neu.coe.info6205.mcts.core.Move;
import edu.neu.coe.info6205.mcts.core.State;
import edu.neu.coe.info6205.mcts.tictactoe.Position;


import java.util.*;

public class DotsAndBoxes implements Game<DotsAndBoxes> {

    public static final int X = 1;
    public static final int O = 0;
    public static final int blank = -1;

    private final Random random;
    public DotsAndBoxes(Random random){
        this.random=random;
    }
    public DotsAndBoxes(long seed) {
        this(new Random(seed));
    }
    public DotsAndBoxes() {
        this(System.currentTimeMillis());
    }
    public class DotsAndBoxesState implements State<DotsAndBoxes>{
        private final BoxPosition position;


        public DotsAndBoxesState(BoxPosition boxPosition){
            this.position=boxPosition;
        }
        public DotsAndBoxesState(State<DotsAndBoxes> state){
            this.position=new BoxPosition(state.boxPosition().copyGrid(),state.player(),state.boxPosition().getCount(),state.boxPosition().boxCaptured);
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
            if(this.boxPosition().boxCaptured){
                return position.last;
            }
            return switch (position.last) {
                case 0, -1 -> X;
                case 1 -> O;
                default -> blank;
            };
        }

        @Override
        public Optional<Integer> winner() {
            return position.winner();
        }

        @Override
        public Random random() {
            return random;
        }

        @Override
        public Collection<Move<DotsAndBoxes>> moves(int player) {
//            if (player == position.last) throw new RuntimeException("consecutive moves by same player: " + player);
            List<Box> moves = position.moves(player);
            ArrayList<Move<DotsAndBoxes>> list = new ArrayList<>();
            String direction="";
            for (Box coordinates : moves){
                if(coordinates.right){
                    direction="right";
                }
                if(coordinates.left){
                    direction="left";
                }
                if(coordinates.top){
                    direction="top";
                }
                if(coordinates.bottom){
                    direction="bottom";
                }
                list.add(new DotsAndBoxesMove(coordinates.x, coordinates.y,direction,player));
            }
            return list;
        }


        @Override
        public State<DotsAndBoxes> next(Move<DotsAndBoxes> move) {
            DotsAndBoxesMove dotsAndBoxesMove=(DotsAndBoxesMove) move;
            return new DotsAndBoxesState(position.move(dotsAndBoxesMove.i,dotsAndBoxesMove.j,dotsAndBoxesMove.direction, dotsAndBoxesMove.player()));
        }
//        private final BoxPosition position;
    }
    State<DotsAndBoxes> runGame() {
        int count=0;
        State<DotsAndBoxes> state = start();
        int player = 0;
        while (!state.isTerminal()) {
            count++;
//
            System.out.println( "Player:"+player);
            state=new DotsAndBoxes().new DotsAndBoxesState(state);

            State<DotsAndBoxes> newState = state.next(state.chooseMove(player));
//            if(newState.boxPosition().boxCaptured){
                player= newState.player();
//            }
            state=newState;


//            player= newState.player();
//            player=newState.boxPosition().last;
//            state=newState;
            System.out.println(state.boxPosition().render());

//            if(switchPlayer)
//            player = 1 - player;
            System.out.println("Move no:"+count+" Player:"+player+"Capurted a box? "+state.boxPosition().boxCaptured);

        }

        return state;
    }
    public static class DotsAndBoxesMove implements Move<DotsAndBoxes>{

        @Override
        public int player() {
            return player;
        }

        public DotsAndBoxesMove(int i,int j, String direction, int player){
            this.player=player;
            this.i=i;
            this.j=j;
            this.direction=direction;
        }

        Box move(){
            return new Box(this.i,this.j,this.direction);
        }
        final int i,j,player;
        String direction;
    }
    @Override
    public State<DotsAndBoxes> start() {
        return new DotsAndBoxesState(BoxPosition.startingPostion());
    }

    @Override
    public int opener() {
        return 0;
    }

    public static void main(String[] args) {
        State<DotsAndBoxes> state = new DotsAndBoxes().runGame();
        System.out.println(state.boxPosition().render());
        System.out.println("Winner:"+state.boxPosition().winner().get());
//        BoxPosition p = BoxPosition.startingPostion();
////        System.out.println(p.render());
//        BoxPosition p1=p.move(2,0,"left",0);
////        System.out.println(p1.render());
//
//        BoxPosition p2=p1.move(2,0,"right",1);
//        BoxPosition p3=p2.move(2,0,"top",0);
//        BoxPosition p4=p3.move(2,0,"bottom",1);
//        System.out.println(p4.render());
//
//        List<Box> moves=p3.moves(1);
//        System.out.println(moves.size());
//
//        DotsAndBoxesState state= new DotsAndBoxesState(p3);
//
//        Collection<Move<DotsAndBoxes>> randomMoves=state.moves(1);
//        List<Move<DotsAndBoxes>> listFromCollection = new ArrayList<>(randomMoves);
//        State<DotsAndBoxes> newState=state.next(listFromCollection.get(0));
//        System.out.println(newState.boxPosition().render());

    }

    static BoxPosition startingPosition() {
        return BoxPosition.startingPostion();
    }
}
