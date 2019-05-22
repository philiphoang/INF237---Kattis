
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

public class SimplePolygon {
    static Kattio io;
    public static void main(String[] args) {
        io = new Kattio(System.in);

        int testCases = io.getInt();

        while(testCases > 0) {
            int n = io.getInt();
            List<Point> points = new ArrayList<>();

            for (int i = 0; i < n; i++) {
                int x = io.getInt();
                int y = io.getInt();
                Point p = new Point(x, y);
                points.add(p);
            }
            convexHull(points);

            testCases--;
        }
        io.close();
    }

    static void convexHull(List<Point> points) {
        List<Point> list = new ArrayList<>();
        List<Point> sorted = new ArrayList<>(points);
        
        Collections.sort(sorted); 
   
        Stack<Point> stack = grahamScan(sorted);
        while (!stack.isEmpty()) {
            list.add(stack.pop());
        }
        
        for (int i = 0; i < sorted.size(); i++) {
            if (!list.contains(sorted.get(i))) {
                list.add(sorted.get(i));
            }
        }

        for (int i = 0; i < list.size(); i++) {
            io.print(points.indexOf(list.get(i)) + " ");
        }
        io.println();

    }

    static Stack grahamScan(List<Point> points) {
        Stack<Point> stack = new Stack<>();
        stack.push(points.get(0));
        stack.push(points.get(1));

        for (int i = 2; i < points.size(); i++) {
            while (stack.size() > 1 && orientation(nextToTop(stack), stack.peek(), points.get(i)) == 2) {
                stack.pop();
            }
            stack.push(points.get(i));
        }
        return stack;
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
            return -1;
        if (this.x > that.x) 
            return 1;
        if (this.y < that.y) 
            return -1;
        if (this.y > that.y) 
            return 1;
        
        return 0;
        
    } 
}
