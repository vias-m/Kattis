import java.util.*;
import java.io.*;

public class AlmostUnionFind {
    public static void main(String[] args) throws Exception {
        Kattio io = new Kattio(System.in, System.out);

        while (io.hasMoreTokens()) {
            int n = io.getInt();
            int m = io.getInt();

            ModifiedUnionFind set = new ModifiedUnionFind(n);

            for (int i = 0; i < m; i++) {
                int command = io.getInt();

                if (command == 1) {
                    set.unionSet(io.getInt(), io.getInt());
                } else if (command == 2) {
                    set.move(io.getInt(), io.getInt());
                } else {
                    io.println(set.numAndSum(io.getInt()));
                }
            }
        }
        io.flush();
    }
}

class ModifiedUnionFind{
    public int[] roots;
    public HashMap<Integer,Integer>[] sets;
    public long[] sum;
    public int[] num;

    ModifiedUnionFind(int n){
        roots = new int[n+1];
        sets = new HashMap[n + 1];
        sum = new long[n+1];
        num = new int[n+1];
        for (int i = 1; i < n+1; i++) {
            roots[i] = i;
            sets[i] = new HashMap<>();
            sets[i].put(i,i);
            sum[i] = i;
            num[i] = 1;
        }
    }
    public void unionSet(int i, int j) {

        int temp1 = roots[i];
        int temp2 = roots[j];

        if (temp1 == temp2) {
            return;
        }
        if (sets[temp1].size() >= sets[temp2].size()) {
            for (int k = 0; k < sets[temp2].size(); k++) {
                Object[] values = sets[temp2].values().toArray();
                int n = (Integer) values[k];
                sets[temp1].put(n,n);
                roots[n] = temp1;
                sum[temp1] += n;
                sum[temp2] -= n;
                num[temp1] += 1;
                num[temp2] -= 1;
            }
            sets[temp2].clear();
        } else {
            for (int k = 0; k < sets[temp1].size(); k++) {
                Object[] values = sets[temp1].values().toArray();
                int n = (Integer) values[k];
                sets[temp2].put(n,n);
                roots[n] = temp2;
                sum[temp1] -= n;
                sum[temp2] += n;
                num[temp1] -= 1;
                num[temp2] += 1;
            }
            sets[temp1].clear();
        }
    }
    public void move(int i, int j){
        int temp1 = roots[i];
        int temp2 = roots[j];
        if (temp1 == temp2) {
            return;
        }
        sets[temp1].remove((Integer) i);
        sets[temp2].put(i,i);
        roots[i] = roots[j];
        sum[temp1] -= i;
        sum[temp2] += i;
        num[temp1] -= 1;
        num[temp2] += 1;
    }
    public String numAndSum(int i){
        return num[roots[i]]+" "+sum[roots[i]];
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
           } catch (IOException e) {
           }
       return token;
   }

   private String nextToken() {
       String ans = peekToken();
       token = null;
       return ans;
   }
}
