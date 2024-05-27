/*
시작시간: 16:47
종료시간: 

문제해석
규칙에 따라 프로세스를 관리할 경우 특정 프로세스가 몇번째로 실행?
1. RQ에서 프로세스 1개꺼냄
2. 큐에 대기중인 프로세스 중 우선순위가 더 높은 프로세스가 있으면 방금 꺼낸 프로세스를 다시 넣음
3. 그런 프로세스가 없으면 방금 꺼낸 프로세스를 실행
    3-1. 한번 실행한 프로세스는 다시 큐에 넣지 않고 그대로 종료

A,B,C,D
2,1,3,2

B,C,D,A
C,D,A,B
D,A,B
A,B
B
-> C,D,A,B

A,B,C,D,E,F
1,1,9,1,1,1

입력

출력

문제해결프로세스
우선순위 max값
1. poll
2. max보다 작으면 -> offer
3. max보다 크거나 같으면 -> 계속
4. 제거한게 location과 같으면 종료

idx
priority[]

시간복잡도


*/
import java.util.*;

class Solution {
    static int index[];
    static Queue<Element> q = new ArrayDeque<>();
    static PriorityQueue<Integer> number = new PriorityQueue<>(Collections.reverseOrder());
    static int answer, result;
    static class Element {
        int priority, idx;
        public Element(int priority, int idx){
            this.priority = priority;
            this.idx = idx;
        }
    }
    public int solution(int[] priorities, int location) {
        result = location;
        for(int i=0; i<priorities.length; i++){
            q.offer(new Element(priorities[i], i));
            number.offer(priorities[i]);
        }
        process();
        return answer;
    }
    /*
    문제해결프로세스
    우선순위 max값
    1. poll
    2. max보다 작으면 -> offer
    3. max보다 크거나 같으면 -> 계속
    4. 제거한게 location과 같으면 종료
    */
    public static void process(){
        int cnt = 1;
        while(q.size()>=0){
            Element e = q.poll();
            int priority = e.priority;
            int idx = e.idx;
            if(priority!=number.peek()){
                q.offer(e);
            }
            else{
                number.poll();
                if(idx == result){
                    answer = cnt;
                    return;
                }
                cnt++;
            }
        }
        
    }
}