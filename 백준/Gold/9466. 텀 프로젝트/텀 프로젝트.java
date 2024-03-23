import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 시작: 19:36
 * 종료: 
 * 
 * 문제해석
 * 팀원 수에는 제한X
 * 모든 학생들이 동일한 팀의 팀원인 경우 한개 팀만 있을수도 있음
 * 팀을 구성하기 위해 함께하고 싶은 1명의 팀원 선택
 * (혼자하고 싶은 경우 자기자신도 가능)
 * 학생 S1~Sr 
 * r=1이고  s1-s1을 선택
 * 또는 s1-s2선택, s2-s3선택, .. sr-1-sr선택, sr-s1선택
 * 하는 경우 1개의 팀 가능
 * 어느팀에도 속하지 않은 학생들의 수 계산
 * 
 * 입력
 * 첫째줄: 테케의 수 T
 * 각테케 첫째줄: 학생수 n
 * 각테케 둘째줄: 선택된 학생들의 번호(1~n) 
 * 
 * 출력
 * 어느팀에도 속하지 않은 학생들의 수 계산
 * 
 * 문제해결프로세스(union -> 틀림)
 * 1. 자기자신 부모
 * 2. 부모 고르기
 * 3. 부모 재정렬 했을때 부모
 * 	자기자신==부모 또는 같은 부모인 다른학생 존재: continue
 *	자기자신!=부모이고 같은 부모인 다른학생 존재x -> cnt++
 * 
 * 문제해결프로세스(bfs)
 * cycle발생 체크
 * 
 * 제한사항
 * 
 * 
 * 시간복잡도
 * 
 */
public class Main {
	static int n, cnt;
	static int student[];
	static boolean visited[],done[];
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < T; i++) {
			n = Integer.parseInt(br.readLine());
			cnt = 0;
			visited = new boolean[n+1];
			student = new int[n+1];
			done = new boolean[n+1];
			st = new StringTokenizer(br.readLine());
			for (int j = 1; j <= n; j++) {
				student[j] = Integer.parseInt(st.nextToken());
			}
			for (int j = 1; j <= n; j++) {
				dfs(student[j]);
			}
			sb.append(n-cnt).append('\n');
		}
		System.out.println(sb);
	}
	
	public static void dfs(int now) {
		visited[now] = true;
		int next = student[now];
		
		if(!visited[next])
			dfs(next);
		else {
			//cycle 탐색한적없음(이미 탐색했으면 cnt++했기때문)
			if(!done[next]) {
				while(true) {
					cnt++;
					if(now == next) {
						break;
					}
					next = student[next];
				}
			}
		}
		done[now] = true;
	}
}