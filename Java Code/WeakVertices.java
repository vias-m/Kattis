import java.util.*;
import java.io.*;

public class WeakVertices {
    public static void main(String[] args) throws Exception {
        Kattio io = new Kattio(System.in, System.out);

        int n = io.getInt();

        while(n!=-1){
            ArrayList<Integer>[] vertices = new ArrayList[n];
            for (int i = 0; i < n; i++) {
                ArrayList<Integer> vertex = new ArrayList<>();
                for (int j = 0; j < n; j++) {
                    int contains = io.getInt();
                    if(contains == 1){
                        vertex.add(j);
                    }
                }
                vertices[i] = vertex;
            }
            int[] strong = new int[n];
            for (int i = 0; i < n; i++) {
                for (Integer j : vertices[i]) {
                    for(Integer k : vertices[j]){
                        if( vertices[i].contains(k)){
                            strong[i] = 1;
                            break;
                        }
                    }
                    if(strong[i] == 1){
                        break;
                    }
                }
            }
            for (int i = 0; i < n; i++) {
                if(strong[i]==0){
                    io.print(i);
                    io.print(" ");
                }
            }
            io.println();
            n = io.getInt();
        }
        io.flush();
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
