import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 시작: 12:29
 * 종료: 
 * 
 * 문제해석
 * 4X4 격자
 * (1,1)~(4,4)
 * 격자에는 물고기 M마리, 이동방향-8방 중 하나
 * 상어도 격자 안에 있음
 * 2마리 이상 물고기가 같은 칸에 존재 가능
 * 상어-물고기도 같은 칸에 존재 가능
 * 1. 상어가 모든 물고기에게 복제마법
 *    5번에서 물고기가 복제되어 칸에 나타남
 * 2. 모든 물고기가 1칸 이동
 *    상어칸,물고기냄새칸,격자범위밖==이동불가
 *    각 물고기는 자신의 이동방향이 이동할 수 있는 칸을 향할 때까지 45도 반시계 회전
 *    이동할 수 있는 칸이 없으면 이동X
 *    그 외의 경우는 그 칸으로 이동
 * 3. 상어가 연속 3칸 이동
 *    현재 칸에서 상하좌우 인접 칸으로 이동가능
 *    연속해서 이동하는 칸 중 격자 범위를 벗어나는 칸이 있음==이동불가
 *    연속해서 이동하는 중 상어가 물고기 있는 칸으로 이동->그 칸에 잇는 모든 물고기 격자에서 제외
 *    제외되는 모든 물고기는 물고기 냄새를 남김
 *    가능한 이동 방법 중에서 제외되는 물고기의 수가 가장 많은 방법으로 이동
 *       그런 방법이 여러가지면 사전 순으로 가장 앞서는 방법 이용
 *          (사전순 -> 방향을 정수로 변환, 상1좌2하3우4
 *             수를 이어붙여 정수로 하나 만듦
 *             A,B 두가지 있음 a<b이면 A가 B보다 사전순 앞섬
 *          상하좌 -> 132 하우하->343 => 132<343
 * 4. 두번 전 연습에서 생긴 물고기 냄새가 격자에서 사라짐
 * 5. 1번에서 사용한 복제 마법 완료, 모든 복제된 물고기는 1에서의 위치와 방향을 그대로 가짐
 * 
 * 입력
 * 
 * 출력
 * s번의 연습 후 격자에 있는 물고기의 수
 * 
 * 문제해결프로세스
 * 1. 현재 상태 저장하는 물고기 copyfish
 * 2. 물고기 이동 movefish
 * 3. 상어 이동 4*4*4 -> 가능한 방향 저장
 *       우선순위 : 제외 물고기 수 => 같으면 사전순(3개 격자 상좌하우)
 *       shark(outfish, list 0->1->2)
 *       제외시킨 list 0,1,2의 물고기>0이면 물고기냄새==time
 * 4. time-2값인 물고기냄새 제거
 * 5. copyfish 모두 add
 * 
 * 
 * 시간복잡도
 * 
 * 
 */
public class Main {
   static int M,S,time;
   static int dx[] = {0,-1,-1,-1,0,1,1,1};
   static int dy[] = {-1,-1,0,1,1,1,0,-1};
   static int sx,sy;
   static int [][] map;
   static List<Integer>[][] fishmap;
   static class Fish{
      int x,y,d;
      public Fish(int x, int y, int d) {
         super();
         this.x = x;
         this.y = y;
         this.d = d;
      }
      @Override
      public String toString() {
         return "Fish [x=" + x + ", y=" + y + ", d=" + d + "]";
      }
   }
   public static void main(String[] args) throws IOException {
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      StringTokenizer st = new StringTokenizer(br.readLine());
      M = Integer.parseInt(st.nextToken());
      S = Integer.parseInt(st.nextToken());
      map = new int[4][4];
      fishmap = new ArrayList[4][4];
      for (int i = 0; i < 4; i++) {
         for (int j = 0; j < 4; j++) {
            fishmap[i][j] = new ArrayList<>();
         }
      }
      for (int i = 0; i < M; i++) {
         st = new StringTokenizer(br.readLine());
         int x = Integer.parseInt(st.nextToken())-1;
         int y = Integer.parseInt(st.nextToken())-1;
         int d = Integer.parseInt(st.nextToken())-1;
         fishmap[x][y].add(d);
      }
      st = new StringTokenizer(br.readLine());
      sx = Integer.parseInt(st.nextToken())-1;
      sy = Integer.parseInt(st.nextToken())-1;
      
      for (int i = 1; i <= S; i++) {
         time = i;
         List<Integer>[][] copyfish = copyfish();
         movefish();
         moveshark();
         deletesmell();
         addfish(copyfish);
      }
      
      int result = 0;
      for (int i = 0; i < 4; i++) {
         for (int j = 0; j < 4; j++) {
            result+=fishmap[i][j].size();
         }
      }
      
      System.out.println(result);
   }
   /* 문제해결프로세스
    * 1. 현재 상태 저장하는 물고기 copyfish
    * 2. 물고기 이동 movefish
    * 3. 상어 이동 4*4*4 -> 가능한 방향 저장
    *       우선순위 : 제외 물고기 수 => 같으면 사전순(3개 격자 상좌하우)
    *       shark(outfish, list 0->1->2)
    *       제외시킨 list 0,1,2의 물고기>0이면 물고기냄새==time
    * 4. time-2값인 물고기냄새 제거
    * 5. copyfish 모두 add
    */
   public static List<Integer>[][] copyfish(){
      List<Integer>[][] copyfish = new ArrayList[4][4];
      for (int i = 0; i < 4; i++) {
         for (int j = 0; j < 4; j++) {
            copyfish[i][j] = new ArrayList<>(fishmap[i][j]);
         }
      }
      return copyfish;
   }
   
   public static void addfish(List<Integer>[][] copyfish) {
      for (int i = 0; i < 4; i++) {
         for (int j = 0; j < 4; j++) {
            fishmap[i][j].addAll(copyfish[i][j]);
         }
      }
   }
   
   public static void deletesmell() {
      
//      for (int i = 0; i < 4; i++) {
//         for (int j = 0; j < 4; j++) {
//            if(map[i][j]==time-2) {
//               map[i][j]=0;
//            }
//         }
//      }
   }
   
   static int mx[] = {-1,0,1,0};
   static int my[] = {0,-1,0,1};
   public static void moveshark() {
      int max = Integer.MIN_VALUE;
      int si = -1;
      int sj = -1;
      int sk = -1;
      boolean visited[][] = new boolean[4][4];
      for (int i = 0; i < 4; i++) {
         int xi = sx+mx[i];
         int yi = sy+my[i];
         if(!rangecheck(xi,yi)) continue;
         for (int j = 0; j < 4; j++) {
            int xj = xi+mx[j];
            int yj = yi+my[j];
            if(!rangecheck(xj,yj)) continue;
            for (int k = 0; k < 4; k++) {
               int xk = xj+mx[k];
               int yk = yj+my[k];
               if(!rangecheck(xk,yk)) continue;
               
               //경로 생성완료
               visited = new boolean[4][4];
               int localmax = 0;
               if(!visited[xi][yi]) {
                  visited[xi][yi]=true;
                  localmax += fishmap[xi][yi].size();
               }
               
               if(!visited[xj][yj]) {
                  visited[xj][yj]=true;
                  localmax += fishmap[xj][yj].size();
               }

               if(!visited[xk][yk]) {
                  visited[xk][yk]=true;
                  localmax += fishmap[xk][yk].size();
               }

               if(localmax==0 && max==Integer.MIN_VALUE) {
                  max = localmax;
                  si = i;
                  sj = j;
                  sk = k;
               }
               else if(localmax>max) {
                  max = localmax;
                  si = i;
                  sj = j;
                  sk = k;
               }
            }
         }
      }
      sx+=mx[si];
      sy+=my[si];
      if(fishmap[sx][sy].size()>0) {
         map[sx][sy] = time+2;
         fishmap[sx][sy].clear();
      }
      sx+=mx[sj];
      sy+=my[sj];
      if(fishmap[sx][sy].size()>0) {
         map[sx][sy] = time+2;
         fishmap[sx][sy].clear();
      }
      sx+=mx[sk];
      sy+=my[sk];
      if(fishmap[sx][sy].size()>0) {
         map[sx][sy] = time+2;
         fishmap[sx][sy].clear();
      }
      
//      System.out.println("상어 이동 "+sx+" "+sy+"-> "+xk+" "+yk+"/ "+si+" "+sj+" "+sk);
   }
   
   public static void movefish() {
      List<Integer>[][] movefish = new ArrayList[4][4];
      for (int i = 0; i < 4; i++) {
         for (int j = 0; j < 4; j++) {
            movefish[i][j] = new ArrayList<>();
         }
      }
      for (int i = 0; i < 4; i++) {
         for (int j = 0; j < 4; j++) {
            for (int k = 0; k < fishmap[i][j].size(); k++) {
               int mf = fishmap[i][j].get(k);
               int cnt = 0;
               while(true) {
                  int nx = i+dx[mf];
                  int ny = j+dy[mf];
                  
                  if(cnt==8) {
                     movefish[i][j].add(mf);
                     break;
                  }
                  else if(!rangecheck(nx,ny) || (nx==sx && ny==sy) || map[nx][ny]>=time) {
                	 cnt++;
                     mf-=1;
                     if(mf<0) mf+=8;
                  }
                  else {
                     movefish[nx][ny].add(mf);
                     break;
                  }
               }
            }
         }
      }
      for (int i = 0; i < 4; i++) {
         for (int j = 0; j < 4; j++) {
            fishmap[i][j] = new ArrayList<>(movefish[i][j]);
         }
      }
      
   }
   
   public static boolean rangecheck(int rx, int ry) {
      return rx>=0 && rx<4 && ry>=0 && ry<4;
   }

}