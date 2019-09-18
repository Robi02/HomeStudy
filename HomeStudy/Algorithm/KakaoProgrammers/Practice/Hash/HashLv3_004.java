
/**
 * - 문제 설명 : 베스트앨범
 * 스트리밍 사이트에서 장르 별로 가장 많이 재생된 노래를 두 개씩 모아 베스트 앨범을 출시하려 합니다. 노래는 고유 번호로 구분하며, 노래를 수록하는 기준은 다음과 같습니다.
 * 
 * 속한 노래가 많이 재생된 장르를 먼저 수록합니다.
 * 장르 내에서 많이 재생된 노래를 먼저 수록합니다.
 * 장르 내에서 재생 횟수가 같은 노래 중에서는 고유 번호가 낮은 노래를 먼저 수록합니다.
 * 노래의 장르를 나타내는 문자열 배열 genres와 노래별 재생 횟수를 나타내는 정수 배열 plays가 주어질 때, 베스트 앨범에 들어갈 노래의 고유 번호를 순서대로 return 하도록 solution 함수를 완성하세요.
 * 
 * - 제한사항
 * genres[i]는 고유번호가 i인 노래의 장르입니다.
 * plays[i]는 고유번호가 i인 노래가 재생된 횟수입니다.
 * genres와 plays의 길이는 같으며, 이는 1 이상 10,000 이하입니다.
 * 장르 종류는 100개 미만입니다.
 * 장르에 속한 곡이 하나라면, 하나의 곡만 선택합니다.
 * 모든 장르는 재생된 횟수가 다릅니다.
 * 
 * - 입출력 예
 * genres	plays	return
 * [classic, pop, classic, classic, pop]	[500, 600, 150, 800, 2500]	[4, 1, 3, 0]
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class HashLv3_004 {

    public static class MusicInfo {
        public int id;
        public String genres;
        public int plays;
        public MusicInfo(int id, String genres, int plays) {
            this.id = id;
            this.genres = genres;
            this.plays = plays;
        }
    }

    public int[] solution(String[] genres, int[] plays) {
        // 장르별 playtime 총합 구하기
        Map<String, Integer> playtimeMap = new HashMap<String, Integer>();
        Map<String, PriorityQueue<MusicInfo>> musicMap = new HashMap<String, PriorityQueue<MusicInfo>>();
        
        for (int id = 0; id < plays.length; ++id) {
            playtimeMap.put(genres[id], playtimeMap.getOrDefault(genres[id], 0) + plays[id]);
            PriorityQueue<MusicInfo> grpMusicInfo = musicMap.getOrDefault(genres[id], 
                new PriorityQueue<MusicInfo>(new Comparator<MusicInfo>() {
                    @Override
                    public int compare(MusicInfo o1, MusicInfo o2) {
                        return Integer.compare(o2.plays, o1.plays);
                    }
            }));
            grpMusicInfo.add(new MusicInfo(id, genres[id], plays[id]));
            musicMap.put(genres[id], grpMusicInfo);
        }

        List<Integer> ansList = new ArrayList<Integer>();

        while (!playtimeMap.isEmpty()) {
            // 장르를 재생횟수의 합중 높은순으로 뽑아냄
            String maxPlaytimeKey = Collections.max(playtimeMap.entrySet(), (o1, o2) -> o1.getValue() - o2.getValue()).getKey();
            playtimeMap.remove(maxPlaytimeKey);
            
            // 뽑힌 장르의 재생횟수 상위 2개 음악의 id획득
            PriorityQueue<MusicInfo> grpMusicInfo = musicMap.get(maxPlaytimeKey);
            ansList.add(grpMusicInfo.remove().id);
            if (grpMusicInfo.size() == 0) continue;
            ansList.add(grpMusicInfo.remove().id);
        }
        
        int[] ansAry = new int[ansList.size()];
        for (int i = 0 ; i < ansAry.length; ++i) {
            ansAry[i] = ansList.get(i);
        }

        return ansAry;
    }

    public static void main(String[] args) {
        // ================================================================
        final HashLv3_004 solution = new HashLv3_004();
        final String[] genres = { "classic", "pop", "classic", "classic", "pop" };
        final int[] plays = { 500, 600, 150, 800, 2500 };
        // ================================================================
        final long bgnTime = System.currentTimeMillis();
        final int[] result = solution.solution(genres, plays);
        final long elapsedTime = System.currentTimeMillis() - bgnTime;
        // ================================================================
        System.out.println("Result : " + Arrays.toString(result));
        System.out.println("ElapsedTime : " + elapsedTime + "ms");
        // ================================================================
    }
}