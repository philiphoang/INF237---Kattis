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
import java.util.Arrays;
import java.util.Collections;

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

public class MuddyHike {
    static int[][] terrain;
    static boolean[][] visited;
    static List<Integer> mst = new ArrayList<>();
    static List<Integer> queue = new ArrayList<>();
    static int largestValue;
    static int rows;
    static int columns;
    static boolean first, last;
    public static void main(String[] args) {
        Kattio io = new Kattio(System.in);

        rows = io.getInt();
        columns = io.getInt();

        terrain = new int[rows][columns];
        visited = new boolean[rows][columns];

        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < columns; y++) {
                terrain[x][y] = io.getInt();
            }
        }
        
        int smallest = 1000001;
        int dx = 0;
        int dy = 0;
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < columns; y++) {
                if (smallest > terrain[x][y]) {
                    smallest = terrain[x][y];
                    dx = x;
                    dy = y; 
                }
            }
        }
        System.out.println("Val: " + smallest + " (" + dx + ", " + dy + ")");

        //Run prim on the smallest value;
        prim(smallest, dx, dy);
 
    
        System.out.println(Collections.max(mst));

    }

    static void prim(int currentValue, int x, int y) {
        mst.add(currentValue);
        visited[x][y] = true;
 
        if (y == 0) {
            System.out.println("First achieved");
            first = true;
        }

        if (y == rows - 1) {
            System.out.println("Last achieved");
            last = true;
        }

        if (first && last) {
            queue.clear();
            return;
        } 
        else {
            findNeighbours(x, y);
            int curr = queue.remove(0);

            if (!queue.isEmpty()) 
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < columns; j++) {
                        if (curr == terrain[i][j] && !visited[i][j]) {
                            System.out.println("Val: " + curr + " (" + i + ", " + j + ")");
                           prim() 
                        } 
                    }
                }


                
        }

    }

    static void findNeighbours(int x, int y) {
        if (y + 1 < columns) //East
            if (!visited[x][y + 1]) {
                queue.add(terrain[x][y + 1]); 
                System.out.println("Seeing: " + terrain[x][y + 1]);
            }
        
        if (y - 1 >= 0) //West
            if (!visited[x][y - 1]) {
                queue.add(terrain[x][y - 1]);
                System.out.println("Seeing: " + terrain[x][y - 1]);
            }

        if (x - 1 >= 0) //North
            if (!visited[x - 1][y]) {
                queue.add(terrain[x - 1][y]);
                System.out.println("Seeing: " + terrain[x - 1][y]);

            }

        if (x + 1 < rows) //South
            if (!visited[x + 1][y]) {
                queue.add(terrain[x + 1][y]);
                System.out.println("Seeing: " + terrain[x + 1][y]);

            }
        
        Collections.sort(queue);
        for (int i : queue) {
            System.out.print(i + " ");
        }
        System.out.println();
    }
}