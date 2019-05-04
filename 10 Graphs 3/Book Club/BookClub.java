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

public class BookClub {
    static List<Node> club;
    static int N, M;
    static int[] matchR;
    static int[] matchL;
    static boolean[] visited;
    public static void main(String args[]) {
        Kattio io = new Kattio(System.in);
        N = io.getInt(); //Number of people
        M = io.getInt(); //Number of "declarations of interest"

        club = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            club.add(new Node(i));
        }

        for (int i = 0; i < M; i++) {
            int A = io.getInt();
            int B = io.getInt();

            Node personA = club.get(A);
            personA.neighbours.add(club.get(B));
        }

        if (run() == N) 
            io.println("YES");  
        else 
            io.println("NO");

        io.close();
    }

    static int run() {
        int result = 0;

        matchR = new int[N];
        matchL = new int[N];
        visited = new boolean[N]; 
        
        Arrays.fill(matchR, -1); //Initialize all to not having a book
        Arrays.fill(matchL, -1); 

        boolean works = true;
        while (works) {
            works = false;
            Arrays.fill(visited, false); 
            for (int i = 0; i < N; i++) {
                if (matchL[i] < 0) {
                    works |= bpm(club.get(i));
                }
            }
        }

        for (int i = 0; i < N; i++) {
            if (matchL[i] != -1) {
                result++;
            }
        }

        return result;
    }

    static boolean bpm(Node v) {
        if (visited[v.value]) {
            return false;
        }
        visited[v.value] = true;

        for (Node u : v.neighbours) {
            if (matchR[u.value] == -1 || bpm(club.get(matchR[u.value]))) {
                matchL[v.value] = u.value;
                matchR[u.value] = v.value;
                return true;
            }  
        }

        return false;      
    }
   
}

class Node {
    int value;
    List<Node> neighbours;

    public Node(int value) {
        this.value = value;
        this.neighbours = new ArrayList<>();
    }
}
