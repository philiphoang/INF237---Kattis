
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


class st {

    public static int get(int[] t, int i) {
      return t[i + t.length / 2];
    }
  
    public static void add(int[] t, int i, int value) {
      i += t.length / 2;
      t[i] += value;
      for (; i > 1; i >>= 1)
        t[i >> 1] = Math.max(t[i], t[i ^ 1]);
    }
  
    // max[a, b]
    public static int max(int[] t, int a, int b) {
      int res = Integer.MIN_VALUE;
      for (a += t.length / 2, b += t.length / 2; a <= b; a = (a + 1) >> 1, b = (b - 1) >> 1) {
        if ((a & 1) != 0)
          res = Math.max(res, t[a]);
        if ((b & 1) == 0)
          res = Math.max(res, t[b]);
      }
      return res;
    }
  
    // Usage example
    public static void main(String[] args) {
        Kattio io = new Kattio(System.in);
        
        int N = io.getInt(); //Length of Lineland
        int K = io.getInt(); //Number of weather reports
        int Q = io.getInt(); //NUmber of snow depth queries

        int[] array = new int[N]; //Lineland
        for (int i = 0; i < array.length; i++) 
            add(array, i, 0);

        for (int i = 0; i < K + Q; i++) {
            String C = io.getWord(); //Report or query

            if (C.equals("!")) {
                int L = io.getInt(); //Left
                int R = io.getInt(); //Right
                int D = io.getInt(); //Value 
                for (int k = L; k < R; k++) 
                    add(array, k, D);
                
                for (int j = 0; j < array.length; j++) {
                    System.out.print(array[j] + " ");
                }
                System.out.println();
            }

            if (C.equals("?")) {
                int X = io.getInt(); //Position we are interested in
                System.out.println(max(array, X-1, X));
                //System.out.println(segmentTree.getQ(X-1, X));
            }
        }
        io.close();
    }
  }