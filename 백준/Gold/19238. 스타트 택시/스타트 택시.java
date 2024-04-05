import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 시작: 16:56
 * 종료: 
 * 
 * 문제해석
 * 손님을 도착지에 데려다주면 연료충전, 연료 바닥나면 끝
 * M명의 승객을 태우는 것이 목표
 * NxN 크기의 격자 (비어있거나 벽)
 * 4방 이동가능 - 최단경로로만 이동
 * 
 * M명의 승객은 빈칸 중 하나에 서있음
 * 다른 빈칸 중 하나로 이동하려함
 * 여러 승객이 같이 탑승하는 경우는 없음
 *  -> 한 승객-목적지 이동 M번 반복
 * 각 승객은 출발지에서만 택시타고 목적지에서만 내릴수있음
 * 
 * 태울 승객 고르기 -> 현재 위치에서 최단거리가 가장 짧은 승객
 * 			그런 승객이 여러명 -> 행번호 작게
 * 			행번호 같은 승객여러명 -> 열번호 작게
 * 택시==승객 위치면 최단거리는 0임
 * 
 * 연료는 1칸이동 1소모
 * 승객 태우면 소모한 연료양의 두배 충전
 * 이동하는 도중에 연료 다쓰면 이동실패, 업무 끝
 * 승객을 목적지로 이동시킨 동시에 연료가 바닥나는 경우는 실패X
 * 
 * 
 * 입력
 * 첫째줄: N,M, 초기 연료양
 * N개줄: 지도NxN
 * 1개줄: 택시위치
 * M개줄: 출발지 행,열 / 목적지 행,열
 * 
 * 출력
 * 모든 승객 데려다줌 -> 최종적으로 남은 연료의양
 * 못데려다줌 -> -1
 * 
 * 문제해결프로세스 bfs(depth별)
 * 1. texi: 택시-손님 최단거리
 * 		손님 발견시 pq에 담음
 * 		pq의 size가 0보다 크면 종료
 * 		point -= depth 가 0보다 크거나 같으면 이동
 * 						0보다 작으면 종료(-1)
 * 		이동 완료 -> 택시 위치조정
 * 2. move: pq의 맨 첫번째의 goal()까지 최단거리
 * 		point -= depth 가 0보다 크거나 같으면 이동
 * 						0보다 작으면 종료(-1)
 * 		이동 완료 -> point += depth*2 / cnt++; / 택시 위치조정
 * 3. cnt==승객수면 종료 (연료출력)
 * 	  cnt<승객수면 1번,2번 반복
 * 
 * 
 * 시간복잡도
 * 
 * 
 */
public class Main {
	static int N,M,total,cnt;
	static int [][] map;
	static int [][] guest;
	static int [][] goal;
	static int tx,ty;
	static class Pair implements Comparable<Pair>{
		int x,y;
		public Pair(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
		@Override
		public int compareTo(Pair o) {
			if(this.x==o.x) {
				return this.y-o.y;
			}
			return this.x-o.x;
		}
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		total = Integer.parseInt(st.nextToken());
		map = new int[N][N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		st = new StringTokenizer(br.readLine());
		tx = Integer.parseInt(st.nextToken())-1;
		ty = Integer.parseInt(st.nextToken())-1;
		guest = new int[N][N];
		goal = new int [M+1][2];
		for (int i = 1; i <= M; i++) {
			st = new StringTokenizer(br.readLine());
			int sx = Integer.parseInt(st.nextToken())-1;
			int sy = Integer.parseInt(st.nextToken())-1;
			int ex = Integer.parseInt(st.nextToken())-1;
			int ey = Integer.parseInt(st.nextToken())-1;
			guest[sx][sy] = i;
			goal[i][0] = ex;
			goal[i][1] = ey;
		}
		bfs();
		System.out.println(total);
	}
	static int dx[] = {-1,1,0,0};
	static int dy[] = {0,0,-1,1};
	public static void bfs() {
		int t = texi();
		if(t==-1) {
			System.out.println(-1);
			System.exit(0);
		}
		//else
		int m = move(t);
		if(m==-1) {
			System.out.println(-1);
			System.exit(0);
		}
		if(cnt==M) return;
		//else
		bfs();
	}
	/* 문제해결프로세스 bfs(depth별)
	 * 1. texi: 택시-손님 최단거리
	 * 		손님 발견시 pq에 담음
	 * 		pq의 size가 0보다 크면 종료
	 * 		total -= depth 가 0보다 크거나 같으면 이동
	 * 						0보다 작으면 종료(-1)
	 * 		이동 완료 -> 택시 위치조정
	 * 2. move: pq의 맨 첫번째의 goal()까지 최단거리
	 * 		total -= depth 가 0보다 크거나 같으면 이동
	 * 						0보다 작으면 종료(-1)
	 * 		이동 완료 -> total += depth*2 / cnt++; / 택시 위치조정
	 * 3. cnt==승객수면 종료 (연료출력)
	 * 	  cnt<승객수면 1번,2번 반복
	 */
	
	public static int move(int t) {
		boolean visited[][] = new boolean[N][N];
		Queue<Pair> q = new ArrayDeque<>();
		q.offer(new Pair(tx,ty));
		visited[tx][ty] = true;
		int depth = 0;
		while(!q.isEmpty() && total>=depth) {
			int size = q.size();
			for (int k = 0; k < size; k++) {
				Pair p = q.poll();
				int x = p.x;
				int y = p.y;
				//도착지발견
				if(goal[t][0]==x && goal[t][1]==y) {
					tx = x;
					ty = y;
//					total-=depth;
//					total+=depth*2;
					total+=depth;
					cnt++;
					return 0;
				}
				for (int i = 0; i < 4; i++) {
					int nx = x+dx[i];
					int ny = y+dy[i];
					if(!rangecheck(nx,ny) || visited[nx][ny] || map[nx][ny]==1) continue;
					//else
					q.offer(new Pair(nx,ny));
					visited[nx][ny] = true;
					
				}
			}
			depth++;
		}
		return -1;
	}
	
	public static int texi() {
		boolean visited[][] = new boolean[N][N];
		Queue<Pair> q = new ArrayDeque<>();
		q.offer(new Pair(tx,ty));
		visited[tx][ty] = true;
		
		PriorityQueue<Pair> pq = new PriorityQueue<>();
		int depth = 0;
		while(!q.isEmpty() && total>=depth) {
			int size = q.size();
			for (int k = 0; k < size; k++) {
				Pair p = q.poll();
				int x = p.x;
				int y = p.y;
				//손님발견
				if(guest[x][y]>0) {
					pq.offer(new Pair(x,y));
				}
				for (int i = 0; i < 4; i++) {
					int nx = x+dx[i];
					int ny = y+dy[i];
					if(!rangecheck(nx,ny) || visited[nx][ny] || map[nx][ny]==1) continue;
					//else
					q.offer(new Pair(nx,ny));
					visited[nx][ny] = true;
				}
			}
			// 한명이라도 손님 만남
			if(pq.size()>0) {
				//우선순위 가장높은 손님
				Pair p = pq.poll();
				tx = p.x;
				ty = p.y;
				total-=depth;
				int g = guest[p.x][p.y];
				guest[p.x][p.y] = 0;
				return g;
			}
			depth++;
		}
		return -1;
	}
	
	public static boolean rangecheck(int rx, int ry) {
		return rx>=0 && rx<N && ry>=0 && ry<N;
	}
}