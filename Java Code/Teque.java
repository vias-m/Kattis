import java.util.*;
import java.io.*;

public class Teque {
    public static void main(String[] args) throws Exception {
        Kattio io = new Kattio(System.in, System.out);

        int N = io.getInt();
        TeHash tehash = new TeHash();

        for (int i = 0; i < N; i++) {
            String S = io.getWord();
            int x = io.getInt();
            if(S.equals("push_back")){
                tehash.pushBack(x);
            }
            else if(S.equals("push_front")){
                tehash.pushFront(x);
            }
            else if(S.equals("push_middle")){
                tehash.pushMiddle(x);
            }
            else if(S.equals("get")){
                io.println(tehash.get(x));
            }
        }
        io.flush();

    }
}


class TeHash{
    Hashtable<Integer,Integer> l1 = new Hashtable<>();
    Hashtable<Integer,Integer> l2 = new Hashtable<>();
    int min1 = 0;
    int min2 = 0;
    int max1 = -1;
    int max2 = -1;

    public void balancing(){
        while(l1.size()- l2.size()>1){
            min2-=1;
            l2.put(min2,l1.get(min1));
            l1.remove(min1);
            min1+=1;
        }
        while(l2.size()- l1.size()>0) {
            min1-=1;
            l1.put(min1,l2.get(min2));
            l2.remove(min2);
            min2+=1;
        }
    }

    public void pushBack(int x){
        max2 +=1;
        l2.put(max2,x);
        balancing();
    }
    public void pushFront(int x) {
        max1+=1;
        l1.put(max1, x);
        balancing();
    }
    public void pushMiddle(int x) {
        int k1 = l1.size();
        int k2 = l2.size();
        if (k1>k2){
            min2 -=1;
            l2.put(min2,x);
        }
        else{
            min1-=1;
            l1.put(min1,x);
        }
        balancing();
    }
    public int get(int index){
        int s1 = l1.size();
        if(index>=s1){
            index -= s1;
            index += min2;
            return l2.get(index);
        }
        else{
            index = max1 - index;
            return l1.get(index);
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
