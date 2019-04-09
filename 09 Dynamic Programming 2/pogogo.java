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

public class pogogo { 
    static int[][] distBetweenTwoMons; //This is the weighted array
    static int[][] dist;
    public static void main(String args[]) {
        Kattio io = new Kattio(System.in);
        int N = io.getInt();

        List<Pokemon> pokemons = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            int r = io.getInt();
            int c = io.getInt();
            String name = io.getWord();

            pokemons.add(new Pokemon(r, c));
        }

        Pokemon start = new Pokemon(0, 0);
        pokemons.add(start);
        Collections.sort(pokemons);

        distBetweenTwoMons = new int[N+1][N+1];
        dist = new int[N+1][N+1];

        for (int i = 0; i < pokemons.size(); i++) {
            for (int j = 0; j < pokemons.size(); j++) {
                
                int distance =  Math.abs(pokemons.get(i).value - pokemons.get(j).value);
                
                distBetweenTwoMons[i][j] = distance;
                dist[i][j] = Integer.MAX_VALUE;
            }
        }

        
        for (int i = 0; i < N+1; i++) {
            for (int j = 0; j < N+1; j++) {
                System.out.print(distBetweenTwoMons[i][j]+ " ");
            }
            System.out.println();
        }

        dist[0][N] = 0;
        Queue<Pokemon> queue = new PriorityQueue<>();
        queue.add(start);

        
        while(!queue.isEmpty()) {
            Pokemon v = queue.poll();
            
            for (int i = 0; i < N+1; i++) {
                Pokemon u = pokemons.get(i);
                int weight = distBetweenTwoMons[v.x][i];
                if (dist[u.x][u.y] > dist[v.x][v.y] + weight) {
                    dist[u.x][u.y] = dist[v.x][v.y] + weight;
                    queue.add(new Pokemon(u.x, u.y));
                }
            }
        }
        
        System.out.println();

        for (int i = 0; i < N+1; i++) {
            for (int j = 0; j < N+1; j++) {
                System.out.print(dist[i][j]+ " ");
            }
            System.out.println();
        }
    }

}


class Pokemon implements Comparable<Pokemon>{
    String name;
    int x;
    int y;
    int value; 
    //ArrayList<Integer> distanceToOther;
    Map<Pokemon, Integer> distanceToOther;

    public Pokemon(int x, int y) {
        this.x = x;
        this.y = y;
        this.value = x + y;
        //distanceToOther = new ArrayList<>();
        distanceToOther = new HashMap<>();
    }

    @Override 
    public int compareTo(Pokemon other) {
       if (this.x < other.x) {
           return 1;
       }
       else if (this.x > other.x) {
           return -1;
       }
       else {
           if (this.y < other.y) {
               return 1;
           }
           else {
               return -1;
           }
       }
    }

    

}