
import java.util.*;
import java.io.*;

public class Dominos {
    public static void main(String[] args) throws Exception {
        Kattio io = new Kattio(System.in, System.out);

        int testcases =io.getInt();
        for (int i = 0; i < testcases; i++) {
            int totalTiles = io.getInt();
            int lines = io.getInt();
            ArrayList<Integer>[] tilesCombination = new ArrayList[totalTiles+1];
            for (int j = 1; j < totalTiles+1; j++) {
                tilesCombination[j] = new ArrayList<>();
            }
            for (int j = 0; j < lines; j++) {
                int first = io.getInt();
                int second = io.getInt();
                tilesCombination[first].add(second);
            }
            boolean[] visited =new boolean[totalTiles+1];
            LinkedList<Integer> cycles = new LinkedList<>();
            for (int j = 1; j < totalTiles+1 ; j++) {
                if(!visited[j]){
                    DFScycles(j,tilesCombination,visited,cycles);
                }
            }
            Arrays.fill(visited, false);
            int toKnock =0;
            while (!cycles.isEmpty()) {
                int j = cycles.pollLast();
                if(!visited[j]){
                    toKnock+=1;
                    DFS(j,tilesCombination,visited);
                }
            }
            io.println(toKnock);
        }
        io.flush();
    }
    public static void DFScycles(int tile, ArrayList<Integer>[] tilesCombination,boolean[] visited, LinkedList<Integer> cycles ){
        visited[tile]=true;
        for(int i : tilesCombination[tile]) {
            if (!visited[i]) {
                DFScycles(i, tilesCombination, visited, cycles);
            }
        }
        cycles.add(tile);
    }

    public static void DFS(int tile, ArrayList<Integer>[] tilesCombination,boolean[] visited){
        visited[tile]=true;
        for(int i : tilesCombination[tile]){
            if(!visited[i]){
                DFS(i, tilesCombination,visited);
            }
        }
    }
}

class Kattio extends PrintWriter {
    public Kattio(InputStream i) {
        super(new BufferedOutputStream(System.out));
        r = new BufferedReader(new InputStreamReader(i));
    }
    public Kattio(InputStream i, OutputStream o) {
        super(new BufferedOutputStream(o));
        r = new BufferedReader(new InputStreamReader(i));
    }

    public boolean hasMoreTokens() {
        return peekToken() != null;
    }

    public int getInt() {
        return Integer.parseInt(nextToken());
    }

    public double getDouble() {
        return Double.parseDouble(nextToken());
    }

    public long getLong() {
        return Long.parseLong(nextToken());
    }

    public String getWord() {
        return nextToken();
    }



    private BufferedReader r;
    private String line;
    private StringTokenizer st;
    private String token;

    private String peekToken() {
        if (token == null)
            try {
                while (st == null || !st.hasMoreTokens()) {
                    line = r.readLine();
                    if (line == null) return null;
                    st = new StringTokenizer(line);
                }
                token = st.nextToken();
            } catch (IOException e) { }
        return token;
    }

    private String nextToken() {
        String ans = peekToken();
        token = null;
        return ans;
    }
}
