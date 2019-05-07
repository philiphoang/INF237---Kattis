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

public class GeneticSearch {
    public static void main(String[] args) {
        Kattio io = new Kattio(System.in);
        String S = "", L = "";

        while (true) {
            S = io.getWord();
            if (S.equals("0"))
                break;
            L = io.getWord();

            io.println(type1(S, L) + " " + type2(S, L) + " " + type3(S, L));
        }
        io.close();
    }

    static int type1(String S, String L) {
        int count = 0;
        int fromIndex = 0;

        while ((fromIndex = L.indexOf(S, fromIndex)) != -1) {
            count++;
            fromIndex++;
        }

        return count;
    }

    static int type2(String S, String L) {
        int count = 0;
        Set<String> set = new HashSet<>();

        for (int i = 0; i < S.length(); i++) {
            String temp = S;
            String s = temp.substring(0, i) + temp.substring(i+1);
            set.add(s);
        }

        for (String s : set) {
            count += type1(s, L);
        }

        return count;
    }

    static int type3(String S, String L) {
        int count = 0;
        Set<String> set = new HashSet<>();

        String[] chars = {"A", "T", "C", "G"};
        for (int i = 0; i < S.length()+1; i++) {
            for (int j = 0; j < 4; j++) {
                String temp = S;
                String s = temp.substring(0, i) + chars[j] + temp.substring(i);
                set.add(s);
            }
        }

        for (String s : set) {
            count += type1(s, L);
        }

        return count;
    }
}