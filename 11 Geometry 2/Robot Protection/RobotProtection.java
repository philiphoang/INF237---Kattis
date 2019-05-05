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
    public static void main(String[] args) {
        Kattio io = new Kattio(System.in);

        int n = io.getInt(); 

        while (n != 0) {
            Point[] points = new Point[n];
            for (int i = 0; i < n; i++) {
                int x = io.getInt();
                int y = io.getInt();
                points[i] = new Point(x, y);
            }

            if (n > 2) {
                io.println(convexHull(points));
            }
            else 
                io.println(0);
            n = io.getInt();

        } 
        io.close();
    }

    static double convexHull(Point[] points) {
        ArrayList<Point> list = new ArrayList<>();
        
        Arrays.sort(points); //Lower wrap
  
        Stack<Point> stack = grahamScan(points);
        while (!stack.isEmpty()) {
            list.add(stack.pop());
        }

        Arrays.sort(points, Collections.reverseOrder()); //Upper wrap

        stack = grahamScan(points);
        while (!stack.isEmpty()) {
            list.add(stack.pop());
        }

        return computeAreaOfPolygon(list);
    }

    

    static Stack grahamScan(Point[] points) {
        Stack<Point> stack = new Stack<>();
        stack.push(points[0]); 
        stack.push(points[1]);

        for (int i = 2; i < points.length; i++) {
            while (stack.size() > 1 && orientation(nextToTop(stack), stack.peek(), points[i]) != 2) {
                stack.pop();
            }
            stack.push(points[i]);
        }
        return stack;
    }

    static float computeAreaOfPolygon(ArrayList<Point> list) {
        float area = 0;
        for (int i = 1; i < list.size(); i++) {
            area += list.get(i - 1).x * list.get(i).y -
                    list.get(i - 1).y * list.get(i).x;
        }

        return -area / 2;
    }

    static int distance(Point P1, Point P2) {
        return (P1.x - P2.x)*(P1.x - P2.x) +
               (P1.y - P2.y)*(P1.y - P2.y);
    }

    static Point nextToTop(Stack<Point> stack) {
        Point point = stack.pop();
        Point res = stack.peek();
        stack.push(point);
        return res;
    }

    static int orientation(Point P0, Point P1, Point P2) {
        int area = (P1.y - P0.y) * (P2.x - P1.x)
                 - (P1.x - P0.x) * (P2.y - P1.y);
            
        if (area == 0) 
            return 0; //Colinear
        else if (area > 0)
            return 1; //Clockwise
        else 
            return 2; //Counter clockwise
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
        if (this.x < that.x) 
            return 1;
        if (this.x > that.x) 
            return -1;
        if (this.y < that.y) 
            return 1;
        if (this.y > that.y) 
            return -1;
          
        return 0;
    } 
}
