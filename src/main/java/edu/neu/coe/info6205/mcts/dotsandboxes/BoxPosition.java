package edu.neu.coe.info6205.mcts.dotsandboxes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BoxPosition {
    Box[][] grid;
    final int last;
    private final static int gridSize = 3;
    static BoxPosition startingPostion (){
        Box[][] matrix = new Box[gridSize][gridSize];
        for (int i =0;i<gridSize;i++){
            for (int j=0;j<gridSize;j++){
//                if(i==2&&j==2){
//                    matrix[i][j]=new Box(true,true,true,true,-1);
//                }
//                else
                    matrix[i][j]=new Box();
            }
        }
        return new BoxPosition(matrix, -1);
    }

    public BoxPosition(Box[][] grid, int last){
        this.grid=grid;
        this.last=last;
    }

    private Box[][] copyGrid() {
        Box[][] result = new Box[gridSize][gridSize];
        for (int i = 0; i < gridSize; i++)
            result[i] = Arrays.copyOf(grid[i], gridSize);
        return result;
    }

    public String render() {
        StringBuilder sb = new StringBuilder();

        // Render horizontal lines and dots
        for (int i = 0; i < gridSize+1; i++) {
            for (int j = 0; j < gridSize+1; j++) {
                sb.append("·");
                if(j<gridSize){
                    if(i==gridSize){
                        int k = i-1;
                        if(grid[k][j].bottom)sb.append("--");
                        else sb.append("  ");
                    }
                    else if (grid[i][j].top) {
                        sb.append("--");
                    } else {
                        sb.append("  ");
                    }
                }

            }
            sb.append("\n");
            if (i < gridSize ) {
                for (int j = 0; j < gridSize; j++) {
                    if (grid[i][j].left) {
                        sb.append("|");
                    } else {
                        sb.append(" ");
                    }
//                    if(i==2&&j==2){
//                        sb.append("p1");
//                    }
                    if(grid[i][j].left && grid[i][j].right && grid[i][j].top && grid[i][j].bottom){
                        sb.append(grid[i][j].owner);
                    }
                    else{
                        sb.append("  ");
                    }
                    if(j==gridSize-1) {
                    if(grid[i][j].right) {
                        sb.append("|");
                    }else {
                        sb.append(" ");
                    }
                    }

                }
                sb.append("\n");
            }
        }

        return sb.toString();
    }

    public BoxPosition move(int x, int y,String direction,int player){

        if(direction.toLowerCase().equals("left")){
            if(grid[x][y].left==true){
                throw new RuntimeException("This move has been already made");
            }
            if(y>0){
                grid[x][y-1].right=true;
            }
            grid[x][y].left=true;
        }else if(direction.toLowerCase().equals("right")){
            if(y<gridSize-1){
                grid[x][y+1].left=true;
            }
            grid[x][y].right=true;
        }else if(direction.toLowerCase().equals("top")){
            if(x>0){
                grid[x-1][y].bottom=true;
            }
            grid[x][y].top=true;
        }else if(direction.toLowerCase().equals("bottom")){
            if(x<gridSize-1){
                grid[x+1][y].top=true;
            }
            grid[x][y].bottom=true;
        }else{
            System.out.println("Please enter valid direction");
        }

        if(grid[x][y].left && grid[x][y].right && grid[x][y].top && grid[x][y].bottom){
            grid[x][y].owner=player;
        }


        return new BoxPosition(grid,player);
    }

    public List<Box> moves(int player){
        if (player == last) throw new RuntimeException("consecutive moves by same player: " + player);
        List<Box> result = new ArrayList<>();
        for (int i = 0; i < gridSize; i++)
            for (int j = 0; j < gridSize; j++)
                if (grid[i][j].owner < 0) {// TO BE IMPLEMENTED
                    result.add(new Box(grid[i][j].top, grid[i][j].bottom, grid[i][j].right,grid[i][j].left,grid[i][j].owner));
//                    System.out.println(Arrays.toString(new int[]{i, j}));
                }
        return result;
    }

    boolean full(){
        int count=0;
        for(int i=0;i<gridSize;i++){
            for(int j=0;j<gridSize;j++){
                if(grid[i][j].owner>-1){
                    count++;
                }
            }
        }
        return count==9;
    }

}
