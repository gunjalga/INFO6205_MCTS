package edu.neu.coe.info6205.mcts.dotsandboxes;

public class Position {
    Box[][] grid;
    private final static int gridSize = 3;
    static Position startingPostion (){
        Box[][] matrix = new Box[gridSize][gridSize];
        for (int i =0;i<gridSize;i++){
            for (int j=0;j<gridSize;j++){
                if(i==2&&j==2){
                    matrix[i][j]=new Box(true,true,true,true);
                }
                else matrix[i][j]=new Box();
            }
        }
        return new Position(matrix);
    }

    Position(Box[][] grid){
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

}
