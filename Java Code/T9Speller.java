import java.util.*;
import java.io.*;

public class T9Speller {
    public static void main(String[] args) throws Exception {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

        int n = Integer.parseInt(input.readLine());
        for (int i = 0; i<n; i++){
            output.print("Case #" + (i + 1) + ": ");
            String string = input.readLine();
            String ans = " ";
            String a = " ";
            for (int j=0; j<string.length(); j++){
                String b = string.substring(j,j+1);
                if (b .equals("a") ){
                    a = "2";
                }else if (b .equals("b") ){
                    a = "22";
                }else if (b .equals("c") ){
                    a = "222";
                }else if (b .equals("d") ){
                    a = "3";
                }else if (b .equals("e") ){
                    a = "33";
                }else if (b .equals("f") ){
                    a = "333";
                }else if (b .equals("g") ){
                    a = "4";
                }else if (b .equals("h") ){
                    a = "44";
                }else if (b .equals("i") ){
                    a = "444";
                }else if (b .equals("j") ){
                    a = "5";
                }else if (b .equals("k") ){
                    a = "55";
                }else if (b .equals("l") ){
                    a = "555";
                }else if (b .equals("m") ){
                    a = "6";
                }else if (b .equals("n") ){
                    a = "66";
                }else if (b .equals("o") ){
                    a = "666";
                }else if (b .equals("p") ){
                    a = "7";
                }else if (b .equals("q") ){
                    a = "77";
                }else if (b .equals("r") ){
                    a = "777";
                }else if (b .equals("s") ){
                    a = "7777";
                }else if (b .equals("t") ){
                    a = "8";
                }else if (b .equals("u") ){
                    a = "88";
                }else if (b .equals("v") ){
                    a = "888";
                }else if (b .equals("w") ){
                    a = "9";
                }else if (b .equals("x") ){
                    a = "99";
                }else if (b .equals("y") ){
                    a = "999";
                }else if (b .equals("z") ){
                    a = "9999";
                }else if (b .equals(" ") ){
                    a = "0";
                }if (ans.substring(ans.length() - 1) .equals(a.substring(0,1)) ){
                    ans += " ";
                }ans += a;
            }output.print(ans);
            output.println();
        }output.close();
        input.close();
    }
}
