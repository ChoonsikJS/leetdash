import java.util.*;

class Solution {
    class Puzzle {
        int blockCnt;
        boolean[][] grid;
        Puzzle(boolean[][] grid, int blockCnt) {
            this.grid = grid;
            this.blockCnt = blockCnt;
        }
    }
    
    class BFS {
        int i;
        int j;
        
        BFS(int i, int j) {
            this.i  = i; 
            this.j = j;
       } 
    }
    static final int[] ROWS = {0,0,1,-1};
    static final int[] COLS = {-1,1,0,0};
    static boolean[][] tableVisited;
    static ArrayDeque<Puzzle> puzzleq;
    static ArrayDeque<BFS> bfsq;
    static ArrayDeque<Integer[]> poses;
    static int[][] sTable;
    static int[][] sGame_board;
    public int solution(int[][] game_board, int[][] table) {
        int answer = 0;
        
        sTable = table;
        sGame_board = game_board;
        tableVisited = new boolean[table.length][table[0].length];
        bfsq = new ArrayDeque<>();
        poses = new ArrayDeque<>();
        puzzleq = new ArrayDeque<>();

        // let's bfs for table to create puzzle;
        for (int i = 0 ; i <  table.length; i++) {
            for (int j = 0 ; j  <table[0].length; j++) {
                if (table[i][j] == 1 && !tableVisited[i][j])
                    bfsq.add(new BFS(i, j));
                
                while (!bfsq.isEmpty()) {
                    BFS b = bfsq.pollFirst();
                    BFS(b);
                }
                if (poses.size() == 0)
                    continue;
                ArrayList<Integer[]> lst = new ArrayList<>(poses);
                // create puzzle by poses and clear poses queue
                lst.sort((a, b) -> a[0] - b[0]);
                int minI = lst.get(0)[0];
                int maxI = lst.get(lst.size() - 1)[0];
                lst.sort( (a, b) -> a[1] - b[1]);
                int minJ = lst.get(0)[1];
                int maxJ = lst.get(lst.size() - 1)[1];
                
                boolean[][] grid = new boolean[maxI - minI + 1][maxJ - minJ + 1];
                for (Integer[] pos: poses) {
                    grid[pos[0] - minI][pos[1] - minJ] = true;
                }
                
                puzzleq.add(new Puzzle(grid, poses.size()));
                poses.clear();
            }
        }
        
        // reset table into use board;
        tableVisited = new boolean[table.length][table[0].length];
        for (int i = 0 ; i <  table.length; i++) {
            for (int j = 0 ; j  <table[0].length; j++) {
                if (sGame_board[i][j] == 0 && !tableVisited[i][j])
                    bfsq.add(new BFS(i, j));
                
                while (!bfsq.isEmpty()) {
                    BFS b = bfsq.pollFirst();
                    BFSBoard(b);
                }
                if (poses.size() == 0)
                    continue;
                ArrayList<Integer[]> lst = new ArrayList<>(poses);
                // create puzzle by poses and clear poses queue
                lst.sort((a, b) -> a[0] - b[0]);
                int minI = lst.get(0)[0];
                int maxI = lst.get(lst.size() - 1)[0];
                lst.sort( (a, b) -> a[1] - b[1]);
                int minJ = lst.get(0)[1];
                int maxJ = lst.get(lst.size() - 1)[1];

                boolean[][] grid = new boolean[maxI - minI + 1][maxJ - minJ + 1];
                for (Integer[] pos: poses) {
                    grid[pos[0] - minI][pos[1] - minJ] = true;
                }
                // lets check
                int s = puzzleq.size();
                while (s--  > 0) {
                    Puzzle p = puzzleq.pollFirst();
                    
                    boolean[][] original = p.grid;
                    if (isSameGrid(grid, original)) {
                        answer += p.blockCnt;
                        break;
                    }
                    boolean[][] rotated90G = rotate90(p.grid);
                    if (isSameGrid(grid, rotated90G)) {
                        answer += p.blockCnt;
                        break;
                    }
                    boolean[][] rotated180G = rotate90(rotated90G);
                    if (isSameGrid(grid, rotated180G)) {
                        answer += p.blockCnt;
                        break;
                    }
                    boolean[][] rotated270G = rotate90(rotated180G);
                    if (isSameGrid(grid, rotated270G)) {
                        answer += p.blockCnt;
                        break;
                    }
                    
                    puzzleq.offerLast(p);
                    
                }
                
                poses.clear();
            }
        }
        
        
        return answer;
    }
    
    private boolean isSameGrid(boolean[][] g1, boolean[][] g2) {
        if (g1.length != g2.length || g1[0].length != g2[0].length)
            return false;
        for (int i = 0 ; i < g1.length; i++) {
            for (int j = 0; j < g1[0].length; j++) {
                if (g1[i][j] != g2[i][j])
                    return false;
            }
        }
        return true;
    }
    
    private boolean[][] rotate90(boolean[][] grid) {
        int rSize = grid.length;
        int cSize = grid[0].length;
        
        boolean[][] rotatedGrid = new boolean[cSize][rSize];
        
        for (int i = 0 ; i < cSize; i++) {
            for (int j = 0 ; j < rSize; j++) {
                rotatedGrid[i][j] = grid[rSize - j - 1][i];
            }
        }
        return rotatedGrid;
    }
    
    private void BFSBoard(BFS b) {
        if (tableVisited[b.i][b.j])
            return;
        
        tableVisited[b.i][b.j] = true;
        Integer[] a = new Integer[2];
        a[0] = b.i;
        a[1] = b.j;
        poses.add(a);
        
        for (int i = 0 ; i < 4; i++) {
            int nextI = b.i + ROWS[i];
            int nextJ = b.j + COLS[i];
            
            if (nextI < 0 || nextI >= sGame_board.length || nextJ < 0 || nextJ >= sGame_board[0].length)
                continue;
            
            if (tableVisited[nextI][nextJ])
                continue;
        
            if (sGame_board[nextI][nextJ] == 0)
                bfsq.add(new BFS(nextI, nextJ));
        }  
    }
    
    private void BFS(BFS b) {
        if (tableVisited[b.i][b.j])
            return;
        
        tableVisited[b.i][b.j] = true;
        Integer[] a = new Integer[2];
        a[0] = b.i;
        a[1] = b.j;
        poses.add(a);
        
        for (int i = 0 ; i < 4; i++) {
            int nextI = b.i + ROWS[i];
            int nextJ = b.j + COLS[i];
            
            if (nextI < 0 || nextI >= sTable.length || nextJ < 0 || nextJ >= sTable[0].length)
                continue;
            
            if (tableVisited[nextI][nextJ])
                continue;
        
            if (sTable[nextI][nextJ] == 1)
                bfsq.add(new BFS(nextI, nextJ));
        }  
    }
}