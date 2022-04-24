import java.util.*;
import java.io.*;

public class MillionaireMadness {
    public static void main(String[] args) throws Exception {
        Kattio io = new Kattio(System.in, System.out);
        int m = io.getInt();
        int n = io.getInt();

        int[][] grid = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = io.getInt();
            }
        }
        int[][] movement = {{1, 0}, {-1, 0}, {0, -1}, {0, 1}};
        ArrayList<IntegerPair>[][] weights = new ArrayList[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                ArrayList<IntegerPair> temp = new ArrayList<>();

                for(int[] a : movement){
                    int row = i + a[0];
                    int col = j + a[1];

                    if(row >= 0 && row < m && col >=0 && col < n){
                        int weight = Math.max(0,grid[row][col]-grid[i][j]);
                        temp.add(new IntegerPair(new int[]{row,col}, weight));
                    }
                }
                weights[i][j] = temp;
            }
        }
        boolean[][] visited = new boolean[m][n];
        PriorityQueue<IntegerPair> pq = new PriorityQueue<>();
        pq.add(new IntegerPair(new int[] {0,0}, 0));
        int ladder = 0;
        //Perform BFS
        while(!pq.isEmpty()){
            IntegerPair u = pq.poll();
            ladder = u.second;
            int row = u.first[0];
            int col = u.first[1];

            Queue<int[]> q = new LinkedList<>();
            q.add(new int[] {row,col});

            while(!q.isEmpty()){
                int[] v = q.poll();
                if(v == new int[] {m-1,n-1}){
                    break;
                }
                for(IntegerPair z : weights[v[0]][v[1]]){
                    int[] cord = z.first;
                    if(ladder < z.second && !visited[cord[0]][cord[1]]){
                        pq.add(new IntegerPair(v, z.second));
                    }
                    else if(!visited[cord[0]][cord[1]]){
                        visited[cord[0]][cord[1]] = true;
                        q.add(new int[] {cord[0],cord[1]});
                    }
                }
            }
            if(visited[m-1][n-1]){
                break;
            }
        }
        io.print(ladder);
        io.flush();
    }
}

class IntegerPair implements Comparable<IntegerPair> {
    int[] first;
    Integer second;

    public IntegerPair(int[] first, Integer second) {
        this.first = first;
        this.second = second;
    }

    // sort by weight
    public int compareTo(IntegerPair o) {
        return this.second.compareTo(o.second);
    }
}

//class Kattio extends PrintWriter {
//    public Kattio(InputStream i) {
//        super(new BufferedOutputStream(System.out));
//        r = new BufferedReader(new InputStreamReader(i));
//    }
//    public Kattio(InputStream i, OutputStream o) {
//        super(new BufferedOutputStream(o));
//        r = new BufferedReader(new InputStreamReader(i));
//    }
//
//    public boolean hasMoreTokens() {
//        return peekToken() != null;
//    }
//
//    public int getInt() {
//        return Integer.parseInt(nextToken());
//    }
//
//    public double getDouble() {
//        return Double.parseDouble(nextToken());
//    }
//
//    public long getLong() {
//        return Long.parseLong(nextToken());
//    }
//
//    public String getWord() {
//        return nextToken();
//    }
//
//
//
//    private BufferedReader r;
//    private String line;
//    private StringTokenizer st;
//    private String token;
//
//    private String peekToken() {
//        if (token == null)
//            try {
//                while (st == null || !st.hasMoreTokens()) {
//                    line = r.readLine();
//                    if (line == null) return null;
//                    st = new StringTokenizer(line);
//                }
//                token = st.nextToken();
//            } catch (IOException e) { }
//        return token;
//    }
//
//    private String nextToken() {
//        String ans = peekToken();
//        token = null;
//        return ans;
//    }
//}
