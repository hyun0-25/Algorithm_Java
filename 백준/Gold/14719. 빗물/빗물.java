import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 시작: 20:47
 * 종료:
 * 
 * 문제해석
 * 2차원 격자
 * 블록사이에 빗물 고임
 * 고이는 빗물의 총량
 * 
 * 
 * 입력
 * 첫번째줄: 세로H, 가로W
 * 둘째줄: 블록이 쌓인 높이 (0~H) W개
 * 	바닥은 항상 막혀있음
 * 
 * 출력
 * 한칸==용량1
 * 빗물이 전혀 고이지 않으면 0출력
 * 
 * 문제해결프로세스
 * FOR i 0~H-1
 * 	FOR j 0~W-1
 * 		
 * 
 * 제한조건
 * 
 * 시간복잡도
 * 
 */
public class Main {
	static int H,W,result;
	static int[] water;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		H = Integer.parseInt(st.nextToken());
		W = Integer.parseInt(st.nextToken());
		water = new int[W];
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < W; i++) {
			water[i] = Integer.parseInt(st.nextToken());
		}
		
		totalWater();
		System.out.println(result);
	}
	public static void totalWater() {
		
		for (int i = 0; i < H; i++) {
			boolean start = false;
			int cnt = 0;
			for (int j = 0; j < W; j++) {
				if(water[j]>0) {
					if(start) {
						result += cnt;
						cnt = 0;
					}
					else { //한번도 블록 못만남
						start = true;
					}
					water[j]--;
				}
				else if(water[j]==0 && start) {
					cnt++;
				}
			}
		}
	}
}