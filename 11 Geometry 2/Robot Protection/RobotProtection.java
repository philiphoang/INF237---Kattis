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

public class RobotProtection {
    static PriorityQueue<Point> sortedQueue;
    static Point[] points;
    public static void main(String[] args) {
        Kattio io = new Kattio(System.in);

        int n = io.getInt(); 

        sortedQueue = new PriorityQueue<>();

        while (n != 0) {
            points = new Point[n];
            for (int i = 0; i < n; i++) {
                int x = io.getInt();
                int y = io.getInt();
                sortedQueue.add(new Point(x, y));
                points[i] = new Point(x, y);
            }

            Arrays.sort(points); //Sort list to find lowest point
            convexHull(points);
            n = io.getInt();

        } 
        
        for (Point p : points) {
            System.out.println(p.x + "," + p.y);
        }
    }

    static void convexHull(Point[] points) {
        Point P0 = points[0];

        //Sort points by polar angle with P0

    }

    int polarAngle(Point P0, Point P1, Point P2) {

    }

    int orientation(Point P0, Point P1, Point P2) {
        int val = (P1.y - P0.y) *
    }

    int distance(Point p1, Point p2) {
        return (p1.x - p2.x)*(p1.x - p2.x) + (p1.y - p2.x)*(p1.y - p2.x);
    }


}

class Point implements Comparable<Point> {
    int x;
    int y; 

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int compareTo(Point that) {
        if (this.y > that.y)
            return 1;
        if (this.y < that.y)
            return -1;
        if (this.x > that.x)
            return 1;
        if (this.x < that.x)
            return -1;
        
        return 0;
    }
}
