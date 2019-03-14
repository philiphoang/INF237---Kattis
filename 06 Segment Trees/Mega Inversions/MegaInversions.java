import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.OutputStream;

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


public class MegaInversions {
    static int[] sequence; 
    static int[] treeA;
    public static void main(String[] args) {
        Kattio io = new Kattio(System.in);

        int length = io.getInt();

        sequence = new int[length];
        treeA = new int[length];
        for (int i = 0; i < length; i++) {
            sequence[i] = io.getInt();
        }

        int val = 0x3333;
        System.out.println(val & -val);


    }

    public static void constructTree(int length, int[] seq) {
        for (int i = 1; i <= length; i++) 
            treeA[i] = 0;

        for (int i = 0; i < length; i++) 
            updateTree(length, i, seq[i]);
    }

    public static void updateTree(int length, int index, int value) {
        index = index + 1;

        while(index <= length) {
            treeA[index] += value;

            index += index & (-index);
        }
    }
}