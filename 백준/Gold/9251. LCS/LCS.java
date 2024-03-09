import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 
 * 점화식
 * dp[i][j] = 일치하는개수 최대
 * i = 2번째문자열
 * j = 1번째문자열
 * 일치
 * dp[i][j] = dp[i-1][j-1]+1
 * 불일치
 * dp[i][j] = max(dp[i-1][j], dp[i][j-1])
 * 
 */
public class Main {
	static String first,second;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		first = br.readLine();
		second = br.readLine();
		
		LCS();

	}
	public static void LCS() {
		int size2 = second.length();
		int size1 = first.length();
		int dp[][] = new int [size2+1][size1+1];
		for (int i = 1; i <= size2; i++) {
			for (int j = 1; j <= size1; j++) {
				//일치
				if(second.charAt(i-1) == first.charAt(j-1)) {
					dp[i][j] = dp[i-1][j-1]+1;
				}
				//불일치
				else {
					dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
				}
			}
		}
		System.out.println(dp[size2][size1]);
	}

}