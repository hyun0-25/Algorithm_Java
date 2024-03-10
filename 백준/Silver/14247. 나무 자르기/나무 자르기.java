import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;
public class Main {
	static int [] H,A;
	static int max, max_idx;
	public static void main(String[] args) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		StringTokenizer st;
		
		A = new int[n];
		st = new StringTokenizer(br.readLine());
        //결과 값이 overflow가 일어나기 때문에 long으로
		long result=0L;
		for(int i=0;i<n;i++) {
			result+= Integer.parseInt(st.nextToken());
		}
		st = new StringTokenizer(br.readLine());
		for(int i=0;i<n;i++) {
			A[i] = Integer.parseInt(st.nextToken());
		}
		
		
		//나무 자르는 n일
		Arrays.sort(A);
		for (int d = 0; d < n; d++) {
			result+=A[d]*d;
//			System.out.println(A[d]*d);
		}
		System.out.println(result);
	}
}