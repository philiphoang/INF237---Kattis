import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class hw {

    static int gridX;
    static int gridY;

    static int wordsFound;

    public static void main(String[] args) {
        Kattio io = new Kattio(System.in, System.out);

        gridX = io.getInt();
        gridY = io.getInt();
        int wordsFound = 0;

        String[][] grid = new String[gridX][gridY];
        for (int i = 0; i < gridX; i++) {
            String in = io.getWord();
            for (int j = 0; j < gridY; j++) {
                grid[i][j] = in.substring(j, j + 1);
            }
        }

        for (int i = 0; i < gridX; i++) {
            for (int j = 0; j < gridY; j++) {
                System.out.print(grid[i][j]);
            }
            System.out.println();
        }
        System.out.println("-------------------------");

        int words = io.getInt();

        for (int i = 0; i < words; i++) {
            String[] word = io.getWord().split("");


            for (int j = 0; j < word.length; j++) {
                System.out.print(word[j]);
            }
            System.out.println();


            for (int j = 0; j < gridX; j++) {
                for (int k = 0; k < gridY; k++) {
                    if (grid[j][k].equals(word[0])) {
                        System.out.println(gridX + " " + gridY);
                        boolean[][] visited = new boolean[gridX][gridY];
                        explore(grid, visited, word, j, k, 1);
                    }
                }
            }
        }
        System.out.println(wordsFound);

    }

    private static void explore(String[][] grid, boolean[][] visited, String[] word, int i, int j, int wordPos) {
        System.out.println(word[wordPos] + " på plass " + wordPos + " med i: " + i + " , j: " + j);

        if (word.length == wordPos) {
            //System.out.println("ret");
            wordsFound++;
            return;
        }

        visited[i][j] = true;
        ArrayList<Pos> pos = getPossiblePos(i, j);

        System.out.println(pos.size());

        for (Pos p : pos) {

            if (!visited[p.x][p.y]) {
                System.out.println("alive");
                if (grid[p.x][p.y].equals(word[wordPos])) {
                    System.out.println("duck");
                    explore(grid, visited, word, p.x, p.y, wordPos + 1);
                }
            }
        }
    }

    private static ArrayList<Pos> getPossiblePos(int i, int j) {
        System.out.println("ser på " + i + " " + j + " med gridX " + gridX + " gridY " + gridY);
        ArrayList<Pos> list = new ArrayList<>();

        if (i - 1 >= 0) {
            list.add(new Pos(i - 1, j));
        }
        if (j - 1 >= 0) {
            list.add(new Pos(i, j - 1));
        }
        if (i + 1 < gridX) {
            list.add(new Pos(i + 1, j));
        }
        if (j + 1 < gridY) {
            list.add(new Pos(i, j + 1));
        }
        return list;
    }

    private static class Pos {
        int x;
        int y;

        public Pos(int x, int y) {
            this.x = x;
            this.y = y;
        }
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