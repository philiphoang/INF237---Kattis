import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.util.List;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.OutputStream;
import java.util.Map;
import java.util.HashMap;

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

public class BreakingBad {
    static boolean isPossible = true;
    public static void main(String[] args) {
        Kattio io = new Kattio(System.in);
        int N = io.getInt();

        HashMap<String, Vertex> graph = new HashMap<>();

        for (int i = 0; i < N; i++) {
            String label = io.getWord();
            Vertex v = new Vertex(label);
            v.setColor(true);
            graph.put(label, v);
        }

        int M = io.getInt(); 
        for (int j = 0; j < M; j++) {
            String item1 = io.getWord();
            String item2 = io.getWord();
            Vertex v1 = graph.get(item1);
            Vertex v2 = graph.get(item2);

            addEdge(v1, v2);   
        }
      
        for (String s : graph.keySet()) {
            Vertex v = graph.get(s);
            dfs(v);
        }

        List<String> walter = new ArrayList<>();
        List<String> jesse = new ArrayList<>();
        if (isPossible) {
            for (String s : graph.keySet()) {
                Vertex v = graph.get(s);
                if (v.getColor()) 
                    walter.add(v.getLabel());
                else
                    jesse.add(v.getLabel());
            }
            
            for (String w : walter)
                System.out.print(w + " ");

            System.out.println();
            for (String j : jesse)
                System.out.print(j + " ");
        }
        else 
            System.out.println("impossible");

        
    }

    static void dfs(Vertex v) {

        v.setVisited(true);
        if (v.getAdjacencyList() != null) {
            for (Vertex w : v.getAdjacencyList()) { //neighbours to v 
                if (!w.isVisited()) {
                    w.setColor(!v.getColor());
                    dfs(w);
                }
                else if (w.getColor() == v.getColor()) {
                    isPossible = false;
                    break;
                }
            }
        }
    }
    
    static void addEdge(Vertex v1, Vertex v2) {
        if (v1.getAdjacencyList() == null) {
            List<Vertex> v1adjacency = new ArrayList<Vertex>();
            v1.setAdjacencyList(v1adjacency);
        }
        v1.getAdjacencyList().add(v2);

        if (v2.getAdjacencyList() == null) {
            List<Vertex> v2adjacency = new ArrayList<Vertex>();
            v2.setAdjacencyList(v2adjacency);
        }
        v2.getAdjacencyList().add(v1);
    }
}

class Vertex {
    private String label;
    private List<Vertex> adjacencyList;
    private boolean color;
    private boolean visited;

    public Vertex(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }

    public void setAdjacencyList(List<Vertex> adjacencyList) {
        this.adjacencyList = adjacencyList;
    }

    public List<Vertex> getAdjacencyList() {
        return this.adjacencyList;
    }

    public boolean getColor() {
        return this.color;
    }

    public void setColor(boolean color) {
        this.color = color;
    }

    public boolean isVisited() {
        return this.visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
}