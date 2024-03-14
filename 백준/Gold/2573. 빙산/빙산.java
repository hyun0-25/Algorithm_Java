import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 시작: 21:49
 * 종료:
 * 
 *  
 * 문제해석
 * 빙산을 2차원배열에 표시
 * 빙산의 각부분별 높이 정보는 각 칸에 저장
 * 빙산 이외의 바다에 해당되는 칸(빈칸)에는 0
 * 
 * 빙산의 높이는 바닷물에 많이 접해있는 부분에서 더 빨리 줄어듦
 * 배열에서 빙산의 각 부분에 해당되는 칸에 있는 높이는 일년마다 4방으로 붙어있는 0개수만큼 감소
 * 단, 각 칸에 저장된 높이는 0보다 작을수없음
 * 4방으로 붙어있으면 서로 연결됨
 * 
 * 입력
 * N행,M열
 * N개줄: M개 정수(0~10)
 * 
 * 출력
 * 한 덩어리 빙산이 주어질 때, 두덩어리 이상으로 분리되는 최초의 년도
 * 전부 다 녹을 떄까지 두덩이 이상 분리X -> 0출력
 * 
 * 
 * 문제해결프로세스 (구현, BFS)
 * 1. for i
 * 		for j -> 4방의 빈칸개수 세기
 * 	melt[][] = cnt
 * 	map[][] -= melt[][]
 * 2. countIce()
 * 		: bfs로 돌면서 덩어리 세기
 * 	덩어리수 == 0이면 종료(0출력)
 * 	덩어리수 == 2이면 종료(year출력)
 * 
 * 제한사항
 * N,M<=300
 * 0<=각 칸 값 <=10
 * 
 * 시간복잡도
 * 충분한듯
 * 
 */

public class Main {
	static int N,M,year;
	static int map[][],melt[][];
	static int numIce;
	static boolean visited[][];
	static class Pair {
		int x,y;
		public Pair(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
	}
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
		
		while(true) {
			year++;
			calculate();
			countIce();
			if(numIce == 0 || numIce >= 2)
				break;
		}
		System.out.println(numIce == 0 ? 0 : year);
	}
	/* 문제해결프로세스 (구현, BFS)
	 * 1. for i
	 * 		for j -> 4방의 빈칸개수 세기
	 * 	melt[][] = cnt
	 * 	map[][] -= melt[][]
	 * 2. countIce()
	 * 		: bfs로 돌면서 덩어리 세기
	 * 	덩어리수 == 0이면 종료(-1출력)
	 * 	덩어리수 == 2이면 종료(year출력)
	 */
	static int dx[] = {-1,1,0,0};
	static int dy[] = {0,0,-1,1};
	public static void calculate() {
		melt = new int[N][M];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if(map[i][j]>0) {
					int cnt = 0;
					for (int k = 0; k < 4; k++) {
						int nx = i+dx[k];
						int ny = j+dy[k];
						if(!rangecheck(nx,ny)) continue;
						//else
						if(map[nx][ny]==0) cnt++;
					}
					melt[i][j] = cnt;
				}
			}
		}
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				map[i][j] -= melt[i][j];
				if(map[i][j] < 0) map[i][j] = 0;
			}
		}
	}
	
	public static void countIce() {
		visited = new boolean[N][M];
		numIce = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				//새 덩어리 발견
				if(!visited[i][j] && map[i][j] > 0) {
					numIce++;
					bfs(i,j);
				}
			}
		}
	}
	
	public static void bfs(int bx, int by) {
		Queue<Pair> q = new ArrayDeque<>();
		q.offer(new Pair(bx,by));
		visited[bx][by] = true;
		
		while(!q.isEmpty()) {
			Pair p = q.poll();
			int x = p.x;
			int y = p.y;
			for (int i = 0; i < 4; i++) {
				int nx = x+dx[i];
				int ny = y+dy[i];
				if(!rangecheck(nx,ny) || visited[nx][ny] || map[nx][ny]==0) continue;
				//else
				q.offer(new Pair(nx,ny));
				visited[nx][ny] = true;
			}
		}
	}
	
	public static boolean rangecheck(int rx, int ry) {
		return rx>=0 && rx<N && ry>=0 && ry<M;
	}

}