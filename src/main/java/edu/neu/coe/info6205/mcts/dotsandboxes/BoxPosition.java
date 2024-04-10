package edu.neu.coe.info6205.mcts.dotsandboxes;

public class BoxPosition {
    Box[][] grid;
    private final static int gridSize = 3;
    static BoxPosition startingPostion (){
        Box[][] matrix = new Box[gridSize][gridSize];
        for (int i =0;i<gridSize;i++){
            for (int j=0;j<gridSize;j++){
                if(i==2&&j==2){
                    matrix[i][j]=new Box(true,true,true,true);
                }
                else matrix[i][j]=new Box();
            }
        }
        return new BoxPosition(matrix);
    }

    public BoxPosition(Box[][] grid){
        this.grid=grid;
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
                    if(i==2&&j==2){
                        sb.append("p1");
                    }else{
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

    public BoxPosition move(int x, int y,String direction){
        if(direction.toLowerCase().equals("left")){
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


        return new BoxPosition(grid);
    }

}
