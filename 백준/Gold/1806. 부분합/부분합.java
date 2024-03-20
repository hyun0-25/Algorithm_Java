import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 시작: 21:02
 * 종료:
 * 
 * 문제해석
 * 10000이하의 자연수로 이루어진 길이 N짜리 수열
 * 이 수열에서 연속된 수들의 부분합 중 그 합이 S이상 되는 것 중
 * 가장 짧은 것의 길이를 구하여라
 * 
 * 입력 
 * N,S
 * 수열
 * 
 * 출력
 * 구하고자하는 최소 길이 출력
 * 합 만드는 것 불가능시 0 출력
 * 
 * 문제해결프로세스1 (시간초과)
 * 1. 누적합
 * 2. NC2 -> 백트래킹
 * 3. 최솟값 출력
 * 
 * 문제해결프로세스2 (투포인터)
 * start,end에서 
 * 		sum[e]-sum[s]>=S일때까지 end증가
 * 		sum[e]-sum[s]<S일때까지 길이 줄이기 위해서 start증가
 * 
 * 제한조건
 * N<=100,000
 * S<=100,000,000
 * 
 * 시간복잡도
 * 
 */

public class Main {
	static int N,S;
	static int sum[];
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		S = Integer.parseInt(st.nextToken());
		st = new StringTokenizer(br.readLine());
		sum = new int[N+1];
		for (int i = 1; i < N+1; i++) {
			int next = Integer.parseInt(st.nextToken());
			if(next>=S) {
				System.out.println(1);
				System.exit(0);
			}
			sum[i] += sum[i-1] + next;
		}
		if(sum[N] < S) {
			System.out.println(0);
			System.exit(0);
		}
		
		int min = Integer.MAX_VALUE;
		int start=0,end=0;
		while(start<=N && end<=N) {
			int num = sum[end]-sum[start];
			if(num >= S) {
				min = Math.min(end-start, min);
				start++;
			}
			else {
				if(end==N) break;
				end++;
				
			}
		}
		
		System.out.println(min);
	}

}