import java.io.BufferedReader;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 * 시작: 22:32
 * 
 * 
 * 문제해석
 * 보드 위에 있는 전체 블록을 상하좌우 4방향 중 하나로 이동
 * 같은 값을 갖는 두 블록이 충돌하면 두 블록은 하나로 합쳐짐
 * 한번의 이동에서 이미 합쳐진 블록은 또 다른 블록과 다시 합쳐질 수 없음
 * 똑같은 수가 3개 있는 경우 이동하려고 하는 쪽의 칸이 먼저 합쳐짐
 * 위로 이동시키는 경우 위ㅉ고에 있는 블록이 먼저 합쳐짐
 * 
 * 
 * 입력
 * 첫째줄: 보드크기 N
 * N개줄: 보드판 초기상태
 * 
 * 출력
 * 최대 5번 이동해서 만들 수 있는 가장 큰 블록의 값
 * 
 * 문제해결프로세스
 * 상 -> for j 0~n-1 / for i 0~n-1
 * 하 -> for j 0~n-1 / for i n-1~0
 * 좌 -> for i 0~n-1 / for j 0~n-1
 * 우 -> for i 0~n-1 / for j n-1~0
 * 
 * 0 만나면 break
 * 
 * 
 * 제한조건 
 * 
 * 시간복잡도
 * 4^5 = 2^10 = 1024 * 400
 * 
 */
public class Main {
	static int N, max;
	static int[][] map;
	public static void main(String[] args) throws IOException {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	N = Integer.parseInt(br.readLine());
	map = new int[N][N];
	StringTokenizer st;
	for (int i = 0; i < N; i++) {
		st = new StringTokenizer(br.readLine());
		for (int j = 0; j < N; j++) {
			map[i][j] = Integer.parseInt(st.nextToken());
		}
	}
	
	dfs(new int[N][N],0);
	System.out.println(max);
	}
	
	public static void dfs(int[][] copymap, int cnt) {
		if(cnt==5) {
			
			return;
		}
		
		for (int i = 0; i < 4; i++) {
			if(cnt == 0) {
				for (int j = 0; j < N; j++) {
					copymap[j] = map[j].clone();
				}
			}
			//이동함수
			dfs(move(i, copymap), cnt+1);
		}
	}
	
	/* 문제해결프로세스
	 * 상 -> for j 0~n-1 / for i 0~n-1
	 * 하 -> for j 0~n-1 / for i n-1~0
	 * 좌 -> for i 0~n-1 / for j 0~n-1
	 * 우 -> for i 0~n-1 / for j n-1~0
	 * 
	 * 0인 경우
	 * 	newmap = copymap
	 * 0보다큼
	 * 	같음 -> 합치고 idx++;
	 * 	다름 -> idx++;
	 * 
	 */
	public static int[][] move(int dir, int[][] copymap){
		int [][] newmap = new int[N][N];
		boolean [][] visited = new boolean[N][N];
		switch(dir) {
		case 0:
			for (int j = 0; j < N; j++) {
				int idx = 0;
				for (int i = 0; i < N; i++) {
					int x = copymap[i][j];
					int y = newmap[idx][j];
					if(x>0) {
						//초기상태
						if(y==0) {
							newmap[idx][j] = x;
						}
						else {
							if(x==y) {
								newmap[idx][j] = 2*x;
								idx++;
							}
							else {
								idx++;
								newmap[idx][j] = x;
							}
						}
					}
				}
			}
			break;
		case 1:
			for (int j = 0; j < N; j++) {
				int idx = N-1;
				for (int i = N-1; i >= 0; i--) {
					int x = copymap[i][j];
					int y = newmap[idx][j];
					if(x>0) {
						//초기상태
						if(y==0) {
							newmap[idx][j] = x;
						}
						else {
							if(x==y) {
								newmap[idx][j] = 2*x;
								idx--;
							}
							else {
								idx--;
								newmap[idx][j] = x;
							}
						}
					}
				}
			}
			break;
		case 2:
			for (int i = 0; i < N; i++) {
				int idx = 0;
				for (int j = 0; j < N; j++) {
					int x = copymap[i][j];
					int y = newmap[i][idx];
					if(x>0) {
						//초기상태
						if(y==0) {
							newmap[i][idx] = x;
						}
						else {
							if(x==y) {
								newmap[i][idx] = 2*x;
								idx++;
							}
							else {
								idx++;
								newmap[i][idx] = x;
							}
						}
					}
				}
			}
			break;
		case 3:
			for (int i = 0; i < N; i++) {
				int idx = N-1;
				for (int j = N-1; j >= 0; j--) {
					int x = copymap[i][j];
					int y = newmap[i][idx];
					if(x>0) {
						//초기상태
						if(y==0) {
							newmap[i][idx] = x;
						}
						else {
							if(x==y) {
								newmap[i][idx] = 2*x;
								idx--;
							}
							else {
								idx--;
								newmap[i][idx] = x;
							}
						}
					}
				}
			}
			break;
		}
		
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				max = Math.max(newmap[i][j], max);
			}
		}
		
		return newmap;
	}

}