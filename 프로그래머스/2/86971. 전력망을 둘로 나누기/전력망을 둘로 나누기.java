/*
시작: 13:00
종료:

문제해석
n개 송전탑이 하나의 트리형태로 연결
전선들 중하나를 끊어서 현재의 전력망 네트워크를 2개로 분할
두 전력망으로 나누었을 때 송전탑의 개수를 최대한 비슷하게 맞추고자함
송전탑 개수의 차이를 return

입력
2<= n <= 100
wires는 n-1 길이

문제해결프로세스 union-find, 완탐
1. n-1개 중 1개 전선 고르기
2. n-2개 union-find
3. 부모 개수 각 몇개인지 세기


*/
import java.util.*;

class Solution {
    static int parents[];
    static int n;
    static int min = Integer.MAX_VALUE;
    public int solution(int N, int[][] wires) {
        n = N;
        int answer = -1;
        for(int i=0; i<n-1; i++){
            make();
            for(int j=0; j<n-1; j++){
                if(i!=j){
                    int a = wires[j][0];
                    int b = wires[j][1];
                    union(a,b);
                }
            }
            int n1 = -1;
            int n2 = -1;
            int cnt1 = 0;
            int cnt2 = 0;
            for(int k=1; k<=n; k++){
                parents[k] = find(parents[k]);
                if(n1==-1){
                    n1 = parents[k];
                    cnt1++;
                }
                else if(parents[k]==n1){
                    cnt1++;
                }
                else if(n2==-1){
                    n2 = parents[k];
                    cnt2++;
                }
                else if(parents[k]==n2){
                    cnt2++;
                }
            }
            min = Math.min(min, Math.abs(cnt1-cnt2));
        }
        
        return min;
    }
    public static void make(){
        parents = new int[n+1];
        for(int i=1; i<=n; i++){
            parents[i] = i;
        }
    }
    
    public static int find(int a) {
        if(parents[a]==a) return a;
        return parents[a] = find(parents[a]);
    }
    
    public static void union(int a, int b){
        int aroot = find(a);
        int broot = find(b);
        
        if(aroot==broot) return;
        parents[broot] = aroot;
    }
    /*
    문제해결프로세스 union-find, 완탐
    1. n-1개 중 1개 전선 고르기
    2. n-2개 union-find
    3. 부모 개수 각 몇개인지 세기
    4. 개수차이 min값 출력
    */
}