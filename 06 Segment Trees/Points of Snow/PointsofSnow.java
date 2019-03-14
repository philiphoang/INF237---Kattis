
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


public class PointsofSnow {
    static int[] array;
    public static void main(String[] args) {
        Kattio io = new Kattio(System.in);
        
        int N = io.getInt(); //Length of Lineland
        int K = io.getInt(); //Number of weather reports
        int Q = io.getInt(); //NUmber of snow depth queries

        array = new int[N]; //Lineland
        SegmentTree segmentTree = new SegmentTree(N);

        for (int i = 0; i < K + Q; i++) {
            String C = io.getWord(); //Report or query

            if (C.equals("!")) {
                int L = io.getInt(); //Left
                int R = io.getInt(); //Right
                int D = io.getInt(); //Value 
                segmentTree.updateRange(L, R, D);
                
                /*
                for (int j = 0; j < segmentTree.getLength(); j++) {
                    System.out.print(segmentTree.getTree()[j] + " ");
                }
                System.out.println();
                */
            }

            if (C.equals("?")) {
                int X = io.getInt(); //Position we are interested in
                System.out.println(segmentTree.getSum(X));
                //System.out.println(segmentTree.getQ(X-1, X));
            }
        }
        io.close();
    }

}

class SegmentTree {
    static int tree[];
    static int offset;
    static int N;

    public SegmentTree (int n) {
        N = 2;
        while (2*n + 3 > N) {
            N *= 2;
            System.out.println("N: " + N);
        }

        offset = 2;
        while (n + 2 > offset) {
            offset *= 2;
            System.out.println("offset: " + offset);
        }

        tree = new int[N];
    }

    int getLength() {
        return tree.length;
    }

    int[] getTree() {
        return tree;
    }

    void updateRange(int i, int j, int value) { //Is now update(); 
        int L = i + offset;
        int R = j + offset;  
        System.out.println("L: " + L + " R: " + R);

        boolean wrongLRight = false;
        boolean wrongRLeft = false;

        while (true) {
            boolean LRight = (L % 2) == 1; //Is up right?
            boolean RLeft = (R % 2) == 0; //Is up left?
            
            L /= 2;
            R /= 2;

            if (L == R) {
                tree[L] += value;

                if (LRight) {
                    if (!wrongLRight)
                        tree[(2*L) + 1] += value; //One LEFT of interval
                } 
                else {
                    wrongLRight = false;
                }
                if (RLeft) {
                    tree[2*R] += value;
                }  
                else {
                    wrongRLeft = false;
                }
            }
            
            break;
        }
    }

    int getSum(int index) {
        int pos = index + offset;
        int sum = 0;

        int L = pos;
        int R = pos;  
        
        while (true) {
            boolean LRight = (L % 2) == 1; //Is up right?
            boolean RLeft = (R % 2) == 0; //Is up left?
            L /= 2;
            R /= 2;
            
            if (LRight) {
                sum += tree[2*L + 1]; //One LEFT of interval
            }
            if (RLeft) {
                sum += tree[2*R];
            }
            if (L == R) {
                return sum;
            }
        }
    }
}