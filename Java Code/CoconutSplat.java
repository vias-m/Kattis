import java.util.*;
import java.io.*;

public class CoconutSplat {
    public static void main(String[] args) throws Exception {
        Kattio io = new Kattio(System.in, System.out);

        int s = io.getInt();
        int n = io.getInt();

        LinkedList <Coconut> coconuts = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            coconuts.add(new Coconut(i));
        }
        int current = (s-1)%n;
        while (coconuts.size() != 1){
            if (current == -1){
                current = coconuts.size() -1;
            }
            int index = coconuts.get(current).index;
            if(coconuts.get(current).state == "folded"){
                coconuts.remove(current);
                coconuts.add(current, new Coconut(index, "fist"));
                coconuts.add(current, new Coconut(index, "fist"));
                current = (current + s-1) % coconuts.size() ;
            }
            else if (coconuts.get(current).state == "fist"){
                coconuts.remove(current);
                coconuts.add(current, new Coconut(index, "open"));
                current = (current + s) % coconuts.size();
            }
            else if (coconuts.get(current).state == "open") {
                coconuts.remove(current);
                current = (current + s-1) % coconuts.size();
            }
        }
        io.print(coconuts.get(0).index+1);
        io.close();

    }
}

class Coconut{
    public int index;
    public String state;

    Coconut(int index){
        this.index = index;
        this.state = "folded";
    }
    Coconut(int index , String state){
        this.index = index;
        this.state = state;
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
