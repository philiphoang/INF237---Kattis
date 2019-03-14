/** Simple yet moderately fast I/O routines.
 *
 * Example usage:
 *
 * Kattio io = new Kattio(System.in, System.out);
 *
 * while (io.hasMoreTokens()) {
 *    int n = io.getInt();
 *    double d = io.getDouble();
 *    double ans = d*n;
 *
 *    io.println("Answer: " + ans);
 * }
 *
 * io.close();
 *
 *
 * Some notes:
 *
 * - When done, you should always do io.close() or io.flush() on the
 *   Kattio-instance, otherwise, you may lose output.
 *
 * - The getInt(), getDouble(), and getLong() methods will throw an
 *   exception if there is no more data in the input, so it is generally
 *   a good idea to use hasMoreTokens() to check for end-of-file.
 *
 * @author: Kattis
 */

import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

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

public class WalrusWeight {

    static boolean[] memorizedWeights = new boolean[3000];
    static int[] weights;

    static boolean[][] sumWeights;
    public static void main(String[] args) {
        Kattio io = new Kattio(System.in);

        int N = io.getInt();

        weights = new int[N];

        for (int i = 0; i < N; i++) {
            weights[i] = io.getInt();
        }

        io.println(knapSack());

        io.close();
    }

    public static int knapSack() {
        //Knapsack has weights and value 
        //No value here in this problem, but put weights as value
        int numberOfWeights = weights.length;
        int capacity = 2000; //Max sum is 2000 (1000 + 1000), going from 1 to 2000
        sumWeights = new boolean[numberOfWeights + 1][capacity + 1]; 

        //Initialize the first item to be true
        sumWeights[0][0] = true;

        for (int i = 1; i <= numberOfWeights; i++) {
            for (int j = 0; j <= capacity; j++) {
                if (sumWeights[i-1][j]) { 
                    sumWeights[i][j] = true; //Mark this value, marked means it will be added when met

                    int pastWeight = weights[i - 1];

                    //j is index (weight) that is not false 
                    int totalWeight = j + pastWeight; //Check if this item does not exceed our constraint

                    if (totalWeight <= capacity) {//Add if there is a value on this position
                        sumWeights[i][totalWeight] = true; //Place the new sum on current index
                    }
                }
            }
        }

        for (int k = 0; k <= capacity + 1; k++) {
            if (sumWeights[numberOfWeights][1000 + k])
                return 1000 + k;
            if (sumWeights[numberOfWeights][1000 - k])
                return 1000 - k;
        }
        
        return 1;
    
    }

}