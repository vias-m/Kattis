import java.util.*;
import java.io.*;

public class JoinStrings {
    public static void main(String[] args) throws Exception {
        Kattio io = new Kattio(System.in, System.out);

        int n = io.getInt();

        TailedLinkList[] S = new TailedLinkList[n];
        for (int i = 0; i < n; i++) {
            Node str = new Node(io.getWord());
            S[i] = new TailedLinkList(str);
        }
        int lastPause = 0;

        for (int i = 0; i < n-1; i++) {
            int a = io.getInt()-1;
            int b = io.getInt()-1;
            S[a].addOtherLinkedList(S[b]);
            S[b] = null;
            if (i == n - 2){
                lastPause =  a;
            }
        }
        Node N = S[lastPause].head;

        while (N != null) {
            io.print(N.word);
            N = N.next;
        }
        io.flush();
    }
}

class Node{
    public String word;
    public Node next;

    Node(String word){
        this.word = word;
    }

    public Node getLastNode(){
        Node n = this;
        while (n.next != null){
            n= n.next;
        }
        return n;
    }

}

class TailedLinkList{
    public Node head;
    public Node tail;

    TailedLinkList(Node node){
        this.head = node;
        this.tail = node.getLastNode();
    }

    public void addOtherLinkedList(TailedLinkList other){
        this.tail.next = other.head;
        this.tail = other.tail;
    }
}




//class Kattio extends PrintWriter {
//    public Kattio(InputStream i) {
//        super(new BufferedOutputStream(System.out));
//        r = new BufferedReader(new InputStreamReader(i));
//    }
//    public Kattio(InputStream i, OutputStream o) {
//        super(new BufferedOutputStream(o));
//        r = new BufferedReader(new InputStreamReader(i));
//    }
//
//    public boolean hasMoreTokens() {
//        return peekToken() != null;
//    }
//
//    public int getInt() {
//        return Integer.parseInt(nextToken());
//    }
//
//    public double getDouble() {
//        return Double.parseDouble(nextToken());
//    }
//
//    public long getLong() {
//        return Long.parseLong(nextToken());
//    }
//
//    public String getWord() {
//        return nextToken();
//    }
//
//
//
//    private BufferedReader r;
//    private String line;
//    private StringTokenizer st;
//    private String token;
//
//    private String peekToken() {
//        if (token == null)
//            try {
//                while (st == null || !st.hasMoreTokens()) {
//                    line = r.readLine();
//                    if (line == null) return null;
//                    st = new StringTokenizer(line);
//                }
//                token = st.nextToken();
//            } catch (IOException e) { }
//        return token;
//    }
//
//    private String nextToken() {
//        String ans = peekToken();
//        token = null;
//        return ans;
//    }
//}
