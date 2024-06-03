/*
시작: 19:57
종료: 20:42

문제해석
장르별로 가장 많이 재생된 노래를 최대 2개씩 모아서 베스트 앨범 출시
노래는 고유번호로 구분
노래를 수록하는기준
1. 속한 노래가 많이 재생된 장르를 우선
2. 장르 내에서 많이 재생된 노래 우선
3. 장르 내에서 재생횟수가 같은 노래중에서는 고유 번호가 낮은 노래 우선

입력
노래의 장르 genres
노래별 재생횟수 plays

출력
베스트앨범에 들어갈 노래의 고유번호 순으로 return


문제해결프로세스
0. genres, total, pq
1. genres가 있으면 total++, pq에 add
            없으면 list에 add
2. list -> total 순으로 정렬
3. 최대 2개씩 출력

시간복잡도

*/
import java.util.*;
class Solution {
    static class Genre {
        String name;
        int total;
        PriorityQueue<Music> pq = new PriorityQueue<>();
        public Genre(String name, int total, PriorityQueue pq){
            this.name = name;
            this.total = total;
            this.pq = pq;
        }
    }
    static class Music implements Comparable<Music>{
        int idx, play;
        public Music(int idx, int play){
            this.idx = idx;
            this.play = play;
        }
        @Override
        public int compareTo(Music m){
            if(this.play==m.play){
                return this.idx-m.idx;
            }
            return m.play-this.play;
        }
    }
    static List<Genre> list = new ArrayList<>();
    public int[] solution(String[] genres, int[] plays) {
        for(int i=0; i<genres.length; i++){
            String genre = genres[i];
            //존재x
            int genre_idx = isexist(genre);
            if(genre_idx==-1){
                PriorityQueue<Music> pq = new PriorityQueue<>();
                pq.add(new Music(i, plays[i]));
                list.add(new Genre(genre, plays[i], pq));
            }
            //존재o
            else{
                Genre g = list.get(genre_idx);
                g.total+=plays[i];
                g.pq.add(new Music(i, plays[i]));
            }
        }
        Collections.sort(list, (g1,g2)->g2.total-g1.total);
        int[] answer = new int[genres.length];
        int count = 0;
        for(int i=0; i<list.size(); i++){
            Genre g = list.get(i);
            int cnt = 0;
            while(cnt<2 && !g.pq.isEmpty()){
                cnt++;
                Music m = g.pq.poll();
                answer[count++] = m.idx;
            }
        }

        return Arrays.copyOfRange(answer,0,count);
    }
    
    public static int isexist(String genre){
        for(int i=0; i<list.size(); i++){
            if(list.get(i).name.equals(genre))
                return i;
        }
        return -1;
    }
}