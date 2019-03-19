import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;
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


public class pm {

    public static void main(String[] args) throws IOException {

        List<Integer> bestList = new ArrayList<>();
        List<Coordinate> pointsCaptured;
        List<Coordinate> pointsInBest = new ArrayList<>();

        Kattio reader = new Kattio(System.in);
        int numTestCases = reader.getInt();

        while (numTestCases-- > 0) {
           

            int numMosquitoes = reader.getInt();
            double radius = reader.getDouble() / 2.0;

            Coordinate[] mosquitoes = new Coordinate[numMosquitoes];

            for (int i = 0; i < numMosquitoes; i++) {
                double x = reader.getDouble();
                double y = reader.getDouble();
                mosquitoes[i] = new Coordinate(x, y);
            }

            int bestCatch = 0;
            Coordinate bestPoint = null;
            Coordinate centroid = null;
            for (int i = 0; i < numMosquitoes; i++) {
                for (int j = 0; j < numMosquitoes; j++) {
                    int caughtMosquitoes = 0;

                    pointsCaptured = new ArrayList<>();


                    Coordinate center = centerOfCircle(mosquitoes[i], mosquitoes[j], radius);
                    for (int k = 0; k < numMosquitoes; k++) {
                        double pointsDistance = Math.sqrt(Math.pow(center.x - mosquitoes[k].x, 2) + Math.pow(center.y - mosquitoes[k].y, 2));
                        if (pointsDistance <= radius) {
                            caughtMosquitoes++;
                            pointsCaptured.add(new Coordinate(mosquitoes[k].x, mosquitoes[k].y));
                        }
                    }

                    if (caughtMosquitoes > bestCatch) {
                        bestCatch = caughtMosquitoes;
                        pointsInBest = pointsCaptured;
                        bestPoint = center;
                    }
                }
            }

            System.out.println(bestCatch);
            System.out.println("Centroid.x: " + bestPoint.x + " Centroid.y: " + bestPoint.y);


            System.out.println();

            
            System.out.print("[");
            for (Coordinate p : pointsInBest) 
                System.out.print("(" + p.x + ", " + p.y + "), ");
            System.out.print("]");
            
        }
    }

    public static double distanceBetweenPoints(Coordinate p1, Coordinate p2) {
        return Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
    }

    public static Coordinate centerOfCircle(Coordinate p1, Coordinate p2, double radius) {
        Coordinate midpoint = new Coordinate((p1.x + p2.x) / 2, (p1.y + p2.y) / 2);
        double lengthP1ToMidpoint = distanceBetweenPoints(p1, midpoint);
        double lengthMidToCenter = Math.sqrt(Math.pow(radius, 2) - Math.pow(lengthP1ToMidpoint, 2));
        double radians = Math.atan2(p1.x - p2.x, p2.y - p1.y);

        Coordinate centerOfCircle = new Coordinate(lengthMidToCenter * Math.cos(radians) + midpoint.x, lengthMidToCenter * Math.sin(radians) + midpoint.y);
        return centerOfCircle;
    }

    public static class Coordinate {

        double x, y;

        public Coordinate(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

}