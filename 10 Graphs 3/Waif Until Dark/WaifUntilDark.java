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

public class WaifUntilDark {
    static int numberOfNodes;
    public static void main(String args[]) {
        Kattio io = new Kattio(System.in);
        int n = io.getInt(); //Number of children
        int m = io.getInt(); //Number of toys
        int p = io.getInt(); //Number of toy categories

        numberOfNodes = 2 + n + m + p;
        Map<Integer, Boolean> toyHasCategory = new HashMap<>();

        int source = 0; //Source
        int sink = numberOfNodes - 1;

        Graph graph = new Graph(numberOfNodes);
        for (int i = 1; i <= n; i++) { //Starting at 1
            int k = io.getInt(); //Number of toys willing to play with
            
            graph.addEdge(0, i, 1); //Source to children

            for (int j = 0; j < k; j++) {
                int toy = io.getInt();
                toy += n;
                toyHasCategory.put(toy, false); 
                graph.addEdge(i, toy, 1); //Children to toy
            }
        }

        for (int l = 1; l <= p; l++) {
            int numberOfToys = io.getInt();
            int category = l + n + m;

            for (int j = 0; j < numberOfToys; j++) {
                int toy = io.getInt();
                toy += n;
                toyHasCategory.replace(toy, false, true);
                graph.addEdge(toy, category, 1); //Toy to category
            }
            
            int r = io.getInt(); //Number of usage
            graph.addEdge(category, sink, r); //Category to sink
        }

        //Connect toys without categories to sink
        for (Integer i : toyHasCategory.keySet()) {
            if (toyHasCategory.get(i) == false) {
                graph.addEdge(i, sink, 1);

            }
        }

        //prettyPrint(graph);
        
        System.out.println(run(graph, source, sink));
    }

    static int run(Graph graph, int source, int target) {
        int u, v;

        int parent[] = new int[numberOfNodes];
        int max_flow = 0;

        while (bfs(graph, source, target, parent)) {
            int path_flow = Integer.MAX_VALUE;

            for (v = target; v != source; v = parent[v]) {
                u = parent[v]; 
                path_flow = Math.min(path_flow, graph.getEdge(u, v).capacity); 
            } 
  
            for (v = target; v != source; v = parent[v]) {
                u = parent[v]; 
                graph.getEdge(u, v).capacity -= path_flow;
                graph.getEdge(v, u).capacity += path_flow;
            }

            max_flow += path_flow; 

        }
        return max_flow; 

    }

    static boolean bfs(Graph graph, int source, int target, int[] parent) {
        LinkedList<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[numberOfNodes];
        queue.add(source);
        visited[source] = true; 
        parent[source] = -1;

        while (!queue.isEmpty()) {
            int v = queue.poll();


            for (Edge e : graph.adjacencyList(v)) {
                //.get() asks for index, but we give it a value
                if (!visited[e.target] && graph.getEdge(v, e.target).capacity > 0){
                    queue.add(e.target);
                    parent[e.target] = v;
                    visited[e.target] = true;
                }
            }
        }
        
        return (visited[target] == true);
    }

    static void prettyPrint(Graph graph) {
        for (int i = 0; i < numberOfNodes; i++) {
            System.out.print(i + ": ");
            for (Edge v : graph.adjacencyList(i)) {
                System.out.print(v.target + ", ");
            }
            System.out.println();
        }

    }
}

class Graph { 
    List<Edge>[] graph;

    Graph(int numberOfNodes) {
        graph = new List[numberOfNodes];
        for (int i = 0; i < numberOfNodes; i++) {
            graph[i] = new ArrayList<>();
        }
    }

    void addEdge(int source, int target, int capacity) {
        graph[source].add(new Edge(target, capacity));
        graph[target].add(new Edge(source, 0));
    }


    Edge getEdge(int source, int target) {
        for (Edge e : graph[source]) {
            if (e.target == target) {
                return e;
            }
        } 
        return null;
    }

    List<Edge> adjacencyList(int i) {
        return graph[i];
    }
}

class Edge {
    int target;
    int capacity; 

    public Edge(int target, int capacity) {
        this.target = target;
        this.capacity = capacity;
    }

}

