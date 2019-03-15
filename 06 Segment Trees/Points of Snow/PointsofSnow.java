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
    public static void main(String[] args) {
        Kattio io = new Kattio(System.in);
        
        int N = io.getInt(); //Length of Lineland
        int K = io.getInt(); //Number of weather reports
        int Q = io.getInt(); //NUmber of snow depth queries

        SegmentTree segmentTree = new SegmentTree(N);

        for (int i = 0; i < K + Q; i++) {
            String C = io.getWord(); //Report or query

            if (C.equals("!")) {
                int L = io.getInt(); //Left
                int R = io.getInt(); //Right
                int D = io.getInt(); //Value 
                segmentTree.updateRange(L, R - 1, D);        
            }

            if (C.equals("?")) {
                int X = io.getInt(); //Position we are interested in
                io.println(segmentTree.getSum(X - 1));
            }
        }
        io.close();
    }

}

class SegmentTree {
    long tree[];
    int offset;
    int N;

    public SegmentTree (int n) {
        N = 2;
        while (2*n + 3 > N) {
            N *= 2;
        }

        offset = 2;
        while (n + 2 > offset) {
            offset *= 2;
        }

        tree = new long[N];
    }

    long getLength() {
        return tree.length;
    }

    long[] getTree() {
        return tree;
    }

    void updateRange(int i, int j, int value) { 
        int L = i + offset;
        int R = j + offset;  

        boolean wrongLRight = false;
        boolean wrongRLeft = false;

        if (L == R) {
            tree[L] += value;
            return;
        }

        while (true) {
            boolean LRight = (L % 2) == 0; //Is up right? 
                //If LRight = False, child is outside interval
                //If LRight = True, then child is in interval and we go up
            boolean RLeft = (R % 2) == 1; //Is up left?
                //If RLeft = False, child is outside interval
                //If RLeft = True, then child is in interval and we go up

            L /= 2;
            R /= 2;
        
            if (L == R) { //If the nodes meet on the same parent  
                if (!wrongLRight && !wrongRLeft) { //Perfect interval, update parent of these indexes
                    tree[L] += value;
                }
                else if (wrongLRight && !wrongRLeft) {  //Update the node where it was valid (node in interval)
                    tree[R*2+1] += value;
                }
                    
                else if (wrongRLeft && !wrongLRight) {
                    tree[L*2] += value;
                    
                }
                break;
            }
            
            if (LRight) { //If going upright from left-side is not valid (node not in interval)
                if (wrongLRight) {
                    tree[L*2+1] += value;
                }
            }
            else {
                if (!wrongLRight) {
                    wrongLRight = true;
                    tree[L*2+1] += value;
                }  
            }

            if (RLeft) { //If going upleft from right-side is not valid (node not in interval)
                if (wrongRLeft) {
                    tree[R*2] += value;

                }  
            } 
            else {  
                if (!wrongRLeft) {
                    wrongRLeft = true; //Else, remember latest where it was valid
                    tree[R*2] += value;
                }
            }
        }
    }

    long getSum(int index) {
        int pos = index + offset;

        long sum = 0;
        do {
            sum += tree[pos];
            pos /= 2;
            
        } while (pos > 0) ;
        
        return sum;
    }
}