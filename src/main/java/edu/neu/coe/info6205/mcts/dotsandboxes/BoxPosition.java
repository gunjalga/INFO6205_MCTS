package edu.neu.coe.info6205.mcts.dotsandboxes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class BoxPosition {
    Box[][] grid;
    final int last;
    private final static int gridSize = 3;
    public int getGridSize(){
        return gridSize;
    }
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
        return new BoxPosition(matrix, -1, 0);
    }

    public BoxPosition(Box[][] grid, int last,int count){
        this.grid=grid;
        this.last=last;
        this.count=count;
    }

    private Box[][] copyGrid() {
        Box[][] result = new Box[gridSize][gridSize];
        for (int i = 0; i < gridSize; i++){
            for(int j=0;j<gridSize;j++){
                result[i][j]=new Box(grid[i][j]);
            }
        }
//            result[i] = Arrays.copyOf(grid[i], gridSize);
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
                        sb.append("p");
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
        int repeatPlayer= player;
        if(direction.toLowerCase().equals("left")){
//            if(grid[x][y].left==true){
//                throw new RuntimeException("This move has been already made");
//            }
            if(y>0){
                grid[x][y-1].right=true;
                if(grid[x][y-1].left && grid[x][y-1].right && grid[x][y-1].top && grid[x][y-1].bottom){
                    grid[x][y-1].owner=player;
                    repeatPlayer=1-player;
                }
            }
            grid[x][y].left=true;
        }else if(direction.toLowerCase().equals("right")){
            if(y<gridSize-1){
                grid[x][y+1].left=true;
                if(grid[x][y+1].left && grid[x][y+1].right && grid[x][y+1].top && grid[x][y+1].bottom){
                    grid[x][y+1].owner=player;
                    repeatPlayer=1-player;
                }
            }
            grid[x][y].right=true;
        }else if(direction.toLowerCase().equals("top")){
            if(x>0){
                grid[x-1][y].bottom=true;
                if(grid[x-1][y].left && grid[x-1][y].right && grid[x-1][y].top && grid[x-1][y].bottom){
                    grid[x-1][y].owner=player;
                    repeatPlayer=1-player;
                }
            }
            grid[x][y].top=true;
        }else if(direction.toLowerCase().equals("bottom")){
            if(x<gridSize-1){
                grid[x+1][y].top=true;
                if(grid[x+1][y].left && grid[x+1][y].right && grid[x+1][y].top && grid[x+1][y].bottom){
                    grid[x+1][y].owner=player;
                    repeatPlayer=1-player;
                }
            }
            grid[x][y].bottom=true;
        }else{
            System.out.println("Please enter valid direction");
        }

        if(grid[x][y].left && grid[x][y].right && grid[x][y].top && grid[x][y].bottom){
            grid[x][y].owner=player;
            repeatPlayer=1-player;
        }


        return new BoxPosition(grid,repeatPlayer,count+1);
    }

    public List<Box> moves(int player){
//        if (player == last) throw new RuntimeException("consecutive moves by same player: " + player);
        List<Box> result = new ArrayList<>();
        Box[][] copy=copyGrid();
        for (int i = 0; i < gridSize; i++)
            for (int j = 0; j < gridSize; j++)
                if (copy[i][j].owner < 0) {
                    Box temp=copy[i][j];
                    if(!temp.left){
                        if(j>0){
                            copy[i][j-1].right=true;
                        }
                        copy[i][j].left=true;
                        result.add(new Box(i,j,"left"));
//                        result.add(new Box(temp.top, temp.bottom, temp.right, true, temp.owner,i,j));
                    }
                    if(!temp.right){
                        if(j<gridSize-1){
                            copy[i][j+1].left=true;
                        }
                        copy[i][j].right=true;
                        result.add(new Box(i,j,"right"));
//                        result.add(new Box(temp.top, temp.bottom, true, temp.left, temp.owner,i,j));
                    }
                    if(!temp.top){
                        if(i>0){
                            copy[i-1][j].bottom=true;
                        }
                        copy[i][j].top=true;
                        result.add(new Box(i,j,"top"));
//                        result.add(new Box(true, temp.bottom, temp.right, temp.left, temp.owner,i,j));
                    }
                    if(!temp.bottom){
                        if(i<gridSize-1){
                            copy[i+1][j].top=true;
                        }
                        copy[i][j].bottom=true;
                        result.add(new Box(i,j,"bottom"));
//                        result.add(new Box(temp.top, true, temp.right, temp.left, temp.owner,i,j));
                    }
                }
        return result;
    }

    public Optional<Integer> winner() {
        int count=0;
        int winner;
        int p0=0;
        int p1=0;
        for(int i=0;i<gridSize;i++){
            for(int j=0;j<gridSize;j++){
                if(grid[i][j].owner>-1){
                    count++;
                    if(grid[i][j].owner==0){
                        p0++;
                    }else if(grid[i][j].owner==1){
                        p1++;
                    }
                }
            }
        }
        if(p0>p1){
            winner=0;
        }else{
            winner=1;
        }
        if (count ==9) return Optional.of(winner);
        return Optional.empty();
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
    private final int count;
    @Override
    public String toString() {
        return this.render();
    }
}
