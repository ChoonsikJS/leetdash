class Solution {
    public long solution(int[] sequence) {

        // 1. 전체 수열에 +-+-... 곱
        int[] pm = new int[sequence.length];
        for(int i = 0; i < pm.length; i++){
            pm[i] = sequence[i];
            if(i % 2 == 1){
                pm[i] *= -1;
            }
        }

        long pmMax = 0;
        long sum = 0;

        for(int i = 0; i < pm.length; i++){
            if(pm[i] < 0){
                pmMax = Math.max(pmMax, sum);
            }
            sum += pm[i];
            if(sum < 0){
                sum = 0;
            }
        }

        pmMax = Math.max(pmMax, sum);


        // 2. 전체 수열에 -+-+... 곱
        int[] mp = new int[sequence.length];
        for(int i = 0; i < mp.length; i++){
            mp[i] = sequence[i];
            if(i % 2 == 0){
                mp[i] *= -1;
            }
        }

        long mpMax = 0;
        sum = 0;

        for(int i = 0; i < mp.length; i++){
            if(mp[i] < 0){
                mpMax = Math.max(mpMax, sum);
            }
            sum += mp[i];
            if(sum < 0){
                sum = 0;
            }
        }

        mpMax = Math.max(mpMax, sum);


        long answer = Math.max(pmMax, mpMax);
        return answer;
    }
}