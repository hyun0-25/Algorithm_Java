import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 시작시간: 23:37 종료시간:
 * 
 * 문제해석 
 * 교실은 NxN 크기의 격자 
 * 학생수는 N^2(1~N^2) 
 * 교실 왼윗(1,1) 오아랫(N,N) 
 * 학생의 순서 + 각 학생이 좋아하는 학생 4명 
 * <<규칙>> 
 * 1. 비어있는 칸 중 좋아하는 학생이 인접한 칸에 가장 많은 칸으로 자리 
 * 2. 1을 만족하는 칸이 여러개면, 인접한 칸 중 비어있는 칸이 가장 많은 칸으로 자리 
 * 3. 2를 만족하는 칸이 여러개면, 행의 번호가 가장작게 -> 열의 번호가 가장 작게 
 * 학생의 만족도 = 인접한 칸에 좋아하는 학생의 수 
 * 0 -> 0 
 * 1 -> 1 
 * 2 -> 10 
 * 3 -> 100 
 * 4 -> 1000
 * 
 * 
 * 입력 첫째줄: N 둘째줄~끝: 학생번호+학생이 좋아하는 학생 4명번호
 * 
 * 출력 학생의 만족도의 총합
 * 
 * 문제해결프로세스 
 * 좋아하는학생수, 비어있는칸수, 행, 열
 * 1. (0,0)~(n-1,n-1)을 n^2번 반복
 * 2. queue에 모든 칸 정보를 담음
 * 3. 정렬 -> 맨 첫번째 pop해서 그 자리에 번호 넣음
 * 4. 반복
 * 
 * 제한조건 N<=20
 * 
 * 시간복잡도
 * n^3 = 8000
 */

public class Main {
	static class Shark implements Comparable<Shark>{
		int x, y, empty, like;

		public Shark(int x, int y, int empty, int like) {
			super();
			this.x = x;
			this.y = y;
			this.empty = empty;
			this.like = like;
		}
		public int compareTo(Shark shark) {
			if(this.like == shark.like) {
				if(this.empty == shark.empty) {
					if(this.x == shark.x) {
						return this.y - shark.y;
					}
					return this.x - shark.x;
				}
				return shark.empty - this.empty;
			}
			return shark.like - this.like;
		}
		@Override
		public String toString() {
			return "Shark [x=" + x + ", y=" + y + ", empty=" + empty + ", like=" + like + "]";
		}
		
	}
	
	static class Info {
		int f1,f2,f3,f4;

		public Info(int f1, int f2, int f3, int f4) {
			super();
			this.f1 = f1;
			this.f2 = f2;
			this.f3 = f3;
			this.f4 = f4;
		}
	}

	static int N, total;
	static int number, s1,s2,s3,s4;
	static int [][] student;
	static int dx[] = {-1,1,0,0};
	static int dy[] = {0,0,-1,1};
	static Info[] info;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		student = new int[N][N];
		info = new Info[N*N+1];
		StringTokenizer st;
		for (int i = 0; i < N*N; i++) {
			st = new StringTokenizer(br.readLine());
			number = Integer.parseInt(st.nextToken());
			s1 = Integer.parseInt(st.nextToken());
			s2 = Integer.parseInt(st.nextToken());
			s3 = Integer.parseInt(st.nextToken());
			s4 = Integer.parseInt(st.nextToken());
			info[number] = new Info(s1,s2,s3,s4); 
			position();
		}
		
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				total += calculate(i,j);
			}
		}
		System.out.println(total);

	}
	
	public static int calculate(int xx, int yy) {
		int like_count = 0;
		int n = student[xx][yy];
		for (int d = 0; d < 4; d++) {
			int nx = xx+dx[d];
			int ny = yy+dy[d];
			if(rangecheck(nx,ny)) {
				s1 = info[n].f1;
				s2 = info[n].f2;
				s3 = info[n].f3;
				s4 = info[n].f4;
				if(likecheck(nx,ny))
					like_count++;
			}
		}
		if(like_count == 1) {
			return 1;
		}
		if(like_count == 2) {
			return 10;
		}
		if(like_count == 3) {
			return 100;
		}
		if(like_count == 4) {
			return 1000;
		}
		//if (like_count == 0) {
		return 0;
	}
	
	public static void position() {
		ArrayList<Shark> sharks = new ArrayList<>();
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if(student[i][j] != 0)
					continue;
				int empty_count = 0;
				int like_count = 0;
				for (int d = 0; d < 4; d++) {
					int nx = i+dx[d];
					int ny = j+dy[d];
					if(rangecheck(nx,ny)) {
						if(emptycheck(nx,ny))
							empty_count++;
						if(likecheck(nx,ny))
							like_count++;
					}
				}
				sharks.add(new Shark(i,j,empty_count,like_count));
//				System.out.println(i+" "+j+" "+empty_count +" "+like_count);
			}
		}
		Collections.sort(sharks);
		Shark s = sharks.get(0);
		student[s.x][s.y] = number;
	}
	public static boolean rangecheck(int rx, int ry) {
		if(rx<0 || rx>=N || ry<0 || ry>=N)
			return false;
		return true;
	}
	public static boolean emptycheck(int ex, int ey) {
		if(student[ex][ey] == 0)
			return true;
		return false;
	}
	
	public static boolean likecheck(int lx, int ly) {
		if(student[lx][ly] == s1 || student[lx][ly] == s2 || student[lx][ly] == s3 || student[lx][ly] == s4)
			return true;
		return false;
	}
	
}