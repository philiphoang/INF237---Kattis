import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

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

public class FruitBasket {
    static final int MIN = 200;
    static int N;
    static int[] fruits;
    static int total;
    static long sumfruits;
    static List<Integer> baskets = new ArrayList<>();
    public static void main(String[] args) {
        Kattio io = new Kattio(System.in); 

        N = io.getInt();
        fruits = new int[N];
        for (int i = 0; i < N; i++) {
            fruits[i] = io.getInt();
            sumfruits += fruits[i];
        }

        sumfruits = (long) Math.pow(2, N-1) * sumfruits; //Each fruit appears in that many sum 

        //We know basket must be atmost 4 fruits, therefore remove 1, 2 or 3 fruits if the sum is less than 200
        //Filter them out
        int weight = 0;   
        for (int i = 0; i < N; i++) {
            weight = fruits[i];
            if (weight < 200) {
                sumfruits -= weight;
                for (int j = i+1; j < N; j++) {
                    weight = fruits[i] + fruits[j];
                    if (weight < 200) {
                        sumfruits -= weight;
                        for (int k = j+1; k < N; k++) {
                            weight = fruits[i] + fruits[j] + fruits[k];
                            if (weight < 200)
                                sumfruits -= weight;
                        }
                    }
                    
                }
            }
        }

        io.print(sumfruits);

        io.close();
    }

    
}