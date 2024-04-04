import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 시작: 20:35
 * 종료: 
 * 
 * 
 * 문제해석
 * 직사각형보드에 빨간,파란 구슬 1개씩, 빨간 구슬을 구멍을 통해 빼냄
 * 
 * NxM 보드, 가장자리 막혀있음
 * 보드에는 구멍 하나
 * 파란 구슬은 구멍에 들어가면 안됨
 * 
 * 중력을 이용해서 굴리기
 * 상하좌우 4방 굴리기 가능
 * 
 * 각 동작에서 공은 동시에 움직임
 * 빨 구멍->성공
 * 파 구멍->실패
 * 빨,파 구멍 동시에->실패
 * 빨,파는 동시에 같은 칸에 있을 수 없음
 * 기울이는 동작을 그만하는 것 == 더이상 구슬 안움직임
 * 
 * 최소 몇번만에 빨간 구슬을 구멍에 넣는지
 * 
 * 입력
 * 첫째줄: 세로N,가로M
 * N개줄: M개 문자열
 * .:빈칸
 * #:장애물,벽
 * o:구멍
 * R:빨간
 * B:파란
 * 가장자리는 모두 #벽
 * 
 * 출력
 * 최소 몇 번 만에 빨간 구슬을 구멍을 통해 빼낼 수 있는지 출력
 * 10이하로 움직여서 빨간구슬 못빼내면 -1
 * 
 * 
 * 문제해결프로세스 (백트래킹)
 * 초기 거르기
 * A의 4방이 막혀있음
 * A==구멍
 * B==구멍
 * -----------
 * 기저조건
 * B가 구멍에 들어감(A,B가 동시에들어가는것도해당)
 * 10번 초과
 * min보다 이동횟수 큼
 * 
 * for i 0~3
 * 	move([][], cnt);
 * 
 *
 * move함수
 * R,B
 * 위 : R,B의 j가 다른경우 - 둘다 벽 또는 구멍만날때까지 i++이동
 * 				같은경우 - R,B중 i가 작은것부터 벽 또는 구멍만날때까지 이동
 * 아래: R,B의 j가 다른경우 - 둘다 벽 또는 구멍만날때까지 i--이동
 * 				같은경우 - R,B중 i가 큰것부터 벽 또는 구멍만날때까지 이동
 * 오른 : R,B의 i가 다른경우 - 둘다 벽 또는 구멍만날때까지 j++이동
 * 				같은경우 - R,B중 j가 큰것부터 벽 또는 구멍만날때까지 이동
 * 왼 : R,B의 i가 다른경우 - 둘다 벽 또는 구멍만날때까지 j--이동
 * 				같은경우 - R,B중 j가 큰것부터 벽 또는 구멍만날때까지 이동 
 * 
 * 제한사항
 * 
 * 시간복잡도
 * 
 * 
 */
public class Main {
	static int N,M;
	static char[][] map;
	static class Pair{
		int x,y;
		public Pair(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
		@Override
		public String toString() {
			return "Pair [x=" + x + ", y=" + y + "]";
		}
		
	}
	static Pair red,blue,hole;
	static int dx[] = {-1,1,0,0};
	static int dy[] = {0,0,-1,1};
	static int min = Integer.MAX_VALUE;
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		map = new char[N][M];
		for (int i = 0; i < N; i++) {
			String str = br.readLine();
			for (int j = 0; j < M; j++) {
				map[i][j] = str.charAt(j);
				if(map[i][j]=='R') {
					red = new Pair(i,j);
					map[i][j]='.';
				}
				else if(map[i][j]=='B') {
					blue = new Pair(i,j);
					map[i][j]='.';
				}
				else if(map[i][j]=='O') {
					hole = new Pair(i,j);
				}
			}
		}
		int rx = red.x;
		int ry = red.y;
		int cnt = 0;
		for (int i = 0; i < 4; i++) {
			int nx = rx+dx[i];
			int ny = ry+dy[i];
			if(map[nx][ny]=='#') cnt++;
		}
		if(cnt==4) {
			System.out.println(-1);
			return;
		}
		int bx = blue.x;
		int by = blue.y;
		dfs(new char[N][M], 1, new Pair(rx,ry), new Pair(bx,by));
		
		if(min==Integer.MAX_VALUE) {
			System.out.println(-1);
			return;
		}
		System.out.println(min);
	}

	static class RBcopymap{
		boolean R_hole, B_hole;
		char[][] cmap = new char[N][M];
		Pair red,blue;
		public RBcopymap(boolean r_hole, boolean b_hole, char[][] cmap, Pair red, Pair blue) {
			super();
			R_hole = r_hole;
			B_hole = b_hole;
			this.cmap = cmap;
			this.red = red;
			this.blue = blue;
		}

	}
	static int count;
	public static void dfs(char[][] copymap, int cnt, Pair red, Pair blue) {
		if(cnt>10) {
			return;
		}
		if(cnt>min) {
			return;
		}
		for (int i = 0; i < 4; i++) {
			count = cnt;
			if(cnt==0) {
				for (int j = 0; j < N; j++) {
					copymap[j] = map[j].clone();
				}
			}
			RBcopymap RBcopymap = move(copymap,i,red,blue);
			//실패
			if(RBcopymap.B_hole) continue;
			//성공
			else if(RBcopymap.R_hole) {
				min = Math.min(min, cnt);
				return;
			}
			//else 둘다 아님, 계속
			dfs(RBcopymap.cmap, cnt+1, RBcopymap.red, RBcopymap.blue);
		}
	}
	 /* 기저조건
	 * B가 구멍에 들어감(A,B가 동시에들어가는것도해당)
	 * 10번 초과
	 * min보다 이동횟수 큼
	 * 
	 * for i 0~3
	 * 	move([][], cnt);
	 * 
	 *
	 * move함수
	 * R,B
	 * 위 : R,B의 j가 다른경우 - 둘다 벽 또는 구멍만날때까지 i--이동
	 * 				같은경우 - R,B중 i가 작은것부터 벽 또는 구멍만날때까지 이동
	 * 아래: R,B의 j가 다른경우 - 둘다 벽 또는 구멍만날때까지 i++이동
	 * 				같은경우 - R,B중 i가 큰것부터 벽 또는 구멍만날때까지 이동
	 * 왼 : R,B의 i가 다른경우 - 둘다 벽 또는 구멍만날때까지 j--이동
	 * 				같은경우 - R,B중 j가 큰것부터 벽 또는 구멍만날때까지 이동 
	 * 오른 : R,B의 i가 다른경우 - 둘다 벽 또는 구멍만날때까지 j++이동
	 * 				같은경우 - R,B중 j가 큰것부터 벽 또는 구멍만날때까지 이동

	 */
	public static RBcopymap move(char[][] copymap, int dir, Pair red, Pair blue) {
		int rx = red.x;
		int ry = red.y;
		int bx = blue.x;
		int by = blue.y;
		boolean r_hole = false,b_hole=false;
		switch (dir) {
		case 0:
			if(ry!=by) {
				for (int i = rx; i >= 0; i--) {
					if(map[i][ry]=='#') {
						red = new Pair(i+1,ry);
						break;
					}
					if(map[i][ry]=='O') {
						r_hole=true;
						red = new Pair(i,ry);
						break;
					}
				}
				for (int i = bx; i >= 0; i--) {
					if(map[i][by]=='#') {
						blue = new Pair(i+1,by);
						break;
					}
					if(map[i][by]=='O') {
						b_hole=true;
						blue = new Pair(i,by);
						break;
					}
				}
			}
			else {
				if(rx<bx) {
					for (int i = rx; i >= 0; i--) {
						if(map[i][ry]=='#') {
							red = new Pair(i+1,ry);
							break;
						}
						if(map[i][ry]=='O') {
							r_hole=true;
							red = new Pair(i,ry);
							break;
						}
					}
					for (int i = bx; i >= 0; i--) {
						if(map[i][by]=='#') {
							blue = new Pair(i+1,by);
							break;
						}
						if(map[i][by]=='O') {
							b_hole=true;
							blue = new Pair(i,by);
							break;
						}
						if(i==red.x) {
							blue = new Pair(i+1,by);
							break;
						}
					}
				}
				else {
					for (int i = bx; i >= 0; i--) {
						if(map[i][by]=='#') {
							blue = new Pair(i+1,by);
							break;
						}
						if(map[i][by]=='O') {
							b_hole=true;
							blue = new Pair(i,by);
							break;
						}
					}
					for (int i = rx; i >= 0; i--) {
						if(map[i][ry]=='#') {
							red = new Pair(i+1,ry);
							break;
						}
						if(map[i][ry]=='O') {
							r_hole=true;
							red = new Pair(i,ry);
							break;
						}
						if(i==blue.x) {
							red = new Pair(i+1,ry);
							break;
						}
					}
				}
			}
			break;
		case 1:
			if(ry!=by) {
				for (int i = rx; i < N; i++) {
					if(map[i][ry]=='#') {
						red = new Pair(i-1,ry);
						break;
					}
					if(map[i][ry]=='O') {
						r_hole=true;
						red = new Pair(i,ry);
						break;
					}
				}
				for (int i = bx; i <N; i++) {
					if(map[i][by]=='#') {
						blue = new Pair(i-1,by);
						break;
					}
					if(map[i][by]=='O') {
						b_hole=true;
						blue = new Pair(i,by);
						break;
					}
				}
			}
			else {
				if(rx>bx) {
					for (int i = rx; i < N; i++) {
						if(map[i][ry]=='#') {
							red = new Pair(i-1,ry);
							break;
						}
						if(map[i][ry]=='O') {
							r_hole=true;
							red = new Pair(i,ry);
							break;
						}
					}
					for (int i = bx; i <N; i++) {
						if(map[i][by]=='#') {
							blue = new Pair(i-1,by);
							break;
						}
						if(map[i][by]=='O') {
							b_hole=true;
							blue = new Pair(i,by);
							break;
						}
						if(i==red.x) {
							blue = new Pair(i-1,by);
							break;
						}
					}
				}
				else {
					for (int i = bx; i <N; i++) {
						if(map[i][by]=='#') {
							blue = new Pair(i-1,by);
							break;
						}
						if(map[i][by]=='O') {
							blue = new Pair(i,by);
							b_hole=true;
							break;
						}
					}
					for (int i = rx; i < N; i++) {
						if(map[i][ry]=='#') {
							red = new Pair(i-1,ry);
							break;
						}
						if(map[i][ry]=='O') {
							r_hole=true;
							red = new Pair(i,ry);
							break;
						}
						if(i==blue.x) {
							red = new Pair(i-1,ry);
							break;
						}
					}
					
				}
			}
			break;
		case 2:
			if(rx!=bx) {
				for (int i = ry; i >= 0; i--) {
					if(map[rx][i]=='#') {
						red = new Pair(rx,i+1);
						break;
					}
					if(map[rx][i]=='O') {
						r_hole=true;
						red = new Pair(rx,i);
						break;
					}
				}
				for (int i = by; i >= 0; i--) {
					if(map[bx][i]=='#') {
						blue = new Pair(bx,i+1);
						break;
					}
					if(map[bx][i]=='O') {
						b_hole=true;
						blue = new Pair(bx,i);
						break;
					}
				}
			}
			else {
				if(ry<by) {
					for (int i = ry; i >= 0; i--) {
						if(map[rx][i]=='#') {
							red = new Pair(rx,i+1);
							break;
						}
						if(map[rx][i]=='O') {
							r_hole=true;
							red = new Pair(rx,i);
							break;
						}
					}
					for (int i = by; i >= 0; i--) {
						if(map[bx][i]=='#') {
							blue = new Pair(bx,i+1);
							break;
						}
						if(map[bx][i]=='O') {
							b_hole=true;
							blue = new Pair(bx,i);
							break;
						}
						if(i==red.y) {
							blue = new Pair(bx,i+1);
							break;
						}
					}
					
				}
				else {
					for (int i = by; i >= 0; i--) {
						if(map[bx][i]=='#') {
							blue = new Pair(bx,i+1);
							break;
						}
						if(map[bx][i]=='O') {
							b_hole=true;
							blue = new Pair(bx,i);
							break;
						}
					}
					for (int i = ry; i >= 0; i--) {
						if(map[rx][i]=='#') {
							red = new Pair(rx,i+1);
							break;
						}
						if(map[rx][i]=='O') {
							r_hole=true;
							red = new Pair(rx,i);
							break;
						}
						if(i==blue.y) {
							red = new Pair(rx,i+1);
							break;
						}
					}
				}
			}
			break;
		case 3:
			if(rx!=bx) {
				for (int i = ry; i < M; i++) {
					if(map[rx][i]=='#') {
						red = new Pair(rx,i-1);
						break;
					}
					if(map[rx][i]=='O') {
						r_hole=true;
						red = new Pair(rx,i);
						break;
					}
				}
				for (int i = by; i < M; i++) {
					if(map[bx][i]=='#') {
						blue = new Pair(bx,i-1);
						break;
					}
					if(map[bx][i]=='O') {
						b_hole=true;
						blue = new Pair(bx,i);
						break;
					}
				}
			}
			else {
				if(ry>by) {
					for (int i = ry; i < M; i++) {
						if(map[rx][i]=='#') {
							red = new Pair(rx,i-1);
							break;
						}
						if(map[rx][i]=='O') {
							r_hole=true;
							red = new Pair(rx,i);
							break;
						}
					}
					for (int i = by; i < M; i++) {
						if(map[bx][i]=='#') {
							blue = new Pair(bx,i-1);
							break;
						}
						if(map[bx][i]=='O') {
							b_hole=true;
							blue = new Pair(bx,i);
							break;
						}
						if(i==red.y) {
							blue = new Pair(bx,i-1);
							break;
						}
					}
				}
				else {
					for (int i = by; i < M; i++) {
						if(map[bx][i]=='#') {
							blue = new Pair(bx,i-1);
							break;
						}
						if(map[bx][i]=='O') {
							b_hole=true;
							blue = new Pair(bx,i);
							break;
						}
					}
				
					for (int i = ry; i < M; i++) {
						if(map[rx][i]=='#') {
							red = new Pair(rx,i-1);
							break;
						}
						if(map[rx][i]=='O') {
							r_hole=true;
							red = new Pair(rx,i);
							break;
						}
						if(i==blue.y) {
							red = new Pair(rx,i-1);
							break;
						}
					}
				}
			}
			break;
		}
		return new RBcopymap(r_hole, b_hole, copymap, red, blue);
	}

}