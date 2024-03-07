import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 시작시간: 21:30
 * 종료시간:
 * 
 * 문제해석
 * 파이어스톰 격자 2^N x 2^N
 * A[r][c]=0 얼음 없음/ 얼음의 양 나타냄
 * 
 * 1. 파이어스톰 시전 -> 시전 단계마다 L 결정
 *       2^L x 2^L 크기부분 격자로 나늠
 * 2. 이후 모든 부분 격자를 시계 방향 90도 회전
 * 3. 얼음이 있는 칸 3개 또는 그 이상과 인접해있지 않은 칸은 얼음의양 -1
 * 
 * 
 * 입력
 * 
 * 출력
 * 
 * 문제해결프로세스
 * i -> n-1-i
 * j -> i
 * 
 * 1. 격자만큼 90도 회전 (rotate)
 * 2. 인접한 칸이 <3인 칸 얼음의양 -1
 * 3. Q번 반복
 * 4. 남아있는 얼음양, bfs로 인접한 덩어리중 가장 크게 차지하는 칸개수 출력
 * 
 * 제한사항
 * 
 * 시간복잡도
 * 
 */
public class Main {
	static int N,Q,size,total;
	static int A[][], copyA[][];
	static int max;
	static boolean visited[][];
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		Q = Integer.parseInt(st.nextToken());
		size =  (int) Math.pow(2, N);
		A = new int[size][size];
		for (int i = 0; i < size; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < size; j++) {
				A[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < Q; i++) {
			int L_Q = Integer.parseInt(st.nextToken());
			int L_size = (int) Math.pow(2, L_Q);
			A = rotate(L_size);
			ice();
		}
		
		visited = new boolean[size][size];
		bfs(0,0);
		System.out.println(total);
		System.out.println(max);
	}
	
	static class Pair{
		int x,y;

		public Pair(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
	}
	public static void bfs(int sx, int sy) {
		
		Queue<Pair> q = new ArrayDeque<>();
		boolean stop = false;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {				
				if(A[i][j]!=0 && !visited[i][j]) {
					int cnt = 0;
					q.offer(new Pair(i,j));
					visited[i][j] = true;
					cnt++;
					total+=A[i][j];
					
					while(!q.isEmpty()) {
						Pair p = q.poll();
						int x = p.x;
						int y = p.y;
						for (int k = 0; k < 4; k++) {
							int nx = x+dx[k];
							int ny = y+dy[k];
							if(!rangecheck(nx,ny) || visited[nx][ny] || A[nx][ny]==0) continue;
							//else
							q.offer(new Pair(nx,ny));
							visited[nx][ny] = true;
							cnt++;
							total+=A[nx][ny];
						}
					}
					max = Math.max(max, cnt);
				}
			}
		}
	}
	
	public static int[][] rotate(int small) {
		copyA = new int[size][size];
		int newsize = size/small;
		for (int i = 0; i < newsize; i++) {
			for (int j = 0; j < newsize; j++) {
				//
				for (int p = 0; p < small; p++) {
					for (int q = 0; q < small; q++) {
						int x = i*small + q;
						int y = j*small + small-1-p;
						// 후 = 전
						copyA[x][y] = A[i*small + p][j*small + q];
					}
				}
			}
		}

		return copyA;
	}
	static int dx[] = {-1,1,0,0};
	static int dy[] = {0,0,-1,1};
	public static void ice() {
		boolean minus[][] = new boolean[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if(A[i][j] == 0) continue;
				//else
				int cnt = 0;
				for (int k = 0; k < 4; k++) {
					int nx = i+dx[k];
					int ny = j+dy[k];
					if(rangecheck(nx,ny) && A[nx][ny]>0) cnt++;
				}
				if(cnt<3) {
					minus[i][j] = true;
				}
			}
		}
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if(minus[i][j]) {
					A[i][j]--;
				}
			}
		}
	}
	
	public static boolean rangecheck(int rx, int ry) {
		return rx>=0 && rx<size && ry>=0 && ry<size;
	}
}