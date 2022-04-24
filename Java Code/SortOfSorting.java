import java.util.*;
import java.io.*;

public class SortOfSorting {
    public static void main(String[] args) throws Exception {


        Kattio io = new Kattio(System.in, System.out);

        while(true){
            int n = io.getInt();
            Name[] lastNames = new Name[n];
            if(n == 0){
                return;
            }
            else{
                for (int i = 0 ; i<n;i++){
                    lastNames[i] = new Name(io.getWord());
                }
            }
            Arrays.sort(lastNames,new CompareName());
            for(int i =0;i<n;i++){
                io.println(lastNames[i]);
            }
            io.flush();
            io.println();
        }


    }
}


class CompareName implements Comparator<Name>{
    public int compare(Name a, Name b){
        if (a.first < b.first){
            return -1;
        }
        else if (a.first > b.first){
            return 1;
        }
        else if (a.first == b.first){
            if (a.second < b.second){
                return -1;
            }
            else if (a.second > b.second){
                return 1;
            }
            else{
                return 0;
            }
        }
        else{
            return 0;
        }
    }
}



class Name{
    String name;
    char first;
    char second;

    Name(String name){
        this.name = name;
        this.first = name.charAt(0);
        this.second = name.charAt(1);
    }

    @Override
    public String toString() {
        return String.format(name);
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
