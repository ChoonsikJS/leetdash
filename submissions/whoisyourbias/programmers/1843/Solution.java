class Solution {
    // 뺄셈의 최대값
    // k 가 operator일때
    // 0... k-1 | k | k + 1 ... n
    //    최대   | - | 최소
    //    최대   | + | 최대
    // 
    // 따라서 각 구간에서의 최대/최소 모두를 알아야한다.
    // DP의 깊이는 Operator의 개수
    // 
    
    // BFS로 풀면, 모든 경우의 수가 201! 이라서 시간초과.
    // DP를 사용해서 각 구간별 연산을 미리 해놓고 사용해야한다.
    public int solution(String arr[]) {
        final int NumberCount = (arr.length+1) /2;
        final int OpCount = NumberCount - 1;
        
        int[] numbers = new int[NumberCount];
        char[] operators = new char[OpCount];
        
        
        // init datas
        for (int numberIdx = 0; numberIdx < NumberCount; numberIdx++) {
            numbers[numberIdx] = Integer.parseInt(arr[numberIdx * 2]);
            
            if (numberIdx < NumberCount -1 )
                operators[numberIdx] = arr[numberIdx * 2 + 1].charAt(0);
        }
        
        int[][] maximums = new int[NumberCount][NumberCount];
        int[][] minimums = new int[NumberCount][NumberCount];
        
        for (int i = 0; i < NumberCount; i++) {
            maximums[i][i] = numbers[i];
            minimums[i][i] = numbers[i];
        }
        
        // 2구간부터 숫자갯수 구간까지
        for (int rangeSize = 2; rangeSize <= NumberCount; rangeSize++) {
            // 구간시작
            for (int start = 0; start <= NumberCount - rangeSize; start++) {
                // 구간끝
                int end = start + rangeSize - 1;
                
                maximums[start][end] = Integer.MIN_VALUE;
                minimums[start][end] = Integer.MAX_VALUE;
                
                // 구간 내에서 구간분할
                // |start ~ split|split + 1-end| 로 분할
                
                for (int split = start; split < end;split++) {
                    if (operators[split] == '+') {
                        // 덧셈인 경우. 
                        int candidateMaximum = 
                            maximums[start][split] + maximums[split + 1][end];
                        maximums[start][end] = Math.max(maximums[start][end], candidateMaximum);
                        
                        int candidateMinimum =
                            minimums[start][split] + minimums[split + 1][end];
                        minimums[start][end] = Math.min(minimums[start][end], candidateMinimum);
                    } else {
                        // 뺄셈인 경우
                        int candidateMaximum =
                            maximums[start][split] - minimums[split + 1][end];
                        maximums[start][end] = Math.max(maximums[start][end], candidateMaximum);
                        
                        int candidateMinimum =
                            minimums[start][split] - maximums[split + 1][end];
                        minimums[start][end] = Math.min(minimums[start][end], candidateMinimum);
                        
                    }
                    
                }
                
            }
        }
        
        return maximums[0][NumberCount - 1];
    }
}