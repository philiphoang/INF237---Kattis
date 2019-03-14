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
import java.util.HashMap;
import java.util.Map;

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

public class VirtualFriends {
    static int[] size = new int[100000];
    static int[] parent = new int[100000];
    static Map<String, Integer> map;
    
    public static void main(String[] args) {
        Kattio io = new Kattio(System.in);


        int testCases = io.getInt();
        
        for (int i = 0; i < testCases; i++) {
            int F = io.getInt();
            int index = 0;
            int firstIndex;
            int secondIndex;

            size = new int[100000];
            parent = new int[100000];
            map = new HashMap<>();

            for (int j = 0; j < F; j++) {
                String firstPerson = io.getWord();
                String secondPerson = io.getWord();
                
                if (!map.containsKey(firstPerson)) {
                    map.put(firstPerson, index);
                    firstIndex = index;

                    size[firstIndex] = 1;
                    parent[index] = index;

                    index++;
                } else {
                    firstIndex = map.get(firstPerson);
                }

                if (!map.containsKey(secondPerson)) {
                    map.put(secondPerson, index);
                    secondIndex = index;

                    size[secondIndex] = 1;
                    parent[index] = index;

                    index++;
                } else {
                    secondIndex = map.get(secondPerson);
                }
 
                
                union(firstIndex, secondIndex);
                
            }

            size = null;
            parent = null;
        }

        io.close();
    }

    static int find(int index) {
        int root = index;

        //Iterate until we find the parent of desired index/node
        while (root != parent[root]) {
            root = parent[root];
        }
        return root;
    }

    static boolean connected(int aIndex, int bIndex) {
        //If they both are connected by the same parent
        int aRoot = find(aIndex); 
        int bRoot = find(bIndex);

        return aRoot == bRoot;
    }

    static void union(int aIndex, int bIndex) {
        //Find parent of 'a' and parent of 'b'
        int aRoot = find(aIndex);
        int bRoot = find(bIndex);

        //If they have the same parent
        if (aRoot == bRoot) {
            System.out.println(size[aRoot]);
            return;
        }

        //If not connected by the same parent, connect to 'b' to 'a'
        if (size[aRoot] >= size[bRoot]) {
            parent[bRoot] = aRoot;
            size[aRoot] += size[bRoot];
            System.out.println(size[aRoot]);
        }
        else { //Connect 'a' to 'b'
            parent[aRoot] = bRoot;
            size[bRoot] += size[aRoot];
            System.out.println(size[bRoot]);
        }
    }
}
