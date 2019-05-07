import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

public class hidden {

    static int gridX;
    static int gridY;

    static HashMap<String, Boolean> wordsFound;
    static int answer;

    static String[][] grid;
    static List<String> list;

    public static void main(String[] args) {
        Kattio io = new Kattio(System.in, System.out);

        gridX = io.getInt();
        gridY = io.getInt();
        wordsFound = new HashMap<>();
        answer = 0;

        list = new ArrayList<>();

        grid = new String[gridX][gridY];
        for (int i = 0; i < gridX; i++) {
            String s = io.getWord();
            String[] in = s.split("");
            for (int j = 0; j < gridY; j++) {
                grid[i][j] = in[j];
                list.add(grid[i][j]);
            }
        }


        int words = io.getInt();

        for (int i = 0; i < words; i++) {
            String s = io.getWord();
            String[] word = s.split("");

            boolean skip = false;
            for (int j = 0; j < word.length; j++) {
                if (!list.contains(word[j])) {
                    skip = true;
                    break;
                }
            }


            if(skip) continue;

            if (!wordsFound.containsKey(s)) {
                wordsFound.put(s, solve(word));
            }

            if (wordsFound.get(word))
                answer++;
        }
        System.out.println(answer);

    }

    private static Boolean solve(String[] word) {
        boolean[][] visited = new boolean[gridX][gridY];

        for (int j = 0; j < gridX; j++) {
            for (int k = 0; k < gridY; k++) {
                if (find(j, k, visited, word, 0))
                    return true;
            }
        }
        return false;
    }

    private static boolean find(int j, int k, boolean[][] visited, String[] word, int pos) {

        if (pos >= word.length)
            return true;
        if (!inRange(j, k))
            return false;

        if (!grid[j][k].equals(word[pos]))
            return false;

        if (visited[j][k])
            return false;

        visited[j][k] = true;

        if (find(j+1, k, visited, word, pos+1)) {
            return true;
        }
        if (find(j-1, k, visited, word, pos+1)) {
            return true;
        }
        if (find(j, k+1, visited, word, pos+1)) {
            return true;
        }
        if (find(j, k-1, visited, word, pos+1)) {
            return true;
        }

        visited[j][k] = false;
        return false;
    }

    private static boolean inRange(int i, int j) {
        return i >= 0 && j >= 0 && i < gridX && j < gridY;
    }

    static class Kattio extends PrintWriter {

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
}