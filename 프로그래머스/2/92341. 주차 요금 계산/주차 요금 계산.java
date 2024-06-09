/*
시작: 16:03
종료: 

문제해석
주차장 요금표, 입차,출차
차량별 주차요금 계산

기본 - 180분
기본요금 - 5000원
단위 - 10분당/600원

입차 후 출차된 내역이 없으면 23:59에 출차된 것으로 간주

누적 주차시간이 기본시간 이하 -> 기본요금
            기본시간 초과 -> 기본요금+초과시간에 대한 요금
초과 시간이 단위 시간10분으로 나누어 떨어지지 않으면 올림


입력

출력


문제해결프로세스

시간복잡도

*/
import java.util.*;
class Solution {
    static int basic_time, basic_fee, per_time, per_fee;
    static int answer[];
    public int[] solution(int[] fees, String[] records) {
        basic_time = fees[0];
        basic_fee = fees[1];
        per_time = fees[2];
        per_fee = fees[3];
        int car[] = new int[10000];
        int answer[] = new int[10000];
        boolean visited[] = new boolean[10000];
        int cnt = 0;
        StringTokenizer st;
        for(int i=0; i<records.length; i++){
            st = new StringTokenizer(records[i]);
            String time = st.nextToken();
            int hour = Integer.parseInt(time.substring(0,2));
            int minute = Integer.parseInt(time.substring(3,5));
            int real_time = hour*60+minute;
            int car_num = Integer.parseInt(st.nextToken());
            String action = st.nextToken();
            
            if(action.equals("IN")){
                car[car_num] = real_time;
                visited[car_num] = true;
            }
            else{
                int total_time = real_time - car[car_num];
                answer[car_num] += total_time;
                visited[car_num] = false;
            }
        }
        
        int result[] = new int[10000];
        int count = 0;
        for(int i=0; i<10000; i++){
            if(visited[i]){
                int total_time = 23*60+59 - car[i];
                answer[i] += total_time;
                result[count++] = calculate(answer[i]);
            }
            else if(answer[i]>0){
                result[count++] = calculate(answer[i]);
            }
        }
        return Arrays.copyOfRange(result, 0, count);
    }
    
    public static int calculate(int total_time){
        if(total_time<=basic_time){
            return basic_fee;
        }
        else{
            return basic_fee + (int) Math.ceil((total_time-basic_time)/(double) per_time) * per_fee;
        }
    }
}