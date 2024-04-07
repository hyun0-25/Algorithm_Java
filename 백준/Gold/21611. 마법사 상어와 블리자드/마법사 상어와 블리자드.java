import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 시작: 20:35
 * 종료:
 * 
 * 문제해석
 * NxN 격자
 * N은 항상 홀수, (1,1)~(N,N)
 * 마법사 상어는 (N+1)/2,(N+1)/2 정중앙에 위치
 * 
 * 같은번호를 자진 구슬이 연속하는 칸에 위치 -> 연속하는 구슬
 * 
 * 1. 블리자드 마법 d방향, s거리 (1상2하3좌4우 순)
 * d방향으로 거리가 s이하인 모든 칸에 얼음 파편을 던져 그 칸에 있는 구슬을 모두 파괴
 * 구슬이 파괴되면 그 칸은 구슬이 들어있지 않은 빈칸
 * 칸A의 번호보다 번호가 하나 작은 칸이 빈칸이면 A에 있는 구슬은 그 빈칸으로 이동
 * 
 * 2. 구슬 폭발 - 4개이상 연속하는 구슬이 있으면 발생
 * 빈칸 생기면 다시 이동
 * 이동 후 또 생기면 또 폭발, 폭발 구슬이 없을때까지 반복
 * 
 * 3. 구슬 변화 - 연속하는 구슬은 하나의 그룹
 * 하나의 그룹은 두개의 구슬 A,B로 변함
 * A: 그룹에 들어있는 구슬의 개수
 * B: 그룹을 이루고 있는 구슬번호
 * 구슬은 그룹의 순서대로 1번칸부터 차례대로 A,B 순으로 칸에 들어감
 * 구슬이 칸의 수보다 많아 칸에 들어가지 못하는 경우 구슬은 사라짐
 * 
 * 
 * 입력
 * 
 * 출력
 * M번 블리자드 시전시
 * 2에서 폭발한 구슬 -> 1*(폭발1번개수)+2*(폭발2번개수)+3*(폭발3번개수)
 * 
 * 
 * 문제해결프로세스
 * 1. 우선순위대로 저장된 순서 List
 * 2. 블리자드 마법
 * 3. 1~끝 까지 우선순위 순서대로 돌기
 * 		0만나는 지점 저장
 * 		0만나는지점~끝 까지 0의개수 세기
 * 		0만나는 지점부터 0의개수만큼 땡기기
 * 4. 1~끝 까지 연속된 개수 4개이상인 구슬 폭발(1,2,3폭발개수세기)
 * 		0만나는 지점 저장
 * 		0만나는지점~끝 까지 0의개수 세기
 * 		0만나는 지점부터 0의개수만큼 땡기기
 * 5. 1~끝 까지 A:구슬번호, B: 연속구슬개수 순서대로 1~끝까지 넣기
 * 		NxN보다 많이넣으면 끝
 *  
 * 시간복잡도
 * 
 * 
 */
public class Main {
	static int N,M;
	static int map[][];
	static List<Pair> number = new ArrayList<>();
	static int n1,n2,n3;
	static class Pair{
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
		map = new int[N][N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		makenumber();
		
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int d = Integer.parseInt(st.nextToken())-1;
			int s = Integer.parseInt(st.nextToken());
			magic(d,s);
			while(bomb()>0);
			change();
		}
		
		int result = n1+2*n2+3*n3;
		System.out.println(result);
	}
	/* 2. 블리자드 마법 (1상2하3좌4우 순)
	 * 3. 1~끝 까지 우선순위 순서대로 돌기 
	 * 		0만나는 지점 저장
	 * 		0만나는지점~끝 까지 0의개수 세기
	 * 		0만나는 지점부터 0의개수만큼 땡기기
	 * 4. 1~끝 까지 연속된 개수 4개이상인 구슬 폭발(1,2,3폭발개수세기)
	 * 		0만나는 지점 저장
	 * 		0만나는지점~끝 까지 0의개수 세기
	 * 		0만나는 지점부터 0의개수만큼 땡기기
	 * 5. 1~끝 까지 A: 연속구슬개수, B:구슬번호 순서대로 1~끝까지 넣기
	 * 		NxN보다 많이넣으면 끝
	 */
	public static void change() {
		int copymap[][] = new int[N][N];
		int idx = 0;
		int cnt = 1;
		int n = 0;
		for (int i = 0; i < N*N-1; i++) {
			Pair move = number.get(i); 
			int x = move.x;
			int y = move.y;
			
			if(map[x][y]==n) {
				cnt++;
			}
			//초기화
			else {
				if(n>0) {
					Pair origin = number.get(idx);
					int ox = origin.x;
					int oy = origin.y;
					//A
					copymap[ox][oy] = cnt;
					idx++;
					if(idx>=N*N-1) break;
					//B
					origin = number.get(idx);
					ox = origin.x;
					oy = origin.y;
					copymap[ox][oy] = n;
					idx++;
					if(idx>=N*N-1) break;
				}
				
				cnt = 1;
				n = map[x][y];
			}
		}
		for (int i = 0; i < N; i++) {
			map[i] = copymap[i].clone();
		}
	}
	
	public static int bomb() {
		int b1 = 0;
		int b2 = 0;
		int b3 = 0;
		int idx = 0;
		int cnt = 1;
		int n = 0;
		for (int i = 0; i < N*N-1; i++) {
			Pair move = number.get(i); 
			int x = move.x;
			int y = move.y;
			
			if(map[x][y]==n) {
				cnt++;
			}
			//초기화
			else {
				if(cnt>=4) {
					for (int j = idx; j < idx+cnt; j++) {
						Pair origin = number.get(j);
						int ox = origin.x;
						int oy = origin.y;				
						if(map[ox][oy]==1) {
							b1++;
						}
						else if(map[ox][oy]==2) {
							b2++;
						}
						else if(map[ox][oy]==3) {
							b3++;
						}
						map[ox][oy] = 0;
					}
				}
				cnt = 1;
				idx = i;
				n = map[x][y];
			}
		}
		n1+=b1;
		n2+=b2;
		n3+=b3;
		moveball();
		
		return b1+b2+b3;
	}
	
	public static void moveball() {
		int copymap[][] = new int[N][N];
		int idx = 0;
		for (int i = 0; i < N*N-1; i++) {
			Pair move = number.get(i); 
			int x = move.x;
			int y = move.y;
			if(map[x][y]>0) {
				Pair origin = number.get(idx);
				int ox = origin.x;
				int oy = origin.y;
				copymap[ox][oy] = map[x][y];
				idx++;
			}
		}
		for (int i = 0; i < N; i++) {
			map[i] = copymap[i].clone();
		}
	}
	
	static int dx[] = {-1,1,0,0};
	static int dy[] = {0,0,-1,1};
	public static void magic(int d, int s) {
		int sx = N/2;
		int sy = N/2;
		for (int i = 0; i < s; i++) {
			sx += dx[d];
			sy += dy[d];
			map[sx][sy] = 0;
		}

		moveball();
	}
	
	
	public static void makenumber() {
		int sx = N/2;
		int sy = N/2;
		int[] dx1 = {0,1,0,-1};
		int[] dy1 = {-1,0,1,0};
		
		int n = 1;
		int k = 0;
		boolean finish=false;
		while(true) {
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < n; j++) {
					int x = sx+dx1[k];
					int y = sy+dy1[k];
					number.add(new Pair(x,y));
					sx = x;
					sy = y;
					if(number.size()==N*N-1) {
						finish = true;
						break;
					}
				}
				if(finish) break;
				k++;
				if(k>=4) k-=4;
			}
			if(finish) break;
			n++;
		}
	}
}