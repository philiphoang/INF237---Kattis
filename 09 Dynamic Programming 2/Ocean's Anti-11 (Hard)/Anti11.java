import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.OutputStream;
import java.util.*;

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

public class Anti11 {
    static int[] arr; 
    static ArrayList<String> allBits;

    static final int MOD = 1000000007;
    public static void main(String[] args) {
        Kattio io = new Kattio(System.in);

        int T = io.getInt(); //Test cases
        

        for (int i = 0; i < T; i++) {
            allBits = new ArrayList<>();
            int n = io.getInt(); //Length of binary string
            arr = new int[n];
            String b = io.getWord();

            generateAllBinaryStrings(n, 0); 

            int counter = 0;
            for (String s : allBits) { //Linear
                if (s.contains(b)) {
                    counter++;
                }
            }
            System.out.println(Math.abs(counter - allBits.size()));

        }

    }

    static void generateAllBinaryStrings(int n, int i) { 
        if (i == n)  { 
            printTheArray(n); 
            return; 
        } 

        arr[i] = 0; 
        generateAllBinaryStrings(n, i + 1); 
    
        arr[i] = 1; 
        generateAllBinaryStrings(n, i + 1); 
    } 

    static void printTheArray(int n) { 
        String bit = "";
        for (int i = 0; i < n; i++)  { 
            bit += arr[i];
        } 
        allBits.add(bit);
    } 
}
