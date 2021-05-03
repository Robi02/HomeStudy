import java.util.List;

/**
 * - 문제 설명
 * 카카오에 신입 개발자로 입사한 콘은 선배 개발자로부터 개발역량 강화를 위해 다른 개발자가 작성한 소스 코드를 분석하여 문제점을 발견하고 수정하라는 업무 과제를 받았습니다. 소스를 컴파일하여 로그를 보니 대부분 소스 코드 내 작성된 괄호가 개수는 맞지만 짝이 맞지 않은 형태로 작성되어 오류가 나는 것을 알게 되었습니다.
 * 수정해야 할 소스 파일이 너무 많아서 고민하던 콘은 소스 코드에 작성된 모든 괄호를 뽑아서 올바른 순서대로 배치된 괄호 문자열을 알려주는 프로그램을 다음과 같이 개발하려고 합니다.
 * 
 * - 용어의 정의
 * '(' 와 ')' 로만 이루어진 문자열이 있을 경우, '(' 의 개수와 ')' 의 개수가 같다면 이를 균형잡힌 괄호 문자열이라고 부릅니다.
 * 그리고 여기에 '('와 ')'의 괄호의 짝도 모두 맞을 경우에는 이를 올바른 괄호 문자열이라고 부릅니다.
 * 예를 들어, "(()))("와 같은 문자열은 균형잡힌 괄호 문자열 이지만 올바른 괄호 문자열은 아닙니다.
 * 반면에 "(())()"와 같은 문자열은 균형잡힌 괄호 문자열 이면서 동시에 올바른 괄호 문자열 입니다.
 * 
 * '(' 와 ')' 로만 이루어진 문자열 w가 균형잡힌 괄호 문자열 이라면 다음과 같은 과정을 통해 올바른 괄호 문자열로 변환할 수 있습니다.
 * 
 * 1. 입력이 빈 문자열인 경우, 빈 문자열을 반환합니다. 
 * 2. 문자열 w를 두 "균형잡힌 괄호 문자열" u, v로 분리합니다. 단, u는 "균형잡힌 괄호 문자열"로 더 이상 분리할 수 없어야 하며, v는 빈 문자열이 될 수 있습니다. 
 * 3. 문자열 u가 "올바른 괄호 문자열" 이라면 문자열 v에 대해 1단계부터 다시 수행합니다. 
 *   3-1. 수행한 결과 문자열을 u에 이어 붙인 후 반환합니다. 
 * 4. 문자열 u가 "올바른 괄호 문자열"이 아니라면 아래 과정을 수행합니다. 
 *   4-1. 빈 문자열에 첫 번째 문자로 '('를 붙입니다. 
 *   4-2. 문자열 v에 대해 1단계부터 재귀적으로 수행한 결과 문자열을 이어 붙입니다. 
 *   4-3. ')'를 다시 붙입니다. 
 *   4-4. u의 첫 번째와 마지막 문자를 제거하고, 나머지 문자열의 괄호 방향을 뒤집어서 뒤에 붙입니다. 
 *   4-5. 생성된 문자열을 반환합니다.
 * 균형잡힌 괄호 문자열 p가 매개변수로 주어질 때, 주어진 알고리즘을 수행해 올바른 괄호 문자열로 변환한 결과를 return 하도록 solution 함수를 완성해 주세요.
 * 
 * - 매개변수 설명
 * p는 '(' 와 ')' 로만 이루어진 문자열이며 길이는 2 이상 1,000 이하인 짝수입니다.
 * 문자열 p를 이루는 '(' 와 ')' 의 개수는 항상 같습니다.
 * 만약 p가 이미 올바른 괄호 문자열이라면 그대로 return 하면 됩니다.
 *
 * - 입출력 예
 * p	result
 * "(()())()"	"(()())()"
 * ")("	"()"
 * "()))((()"	"()(())()"
 * 
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Q2 {
    
    // 올바른 괄호 문자열 체크
    public boolean correctBrace(String p) {
        List<Character> chkList = new ArrayList<Character>();
        int pLen = p.length();
        
        for (int i = 0; i < pLen; ++i) {
            char c = p.charAt(i);

            if (c == '(') {
                chkList.add(c);
            }
            else { // ')'
                if (chkList.size() == 0) {
                    return false;
                }

                chkList.remove(chkList.size() - 1);
            }
        }

        return chkList.size() == 0 ? true : false;
    }

    // 균형잡힌 괄호 문자열 체크
    public boolean balancedBrace(String p) {
        int pLen = p.length();
        int lCnt = 0, rCnt = -1;

        for (int i = 0; i < pLen; ++i) {
            if (p.charAt(i) == '(') ++lCnt;
        }

        rCnt = pLen - lCnt;

        return (lCnt == rCnt);
    }

    // u, v 구하기
    public String[] getUV(String p) {
        int pLen = p.length();
        StringBuilder u = new StringBuilder();
        String v = null;

        for (int i = 0; i < pLen; ++i) {
            u.append(p.charAt(i));
            
            if (i > 0 && (i - 1) % 2 == 0) { // 균형잡힌 괄호가 되기위한 최소한의 조건
                if (balancedBrace(u.toString())) {
                    v = (i + 1 < pLen ? p.substring(i + 1, pLen) : "");
                    break;
                }
            }
        }

        return new String[] { u.toString(), v };
    }

    // u 앞뒤 제거후 반전
    public String cvtU(String u) {
        char[] uAry = u.substring(1, u.length() - 1).toCharArray();

        for (int i = 0; i < uAry.length; ++i) {
            if (uAry[i] == '(') {
                uAry[i] = ')';
            }
            else { // ')'
                uAry[i] = '(';
            }
        }

        return new String(uAry);
    }

    public String solution(String p) {
        if (p == null || p.length() == 0) {
            return "";
        }

        String[] uv = getUV(p);
        String u = uv[0];
        String v = uv[1];

        if (correctBrace(u)) {
            u += solution(v);
            return u;
        }
        else {
            String empty = "(";
            empty += solution(v);
            empty += ")";
            empty += cvtU(u);
            return empty;
        }
    }

    public static void main(String[] args) {
        // ================================================================
        final Q2 solution = new Q2();
        final String[] testCases = new String[] { // #1 Set input type!
            //"(()())()",
            //")(",
            "()))((()",
        };
        // #2 Add extra input!
        // ================================================================
        final long bgnTime = System.currentTimeMillis();
        for (int i = 0; i < testCases.length; ++i) {
            final String result = solution.solution(testCases[i]); // #3 Set return type!
            System.out.println("Result : " + result); // #4 Set output type!
        }
        final long elapsedTime = System.currentTimeMillis() - bgnTime;
        // ================================================================
        System.out.println("ElapsedTime : " + elapsedTime + "ms");
        // ================================================================
    }
}