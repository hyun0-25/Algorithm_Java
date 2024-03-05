import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.Arrays;

/**
 * 시작시간: 9:32 종료시간: 10:01
 * 
 * 문제해석 
 * A와 B로만 이루어진 영단어 존재 
 * AB, BAA, AA, ABBA 등 
 * 두 문자열 S,T가 주어졌을때, S->T로 바꿈 
 * 문자열을 바꿀때는 두가지 연산만 가능 
 * 1. 문자열 뒤에 A를 추가 
 * 2. 문자열 뒤에 B를 추가하고 문자열 뒤집음 
 * 주어진 조건을 이용해서 S->T로 만들수 있는지 없는지 출력
 * 
 * 입력 
 * 첫째줄: S 
 * 둘째줄: T
 * 
 * 출력 
 * 가능 1 
 * 불가능 0
 * 
 * 문제해결프로세스 
 * 1. 연산 1,2를 반복 
 * 2. S길이 == T길이면 종료
 * 
 * 제한조건 S<=49 T<=50
 * 
 * 시간복잡도 2^(T-S)
 * 
 */
public class Main {
	static String S, T;
	static boolean head;
	static int n;
	static int calculate[];
	static StringBuilder sb;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		S = br.readLine();
		T = br.readLine();
		n = T.length() - S.length();

		// 연산 순서 뽑기
		calculate = new int[n];
		pick(0, S);
		System.out.println(0);
	}

	public static void pick(int cnt, String str) {
		if (cnt == n) {
			if (T.equals(str)) {
				System.out.println(1);
				System.exit(0);
			}
			return;
		}
		for (int i = 0; i < 2; i++) {
			calculate[cnt] = i;
			if (i == 0) {
				String strA = str + "A";
				if(contain(strA)) {
					pick(cnt + 1, strA);
				}
			}
			if (i == 1) {
				String strB = reverse(str + "B");
				if(contain(strB)) {
					pick(cnt + 1, strB);
				}
			}
		}
	}
	
	public static boolean contain(String str1) {
		String reverseT = "";
		for (int i = T.length() - 1; i >= 0; i--) {
			reverseT += T.charAt(i);
		}
		if (T.contains(str1) || reverseT.contains(str1)) {
			return true;
		}
		return false;
	}

	public static String reverse(String str1) {
		String newstr = "";
		for (int i = str1.length() - 1; i >= 0; i--) {
			newstr += str1.charAt(i);
		}
		return newstr;
	}
}