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

public class PokemonGoGo {
    public static void main(String []av) {
        Kattio io = new Kattio(System.in);
        int N = io.getInt();
        Stop start = new Stop(0, 0);
        Map<Stop, Integer> map = new HashMap<>(); //Index the stops 
        Stop [] stops = new Stop[N];
        int numStops = 0;

        Map<String, Integer> pokemons = new HashMap<>();
        int numPokemons = 0;
        int [] findAtStops = new int[20];
        for (int i = 0; i < N; i++) {
            int r = io.getInt();
            int c = io.getInt();

            // index stops
            Stop stopLoc = new Stop(r, c); //Create a new stop
            Integer stop = map.get(stopLoc); //If it already exist
            if (stop == null) {
                stop = numStops;
                map.put(stopLoc, numStops++);
                stops[stop] = stopLoc;
            }

            // index pokemons
            String pokemonName = io.getWord();
            Integer pokemon = pokemons.get(pokemonName); //If the pokemon already exists
            if (pokemon == null) {
                pokemon = numPokemons;
                pokemons.put(pokemonName, numPokemons++);
            }
            findAtStops[stop] |= (1 << pokemon); //Find the pokemon at the stop
        }

        int [][] dp = solve(stops, N);

        // a "full tour" may occur without visiting all stops
        // as long we have all pokemons.
        int best = Integer.MAX_VALUE;
        for (int i = 0; i < dp.length; i++) {
            int found = 0;
            for (int j = 0; j < numStops; j++) if (((1 << j) & i) != 0)
                found |= findAtStops[j];

            //If we have not found all pokemons yet
            if (found != (1 << numPokemons) - 1)
                continue;

            //If we have found all pokemons
            for (int j = 0; j < dp[i].length; j++)
                best = Math.min(best, dp[i][j] + calculateDistance(stops[j], start)); //Need to find the best distance back to start
        }

        System.out.println(best);
    }

    static int calculateDistance(Stop s1, Stop s2) {
        return Math.abs(s1.r - s2.r) + Math.abs(s1.c - s2.c);
    }

    static int[][] solve(Stop[] stops, int N) {
        int P = 1 << N; //Length of a subset (number of bits): e.g., N = 3, P = 000

        int[][] dist = new int[N][N];
        int[][] dp = new int[P][N];

        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) {
                if (i != j)
                    dist[j][i] = calculateDistance(stops[j], stops[i]); //Calculate distance between two stops
                else {
                    dist[j][i] = 0; //If it is the stop itself
                }
            }

        for (int[] row : dp)
            Arrays.fill(row, Integer.MAX_VALUE/2);
            
        for (int i = 0; i < N; i++)
            dp[1<<i][i] = Math.abs(stops[i].r - 0) + Math.abs(stops[i].c - 0); //Initialize distance from start to all other stops

            
        for (int S = 0; S < P; S++) {
            for (int v = 0; v < N; v++) {
                int removeV = (S & ~(1 << v)); //Remove vertex v from the set

                if ((S & 1 << v) > 0) {
                    int newDist = Integer.MAX_VALUE/2;

                    for (int u = 0; u < N; u++) {
                        if (v != u && (S & 1 << u) > 0)  
                            if (newDist > dp[removeV][u] + dist[v][u]) {
                                newDist = dp[removeV][u] + dist[v][u]; 
                            }
                    }
                    if (Integer.MAX_VALUE/2 > newDist) {
                        dp[S][v] = newDist; //Update the distance in the subset
                    }
                }
            }
        }

        return dp;
    }
}

class Stop {
    int r;
    int c;

    Stop(int r, int c) {
        this.r = r;
        this.c = c;
    }
}

