import java.util.*;
import java.io.*;

public class Conformity {
    public static void main(String[] args) throws Exception {
        Kattio io = new Kattio(System.in, System.out);

        int n = io.getInt();
        HashMap<String,Integer> list = new HashMap<>();

        for (int i = 0; i < n; i++) {
            int[] combination = new int[5];
            for (int j = 0; j < 5; j++) {
                combination[j]=io.getInt();
            }
            Arrays.sort(combination);
            String key = Arrays.toString(combination);
            if(list.containsKey(key)){
                int value = list.get(key);
                value++;
                list.replace(key,value);
            }
            else{
                list.put(key,1);
            }
        }
        Collection<Integer> popularity = list.values();
        int max = Collections.max(list.values());
        int counter = 0;
        for(int i : popularity){
            if (max == i){
                counter++;
            }
        }
        io.print(counter*max);
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
