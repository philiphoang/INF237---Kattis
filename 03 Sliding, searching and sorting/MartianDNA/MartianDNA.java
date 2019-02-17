import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.OutputStream;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;


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

public class MartianDNA {
    public static void main(String[] args) {
        Kattio io = new Kattio(System.in);
        Map<Integer, Integer> map = new HashMap<>();


        int N = io.getInt(); //Length of the DNA sequence
        int K = io.getInt(); //Number of nucleobases
        int R = io.getInt(); //Define how many times each nucleobase occurs
        
        int[] sequence = new int[N];
        for (int i = 0; i < N; i++) {
            sequence[i] = io.getInt();
        }

        int num =0;
        int requiredOccurences = 0;
        for (int i = 0; i < R; i++) {
            map.put(io.getInt(), num = io.getInt());
            requiredOccurences+=num;
        }
    
        int minLen = find(sequence, requiredOccurences, map);
        if (minLen == Integer.MAX_VALUE)
            System.out.println("impossible");
        else 
            System.out.println(minLen);

    }   

    public static int find(int[] sequence, int requiredOccurences, Map<Integer, Integer> map) {
        
        int start = 0, end = 0;
        int minLen = Integer.MAX_VALUE;

        while (start < sequence.length) {

            if (requiredOccurences > 0 && end < sequence.length) {
                int current = sequence[end];
                if (map.get(current) != null) {
                    if (map.get(current) > 0) {
                        requiredOccurences--;
                    }    
                    map.put(current, map.get(current) - 1);
                }
                end++;

            }
            else {
                if (minLen > end - start && requiredOccurences == 0) {
                    minLen = end - start;

                }
                int head = sequence[start];
                if (map.get(head) != null) {
                    if (map.get(head) >= 0) {
                        requiredOccurences++; //Put it back in
                    }
                    map.put(head, map.get(head) + 1);
                }
                start++;

            }

        }
        return minLen;
    }
}
