import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 시작시간: 9:14
 * 종료시간:
 * 
 * 문제해석
 * N개 돌이 일렬로 나열, A[i]로 숫자 부여
 * 맨 왼->맨 오로 건너기
 * 
 * 1. ->방향으로만 이동가능
 * 2. i에서 j로 이동시 (j-i)(1+|Ai-Aj|) 힘을 씀
 * 3. 돌을 한번 건너갈 때마다 쓸 수 있는 힘의 최대 K
 * 맨왼->맨오로 건너갈 수 있는지 출력(YES/NO)
 * 
 * 입력
 * 1. 돌 개수 N, 최대힘 K
 * 2. N개돌의 Ai
 * 
 * 출력
 * yes/no
 * 
 * 문제해결프로세스
 * boolean만 있으면 될듯?
 * 점화식
 * dp[i] = 최대힘 만족하면 (dp[i-1] || dp[i-2] || ... || dp[i-K])
 * 
 * 제한사항
 * 
 * 시간복잡도
 * 
 * 
 */
public class Main {
	static int N,K;
	static int A[];
	static boolean possible[];
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		A = new int[N];
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++) {
			A[i] = Integer.parseInt(st.nextToken());
		}
		possible = new boolean[N];
		dp();
		if(possible[N-1]) {
			System.out.println("YES");
		}
		else {
			System.out.println("NO");
		}
	}
	
	public static void dp() {
		//맨왼
		possible[0] = true;
		for (int i = 1; i < N; i++) {
			for(int j = i-1; j>=Math.max(0,i-K); j--) {
				if(possible[j] && (i-j)*(1+Math.abs(A[j]-A[i])) <= K) {
					possible[i] = true;
					break;
				}
			}
		}
	}

}