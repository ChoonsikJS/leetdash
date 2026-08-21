// lock은 N x N(3 ≤ N ≤ 20, N은 자연수)크기 2차원 배열입니다.
// M은 항상 N 이하입니다.
// key와 lock의 원소는 0 또는 1로 이루어져 있습니다.
// 0은 홈 부분, 1은 돌기 부분을 나타냅니다.

class Solution {
    public boolean solution(int[][] key, int[][] lock) {
        boolean answer = false;
        
        final int L_ROW = lock.length;
        final int L_COL = lock[0].length;
        final int K_ROW = key.length;
        final int K_COL = key[0].length;
        
        int[][] paddedLock = new int[L_ROW + K_ROW*2][L_COL + K_COL*2];
        
        int hole = 0;
        for (int i =0  ; i < L_ROW; i++) {
            for (int j = 0 ; j < L_COL; j++) {
                paddedLock[i + K_ROW][j + K_COL] = lock[i][j];
                if (lock[i][j] == 0)
                    hole++;
            }
        }
        
        
        int[][] r90 = rotate90(key);
        int[][] r180 = rotate90(r90);
        int[][] r270 = rotate90(r180);
        int[][][] rKeys = new int[4][][];
        rKeys[0] = key;
        rKeys[1] = r90;        
        rKeys[2] = r180;
        rKeys[3] = r270;
        
        for (int ki = 0 ; ki  < 4; ki++) {
            int[][] k = rKeys[ki];
            
            for (int i = 0; i < L_ROW + K_ROW; i++) {
                for (int j = 0; j < L_COL + K_COL; j++) {
                    // current start left - top = (i, j)
            
                    boolean isValidKey = true;
                    int filled = 0;
                   
                    // the hole check boundary from (i, j)
                    // paddedI [K_ROW, L_ROW + K_ROW) 
                    // paddedJ [K_COL, L_COL + K_COL)
                    for (int paddedI = i; paddedI < K_ROW + i; paddedI++) {
                        for (int paddedJ = j; paddedJ < K_COL + j; paddedJ++) {
                            if ((K_ROW <= paddedI) && (paddedI < K_ROW + L_ROW) && 
                                (K_COL <= paddedJ) && (paddedJ < K_COL + L_COL)) {
                                
                                if (paddedLock[paddedI][paddedJ] == 1) {
                                    // filled
                                    if (k[paddedI - i][paddedJ - j] == 0) {
                                        continue;
                                    } else {
                                        isValidKey = false;
                                        break;
                                    }
                                } else {
                                    // hole
                                    if (k[paddedI - i][paddedJ - j] == 1) {
                                        filled++;
                                        continue;
                                    } else {
                                        isValidKey = false;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    if (isValidKey && hole == filled){
                        return true;
                    }
                        
                }
            }
        }
        return answer;
    }
    
    private int[][] rotate90(int[][] key) {
        int[][] rtn = new int[key.length][key.length];
        
        for (int i = 0; i < key.length; i++) {
            for (int j = 0 ; j < key.length; j++) {
                rtn[i][j] = key[j][key.length - i - 1];
            }
        }
        return rtn;
    }
    
}
