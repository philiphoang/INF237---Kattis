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

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

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
    public static void main(String[] args) {
        Kattio io = new Kattio(System.in);
        List<Pokemon> pokemons = new ArrayList<>();
        //Map<Pokemon> distances = new HashMap<>();

        int N = io.getInt();
        pokemons.add(new Pokemon(0, 0, "Start"));

        for (int pokemon = 0; pokemon < N; pokemon++) {
            int r = io.getInt();
            int c = io.getInt();
            String name = io.getWord(); 

            pokemons.add(new Pokemon(r, c, name));
        }

        N++;
        for (int i = 0; i < N; i++) {
            Pokemon first = pokemons.get(i);

            for (int j = 0; j < N; j++) {
                Pokemon second = pokemons.get(j);
                if (first == second)
                    continue;
                else {
                    int distance = Math.abs(first.sum - second.sum);
                    addDistanceToPokemons(first, second, distance);
                }
            }
        }

        //printValues(pokemons);
        
    }

    static void addDistanceToPokemons(Pokemon a, Pokemon b, int distance) {
        a.distanceToOther.put(b, distance);
        b.distanceToOther.put(a, distance);
    }

    static void printValues(ArrayList<Pokemon> pokemons) {
        for (Pokemon p : pokemons) {
            System.out.print(p.name + " {");
            for (Integer i : p.distanceToOther.values()) {
                System.out.print(i + ", ");
            }
            System.out.print("}\n");
        }
    }
}

class Pokemon {
    String name;
    int x;
    int y;
    int sum; 
    //ArrayList<Integer> distanceToOther;
    Map<Pokemon, Integer> distanceToOther;

    public Pokemon(int x, int y, String name) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.sum = x + y;
        //distanceToOther = new ArrayList<>();
        distanceToOther = new HashMap<>();
    }
}