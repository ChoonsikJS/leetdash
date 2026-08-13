import java.util.*;

class Solution {
    static final boolean[] usedCharacters = new boolean[26];
    static String answer;

    public String solution(String sentence) {
        answer = "";
        Arrays.fill(usedCharacters, false);

        dfs(sentence, new StringBuilder());

        if (!answer.equals(""))
            return answer;

        return "invalid";
    }

    private void dfs(String sentence, StringBuilder sb) {
        if (!answer.equals(""))
            return;

        if (sentence.length() == 0) {
            answer = sb.toString();
            return;
        }

        // 소문자 하나만 남는 것은 불가능
        if (sentence.length() == 1) {
            if (Character.isLowerCase(sentence.charAt(0)))
                return;

            StringBuilder sb1 = new StringBuilder(sb);

            if (sb1.length() > 0)
                sb1.append(' ');

            sb1.append(sentence);

            answer = sb1.toString();
            return;
        }

        /*
         * 대문자로 시작
         *
         * 1. 규칙 1 후보
         *      A a B a C
         *
         * 2. 아무 규칙 없는 일반 단어 후보
         */
        if (Character.isUpperCase(sentence.charAt(0))) {

            /*
             * ----------------------------
             * 규칙 1 후보
             * ----------------------------
             */
            if (Character.isLowerCase(sentence.charAt(1))) {

                char marker = sentence.charAt(1);

                if (!isUsedCharacter(marker)) {

                    /*
                     * 이 marker는 다시 사용할 수 없으므로
                     * 현재 남은 문자열에 있는 marker가
                     * 전부 현재 규칙1 단어에 포함되어야 함.
                     */
                    int last = sentence.lastIndexOf(marker);

                    boolean valid = true;

                    // marker 뒤에는 마지막 대문자가 하나 더 있어야 함
                    if (last + 1 >= sentence.length()) {
                        valid = false;
                    } else {

                        /*
                         * A a B a C a D
                         *
                         * 짝수 index : 대문자
                         * 홀수 index : 동일 marker
                         */
                        for (int i = 0; i <= last + 1; i++) {

                            if (i % 2 == 0) {

                                if (!Character.isUpperCase(sentence.charAt(i))) {
                                    valid = false;
                                    break;
                                }

                            } else {

                                if (sentence.charAt(i) != marker) {
                                    valid = false;
                                    break;
                                }
                            }
                        }
                    }

                    if (valid) {

                        StringBuilder sb1 = new StringBuilder(sb);

                        if (sb1.length() > 0)
                            sb1.append(' ');

                        // marker 제거
                        for (int i = 0; i <= last + 1; i += 2) {
                            sb1.append(sentence.charAt(i));
                        }

                        setUsedCharacter(marker);

                        dfs(
                            sentence.substring(last + 2),
                            sb1
                        );

                        unsetUsedCharacter(marker);
                    }
                }
            }

            if (!answer.equals(""))
                return;

            /*
             * ----------------------------
             * 규칙 없는 일반 단어 후보
             * ----------------------------
             *
             * 연속된 대문자까지 하나의 단어로 시도한다.
             *
             * 길게 잡은 것부터 시도하되,
             * 실패하면 짧게 잘라서 다음 DFS에서
             * 규칙 1 / 규칙 2로 해석할 기회를 준다.
             */
            int upperEnd = 0;

            while (
                upperEnd < sentence.length()
                && Character.isUpperCase(sentence.charAt(upperEnd))
            ) {
                upperEnd++;
            }

            for (int len = upperEnd; len >= 1; len--) {

                StringBuilder sb1 = new StringBuilder(sb);

                if (sb1.length() > 0)
                    sb1.append(' ');

                sb1.append(
                    sentence,
                    0,
                    len
                );

                dfs(
                    sentence.substring(len),
                    sb1
                );

                if (!answer.equals(""))
                    return;
            }

        /*
         * 소문자로 시작
         *
         * => 규칙 2
         *
         * a WORD a
         */
        } else {

            char marker = sentence.charAt(0);

            if (isUsedCharacter(marker))
                return;

            /*
             * 첫 글자 다음부터 동일 marker를 찾는다.
             */
            int k = sentence.indexOf(marker, 1);

            if (k == -1)
                return;

            /*
             * 규칙2 marker는 정확히 두 번 등장해야 함.
             *
             * a ... a ... a
             *
             * 이런 형태라면 같은 marker 재사용이므로 invalid.
             */
            if (sentence.indexOf(marker, k + 1) != -1)
                return;

            String inner = sentence.substring(1, k);

            if (inner.length() == 0)
                return;

            if (isUsedCharacter(marker))
                return;

            setUsedCharacter(marker);

            /*
             * 규칙2 내부는
             *
             * a HELLO a
             *
             * 또는
             *
             * a H b E b L b L b O a
             *
             * 두 가지가 가능하다.
             */
            String word = decodeRule2Inner(inner);

            if (word != null) {

                /*
                 * 규칙2 내부에도 규칙1이 있었다면
                 * 그 marker 역시 사용 처리.
                 */
                char innerMarker = 0;

                if (!isAllUpper(inner)) {
                    innerMarker = inner.charAt(1);
                    setUsedCharacter(innerMarker);
                }

                StringBuilder sb1 = new StringBuilder(sb);

                if (sb1.length() > 0)
                    sb1.append(' ');

                sb1.append(word);

                dfs(
                    sentence.substring(k + 1),
                    sb1
                );

                if (innerMarker != 0)
                    unsetUsedCharacter(innerMarker);
            }

            unsetUsedCharacter(marker);
        }
    }


    /*
     * 규칙2 내부 검증
	 * 규칙 2 내부가 규칙 1일수도있음.
     *
     * 1.
     * HELLO
     *
     * 2.
     * HbEbLbLbO
     */
    private String decodeRule2Inner(String inner) {

        /*
         * 규칙2만 적용
         */
        if (isAllUpper(inner))
            return inner;

        /*
         * 규칙2 + 규칙1
         *
         * H b E b L b L b O
         */
        if (
            inner.length() < 3
            || !Character.isUpperCase(inner.charAt(0))
            || !Character.isUpperCase(inner.charAt(inner.length() - 1))
            || !Character.isLowerCase(inner.charAt(1))
        ) {
            return null;
        }

        char marker = inner.charAt(1);

        if (isUsedCharacter(marker))
            return null;

        StringBuilder word = new StringBuilder();

        for (int i = 0; i < inner.length(); i++) {

            if (i % 2 == 0) {

                if (!Character.isUpperCase(inner.charAt(i)))
                    return null;

                word.append(inner.charAt(i));

            } else {

                if (inner.charAt(i) != marker)
                    return null;
            }
        }

        return word.toString();
    }


    private boolean isAllUpper(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isUpperCase(s.charAt(i)))
                return false;
        }

        return true;
    }


    private static boolean isUsedCharacter(char c) {
        return usedCharacters[c - 'a'];
    }

    private static void setUsedCharacter(char c) {
        usedCharacters[c - 'a'] = true;
    }

    private static void unsetUsedCharacter(char c) {
        usedCharacters[c - 'a'] = false;
    }
}
