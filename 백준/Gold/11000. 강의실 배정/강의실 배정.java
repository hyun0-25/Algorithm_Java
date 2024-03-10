import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * 시작: 21:36
 * 종료
 * 
 * 문제해석
 * Si~Ti에 끝나는 N개의 수업
 * 최소의 강의실을 사용해서 모든 수업을 가능하게
 * 수업이 끝난 직후에 다음 수업 가능
 * Ti<=Sj 이면 Si,Tj는 같이 듣기 가능
 * 
 * 입력
 * N
 * N개줄: Si, Ti
 * 
 * 출력
 * 최소 강의실 개수
 * 
 * 문제해결프로세스 (그리디)
 * Si 기준으로 정렬 -> Ti 기준으로 정렬
 * List<Pair> list
 * 
 * 제한조건
 * 
 * 시간복잡도
 * 
 */
public class Main {
	static int N;
	static List<Time> list = new ArrayList<>();
	static class Time implements Comparable<Time>{
		int start,end;

		public Time(int start, int end) {
			super();
			this.start = start;
			this.end = end;
		}

		@Override
		public int compareTo(Time o) {
			if(this.start == o.start) {
				return this.end-o.end;
			}
			return this.start-o.start;
		}

		@Override
		public String toString() {
			return "Time [start=" + start + ", end=" + end + "]";
		}
		
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		StringTokenizer st;
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			int S = Integer.parseInt(st.nextToken());
			int T = Integer.parseInt(st.nextToken());
			list.add(new Time(S,T));
		}
		Collections.sort(list);
		minimumClass();
	}
	public static void minimumClass() {
		PriorityQueue<Integer> pq = new PriorityQueue<>();
		//첫 회의실 생성
		pq.offer(list.get(0).end);
		for (int i = 1; i < N; i++) {
			Time t = list.get(i);
			//존재하는 강의실에 넣기 가능
			if(pq.peek() <= t.start) {
				//맨앞 빼고
				pq.poll();
			}
			//새로운 회의실 생성 or 기존 회의실 end 갱신
			pq.offer(t.end);
		}
		System.out.println(pq.size());
	}

	//실패 -> 시간초과 
//	public static void minimumClass() {
//		List<Time> room = new ArrayList<>();
//		//첫번째 강의실 배정
//		
//		for (int i = 0; i < list.size(); i++) {
//			boolean isadd = false;
//			for (int j = 0; j < room.size(); j++) {
//				if(room.get(j).end <= list.get(i).start) {
//					room.set(j, new Time(room.get(j).start, list.get(i).end));
//					isadd = true;
//					break;
//				}
//			}
//			//존재하는 회의실에 못들어감, 새로운 회의실 생성
//			if(!isadd) {
//				room.add(list.get(i));
//			}
//		}
//		System.out.println(room.size());
//	}

}