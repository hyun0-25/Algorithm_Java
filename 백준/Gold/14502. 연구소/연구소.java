import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 시작시간: 11:24
 * 종료시간:
 * 
 * 
 * 문제 해석
 * NxM 직사각형에 빈칸 또는 벽
 * 바이러스는 상하좌우로 인접한 빈칸으로 퍼져나감
 * 새로 세울 수 있는 벽의 개수는 3개 -> 무조건 세워야됨
 * 
 * 입력
 * 0 빈칸, 1 벽, 2 바이러스
 * 첫째줄 : N,M
 * N개줄: 지도
 * 출력
 * 
 * 
 * 문제해결 프로세스
 * 1. 벽을 세울 3개 (i,j) 선택 -> 조합 comb()
 * 	1-1. 빈칸 0의 위치 3가지 선택
 * 2. 각 경우의 수 마다 바이러스 확산 -> bfs로 virus()
 * 	2-1. 벽 또는 맵 밖은 확산 불가
 * 3. 각 경우의 수 마다 0의 개수 세기 -> count()
 * 4. 3에서 얻은 값의 최댓값 갱신
 * 
 * 
 * 제한조건
 * 
 * 
 * 
 * 시간복잡도
 * 
 * 
 * 
 */

public class Main {
	static int N,M, map[][], newmap[][];
	
	static class Pair{
		int x,y;
		public Pair(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
	}
	static ArrayList<Pair> list = new ArrayList<>();
	static ArrayList<Pair> viruslist = new ArrayList<>();
	static Pair[] isSelected;
	static int max;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		map = new int[N][M];
		newmap = new int[N][M];
		
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if(map[i][j]==0) {
					list.add(new Pair(i,j));
				}
				else if(map[i][j]==2) {
					
					viruslist.add(new Pair(i,j));
				}
			}
		}
		isSelected = new Pair[3];
		comb(0, 0);
		
		System.out.println(max);
	}
	/* 문제해결 프로세스
	 * 1. 벽을 세울 3개 (i,j) 선택 -> 조합 comb()
	 * 	1-1. 빈칸 0의 위치 3가지 선택
	 * 2. 각 경우의 수 마다 바이러스 확산 -> bfs로 virus()
	 * 	2-1. 벽 또는 맵 밖은 확산 불가
	 * 3. 각 경우의 수 마다 0의 개수 세기 -> count()
	 * 4. 3에서 얻은 값의 최댓값 갱신
	 */
	
	public static void comb(int cnt, int start) {
		if(cnt==3) {
			virus(isSelected);
			return;
		}
		
		for (int i = start; i < list.size(); i++) {
			isSelected[cnt] = list.get(i);
			comb(cnt+1, i+1);
		}
	}
	static int dx[] = {-1,1,0,0};
	static int dy[] = {0,0,-1,1};
	public static void virus(Pair[] sel) {
		for (int i = 0; i < N; i++) {
			newmap[i] = Arrays.copyOf(map[i], M);
		}
		//3개 벽 세우기
		for (Pair p: sel) {
			newmap[p.x][p.y]=1;
		}
		//바이러스 확산
		Queue<Pair> q = new ArrayDeque<>();
		for(Pair v: viruslist) {
			q.offer(v);
		}
		while(!q.isEmpty()) {
			Pair p = q.poll();
			for (int i = 0; i < 4; i++) {
				int nx = p.x+dx[i];
				int ny = p.y+dy[i];
				if(nx<0 || nx>=N || ny<0 || ny>=M)
					continue;
				if(newmap[nx][ny]==0) {
					q.offer(new Pair(nx,ny));
					newmap[nx][ny]=2;
				}
			}
		}
		count(newmap);
	}
	public static void count(int[][] newmap) {
		int cnt = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if(newmap[i][j]==0) cnt++;
			}
		}
		max = Math.max(max, cnt);
	}
}