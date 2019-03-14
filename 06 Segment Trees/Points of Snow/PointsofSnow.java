
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
                
                
                for (int j = 0; j < segmentTree.getLength(); j++) {
                    System.out.print(segmentTree.getTree()[j] + " ");
                }
                System.out.println();
                
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
        }

        offset = 2;
        while (n + 2 > offset) {
            offset *= 2;
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
        int L = i + offset +1;
        int R = j + offset +1;  
        System.out.println("Starting L: " + L + " & Starting R: " + R);

        boolean wrongLRight = false;
        boolean wrongRLeft = false;
        
        int tempL = 0;
        int tempR = 0;

        while (true) {
            boolean LRight = (L % 2) == 0; //Is up right? 
                //If LRight = False, child is outside interval
                //If LRight = True, then child is in interval and we go up
            boolean RLeft = (R % 2) == 1; //Is up left?
                //If RLeft = False, child is outside interval
                //If RLeft = True, then child is in interval and we go up

            L /= 2;
            R /= 2;
        
            if (L == R) { //If the same parent is met 
                System.out.println("Parent on node: " + L);
                if (!wrongLRight && !wrongRLeft) //Perfect interval, update parent of these indexes
                    tree[L] += value;
                else {
                    if (wrongLRight) { //Update the position where it was valid (node in interval)
                        System.out.println("Updating node: " + tempL);
                        tree[tempL] += value; 
                    }
                    else {//Went well for leftside, but not for right 
                        System.out.println("Updating node: " + (L*2));
                        tree[L*2] += value;
                    }

                    if (wrongRLeft) {
                        System.out.println("Updating node: " + tempR);
                        tree[tempR] += value;
                    } 
                    else { //Went well for rightside,but not for left 
                        System.out.println("Updating node: " + (R*2+1));
                        tree[R*2+1] += value;
                    }
                }
                break;
            }
            
            if (!LRight && !wrongLRight) { //If going upright from left-side is not valid (node not in interval)
                System.out.println("Leftside: Going up right not valid to node: " + L);
                wrongLRight = true;
                tempL = L; //Previous position
            }
            else {
                System.out.println("Leftside: Going up right to node: " + L);
            }

            if (!RLeft && !wrongRLeft) { //If going upleft from right-side is not valid (node not in interval)
                System.out.println("Rightside: Going up left not valid to node: " + R);
                wrongRLeft = true; //Else, remember latest where it was valid
                tempR = R; //Previous position
            } else {
                System.out.println("Rightside: Going up left to node: " + R);
            }

            
           
        }
    }

    int getSum(int index) {
        int pos = index + offset;
        System.out.println("Starting pos: " + pos);
        int sum = tree[pos];
        while (pos != 0) {
            boolean upRight = (pos % 2) == 1; //Is up right?
            boolean upLeft = (pos % 2) == 0; //Is up left?
            pos /= 2;
            if (upRight == upLeft) { //Cannot go anywhere, reached root
                break;
            }
            
            if (upRight) {
                System.out.println("Goint to node: " + pos + " and found value: " + tree[pos]);
                sum += tree[pos]; 
            }
            if (upLeft) {
                System.out.println("Goint to node: " + pos + " and found value: " + tree[pos]);
                sum += tree[pos];
            }
            
        }
        return sum;

    }
}