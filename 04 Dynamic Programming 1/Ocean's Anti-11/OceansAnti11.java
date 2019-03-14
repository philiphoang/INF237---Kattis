import java.util.List;
import java.util.StringTokenizer;

import javax.swing.plaf.synth.SynthSplitPaneUI;

import java.io.BufferedReader;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.OutputStream;

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


public class OceansAnti11 {
    public static void main(String[] args) {

        Kattio io = new Kattio(System.in);

        int T = io.getInt();
        List<Integer> fib = new ArrayList<Integer>();
        fib.add(0);
        fib.add(2);
        fib.add(3);


        //Fibonacci sequence 
        for (int i = 0; i < T; i++) {
            int n = io.getInt();
            
            for (int j = 3; j < 10000+1; j++) {
                fib.add((fib.get(j-2) + fib.get(j-1)) % 1000000007);
            }
            
            io.println(fib.get(n));
        }
        io.close();
    }
}