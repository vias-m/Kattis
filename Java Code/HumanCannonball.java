import java.util.*;
import java.io.*;

public class HumanCannonball {
    public static final Double INF = 1000000000.0;
    public static ArrayList< ArrayList< IntegerPair > > AdjList = new ArrayList< ArrayList< IntegerPair > >();
    public static ArrayList< Double > D = new ArrayList< Double >();
    public static ArrayList< Integer > p = new ArrayList< Integer >();
    public static int V, E;

    public static void initSSSP(int s) { // initialization phase
        D.clear();
        D.addAll(Collections.nCopies(V, INF)); // use 1B to represent INF
        p.clear();
        p.addAll(Collections.nCopies(V, -1)); // use -1 to represent NULL
        D.set(s, 0.0); // this is what we know so far
    }

    public static void relax(int u, int v, double w_u_v) {
        if (D.get(u) != INF && D.get(v) > D.get(u) + w_u_v) { // if SP can be shortened
            D.set(v, D.get(u) + w_u_v); // relax this edge
            p.set(v, u); // remember/update the predecessor
        }
    }

    public static void backtrack(int s, int u) {
        if (u == -1 || u == s) {
            System.out.printf("%d", u);
            return;
        }
        backtrack(s, p.get(u));
        System.out.printf(" %d", u);
    }


    public static void main(String[] args) throws Exception {
        Kattio io = new Kattio(System.in, System.out);

        Node start = new Node(io.getDouble(), io.getDouble());
        Node end = new Node(io.getDouble(), io.getDouble());

        ArrayList<Node> nodes = new ArrayList<>();
        nodes.add(start);
        nodes.add(end);

        int n = io.getInt();
        for (int i = 0; i < n; i++) {
            Node cannon = new Node(io.getDouble(), io.getDouble());
            nodes.add(cannon);
            AdjList.add(new ArrayList<IntegerPair>());
        }
        AdjList.add(new ArrayList<IntegerPair>());
        AdjList.add(new ArrayList<IntegerPair>());

        for (int i = 0; i < n+2; i++) {
            Node first = nodes.get(i);
            for (int j = 0; j < n+2; j++) {
                Node second = nodes.get(j);
                if(i==0 || i==1 ){
                    double time = first.distance(second)/5;
                    AdjList.get(i).add(new IntegerPair(j,time));
                }
                else{
                    AdjList.get(i).add(new IntegerPair(j, first.travelTime(second)));
                }
            }
        }
        V = n+2;
        initSSSP(0);

        // Bellman Ford's routine, implemented using AdjList (note that you can choose to use EdgeList -- similar performance)
        for (int i = 0; i < V-1; i++){ // relax all E edges V-1 times, O(V)
            for (int u = 0; u < V; u++){ // these two loops = O(E)
                for (int j = 0; j < AdjList.get(u).size(); j++) {
                    IntegerPair v = AdjList.get(u).get(j);
                    relax(u, v._first, v._second);
                }

//        // bonus: negative cycle test
//        boolean negative_cycle_exist = false;
//        for (int u = 0; u < V; u++) // one more pass to check
//            for (int j = 0; j < AdjList.get(u).size(); j++) {
//                IntegerPair v = AdjList.get(u).get(j); // try relaxing this edge one more time
//                if (D.get(u) != INF && D.get(v._first) > D.get(u) + v._second)
//                    negative_cycle_exist = true; // if this is true, then negative cycle exists!
//            }
//
//        System.out.printf("Negative Cycle Exist? %s\n", negative_cycle_exist ? "Yes" : "No");
//        if (!negative_cycle_exist) {
//            for (int i = 0; i < V; i++) {
//                System.out.printf("SSSP(%d, %d) = %d\n", 0, i, D.get(i));
//                if (D.get(i) != INF) {
//                    System.out.printf("Path: ");
//                    backtrack(0, i);
//                    System.out.printf("\n");
//                }
//                System.out.printf("\n");
            }
        }
        io.print(D.get(1));
        io.flush();
    }
}

class Node{
    double x;
    double y;

    Node(double x, double y){
        this.x = x;
        this.y = y;
    }

    double distance(Node o){
        return Math.sqrt(Math.pow(this.x - o.x, 2) + Math.pow(this.y - o.y, 2));
    }

    double travelTime(Node o){
        double dist = this.distance(o);
        if(dist<25){
            return dist/5;
        }
        else{
            return 2 + Math.abs((50-dist))/5;
        }
    }

}

class IntegerPair implements Comparable<IntegerPair> {
    Integer _first;
    Double _second;

    public IntegerPair(Integer f, Double s) {
        _first = f;
        _second = s;
    }

    public int compareTo(IntegerPair o) {
        return this._second.compareTo(o._second);
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
