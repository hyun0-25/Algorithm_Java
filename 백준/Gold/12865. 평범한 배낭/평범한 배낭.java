import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 시작시간: 16:54
 * 종료시간:
 * 
 * 문제해석
 * 물건 N개
 * 각물건은 무게W, 가치V
 * 최대 K만큼의 무게만 가능
 * 
 * 배낭에 넣을 수 있는 물건들의 가치의 최댓값
 * 
 * 입력
 * 첫째줄: 물품수N, 무게K, 
 * N개줄: 각 물건의 무게W와 가치V
 * 
 * 출력
 * 물건들의 가치의 최댓값
 * 
 * 문제해결프로세스
 *  dp[i][j]=가치
 * 	j(무게)에서 가장 큰값이 되야해
 * 
 * i=몇번째물건/j=무게
 * 가능(<=K)
 * dp[i][j] = dp[i-1][j] 또는 dp[i-1][j-무게i]+가치i 중 큰거
 * 불가능(>K)
 * dp[i][j] = dp[i-1][j]
 * 
 * 제한조건
 * N<=100
 * K<=100,000
 * W<=100,000
 * V<=1000
 * 
 * 시간복잡도
 * 
 * 
 */
public class Main {
	static int N,K;
	static int W[], V[];
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		W = new int[N+1];
		V = new int[N+1];
		for (int i = 1; i <= N; i++) {
			st = new StringTokenizer(br.readLine());
			W[i] = Integer.parseInt(st.nextToken());
			V[i] = Integer.parseInt(st.nextToken());
		}
		
		travel();
	}
	 /* 문제해결프로세스
	 *  dp[i][j]=가치
	 * 	j(무게)에서 가장 큰값이 되야해
	 * 
	 * i=몇번째물건/j=무게
	 * 가능(<=K)
	 * dp[i][j] = dp[i-1][j] 또는 dp[i-1][j-무게i]+가치i 중 큰거
	 * 불가능(>K)
	 * dp[i][j] = dp[i-1][j]
	 */
	
	public static void travel() {
		int dp[][] = new int[N+1][K+1];
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= K; j++) {
				//가능
				if(W[i]<=j) {
					dp[i][j] = Math.max(dp[i-1][j], dp[i-1][j-W[i]]+V[i]);
				}
				//불가능
				else {
					dp[i][j] = dp[i-1][j];
				}
			}
		}
		System.out.println(dp[N][K]);
	}

}