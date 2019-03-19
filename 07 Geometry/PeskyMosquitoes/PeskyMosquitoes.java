
/** Simple yet moderately fast I/O routines.
 *
 * Example usage:
 *
 * Kattio io = new Kattio(System.in, System.out);
 *
 * while (io.hasMoreTokens()) {
 *    int n = io.getInt();
 *    double d = io.getDouble();
 *    double ans = d*n;
 *
 *    io.println("Answer: " + ans);
 * }
 *
 * io.close();
 *
 *
 * Some notes:
 *
 * - When done, you should always do io.close() or io.flush() on the
 *   Kattio-instance, otherwise, you may lose output.
 *
 * - The getInt(), getDouble(), and getLong() methods will throw an
 *   exception if there is no more data in the input, so it is generally
 *   a good idea to use hasMoreTokens() to check for end-of-file.
 *
 * @author: Kattis
 */

import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.OutputStream;
import java.util.List;
import java.util.ArrayList;

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

public class PeskyMosquitoes {
    public static void main(String[] args) {
        Kattio io = new Kattio(System.in);

        int n = io.getInt();

        List<Integer> bestList = new ArrayList<>();
        List<Point> pointsCaptured = new ArrayList<>();
        List<Point> pointsInBest = new ArrayList<>();

        for (int amund = 0; amund < n; amund++) {
            int m = io.getInt();
            double d = io.getDouble();

            //double[][] graph = new double[m][m];

            List<Point> points = new ArrayList<>();

            for (int i = 0; i < m; i++) {
                double x1 = io.getDouble();
                double x2 = io.getDouble();

                points.add(new Point(x1, x2));
            }

            int best = 0;
            Point centroid = null;
            double radius = (d / 2) + 0.00005;
            Point bestPoint = new Point(0, 0);
            for (int i = 0; i < points.size(); i++) {
                for (int j = 0; j < points.size(); j++) {
                    int captured = 0;
                    pointsCaptured = new ArrayList<>();

                    centroid = createCentroid(points.get(i), points.get(j));
                    //System.out.println("Centroid.x: " + centroid.x + " Centroid.y: " + centroid.y);
                    for (int ole = 0; ole < points.size(); ole++) {
                        Point P = points.get(ole);

                        double distance = calcDis(centroid.x, centroid.y, P.x, P.y);
                        if (distance <= radius) {
                            captured++;
                            pointsCaptured.add(P);
                        }
                    }

                    if (captured > best) {
                        best = captured;
                        pointsInBest = pointsCaptured;
                        bestPoint = centroid;
                    }
                }

            }

            io.println(best);
            //System.out.println("Best.x: " + bestPoint.x + " Centroid.y: " + bestPoint.y);

            //bestList.add(best);
        }

        /*
        for (int i : bestList)
            io.print(i + " ");
        */

        /*
        System.out.print("[");
        for (Point p : pointsInBest) 
            System.out.print("(" + p.x + ", " + p.y + "), ");
        System.out.print("]");
        */
        io.close();
    }

    static double calcDis(double x1, double y1, double x2, double y2) {
        double dist = Math.pow(y2 - y1, 2) + Math.pow(x2 - x1, 2);
        return Math.sqrt(dist);
    }

    static Point createCentroid(Point P1, Point P2) {
        Point centroid = new Point((P1.x + P2.x)/2, (P1.y + P2.y)/2);
        return centroid; 
    }
}

class Point {
    double x;
    double y;

    Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
}