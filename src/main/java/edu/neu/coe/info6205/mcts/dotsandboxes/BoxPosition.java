package edu.neu.coe.info6205.mcts.dotsandboxes;

import edu.neu.coe.info6205.mcts.tictactoe.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class BoxPosition {
    Box[][] grid;

    public int getLast() {
        return last;
    }

    final int last;

    public boolean isBoxCaptured() {
        return boxCaptured;
    }

    boolean boxCaptured;
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
        return new BoxPosition(matrix, -1, 0,false);
    }

//    "·--·--·  ·\n" +"|p1|p0|  |\n" + "·--·--·  ·\n" + "   |      \n" + "·  ·  ·  ·\n" + "          \n" + "·  ·--·  ·\n"
public static BoxPosition parseBoxPosition(String input, int gridSize) {
        Box[][] grid= new Box[3][3];
        for(int i=0;i<gridSize;i++){
            for(int j=0;j<gridSize;j++){
                grid[i][j]= new Box();
            }
        }
    BoxPosition boxPosition = new BoxPosition(grid,0,0,false);
    int count=0;
    String[] lines = input.split("\n");
    for (int i = 0; i < 7; i++) {
        if (i % 2 == 0) { // Processing horizontal lines
            String[] line=lines[i].split("·");
            for (int j = 0; j < gridSize; j++) {
                if(i<5){
                    boxPosition.grid[i/2][j].top = line[j+1].equals("--");
//                    if(i/2>0){
//                        boxPosition.grid[i/2-1][j].bottom = line[j+1].equals("--");
//                    }
                }
                if(i>0){
                    boxPosition.grid[i / 2-1][j].bottom = line[j+1].equals("--");
                }
            }
        } else { // Processing vertical lines
            for (int j = 0; j < gridSize *gridSize; j++) {
                int linePos=i/2;
                String currentLine=lines[i];
                if(j%3==0){
                    if(currentLine.charAt(j)=='|'){
                        boxPosition.grid[linePos][j/3].left=true;
                        if(j/3>0){
                            boxPosition.grid[linePos][j/3-1].right=true;
                        }
                    }

                }
                else if(j==8 && currentLine.charAt(9)=='|'){
                    boxPosition.grid[linePos][2].right=true;
                }
                else{
                    if(j%3==2){
                        int pos=j/3;

                        if(currentLine.charAt(j)=='0'){
                            boxPosition.grid[linePos][pos].owner=0;
                        }
                        if(currentLine.charAt(j)=='1'){
                            boxPosition.grid[linePos][pos].owner=1;
                        }
                    }
                }
            }
        }
    }
    return boxPosition;
}


    public BoxPosition(Box[][] grid, int last,int count,boolean boxCaptured){
        this.grid=grid;
        this.last=last;
        this.count=count;
        this.boxCaptured=boxCaptured;
    }
    public BoxPosition(BoxPosition boxPosition){
        this.grid=boxPosition.copyGrid();
        this.last=boxPosition.last;
        this.count=boxPosition.count;
    }
    public int getCount(){
        return this.count;
    }

    public Box[][] copyGrid() {
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
        this.boxCaptured=false;
        if(direction.toLowerCase().equals("left")){

            if(y>=0 && grid[x][y].left){
                System.out.println("Move Already made, Make another move");
                return this;
            }
            if(y>0){
                grid[x][y-1].right=true;
                if(grid[x][y-1].left && grid[x][y-1].right && grid[x][y-1].top && grid[x][y-1].bottom){
                    grid[x][y-1].owner=player;
                    boxCaptured=true;
                }
            }
            grid[x][y].left=true;
        }else if(direction.toLowerCase().equals("right")){

            if(grid[x][y].right){
                System.out.println("Move Already made, Make another move");
                return this;
            }
            if(y<gridSize-1){
                grid[x][y+1].left=true;
                if(grid[x][y+1].left && grid[x][y+1].right && grid[x][y+1].top && grid[x][y+1].bottom){
                    grid[x][y+1].owner=player;
                    boxCaptured=true;
                }
            }
            grid[x][y].right=true;
        }else if(direction.toLowerCase().equals("top")){
            if(grid[x][y].top){
                System.out.println("Move Already made, Make another move");
                return this;
            }
            if(x>0){
                grid[x-1][y].bottom=true;
                if(grid[x-1][y].left && grid[x-1][y].right && grid[x-1][y].top && grid[x-1][y].bottom){
                    grid[x-1][y].owner=player;
                    boxCaptured=true;
                }
            }
            grid[x][y].top=true;
        }else if(direction.toLowerCase().equals("bottom")){
            if(grid[x][y].bottom){
                System.out.println("Move Already made, Make another move");
                return this;
            }
            if(x<gridSize-1){
                grid[x+1][y].top=true;
                if(grid[x+1][y].left && grid[x+1][y].right && grid[x+1][y].top && grid[x+1][y].bottom){
                    grid[x+1][y].owner=player;
                    boxCaptured=true;
                }
            }
            grid[x][y].bottom=true;
        }else{
            System.out.println("Please enter valid direction");
            return this;
        }

        if(grid[x][y].left && grid[x][y].right && grid[x][y].top && grid[x][y].bottom){
            grid[x][y].owner=player;
            boxCaptured=true;
        }

        BoxPosition newBoxPosition=new BoxPosition(grid,player,count+1,boxCaptured);
        if(boxCaptured){
            newBoxPosition.boxCaptured=true;
        }
        return newBoxPosition;
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
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BoxPosition)) return false;
        int equalCount=0;
        BoxPosition boxPosition = (BoxPosition) o;
        for(int i=0;i<gridSize;i++){
            for(int j=0;j<gridSize;j++){
                if(boxPosition.grid[i][j].equals(grid[i][j])){
                    equalCount++;
                }
            }
        }
        return equalCount==gridSize*gridSize;
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
        }else if(p1>p0){
            winner=1;
        }
        else winner=-1;
        if (full()) return Optional.of(winner);
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
        return count==gridSize*gridSize;
    }
    private final int count;
    @Override
    public String toString() {
        return this.render();
    }
}
