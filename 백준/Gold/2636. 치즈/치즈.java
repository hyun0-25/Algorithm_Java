import java.io.BufferedReader;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 시작시간: 23:27
 * 종료시간:
 * 
 * 문제해석
 * 회색: 치즈
 * X: 치즈없음
 * 치즈에는 하나 이상의 구멍
 * 
 * 공기와 접촉된 칸은 녹아없어짐
 * 치즈의 구멍 속에는 공기X, 구멍을 둘러싼 치즈가 녹아서 구멍이 열리면 구멍 속에 공기
 * 치즈가 모두 녹아 없어지는데 걸리는 시간과 모두 녹기 한시간전 남아있는 치즈 조각이 놓여 있는 칸의 개수
 * 
 * 입력
 * 
 * 출력
 * 
 * 문제해결프로세스
 * BFS (depth)
 * 
 * 제한조건
 * 
 * 시간복잡도
 * 
 * 
 */
public class Main {
	static class Pair{
		int x,y;

		public Pair(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
		
	}
	static int N,M,depth,last;
	static int [][]map;
	static int dx[] = {-1,1,0,0};
	static int dy[] = {0,0,-1,1};
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		map = new int[N][M];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine()); 
			for (int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		visited = new int[N][M];
		depth = 1;
		while(bfs()) {
			depth++;
		}
		System.out.println(depth-1);
		System.out.println(last);
		
	}

	static int visited[][];
	static boolean hole[][];

	public static boolean bfs() {
		int hole_cnt = 0;
		int count = 0;
		hole = new boolean[N][M];
		Queue<Pair> q = new ArrayDeque<>();
		q.offer(new Pair(0,0));
		hole[0][0] = true;
		hole_cnt++;
		
		while(!q.isEmpty()) {
			int size = q.size();
			if(hole_cnt == N*M) {
				return false;
			}
			for (int i = 0; i < size; i++) {
				Pair p = q.poll();
				int x = p.x;
				int y = p.y;
				for (int d = 0; d < 4; d++) {
					int nx = x+dx[d];
					int ny = y+dy[d];
					if(!rangecheck(nx,ny) || hole[nx][ny] || visited[nx][ny]==depth) continue;
					if(map[nx][ny]==0) {
						q.offer(new Pair(nx,ny));
						hole[nx][ny] = true;
						hole_cnt++;
					}
					if(map[nx][ny]==1) {
						count++;
						visited[nx][ny] = depth;
						map[nx][ny] = 0;
					}
				}
			}
		}
		last = count;
		return true;
	}

	public static boolean rangecheck(int rx, int ry) {
		return rx>=0 && rx<N && ry>=0 && ry<M;
	}
}