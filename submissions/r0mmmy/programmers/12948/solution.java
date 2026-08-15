class Solution {
    public String solution(String phone_number) {
        int n = phone_number.length();
        String marking = "*".repeat(n-4);
        String num = phone_number.substring(n-4);
        return marking+num;
    }
}