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
import java.util.PriorityQueue;
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

public class mh {
    static int[][] terrain;
    static int[][] d;
    static boolean[][] visited;
    static PriorityQueue<Mud> queue = new PriorityQueue<>();
    static int rows;
    static int columns;
        public static void main(String[] args) {
        Kattio io = new Kattio(System.in);

        rows = io.getInt();
        columns = io.getInt();

        terrain = new int[rows][columns];
        d = new int[rows][columns];
        visited = new boolean[rows][columns];

        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < columns; y++) {
                terrain[x][y] = io.getInt();
                d[x][y] = Integer.MAX_VALUE;
            }
        }

        for (int i = 0; i < rows; i++) {
            queue.add(new Mud(terrain[i][0], i, 0)); 
            d[i][0] = terrain[i][0];
        }
        
        io.println(dijkstra());

        io.close();

    }


    static int dijkstra() {
        int max = Integer.MAX_VALUE;

        while (!queue.isEmpty()) {
            Mud v = queue.poll();
            if (!visited[v.x][v.y]) {
                visited[v.x][v.y] = true;

                for (Mud u : findNeighbours(v.x, v.y)) {
                    if (!visited[u.x][u.y]) {
                        if (d[u.x][u.y] > Math.max(terrain[u.x][u.y], v.value)) {
                            d[u.x][u.y] = Math.max(terrain[u.x][u.y], v.value);
                            queue.add(new Mud(d[u.x][u.y], u.x, u.y));
                        }
                    }
                }
            }
        }

        for (int i = 0; i < rows; i++) {
            if (max > d[i][columns-1]) {
                max = d[i][columns-1];
            }
        }

        return max;
 
    }


    static PriorityQueue<Mud> findNeighbours(int x, int y) {
        PriorityQueue<Mud> neighbours = new  PriorityQueue<>();
        if (y + 1 < columns) //East
           {
                neighbours.add(new Mud(terrain[x][y + 1], x, y + 1)); 

            }
        
        if (y - 1 >= 0) //West
             {
                neighbours.add(new Mud(terrain[x][y - 1], x, y - 1));

            }

        if (x - 1 >= 0) //North
            {
                neighbours.add(new Mud(terrain[x - 1][y], x -1, y));

            }

        if (x + 1 < rows) //South
        {
                neighbours.add(new Mud(terrain[x + 1][y], x + 1, y));

        }
        return neighbours;
    }

}

class Mud implements Comparable<Mud> {
    int x;
    int y;
    int value; 

    public Mud(int value, int x, int y) {
        this.value = value;
        this.x = x;
        this.y = y;
    }

    public int getValue(){
        return value;
    }

    public int getX(){
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public int compareTo(Mud other) {
        if (this.value < other.value) {
            return -1;
        }
        else if (this.value > other.value) {
            return 1;
        }
        else return 0;
    }
}