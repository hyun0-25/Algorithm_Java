import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import javax.swing.text.AbstractDocument.BranchElement;

/**
 * 시작: 21:09
 * 종료: 22:07
 * 
 * 문제해석
 * 정사각형 구역 안에 K개의 미생물
 * 가장자리는 약품
 * 1. 최초 미생물 군집의 위치, 미생물수,이동방향 4방
 * 2. 군집은 1시간마다 이동방향에 있는 다음 셀로 이동
 * 3. 군집 이동 후 약품이 칠해진 셀에 도착하면 미생물 절반 죽음, 이동방향 반대로
 * 	미생물 수 홀수->  N/2
 * 	1마리인 경우 0이므로 군집 사라짐
 * 4. 이동 후 2개 이상의 군집이 한 셀에 모이는 경우 군집 합쳐짐
 * 	합쳐진 군집의 미생물수==군집들의 미생물 수의 합
 * 	이동방향==미생물 수가 가장 많은 군집의 이동방향
 * 	미생물 수가 같은 경우는 없음
 * 
 * M시간 동안 미생물 군집을 격리->M시간후 남아있는 미생물 수의 총합
 * 
 * 입력
 * T
 * 첫째줄: N셀크기, M격리시간,K군집수
 * K줄: 군집 정보 r,c,total,dir
 *  상,하,좌,우
 * 출력
 * 
 * 문제해결프로세스
 * 
 * 
 * 
 */
public class Solution {
	static int N,M,K;
	static int[][][] map;
	static class Live{
		int total,dir;
		public Live(int total, int dir) {
			super();
			this.total = total;
			this.dir = dir;
		}
	}
	//상하좌우 순
	static int dx[] = {-1,1,0,0};
	static int dy[] = {0,0,-1,1};
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder();
		for (int test_case = 1; test_case <= T; test_case++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());
			map = new int[N][N][2]; //0:dir,1:max,2:total

			for (int i = 0; i < K; i++) {
				st = new StringTokenizer(br.readLine());
				int r = Integer.parseInt(st.nextToken());
				int c = Integer.parseInt(st.nextToken());
				int val = Integer.parseInt(st.nextToken());
				int dir = Integer.parseInt(st.nextToken())-1;
				map[r][c][0]=dir;
				map[r][c][1]=val;
			}
			
			for (int i = 0; i < M; i++) {
				move();
			}
			
			//결과출력
			int result = 0;
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					result+=map[i][j][1];
				}
			}
			sb.append('#').append(test_case).append(' ').append(result).append('\n');
		}
		System.out.println(sb);
	}
	public static void move() {
		int [][][]copymap = new int[N][N][3];

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if(map[i][j][1]==0) continue;
				int dir = map[i][j][0];
				int val = map[i][j][1];
				
				int nx = i+dx[dir];
				int ny = j+dy[dir];
				if(nx==0 || nx==N-1 || ny==0 || ny==N-1) {
					if(dir==0 || dir==2) dir++;
					else if(dir==1 || dir==3) dir--;
					val/=2;
				}
				if(val > copymap[nx][ny][1]) {
					copymap[nx][ny][1]=val;
					copymap[nx][ny][0]=dir;
				}
				copymap[nx][ny][2]+=val;
			}
		}
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				map[i][j][0] = copymap[i][j][0];
				map[i][j][1] = copymap[i][j][2];
			}
		}
	}
}