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

public class pesky {
    static double radius;
    public static void main(String[] args) {
        Kattio io = new Kattio(System.in);

        int n = io.getInt();

        for (int amund = 0; amund < n; amund++) {
            int m = io.getInt();
            double diameter = io.getDouble();

            List<Point> mosquitoes = new ArrayList<>();

            for (int i = 0; i < m; i++) {
                double x1 = io.getDouble();
                double x2 = io.getDouble();

                mosquitoes.add(new Point(x1, x2));
            }

            int best = 0;
            radius = (diameter / 2) + 0.00001;
            for (int i = 0; i < mosquitoes.size(); i++) {
                int captured = 0;

                for (int j = 0; j < mosquitoes.size(); j++) {
                    Map<Point, Integer> bowls = new HashMap<>();

                    Point A = mosquitoes.get(i);
                    Point B = mosquitoes.get(j);

                    double distance = calcDist(A.x, B.x, A.y, B.y);
                    if (distance > diameter) { //Points too far away, circles do not intersect
                        captured = 1; 
                    } 
                    else if (distance == diameter) { //Circles barely touching oo
                        captured = 2;
                    }
                    else {
                        Point[] intersections = findIntersections(A, B);

                        //Put the points where the circles intersect with a counter
                        bowls.put(intersections[0], 0); 
                        bowls.put(intersections[1], 0);

                        for (int p = 0; p < 2; p++) {
                            Point center = intersections[p];
                            System.out.println("Center: (" + center.x + ", " + center.y + ")");
                        
                            for (int ole = 0; ole < mosquitoes.size(); ole++) {        
                                Point P = mosquitoes.get(ole);
                                
                                //Check if the point P is in the circle
                                distance = calcDist(center.x, P.x, center.y, P.y);
                                if (distance <= radius) {
                                    System.out.println("Captured (" + P. x + ", " + P.y + ")");
                                    bowls.put(center, bowls.get(center) + 1);
                                }
                            }
                        }

                        //Take the circle that caught most points
                        captured = Math.max(bowls.get(intersections[0]), bowls.get(intersections[1])); 
                    }

                    if (best < captured) {
                        best = captured;
                    }
                }
            }

            io.println(best);
            
        }

        io.close();
    }

    static double calcDist(double x1, double x2, double y1, double y2) {
        double dist = Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2);
        return Math.sqrt(dist);
    }

    static Point[] findIntersections(Point P0, Point P1) {
        Point[] intersections = new Point[2];
        double d = calcDist(P0.x, P1.x, P0.y, P1.y);

        //Find a: midpoint of the length between P0 and P1
        //double a = (Math.pow(radius, 2) - Math.pow(radius, 2) + Math.pow(d, 2)) / 2 * d;
        double a = d / 2;

        //Find h: the height of the length between P0 and P1 (also perpendicular)
        double h = Math.sqrt((radius * radius) - (a * a));

        //This is the midpoint between P0 and P1
        double p2x = P0.x + (a * (P1.x - P0.x)) / d;
        double p2y = P0.y + (a * (P1.y - P0.y)) / d;

        //The intersected points
        double midP3x = p2x + (h * (P1.y - P0.y)) / d;
        double midP3y = p2y - (h * (P1.x - P0.x)) / d;

        double midP4x = p2x - (h * (P1.y - P0.y)) / d;
        double midP4y = p2y + (h * (P1.x - P0.x)) / d;
    
        intersections[0] = new Point(midP3x, midP3y);
        intersections[1] = new Point(midP4x, midP4y);

        return intersections;
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