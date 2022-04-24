import java.util.*;
import java.io.*;


public class CardTrading {
    public static void main(String[] args) throws Exception {
    	BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
    	
        String str = in.readLine();
        String[] temp = str.split(" ");
        int N = Integer.parseInt(temp[0]);
        int T = Integer.parseInt(temp[1]);
        int K = Integer.parseInt(temp[2]);
        
        str = in.readLine();
        temp = str.split(" ");
        int[] numberOfEachCards = new int[T];
        for (int z = 0 ; z<N ;z++){
            numberOfEachCards[Integer.parseInt(temp[z])-1] += 1 ;
        }

        Cards[] D = new Cards[T];
        for (int y=0; y<T; y++){
            String str1 = in.readLine();
            String[] temp1 = str1.split(" ");
            long a = Long.parseLong(temp1[0]);
            long b = Long.parseLong(temp1[1]);
            D[y] = new Cards(y+1,numberOfEachCards[y],a,b);
        }
        long ans = 0;
        Arrays.sort(D);
        for (int x = 0; x<T; x++){
            if(x<K){
                ans -= (2 - D[x].quantity)*D[x].a;
            }
            else{
                ans += D[x].quantity*D[x].b;
            }
        }
        out.println(ans);


        out.close();
        in.close();
    }
}

class Cards implements Comparable<Cards>{
    int i;
    int quantity;
    long a;
    long b;
    long diff;

    Cards(int i, int quantity, long a, long b){
        this.i = i;
        this.quantity = quantity;
        this.a = a;
        this.b = b;
        this.diff = quantity*b - (quantity-2)*a;
    }

    @Override
    public int compareTo(Cards nxt){
        return Long.compare(this.diff,nxt.diff);
    }

}
