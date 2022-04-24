import java.util.*;
import java.io.*;

public class KattisQuest {
    public static void main(String[] args) throws Exception {
        Kattio io = new Kattio(System.in, System.out);

        TreeSet<Quest> quests = new TreeSet<>();

        int n = io.getInt();

        for (int i = 0; i < n; i++) {
            String command = io.getWord();
            if(command.equals("add")){
                int energy = io.getInt();
                int gold = io.getInt();
                Quest quest = new Quest(energy,gold,i);
                quests.add(quest);
            }else{
                int x = io.getInt();
                long ans = 0;

                while(true){
                    if(!(x>0) || quests.isEmpty()){
                        break;
                    }
                    Quest temp = new Quest(x,10000000,10000000);
                    Quest complete = quests.floor(temp);
                    if(complete != null){
                        ans += complete.gold;
                        x -= complete.energy;
                        quests.remove(complete);
                    }else{
                        break;
                    }
                }
                io.println(ans);
            }
        }
        io.flush();
    }
}

class Quest implements Comparable<Quest>{
    int energy;
    int gold;
    int id;

    Quest(int energy, int gold, int id){
        this.energy = energy;
        this.gold = gold;
        this.id = id;

    }
    public int compareTo(Quest next){
        if(this.energy ==next.energy) {
            if(this.gold > next.gold) {
                return 1;
            } else if (this.gold < next.gold){
                return -1;
            } else {
                if(this.id < next.id){
                    return -1;
                } else if (this.id > next.id) {
                    return 1;
                } else {
                    return 0;
                }
            }
        } else if(this.energy> next.energy)
            return 1;
        else
            return -1;
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
