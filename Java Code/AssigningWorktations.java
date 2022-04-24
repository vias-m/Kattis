import java.util.*;
import java.io.*;

public class AssigningWorktations {
    public static void main(String[] args) throws Exception {
        Kattio io = new Kattio(System.in, System.out);

        int n = io.getInt();
        int m = io.getInt();

        PriorityQueue<Worker2> stations = new PriorityQueue<>();
        Worker[] workers = new Worker[n];

        for (int i = 0; i < n; i++) {
            int a = io.getInt();
            int s = io.getInt();
            Worker person = new Worker(a,s);
            workers[i] = person;
        }
        Arrays.sort(workers);
        int counter = 0;

        for (int i = 0; i < n; i++){

            while(!stations.isEmpty() && workers[i].arrival - stations.peek().total > m){
                stations.poll();
            }
            if(stations.isEmpty()){
                counter++;
            }
            else if (workers[i].arrival - stations.peek().total<=m && workers[i].arrival - stations.peek().total>=0) {
                stations.poll();
            }

            else{;
                counter++;
            }
            stations.add(new Worker2(workers[i]));
        }
        io.print(n-counter);
        io.close();
    }
}

class Worker implements Comparable<Worker>{
    Integer arrival;
    Integer leave;
    Integer total;

    Worker(int arrival, int leave){
        this.arrival = arrival;
        this.leave = leave;
        this.total = arrival + leave;
    }
    @Override
    public int compareTo(Worker next){
        return this.arrival.compareTo(next.arrival);
    }
}

class Worker2 implements Comparable<Worker2>{
    Integer total;

    Worker2(Worker a){
        this.total = a.total;
    }
    @Override
    public int compareTo(Worker2 next){
        return this.total.compareTo(next.total);
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
