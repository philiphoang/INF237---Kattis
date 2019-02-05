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

public class Islands {
    static int rows;
    static int columns;
    static boolean[][] visited;
    public static void main(String[] args) {
        Kattio io = new Kattio(System.in, System.out);       

        rows = io.getInt();
        columns = io.getInt();

        String[][] island = new String[rows][columns]; 
        visited = new boolean[rows][columns];


        for (int i = 0; i < rows; i++) {
            String[] s = io.getWord().split("");

            for (int j = 0; j < columns; j++) {
                island[i][j] = s[j];
            }
        }
        
        int counter = 0; 

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (island[i][j].equals("L") && !visited[i][j]) {
                    //run dfs on island[i][j]
                    counter++;
                    findIslands(island, i, j);
                }
            }
        }

        System.out.println(counter);

    }

    static void findIslands(String[][] island, int x, int y) {
        visited[x][y] = true;
      
        if (y + 1 < columns)
            if (!island[x][y + 1].equals("W") && !visited[x][y + 1]) //Right
                findIslands(island, x, y + 1);
               
        if (y - 1 >= 0) 
            if (!island[x][y - 1].equals("W") && !visited[x][y - 1]) //Left
                findIslands(island, x, y - 1);
                    
        if (x - 1 >= 0)
            if (!island[x - 1][y].equals("W") && !visited[x - 1][y]) //Up
                findIslands(island, x - 1, y);
        
        if (x + 1 < rows)
            if (!island[x + 1][y].equals("W") && !visited[x + 1][y]) //Down
                findIslands(island, x + 1, y);
    }
}