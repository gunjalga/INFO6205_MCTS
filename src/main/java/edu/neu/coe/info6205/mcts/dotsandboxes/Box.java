package edu.neu.coe.info6205.mcts.dotsandboxes;

public class Box {
    boolean top=false,bottom=false,right=false,left=false;
    int owner,x,y;
    Box(int x,int y,String direction){
        this.x=x;
        this.y=y;
        switch (direction){
            case "left":this.left=true;
                break;
            case "right":this.right=true;
                break;
            case "top":this.top=true;
                break;
            case "bottom":this.bottom=true;
                break;
        }
    }
    Box(){
        this.top=false;
        this.bottom=false;
        this.right=false;
        this.left=false;
        this.owner=-1;
    }
    Box(boolean top,boolean bottom, boolean right, boolean left, int owner,int x,int y){
        this.top=top;
        this.bottom=bottom;
        this.right=right;
        this.left=left;
        this.owner=owner;
        this.x=x;
        this.y=y;
    }

    public Box(Box other) {
        this.top = other.top;
        this.bottom = other.bottom;
        this.right = other.right;
        this.left = other.left;
        this.owner = other.owner;
        this.x = other.x;
        this.y = other.y;
    }
}
