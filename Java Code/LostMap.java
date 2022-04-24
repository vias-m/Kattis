
import java.util.*;
import java.io.*;

public class LostMap {
    public static void main(String[] args) throws Exception {
        Kattio io = new Kattio(System.in, System.out);

        int n = io.getInt();
        ArrayList<Edge> edgeList = new ArrayList<>();
        for (int i = 1; i < n+1 ; i++) {
            for (int j = 1; j < n+1; j++) {
                Edge road = new Edge(i,j,io.getInt());
                edgeList.add(road);
            }
        }
        Collections.sort(edgeList);
        // Kruskal's Argument
        UnionFind UF = new UnionFind(n+1); // all V are disjoint sets at the beginning
        for (int i = 0; i < n*n; i++) {
            Edge road = edgeList.get(i);
            int u = road.node1, v = road.node2, w = road.weight;
            if(!UF.isSameSet(u,v)){
                io.println(road);
                UF.unionSet(u, v);
            }
            if(UF.numSets == 1) {
                break;
            }
        }
        io.flush();
    }
}

class Edge implements Comparable<Edge>{
    public int node1;
    public int node2;
    public Integer weight;

    Edge(int i, int j, int weight){
        this.node1 = i;
        this.node2 = j;
        this.weight = weight;
    }

    public int compareTo(Edge other){
        return this.weight.compareTo(other.weight);
    }
    public String toString(){
        return String.format("%d %d", node1, node2);
    }
}

class UnionFind {
    public int[] p;
    public int[] rank;
    public int[] setSize;
    public int numSets;

    public UnionFind(int N) {
        p = new int[N];
        rank = new int[N];
        setSize = new int[N];
        numSets = N;
        for (int i = 0; i < N; i++) {
            p[i] = i;
            rank[i] = 0;
            setSize[i] = 1;
        }
    }

    public int findSet(int i) {
        if (p[i] == i) return i;
        else {
            p[i] = findSet(p[i]);
            return p[i];
        }
    }

    public Boolean isSameSet(int i, int j) { return findSet(i) == findSet(j); }

    public void unionSet(int i, int j) {
        if (!isSameSet(i, j)) {
            numSets--;
            int x = findSet(i), y = findSet(j);
            // rank is used to keep the tree short
            if (rank[x] > rank[y]) {
                p[y] = x;
                setSize[x] = setSize[x] + setSize[y];
            }
            else {
                p[x] = y;
                setSize[y] = setSize[x] + setSize[y];
                if (rank[x] == rank[y])
                    rank[y] = rank[y]+1;
            }
        }
    }

    public int numDisjointSets() { return numSets; }

    public int sizeOfSet(int i) { return setSize[findSet(i)]; }
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
