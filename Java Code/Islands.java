
import java.util.*;
import java.io.*;

public class Islands {
    public static void main(String[] args) throws Exception {
        Kattio io = new Kattio(System.in, System.out);

        int row = io.getInt();
        int col = io.getInt();
        String[] map = new String[row];
        boolean[][] checked = new boolean[row][col];
        for (int i = 0; i < row; i++) {
            map[i]= io.getWord();
        }
        int islands = 0;
        LinkedList<int[]> list = new LinkedList<>();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if(!checked[i][j] && map[i].charAt(j)=='L'){
                    islands+=1;
                    checked[i][j] = true;
                    list.add(new int[]{i, j});
                    traversal(list,map,checked);
                }
            }
        }
        io.print(islands);
        io.flush();
    }
    public static void traversal(LinkedList<int[]> list, String[] map, boolean[][] checked){
        int[][] mover= {{0,1},{0,-1},{1,0},{-1,0}};

        while(!list.isEmpty()){
            int[] u = list.poll();
            for (int i = 0; i < 4; i++) {
                int row = u[0] + mover[i][0];
                int col = u[1] + mover[i][1];

                if(row>=0 && row<map.length && col>=0 && col<checked[1].length && map[row].charAt(col)!='W' && !checked[row][col]){
                    checked[row][col] = true;
                    int[] temp= {row,col};
                    list.add(temp);
                }
            }
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
