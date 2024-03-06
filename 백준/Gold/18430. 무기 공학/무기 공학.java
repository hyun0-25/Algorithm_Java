import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 시작시간:9:21
 * 종료시간:
 * 
 * 문제해석
 * NxM크기의 직사각형 각 칸마다 서로 다른 강도
 * 잘라서 여러개의 부메랑 만들려고함
 * 항상 3칸을 차지하는 ㄱ모양 -> 4가지 가능
 *  우하, 우상, 좌하, 좌상
 * 부메랑의 중심은 강도의 영향을 2배로 받음(꺾이는부분)
 * 나무조각이 남아도됨 -> 하지만 비효율적일것
 * 
 * 입력
 * 첫째줄: N,M
 * N개줄: M개 자연수들
 *  
 * 출력
 * 부메랑의 강도 합의 최댓값
 * 나무 재료가 작아서 부메랑 제작불가시 0
 * 
 * 문제해결프로세스
 * 중심 뽑기 + 방향 뽑기
 * for i
 * 	for j
 *   visited = false면
 *   ㄱ모양 4가지 가능?
 *   
 * 
 * 제한사항
 * N,M<=5
 * 
 * 2^25*4
 * 
 * 시간복잡도
 * 
 */
public class Main {
	static int N,M,max;
//	static int r,c,dx1,dy1,dx2,dy2,idx1,idx2;
	static int map[][];
	static boolean visited[];
	
	static class Wood{
		int idx, dir;

		public Wood(int idx, int dir) {
			super();
			this.idx = idx;
			this.dir = dir;
		}
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		map = new int[N][M];
		
		//제작불가
		if(N==1 || M==1) {
			System.out.println(0);
			System.exit(0);
		}
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		//제작가능
		visited = new boolean[N*M];
		splitwood(0, 0);
		System.out.println(max);
		
	}
	//우하, 좌하, 좌상, 우상
	//0,1/1,2,/2,3/3,0
	//우하좌상 순
	static int dx[] = {1,0,-1,0};
	static int dy[] = {0,1,0,-1};
	public static void splitwood(int cnt, int total) {
		boolean result = false;
		if(cnt==N*M) {
//			System.out.println("총합 "+total );
			max = Math.max(max, total);
			
			return;
		}
		
//		for (int i = start; i < N*M; i++) {
			//방문하지 않았으면, ㄱ순서대로 대입
//			System.out.println(i);
			if(!visited[cnt]) {
				int r = cnt/M;
				int c = cnt%M;
//				System.out.println("*** "+cnt+" "+r+" rc "+c);
				for (int d = 0; d < 4; d++) {
					int dx1 = r+dx[d];
					int dy1 = c+dy[d];
					int idx1 = dx1*M+dy1;
					if(!rangecheck(dx1,dy1) || visited[idx1]) {
						continue;
					}
					
					int dx2,dy2,idx2;
					if(d==3) {
						 dx2 = r+dx[0];
						 dy2 = c+dy[0];
						 idx2 = dx2*M+dy2;
					}
					else {
						 dx2 = r+dx[d+1];
						 dy2 = c+dy[d+1];
						 idx2 = dx2*M+dy2;
					}
					
					if(!rangecheck(dx2,dy2) || visited[idx2]) {
						continue;
					}
					
//					System.out.println("++ 성공 "+r +" "+c + " "+d);
					
					//else
					visited[cnt] = true;
					visited[idx1] = true;
					visited[idx2] = true;
//					System.out.println(Arrays.toString(visited));
					int add = 2*map[r][c] + map[dx1][dy1] + map[dx2][dy2];
					splitwood(cnt+1, total+add);
//					System.out.println("-- 철수 "+r +" "+c + " "+d);
					visited[cnt] = false;
					visited[idx1] = false;
					visited[idx2] = false;
				}
			}
//			if(!result) {
//				System.out.println("실패 "+cnt);
				splitwood(cnt+1, total);
//			}
		}
//	}
	public static boolean rangecheck(int rx, int ry) {
		return rx>=0 && rx<N && ry>=0 && ry<M;
	}
}