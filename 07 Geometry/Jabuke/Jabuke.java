import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Formatter;
import java.util.Locale;

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
                    if (line == null)
                        return null;
                    st = new StringTokenizer(line);
                }
                token = st.nextToken();
            } catch (IOException e) {
            }
        return token;
    }

    private String nextToken() {
        String ans = peekToken();
        token = null;
        return ans;
    }
}

public class Jabuke {
    public static void main(String args[]) {
        Kattio io = new Kattio(System.in);

        int xA = io.getInt();
        int yA = io.getInt();

        int xB = io.getInt();
        int yB = io.getInt();

        int xC = io.getInt();
        int yC = io.getInt();

        double areaOfTriangle = areaOfTriangle(xA, yA, xB, yB, xC, yC);
        System.out.println(areaOfTriangle);

        int N = io.getInt();
        List<Point> trees = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            int x = io.getInt();
            int y = io.getInt();
            trees.add(new Point(x, y));
        }

        int counter = 0;
        for (Point p : trees) {
            double A1 = areaOfTriangle(xA, yA, xB, yB, p.x, p.y);
            double A2 = areaOfTriangle(xB, yB, xC, yC, p.x, p.y);
            double A3 = areaOfTriangle(xA, yA, xC, yC, p.x, p.y);
            
            double sumTriangles = A1 + A2 + A3;
            if (areaOfTriangle == sumTriangles) {
                counter++;
            }
        }

        System.out.println(counter);

        io.close();
    }

    static double areaOfTriangle(int xA, int yA, int xB, int yB, int xC, int yC) {
        return (double) Math.abs(
            xA*(yB-yC) + xB*(yC - yA) + xC*(yA - yB)
        )/2;
    }
}

class Point {
    int x, y;
    Point(int x, int y) {this.x = x; this.y = y;}
}