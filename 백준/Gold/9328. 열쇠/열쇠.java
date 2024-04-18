import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 시작: 21:54
 * 종료:
 * 
 * 문제해석
 * 문서 훔쳐야됨
 * 평면도에는 문서의 위치
 * 빌딩의 문은 모두 잠겨있음, 문을 열려면 열쇠 필요
 * 일부 열쇠 가지고 있음, 일부 열쇠는 빌딩의 바닥에 잇음
 * 상하좌우 4방 이동
 * 
 * 훔칠 수 있는 문서의 최대 개수
 * 
 * 입력
 * 첫째줄: 지도 높이h,너비w
 * h개줄: w개의 문자
 * . 빈공간
 * * 벽(못지나감)
 * $ 훔쳐야하는 문서
 * 대문자 문
 * 소문자 열쇠(그 문자의 대문자인 모든 문 열수있음)
 * 마지막줄: 이미 가지고 있는 열쇠, 하나도 없는 경우 0
 * 
 * 
 * 
 * 출력
 * 
 * 문제해결프로세스 bfs
 * 
 * 
 * 시간복잡도
 * 
 */
public class Main {
	static int h,w,total;
	static char [][] map;
	static boolean visited[][], keys[];
	static List<Pair> doors[];
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		for (int test_case = 0; test_case < T; test_case++) {
			st = new StringTokenizer(br.readLine());
			h = Integer.parseInt(st.nextToken());
			w = Integer.parseInt(st.nextToken());
			map = new char[h+2][w+2];
			for (int i = 1; i <= h; i++) {
				char[] c = br.readLine().toCharArray();
				for (int j = 1; j <= w; j++) {
					map[i][j] = c[j-1];
				}
			}
			
			keys = new boolean[26];
			doors = new List[26];
			for (int i = 0; i < 26; i++) {
				doors[i] = new ArrayList<>();
			}
			char[] key = br.readLine().toCharArray();
			if(key[0]!='0') {
				for (int i = 0; i < key.length; i++) {
					keys[key[i]-'a'] = true;
				}
			}
			total = 0;
			bfs();
			sb.append(total).append('\n');
		}
		System.out.println(sb);
	}
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1}; 
	static class Pair {
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
	public static void bfs() {
		visited = new boolean[h+2][w+2];
		
		Queue<Pair> q = new ArrayDeque<>();
		q.offer(new Pair(0,0));
		visited[0][0] = true;
		
		while(!q.isEmpty()) {
			Pair p = q.poll();
			int x = p.x;
			int y = p.y;
			
			for (int i = 0; i < 4; i++) {
				int nx = x+dx[i];
				int ny = y+dy[i];
				if(!rangecheck(nx,ny) || map[nx][ny]=='*' || visited[nx][ny]) continue;
				//열쇠 주움
				if(map[nx][ny]>='a' && map[nx][ny]<='z') {

					keys[map[nx][ny]-'a'] = true;
					for (int j = 0; j < doors[map[nx][ny]-'a'].size(); j++) {
						Pair dp = doors[map[nx][ny]-'a'].get(j);
						q.offer(dp);
						visited[dp.x][dp.y] = true;
					}
				}
				//문 만남
				if(map[nx][ny]>='A' && map[nx][ny]<='Z') {
					//열쇠없음
					if(!keys[map[nx][ny]-'A']) {
						doors[map[nx][ny]-'A'].add(new Pair(nx,ny));
						continue;
					}
				}
				//문서 줍기
				if(map[nx][ny]=='$') {
					total++;
					map[nx][ny]='.';
				}
				
				q.offer(new Pair(nx,ny));
				visited[nx][ny]=true;
			}
			
		}
	}
	
	public static boolean rangecheck(int rx, int ry) {
		return rx>=0 && rx<h+2 && ry>=0 && ry<w+2;
	}

}