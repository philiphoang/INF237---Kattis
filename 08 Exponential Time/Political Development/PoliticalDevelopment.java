import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.OutputStream;

import java.util.Map;
import java.util.HashMap;
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

public class PoliticalDevelopment {
    public static void main(String[] args) {
        Kattio io = new Kattio(System.in);

        int N = io.getInt();
        int K = io.getInt();

        Map<Integer, List<Integer>> politicans = new HashMap<>();
        
        for (int i = 0; i < N; i++) {
            int D = io.getInt();
            List<Integer> group = new ArrayList<>();
            for (int j = 0; j < D; j++) {
                group.add(io.getInt());
            } 
            if (D != 0)
                group.add(i);
            politicans.put(i, group);
        }

        //printPoliticans(politicans);
        System.out.println(bruteForce(politicans, N));
        
    }
    
    static long bruteForce(Map<Integer, List<Integer>> politicans, int N) {
        long max = 0;
        for (int i = 0; i < N; i++) {
            List<Integer> groupi = new ArrayList<>(politicans.get(i));
            long size =0;
            for (int j = 0; j < N; j++) {
                //Need to hop over politicans.get(i)
                if (i != j) {
                    //System.out.println("Hop over");
                
                    groupi.retainAll(politicans.get(j));
                    //System.out.println("groupi size: " + groupi.size());
                    size = groupi.size();
                    groupi.clear();


                }
                if (max < size) {
                    max = size;
                }

            }
        }

        return max;
    }

    static void printPoliticans(Map<Integer, List<Integer>> politicans) {
        for (int i : politicans.keySet()) {
            System.out.print(i + " ");
            for (int p : politicans.get(i)) {
                System.out.print(p + " ");
            }
            System.out.println();
        }
    }

}
