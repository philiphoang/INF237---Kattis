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

public class HiddenWords {
    static String[][] puzzle;
    static int h, w;
    static boolean[][] visited;
    public static void main(String[] args) {
        Kattio io = new Kattio(System.in);

        h = io.getInt(); //Height
        w = io.getInt(); //Width
        puzzle = new String[h][w];

        for (int i = 0; i < h; i++) {
            String s = io.getWord();
            String[] word = s.split("");
            for (int j = 0; j < w; j++) {
                puzzle[i][j] = word[j];
            }
        }

        //prettyPrint();

        List<String> ingrid = new ArrayList<>();
        HashMap<String, Boolean> map = new HashMap<>();
        int n = io.getInt();
        int counter = 0;
        for (int i = 0; i < n; i++) {
            String word = io.getWord();
            if (!map.containsKey(word)) {
                map.put(word, true);
                if (lookup(word)) {
                    counter++;
                }
            }
            else if (map.get(word)) {
                counter++;
            } 
        }
        
        io.println(counter);
        io.close();
    }

    static boolean lookup(String word) {
        String[] s = word.split("");
        visited = new boolean[h][w];
        int pos = 0;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if (s.length == findNext(i, j, s, pos, 0)) {
                    return true;         
                }
            }
        } 

        return false;
    }

    boolean inRange(int x, int y) {
        return x < h && y < w &&
               x >= 0 && y >= 0;
    }

    
    static int findNext(int x, int y, String[] s, int pos, int c) {
        int counter = c;
        pos++;
        if (pos >= s.length)
            return counter;

        visited[x][y] = true;
          
        if (y + 1 < w) {
            if (puzzle[x][y + 1].equals(s[pos]))
                if (!visited[x][y + 1]) { //Right
                    counter++;
                    return findNext(x, y + 1, s, pos, counter);
                }
            }
               
        if (y - 1 >= 0) 
            if (puzzle[x][y - 1].equals(s[pos]))
                if (!visited[x][y - 1]) {//Left
                    counter++;
                    return findNext(x, y - 1, s, pos, counter);
            }
                    
        if (x - 1 >= 0)
            if (puzzle[x - 1][y].equals(s[pos]))
                if (!visited[x - 1][y]) {//Up
                    counter++;
                    return findNext(x - 1, y, s, pos, counter);
            }
        
        if (x + 1 < h)
            if (puzzle[x + 1][y].equals(s[pos]))
                if (!visited[x + 1][y]) {//Down
                    counter++;
                    return findNext(x + 1, y, s, pos, counter);
            }
        visited[x][y] = false;

        return counter;
    }



    static void prettyPrint() {
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                System.out.print(puzzle[i][j] + " ");
            }
            System.out.println();
        }
    }
}