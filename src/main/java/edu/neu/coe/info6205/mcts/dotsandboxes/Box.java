package edu.neu.coe.info6205.mcts.dotsandboxes;

public class Box {
    boolean top,bottom,right,left;
    int owner;

    Box(){
        this.top=false;
        this.bottom=false;
        this.right=false;
        this.left=false;
        this.owner=-1;
    }
    Box(boolean top,boolean bottom, boolean right, boolean left, int owner){
        this.top=top;
        this.bottom=bottom;
        this.right=right;
        this.left=left;
        this.owner=owner;
    }
}
