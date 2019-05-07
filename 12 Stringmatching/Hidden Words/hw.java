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

public class hw {
    static String[][] puzzle;
    static int n, m;
    static boolean[][] visited;
    public static void main(String[] args) {
        Kattio io = new Kattio(System.in);

        n = io.getInt(); //Height
        m = io.getInt(); //Width
        puzzle = new String[n][m];

        List<String> chars = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String s = io.getWord();
            String[] word = s.split("");
            for (int j = 0; j < m; j++) {
                puzzle[i][j] = word[j];
                chars.add(puzzle[i][j]);
            }
        }

        //prettyPrint();

        HashMap<String, Boolean> map = new HashMap<>();
        int n = io.getInt();
        int ans = 0;
        for (int i = 0; i < n; i++) {

            String word = io.getWord();
            String[] s = word.split("");
            boolean skip = false;

            //Check 
            for (int j = 0; j < word.length(); j++) {
                if (!chars.contains(s[j])) {
                    skip = true;
                }
            }
            
            if (skip)
                continue;

            if (!map.containsKey(word)) {  
                map.put(word, lookup(word));
            }
            
            if (map.get(word)) {
                ans++;
            } 
        }
        
        io.println(ans);
        io.close();
    }

    static boolean lookup(String word) {
        visited = new boolean[n][m];
        String[] s = word.split("");

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (findNext(i, j, visited, s, 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    
    static boolean findNext(int x, int y, boolean[][] visited, String[] s, int pos) {
        if (pos >= s.length)
            return true;

        if (!inrange(x, y))
            return false;

        if (visited[x][y])
            return false;

        if (!puzzle[x][y].equals(s[pos]))
            return false;

        visited[x][y] = true;
        
        if (findNext(x, y + 1, visited, s, pos+1))
            return true;
                
        if (findNext(x, y - 1, visited, s, pos+1))
            return true;
            
        if (findNext(x - 1, y, visited, s, pos+1))
            return true;
            
        if (findNext(x + 1, y, visited, s, pos+1))
            return true;
            
            
        visited[x][y] = false;

        return false;
    }

    static boolean inrange(int i, int j) {
        return i >= 0 && j >= 0 && i < n && j < m;
    }
    
}