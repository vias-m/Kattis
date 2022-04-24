import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.OutputStream;

public class Ladice {
    public static void main(String[] args) {

        Kattio io = new Kattio(System.in, System.out);
        int numItems = io.getInt();
        int numDrawers = io.getInt();

        UnionFind drawers = new UnionFind(numDrawers);
        for(int i = 0; i < numItems; i++) {
            int drawer1 = io.getInt();
            int drawer2 = io.getInt();
            drawers.unionSet(drawer1, drawer2);

            if (drawers.setSize(drawer1) > 0) {
                drawers.minusSizeByOne(drawer1);
                io.println("LADICA");
            } else {
                io.println("SMECE");
            }
        }
        io.flush();
    }
}

class UnionFind {
    public int[] p;
    public int[] rank;
    public int[] sizeOfSet;

    public UnionFind(int N) {
        p = new int[N+1];
        rank = new int[N+1];
        sizeOfSet = new int[N+1];
        for (int i = 0; i < N+1; i++) {
            p[i] = i;
            rank[i] = 0;
            sizeOfSet[i] = 1;
        }
    }

    public int setSize(int p) {
        return sizeOfSet[findSet(p)];
    }

    public void minusSizeByOne(int p) {
        sizeOfSet[findSet(p)]--;
    }

    public int findSet(int i) {
        if (p[i] == i) return i;
        else {
            p[i] = findSet(p[i]);
            return p[i];
        }
    }

    public boolean isSameSet(int i, int j) { return findSet(i) == findSet(j); }

    public void unionSet(int i, int j) {
        if (!isSameSet(i, j)) {
            int x = findSet(i), y = findSet(j);
            int sumOfSets = sizeOfSet[x] + sizeOfSet[y];
            if (rank[x] > rank[y]) {
                p[y] = x;
                sizeOfSet[x] = sumOfSets;
            } else {
                p[x] = y;
                sizeOfSet[y] = sumOfSets;
                if (rank[x] == rank[y])
                    rank[y] = rank[y]+1;
            }
        }
    }
}

/*class Kattio extends PrintWriter {
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

 */
