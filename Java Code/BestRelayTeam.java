import java.util.*;
import java.io.*;

public class BestRelayTeam {
    public static void main(String[] args) throws Exception {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        
        int n = Integer.parseInt(input.readLine());
        String[][] runners = new String[n][3];
        for (int i = 0 ; i <n  ; i++){
            String str = input.readLine();
            String[] details = str.split(" ");
            runners[i] = details;
        }
        double[][] table = new double[n][3];
        for (int x = 0 ; x < n ; x++){
            table[x][0] = x;
            table[x][1] = Double.parseDouble(runners[x][1]);
            table[x][2] = Double.parseDouble(runners[x][2]);
        }
        
        for (int j = 0; j < ( n - 1 ); j++){
            for (int k = 0; k < n - j - 1; k++){
                if (table[k][2] > table[k+1][2]){
                    double[] temp = table[k];
                    table[k] = table[k+1];
                    table[k+1] = temp;
                }
            }    
        }

        String[] top = new String[] {runners[(int)table[0][0]][0], runners[(int)table[1][0]][0], runners[(int)table[2][0]][0], runners[(int)table[3][0]][0]};
        double total = table[0][1] + table[1][2] + table[2][2] + table[3][2];
        double temp1 = table[0][2] + table[1][1] + table[2][2] + table[3][2];
        if (temp1 < total){
            total = temp1;
            top[0] = runners[(int)table[1][0]][0];
            top[1] = runners[(int)table[0][0]][0];
            top[2] = runners[(int)table[2][0]][0];
            top[3] = runners[(int)table[3][0]][0];
        }
        temp1 = table[0][2] + table[1][2] + table[2][1] + table[3][2];
        if (temp1 < total){
            total = temp1;
            top[0] = runners[(int)table[2][0]][0];
            top[1] = runners[(int)table[0][0]][0];
            top[2] = runners[(int)table[1][0]][0];
            top[3] = runners[(int)table[3][0]][0];
        }
        for (int z = 3; z<n;z++){
            temp1 = table[0][2] + table[1][2] + table[2][2] + table[z][1];
            if (temp1 < total){
                total = temp1;
                top[0] = runners[(int)table[z][0]][0];
                top[1] = runners[(int)table[0][0]][0];
                top[2] = runners[(int)table[1][0]][0];
                top[3] = runners[(int)table[2][0]][0];
            }
        }
        output.print(total);
        output.println();
        for (int c = 0; c<4 ; c++){
            output.print(top[c]);
            output.println();
        }
        output.close();
        input.close();
    }
}
