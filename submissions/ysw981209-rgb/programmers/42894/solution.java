class Solution {
    public int solution(int[][] board) { 
    int[][] board1 = new int[board.length + 2][board[0].length + 4];
    for (int i = 0; i < board.length; i++) {
    for (int j = 0; j < board[i].length; j++) {
        board1[i][j+2] = board[i][j];
        }
    }

    int answer = 0;
        for(int i = 0; i < board1.length-2; i++){
            for(int j = 2; j < board1[0].length-2; j++){
                if(board1[i][j] >= 1 && board1[i][j] <= 200){
                    int c = board1[i][j];
                    if((board1[i+1][j]==c&&board1[i+1][j+1]==c&&board1[i+1][j+2]==c&&board1[i][j+1]==0&&board1[i][j+2]==0)||(board1[i+1][j]==c&&board1[i+2][j]==c&&board1[i+2][j-1]==c&&board1[i][j-1]==0&&board1[i+1][j-1]==0)||(board1[i+1][j]==c&&board1[i+2][j]==c&&board1[i+2][j+1]==c&&board1[i][j+1]==0&&board1[i+1][j+1]==0)||(board1[i+1][j]==c&&board1[i+1][j-1]==c&&board1[i+1][j-2]==c&&board1[i][j-1]==0&&board1[i][j-2]==0)||(board1[i+1][j]==c&&board1[i+1][j-1]==c&&board1[i+1][j+1]==c&&board1[i][j-1]==0&&board1[i][j+1]==0)){
                        answer++;
                        for(int k=i; k<=i+2; k++){
                            for(int l = j-2; l<=j+2; l++){
                                if(board1[k][l] ==c){
                                    board1[k][l] = 0;
                                }
                            }
                        }
                        for(int k = i; k < board1.length; k++){
                            if(board1[k][j] > 200){
                        board1[k][j]=0;
                            }
                        }
                      i=0;
                      j=1;
                    }
                    else {
                        for(int k = i; k < board1.length; k++){
                            if(board1[k][j] == 0){
                        board1[k][j]=k+201;
                            }
                        }
                    }
                }//if문
            }

        }

        return answer;
    }
}