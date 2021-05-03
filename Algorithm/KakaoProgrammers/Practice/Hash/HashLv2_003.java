import java.util.HashMap;
import java.util.Map;

/**
 * - 문제: 위장
 *  스파이들은 매일 다른 옷을 조합하여 입어 자신을 위장합니다.
 *  예를 들어 스파이가 가진 옷이 아래와 같고 오늘 스파이가 동그란 안경, 긴 코트,
 *  파란색 티셔츠를 입었다면 다음날은 청바지를 추가로 입거나 동그란 안경 대신 검정 선글라스를 착용하거나 해야 합니다.
 *    
 *    [종류]  [이름]
 *     얼굴    동그란 안경, 검정 선글라스
 *     상의    파란색 티셔츠
 *     하의    청바지
 *     겉옷    긴 코트
 * 
 *  가진 의상들이 담긴 2차원 배열 clothes가 주어질 때 서로 다른 옷의 조합의 수를 return 하도록 solution 함수를 작성해주세요.
 *
 * - 제한사항
 *    clothes의 각 행은 [의상의 이름, 의상의 종류]로 이루어져 있습니다.
 *    스파이가 가진 의상의 수는 1개 이상 30개 이하입니다.
 *    같은 이름을 가진 의상은 존재하지 않습니다.
 *    clothes의 모든 원소는 문자열로 이루어져 있습니다.
 *    모든 문자열의 길이는 1 이상 20 이하인 자연수이고 알파벳 소문자 또는 '_' 로만 이루어져 있습니다.
 *    스파이는 하루에 최소 한 개의 의상은 입습니다.
 * 
 */

public class HashLv2_003 {

    public static int solution(String[][] clothes) {
        if (clothes == null) {
            return 0;
        }

        // 파츠별 개수 계산
        Map<String, Integer> clothMap = new HashMap<String, Integer>();

        for (int i = 0; i < clothes.length; ++i) {
            final String part = clothes[i][1];
            clothMap.put(part, clothMap.getOrDefault(part, 0) + 1);
        }

        // System.out.println(clothMap.toString());
        Integer[] partCntAry = clothMap.values().toArray(new Integer[0]);
        int rtCnt = 1;

        for (int partCnt : partCntAry) {
            rtCnt *= (partCnt + 1);
        }

        rtCnt -= 1;
        
        /*
        // [Note] 조합(Combination)구하기
        // bit연산을 사용하여 조합을 구함. 만약 ary[] = { A, B, C } 라면,
        // (1),   (2),   (3),   (4),   (5),   (6),   (Max:7 = pow(2, ary.len) - 1)
        // 0 0 1, 0 1 0, 0 1 1, 1 0 0, 1 0 1, 1 1 0, 1 1 1
        // C,     B,     BC,    A,     AC,    AB,    ABC
        // 위와같이 총 7개의 모든 조합을 구할 수 있음.
        // -주의: 이 문제에서는 조합으로 구성될 원소의 개수가 최대 30개이기에
        // bit연산을 int로 사용할 수 있었음. 만약, 그 이상일 경우 long을 사용해야 함.
        // 64개 초과 원소의 조합을 구하려면 다른 방법을 사용해야 함. (ex:재귀함수 등...)
        
        final int combiCnt = (int) Math.pow(2, partCntAry.length) - 1;

        for (int binary = combiCnt; binary > 0; --binary) {
            int partCntAryPivot = 0;
            int combiSum = 1;

            for (int digit = partCntAry.length - 1; digit > -1; --digit) {
                int bit = ((binary >> digit) & 0x01);

                if (bit == 1) {
                    combiSum *= partCntAry[partCntAryPivot];
                }

                ++partCntAryPivot;
            }

            rtCnt += combiSum;
        }
        */

        return rtCnt;
    }

    public static void main(String[] args) {
        long bgnTime = System.currentTimeMillis();
        
        System.out.println(solution(new String[][] {
                            {"yellow_hat", "headgear"},
                            {"yellow_hat2", "headgear2"},
                            {"yellow_hat3", "headgear3"},
                            {"yellow_hat4", "headgear4"},
                            {"yellow_hat5", "headgear5"},
                            {"yellow_hat6", "headgear6"},
                            {"yellow_hat7", "headgear7"},
                            {"yellow_hat8", "headgear8"},
                            {"yellow_hat9", "headgear9"},
                            {"yellow_hat10", "headgear10"},
                            {"yellow_hat11", "headgear11"},
                            {"yellow_hat12", "headgear12"},
                            {"yellow_hat13", "headgear13"},
                            {"yellow_hat14", "headgear14"},
                            {"yellow_hat15", "headgear15"},
                            {"yellow_hat16", "headgear16"},
                            {"yellow_hat17", "headgear17"},
                            {"yellow_hat18", "headgear18"},
                            {"yellow_hat19", "headgear19"},
                            {"yellow_hat20", "headgear20"},
                            {"yellow_hat21", "headgear21"},
                            {"yellow_hat22", "headgear22"},
                            {"yellow_hat23", "headgear23"},
                            {"yellow_hat24", "headgear24"},
                            {"yellow_hat25", "headgear25"},
                            {"yellow_hat26", "headgear26"},
                            {"yellow_hat27", "headgear27"},
                            {"yellow_hat28", "headgear28"},
                            {"blue_sunglasses", "eyewar"},
                            {"green_turban", "headgear"}})
        );

        long timeElapsed = System.currentTimeMillis() - bgnTime;
        System.out.println("Time: " + timeElapsed + "ms");
    }
}