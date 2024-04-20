import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 시작: 19:30=3
 * 종료:
 * 
 * 
 * 문제해석
 * 성냥개비의 개수가 주어졌을때
 * 성냥개비를 모두 사용해서 만들 수 있는 가장 작은수, 가장 큰수
 * 
 * 
 * 입력
 * 첫째줄: 성냥개비개수 n
 * 
 * 출력
 * 가장 큰수, 가장 작은수
 * 0으로 시작 불가
 * 
 * 문제해결프로세스
 * 0->6
 * 1->2
 * 2->5
 * 3->5
 * 4->4
 * 5->5
 * 6->6
 * 7->3
 * 8->7
 * 9->6
 * 
 * 1. 가장 큰수
 * 	자릿수가 많은 수
 * 		2의 배수 만큼 (1)만들기
 * 		남은 숫자로 첫자리수 7로 만들기
 * 2. 가장 작은수
 * 	자릿수가 적은 수
 * 		7의 배수 만큼 (8)로 자릿수 만들기
 * 			몫이 >=1이고 나머지 있으면, (몫-1)만큼만 8만들고
 * 					나머지로 가능한 경우의 수 조합
 * 		남은수 1이하-> 20
 * 		남은수 2이상-> 숫자 우선순위
 * 					2=>1
 * 					3=>7
 * 					4=>4
 * 					5=>2
 * 					6=>0,6
 * 					
 * 		10
 * 		3,7- 78
 * 		4,6- 46
 * 		5,5- 22
 * 
 * 시간복잡도
 * 
 */
public class Main {
	static StringBuilder sbmax = new StringBuilder();
	static StringBuilder sbmin = new StringBuilder();
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < T; i++) {
			sbmax = new StringBuilder();
			sbmin = new StringBuilder();
			int n = Integer.parseInt(br.readLine());
			findmin(n);
			findmax(n);
			sb.append(sbmin.reverse()).append(' ').append(sbmax.reverse()).append('\n');
		}
		
		System.out.println(sb);
	}
	/* 1. 가장 큰수
	 * 	자릿수가 많은 수
	 * 		2의 배수 만큼 (1)만들기
	 * 		남은 숫자로 첫자리수 7로 만들기
	 * 2. 가장 작은수
	 * 	자릿수가 적은 수
	 * 		7의 배수 만큼 (8)로 자릿수 만들기
	 * 			몫이 >=1이고 나머지 있으면, (몫-1)만큼만 8만들고
	 * 					나머지로 가능한 경우의 수 조합
	 * 		남은수 1이하-> 20
	 * 		남은수 2이상-> 숫자 우선순위
	 * 					2=>1
	 * 					3=>7
	 * 					4=>4
	 * 					5=>2
	 * 					6=>0,6
	 * 		10
	 * 		3,7- 78
	 * 		4,6- 40
	 * 		5,5- 22
	 * 		6,4- 
	 */
	public static int changenumber(int idx, int x) {
		switch(x) {
		case 2:
			return 1;
		case 3:
			return 7;
		case 4:
			return 4;
		case 5:
			return 2;
		case 6:
			if(idx==1) return 6;
			else if(idx==2 || idx==3) return 0;
		case 7:
			return 8;
		}
		return 0;
	}
	public static int makemin2(int remain) {
		int m = Integer.MAX_VALUE;
		for (int i = 2; i < 8; i++) {
			for (int j = 2; j < 8; j++) {
				if(i+j==remain) {
					int result = 0;
					result+=changenumber(1,i)*10;
					result+=changenumber(2,j);
					m = Math.min(m, result);
				}
			}
		}
		return m;
	}
	public static int makemin3(int remain) {
		int m = Integer.MAX_VALUE;
		for (int i = 2; i < 8; i++) {
			for (int j = 2; j < 8; j++) {
				for (int k = 2; k < 8; k++) {
					if(i+j+k==remain) {
						int result = 0;
						result+=changenumber(1,i)*100;
						result+=changenumber(2,j)*10;
						result+=changenumber(3,k);
						m = Math.min(m, result);
					}
				}
			}
		}
		return m;
	}
	public static void findmin(int n) {
		int number = n/7;
		int first = n%7;
		if(first==0) {
			for (int i = 0; i < number; i++) {
				sbmin.append(8);
			}
		}
		else {
			if(number==0) {
				sbmin.append(changenumber(1, n));
			}
			else if(number==1) {
				int remain = first+7;
				int val = makemin2(remain);
				sbmin.append(val%10);
				sbmin.append(val/10);
			}
			else {
				for (int i = 0; i < number; i++) {
					if(i==number-2) {
						int remain = first+14;
						int val = makemin3(remain);
						sbmin.append(val%10);
						sbmin.append(val/10%10);
						sbmin.append(val/100);
						break;
					}
					sbmin.append(8);
				}
			}
		}
	}
	public static void findmax(int n) {
		int number = n/2;
		int first = n%2;
		
		if(first==0) {
			for (int i = 0; i < number; i++) {
				sbmax.append(1);
			}
		}
		else {
			for (int i = 0; i < number; i++) {
				if(i==number-1) {
					sbmax.append(7);
					break;
				}
				sbmax.append(1);
			}
		}
	}
}