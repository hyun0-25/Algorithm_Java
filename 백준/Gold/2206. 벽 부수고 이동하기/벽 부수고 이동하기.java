import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 시작시간: 21:58
 * 종료시간:
 * 
 * 문제해석
 * NxM 행렬
 * 0은 이동가능, 1은 이동불가,벽
 * (1,1)에서 (N,M)으로 최단경로 이동
 * 시작하는 칸과 끝나는 칸도 포함
 * 
 * 이동하는 중, 한개의 벽을 부수고 이동하여 더 경로가 짧아지면 한개까지 부수고 이동 가능
 * 상하좌우 한칸 이동가능
 * 
 * 입력
 * 첫째줄: N,M
 * N개줄: NxM
 * 시작과 끝은 항상 0
 * 
 * 출력
 * 최단거리 출력, 불가능하면 -1
 * 
 * 
 * 문제해결프로세스
 * 
 * 
 * 제한조건
 * 
 * 시간복잡도
 * 
 */
public class Main {
	static class Pair {
		int x,y,cnt;

		public Pair(int x, int y, int cnt) {
			super();
			this.x = x;
			this.y = y;
			this.cnt = cnt;
		}
	}
	static int N,M;
	static int[][] map;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		map = new int[N][M];
		for (int i = 0; i < N; i++) {
			String str = br.readLine();
			for (int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(str.charAt(j)+"");
			}
		}
		System.out.println(bfs());
		
	}
	static int dx[] = {-1,1,0,0};
	static int dy[] = {0,0,-1,1};
	public static int bfs() {
		Queue<Pair> q = new ArrayDeque<>();
		boolean visited[][][] = new boolean[N][M][2];
		q.offer(new Pair(0,0,0));
		visited[0][0][0] = true;
		int depth = 0;
		while(!q.isEmpty()) {
			int size = q.size();
			for (int i = 0; i < size; i++) {
				Pair p = q.poll();
				int x = p.x;
				int y = p.y;
				int cnt = p.cnt;
				if(x==N-1 && y==M-1) {
					return depth+1;
				}
				for (int d = 0; d < 4; d++) {
					int nx = x +dx[d];
					int ny = y +dy[d];
					if(!rangecheck(nx,ny)) continue;
					//그냥이동
					if(map[nx][ny] == 0 && !visited[nx][ny][cnt]) {
						q.offer(new Pair(nx,ny,cnt));
						visited[nx][ny][cnt] = true;
					}
					//벽부수고 이동
					if(cnt == 0 && map[nx][ny] == 1 && !visited[nx][ny][cnt+1]) {
						q.offer(new Pair(nx,ny,cnt+1));
						visited[nx][ny][cnt] = true;
					}
				}
			}
			depth++;
		}
		return -1;
	}
	
	public static boolean rangecheck(int rx, int ry) {
		return rx>=0 && rx<N && ry>=0 && ry<M;
	}

}