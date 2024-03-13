import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 입력: 22:18
 * 출력:
 * 
 * 문제해석
 * NxN 체스판, 말 K개
 * 하나의 말 위에 다른말 올리기 가능
 * 체스판 - 흰,빨,파 중 하나
 * 체스판 위에 말 K개 놓고 시작(1~K번, 이동방향 4방중 하나)
 * 한번의 턴 -> 1~K번말까지 순서대로 이동
 * (한말 위에 올려진 말도 함께 이동)
 * 말의 이동 방향에 있는 칸에 따라 말의 이동 다름, 말이 4개 이상 쌓이면 종료
 * - A번 말이 이동하려는 칸이 
 * 	1. 흰색인 경우 그 칸으로 이동, 이동하려는 칸에 말이 이미 있는 경우 가장 위에 A번 말 놓음
 * 	- A번 말의 위에 다른말이 있는 경우 A번 말과 위의 모든 말 이동
 * 	- A,B,C로 쌓여 있고 이동하려는 칸에 D,E -> D,E,A,B,C 순
 *  2. 빨간색인 경우 이동한 후 A번 말과 그 위에 있는 모든 말의 쌓여있는 순서 반대로 바꿈
 *  - A,B,C가 이동, 이동하려는 칸에 말이 없는 경우 C,B,A
 *  - A,D,F,G가 이동, 이동하려는 칸에 말이 E,C, -> E,C,G,F,D,A 순
 *  3. 파란색인 경우 A번말의 이동 방향을 반대로 하고 한칸 이동
 *  	방향을 반대로 바꾼 후 이동하려는 칸이 다시 파란색이면 이동X
 *  4. 체스판을 벗어나는 경우 파란색과 같이 취급
 *  
 * 
 * 입력
 * N,K
 * N개줄 체스판정보 0흰,1빨,2파
 * K개줄 1~K번 말정보 (행,열,방향)
 * 
 * 출력
 * 게임이 종료되는 턴 번호
 * 1000보다 크거나 게임이 종료되지 않는 경우 -1출력
 * 
 * 문제해결프로세스
 * 
 * 
 * 
 * 
 * 제한사항
 * 
 * 시간복잡도
 * 
 */
public class Main {
	static int N,K,round;
	static List<Info> move[][];
	static int[][] map;
	static List<Info> horse = new ArrayList<>();
	//우좌상하 순
	static int dx[] = {0,0,-1,1};
	static int dy[] = {1,-1,0,0};
	static class Info {
		int num, x, y, dir;

		public Info(int num, int x, int y, int dir) {
			super();
			this.num = num;
			this.x = x;
			this.y = y;
			this.dir = dir;
		}

		@Override
		public String toString() {
			return "Info [num=" + num + ", x=" + x + ", y=" + y + ", dir=" + dir + "]";
		}
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		map = new int[N][N];
		move = new ArrayList[N][N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				move[i][j] = new ArrayList<>();
			}
		}
		for (int i = 0; i < K; i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken())-1;
			int y = Integer.parseInt(st.nextToken())-1;
			int dir = Integer.parseInt(st.nextToken())-1;
			horse.add(new Info(i,x,y,dir));
			move[x][y].add(new Info(i,x,y,dir));
		}
		
		while(round<1000) {
			moveHorse();
		}
		System.out.println(-1);

	}
	public static void moveHorse() {
		round++;
		for (int i = 0; i < K; i++) {
			Info info = horse.get(i);
			int x = info.x;
			int y = info.y;
			int dir = info.dir;
			int nx = x+dx[dir];
			int ny = y+dy[dir];
			if(rangecheck(nx,ny) && map[nx][ny] == 0) {
				//흰
				white(info);
			}
			else if(rangecheck(nx,ny) && map[nx][ny] == 1) {
				//빨
				red(info);
			}
			else if(!rangecheck(nx,ny) || map[nx][ny] == 2) {
				//파
				blue(info);
			}
			
//			horsecheck();
//			if(rangecheck(nx,ny) && move[nx][ny].size()>=4) {
//				System.out.println(round);
//				System.exit(0);
//			}
		}
	}
	public static void horsecheck(int hx, int hy) {
//		for (int i = 0; i < N; i++) {
//			for (int j = 0; j < N; j++) {
//				if(move[i][j].size()>=4) {
//					System.out.println(round);
//					System.exit(0);
//				}
//			}
//		}
		if(move[hx][hy].size()>=4) {
			System.out.println(round);
			System.exit(0);
		}
	}
	public static void white(Info info) {
		int x = info.x;
		int y = info.y;
		int dir = info.dir;
		int nx = x+dx[dir];
		int ny = y+dy[dir];
		
		int size = move[x][y].size();
		int idx = -1;
		for (int i = 0; i < size; i++) {
			Info min = move[x][y].get(i);
			if(min.num == info.num) {
				idx = i;
			}
		}
		int msize = move[nx][ny].size();
		for (int i = size-1; i >= idx; i--) {
			Info in = move[x][y].get(i);
			Info hin = horse.get(in.num);
			hin.x = nx;
			hin.y = ny;
			move[nx][ny].add(msize, hin);
			move[x][y].remove(i);
		}
		horsecheck(nx,ny);
	}
	
	public static void red(Info info) {
		int x = info.x;
		int y = info.y;
		int dir = info.dir;
		int nx = x+dx[dir];
		int ny = y+dy[dir];
		
		int size = move[x][y].size();
		int idx = -1;
		for (int i = 0; i < size; i++) {
			Info min = move[x][y].get(i);
			if(min.num == info.num) {
				idx = i;
			}
		}
		
		for (int i = size-1; i >= idx; i--) {
			Info in = move[x][y].get(i);
			Info hin = horse.get(in.num);
			hin.x = nx;
			hin.y = ny;
			move[nx][ny].add(hin);
			move[x][y].remove(i);
		}
		horsecheck(nx,ny);
	}

	public static void blue(Info info) {
		int x = info.x;
		int y = info.y;
		int dir = info.dir;
		
		switch(dir) {
		case 0:
			dir = 1;
			break;
		case 1:
			dir = 0;
			break;
		case 2:
			dir = 3;
			break;
		case 3:
			dir = 2;
			break;
		}
		info.dir = dir;
		int nx = x+dx[dir];
		int ny = y+dy[dir];
		//다시 벽 또는 파란색 -> 이동X
		if(rangecheck(nx,ny) && map[nx][ny] == 0) {
			//흰
			white(info);
		}
		else if(rangecheck(nx,ny) && map[nx][ny] == 1) {
			//빨
			red(info);
		}
		else if(!rangecheck(nx,ny) || map[nx][ny] == 2) {
			//다시 파랑색 또는 벽 => 이동X
		}
		
		
	}
	
	public static boolean rangecheck(int rx, int ry) {
		return rx>=0 && rx<N && ry>=0 && ry<N;
	}

}