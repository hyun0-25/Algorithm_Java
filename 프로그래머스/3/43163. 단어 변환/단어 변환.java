/*
시작: 12:51
종료:

문제해석
2개의 단어 begin과 target, 단어의 집합 words
begin -> target으로 변환하는 가장 짧은 변환 과정
1. 1번에 1개의 알파벳만 교체가능
2. words에 있는 단어로만 변환 가능

hit => cog
hit -> (hot - dot - dog - cog) : 4단계


문제해결프로세스


제한사항
각 단어는 알파벳 소문자
단어의 길이는 3~10, 모든 단어의 길이 동일
변환할  수 없는 경우 0을 return


시간복잡도

*/

// import java.util.*;

// class Solution {
//     static String[] word;
//     static String bg, tg;
//     static int min, size, answer;
//     static List<List<Integer>> list = new ArrayList<>();
//     static List<Integer> flist;
//     static String[] select;
//     public int solution(String begin, String target, String[] words) {

//         size = words.length;
//         word = new String[size];
//         word = words;
//         bg = begin;
//         tg = target;
//         min = Integer.MAX_VALUE;
                
//         for(int i=0; i<size; i++){
//             list.add(new ArrayList<>());
//             for(int j=0; j<size; j++){
//                 if(isnext(word[i], word[j])){
//                     list.get(i).add(j);
//                 }
//             }
//         }
        
//         flist = new ArrayList<>();
//         for(int f=0; f<size; f++){
//             //바로 정답 찾음
//             if(bg == words[f]) return 1;
//             else if(isnext(bg, word[f])){
//                 flist.add(f);
//             }
//         }
//         //변환할 수 없는 경우
//         if(flist.size()==0) return 0;
        
//         makeword(0);
        
//         return answer;
//     }
//     public static void makeword(int cnt) {
//         boolean isvisited[] = new boolean[size];
//         Queue<Integer> q = new ArrayDeque<>();
//         for(int i=0; i<flist.size(); i++){
//             q.offer(flist.get(i));
//             isvisited[flist.get(i)] = true;
//         }
//         int depth = 1;
//         while(!q.isEmpty()){
//             int qsize = q.size();
//             for(int s=0; s<qsize; s++){
//                 int p = q.poll();
//                 if(word[p].equals(tg)){
//                     answer = depth;
//                     return;
//                 }
//                 for(int k=0; k<list.get(p).size(); k++){
//                     int kidx = list.get(p).get(k);
//                     if(isvisited[kidx]) continue;
//                     //else
//                     q.offer(kidx);
//                     isvisited[kidx] = true;
//                 }
//             }
//             depth++;
//         }
//         answer = 0;
//         return;
//     }
    
//     public static boolean isnext(String w1, String w2) {
//         int cnt = 0;
//         for(int i=0; i<w1.length(); i++) {
//             if(w1.charAt(i) != w2.charAt(i)) cnt++;
//         }
        
//         if(cnt==1) return true;
//         else return false;
//     }
// }

/*
시작: 22:44
종료: 23:30

문제해석
규칙을 이용해서 begin -> target으로 변환하는 가장 짧은 변환과정
1. 한번에 1개의 알파벳만 변환가능
2. words에 있는 단어로만 변환가능
최소 몇단계 과정을 거쳐서 변환할 수 있는지 return


입력

출력
최소 몇단계 과정을 거쳐서 변환
변환할 수 없는 경우 0 

문제해결프로세스
1. nC2 -> 인접리스트 만들기
2. begin부터 bfs -> target에 도착하면 depth출력
                            도착못하면 0출력

시간복잡도

*/
import java.util.*;
class Solution {

    static List<List<Integer>> list = new ArrayList<>();
    static int N;
    public int solution(String begin, String target, String[] words) {
        N = words.length;
        int idx = -1;
        String[] newwords = new String[N+1];
        for(int i=0; i<N; i++){
            list.add(new ArrayList<>());
            newwords[i] = words[i];
            if(words[i].equals(target)) idx = i;
        }
        list.add(new ArrayList<>());
        newwords[N] = begin;
        for(int i=0; i<N; i++){
            String first = newwords[i];
            for(int j=1; j<=N; j++){
                String second = newwords[j];
                if(isLinked(first, second)){
                    list.get(i).add(j);
                    list.get(j).add(i);
                }
            }
        }
        if(idx==-1) return 0;
        
        return bfs(idx);
    }
    
    public static int bfs(int idx){
        boolean visited[] = new boolean[N+1];
        
        Queue<Integer> q = new ArrayDeque<>();
        q.add(N);
        visited[N] = true;
        int depth = 0;
        while(!q.isEmpty()){
            int size = q.size();
            for(int i=0; i<size; i++){
                int x = q.poll();
                if(x==idx) {
                    return depth;
                }
                List<Integer> link = list.get(x);
                for(int k=0; k<link.size(); k++){
                    int y = link.get(k);
                    if(visited[y]) continue;
                    //else
                    q.add(y);
                    visited[y] = true;
                }
            }
            depth++;
        }
        return 0;
    }
    
    public static boolean isLinked(String s1, String s2){
        int cnt = 0;
        for(int i=0; i<s1.length(); i++){
            if(s1.charAt(i)!=s2.charAt(i)) cnt++;
        }
        if(cnt==1) return true;
        return false;
    }
}