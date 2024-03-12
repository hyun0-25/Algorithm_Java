import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 시작: 12:14
 * 
 * 문제해석 N자리 숫자 숫자 K개를 지워서 얻을 수 있는 가장 큰수
 * 
 * 입력 N,K N자리수
 * 
 * 출력
 * 
 * 문제해결프로세스 완탐->조합, 시간초과
 * 
 * PQ? 1. 숫자 -> 자릿수, 오름차순 정렬 2. for K -> POP(); 3. sysout
 * 
 * 
 */
public class Main {
	static int N, K;

	public static void main(String[] args) throws IOException {
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      StringTokenizer st = new StringTokenizer(br.readLine());
      N = Integer.parseInt(st.nextToken());
      K = Integer.parseInt(st.nextToken());
      String input = br.readLine();
      char[] arr = input.toCharArray();
      Deque<Character> dq = new ArrayDeque<>();
      for (int i = 0; i < arr.length; i++) {
          // 데크의 맨 뒤의 값이 arr[i]보다 작으면 삭제한다.
          // 아래 조건을 만족할 때까지 반복.
          while (K > 0 && !dq.isEmpty() && dq.getLast() < arr[i]) {
              dq.removeLast();
              K--;
          }
          dq.addLast(arr[i]);
      }

      StringBuilder ans = new StringBuilder();
      // 위 for문에서 K가 0이 되기 전에 끝난 경우를 대비하여
      // dq.size() - K만큼만 출력한다.
      while (dq.size() > K) {
          ans.append(dq.removeFirst());
      }
      System.out.println(ans);
//      List<Character> list = new ArrayList<>();
//      int cnt = 0;
//      for (int i = 0; i < N; i++) {
////    	  int num = Integer.parseInt(str.charAt(i)+"");
//    	  char n = str.charAt(i);
//	  	  
//	  	  for (int j = 0; j < N; j++) {
//	  		  while(K>0 && list.size() > 0 && Integer.parseInt(list.get(list.size()-1)+"") < Integer.parseInt(n+"")) {
//	  			  list.remove(j);
//	  			  cnt++;
//	  		  }
//	  		  list.add(n);
//	  		  System.out.println(n);
//	  	  }
//      }
      
//      StringBuilder sb = new StringBuilder();
//
//      while(list.size()>K) {
//    	  sb.append(list.get(0));
//    	  list.remove(0);
//    	  
//      }
//      
//      for (int i = 0; i < list.size(); i++) {
//		sb.append(list.get(i));
//      }
//      System.out.println(sb);

   }


}