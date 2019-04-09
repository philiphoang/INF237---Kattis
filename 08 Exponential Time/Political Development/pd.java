import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.OutputStream;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

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

public class pd {
    static long min = Integer.MAX_VALUE;
    public static void main(String[] args) {
        Kattio io = new Kattio(System.in);

        long N = io.getLong(); //Number of members in the party
        int K = io.getInt(); 

        List<Vertex> graph = new ArrayList<Vertex>();


        for (int i = 0; i < N; i++) {
            graph.add(new Vertex(i));
        }

        for (int i = 0; i < N; i++) {

            Vertex v = graph.get(i);
            long D = io.getLong();
            for (int j = 0; j < D; j++) {
                int Dw = io.getInt();
                if (Dw == 0)
                    continue;
                else {Vertex w = graph.get(Dw);
                
                addEdge(v, w);
                }
            }
        }

        ArrayList<Vertex> X = new ArrayList<Vertex>();
        ArrayList<Vertex> R = new ArrayList<Vertex>();
        ArrayList<Vertex> P = new ArrayList<Vertex>(graph);

        bronkerb(R, P, X);
        io.println(min);
        io.close();
    }

    static void bronkerb(ArrayList<Vertex> R, ArrayList<Vertex> P, ArrayList<Vertex> X) {
        if (P.size() == 0 && X.size() == 0) {
            if (min > R.size()) {
                min = R.size();
            }
        }

        ArrayList<Vertex> P1 = new ArrayList<Vertex>(P); 

        Vertex u = getMaxDegreeVertex(union(P, X)); 
        if (u == null) {
            return;
        }
        P = removeNbrs(P, u); 

        for (Vertex v : P) {
            R.add(v);
            bronkerb(R, intersect(P1, v.getNeighbours()), intersect(X, v.getNeighbours()));
            P1.remove(v);
            X.add(v);     
        }
    }

    static void addEdge(Vertex v, Vertex w) {
        v.addNeighbour(w);
        w.addNeighbour(v);
    }

    // Removes the neigbours 
    static ArrayList<Vertex> removeNbrs(ArrayList<Vertex> arlFirst, Vertex v) { 
        ArrayList<Vertex> arlHold = new ArrayList<Vertex>(arlFirst); 
        arlHold.removeAll(v.getNeighbours()); 
        return arlHold; 
    } 

    static Vertex getMaxDegreeVertex(ArrayList<Vertex> g) { 
        Collections.sort(g); 
        if (g.size() == 0) {
            return null;
        }
        return g.get(g.size()-1);
    }


    //Intersect
    static ArrayList<Vertex> intersect(ArrayList<Vertex> firstList, ArrayList<Vertex> secondList) {
        ArrayList<Vertex> intersectionSet = new ArrayList<>(firstList);
        intersectionSet.retainAll(secondList);
        return intersectionSet;
    }
    
    //Union 
    static ArrayList<Vertex> union(ArrayList<Vertex> firstList, ArrayList<Vertex> secondList) {
        ArrayList<Vertex> unionSet = new ArrayList<>(firstList);
        unionSet.addAll(secondList);
        return unionSet;
    }

}

class Vertex implements Comparable<Vertex>{
    int label;
    int degree;
    ArrayList<Vertex> adjList;
    boolean visited;

    public Vertex(int label) {
        this.label = label;
        adjList = new ArrayList<>();
    }

    void addNeighbour(Vertex y) {
        this.adjList.add(y); 
            if (!y.getNeighbours().contains(y)) { 
                y.getNeighbours().add(this); 
                y.degree++; 
            } 
        this.degree++; 
    }

    public void removeNbr(Vertex y) {
        this.adjList.remove(y); 
        if (y.getNeighbours().contains(y)) { 
            y.getNeighbours().remove(this); 
            y.degree--; 
        } 
        this.degree--; 

    } 

    void setLabel(int label) {
        this.label = label;
    }

    int getLabel() {
        return label;
    } 

    ArrayList<Vertex> getNeighbours() {
        return adjList;
    }

    public int getDegree() {
        return degree; 
    } 

    public void setDegree(int degree) {
        this.degree = degree; 
    } 


    @Override 
    public int compareTo(Vertex o) {
        if (this.degree < o.degree) {
            return -1; 
        } 
        if (this.degree > o.degree) {
            return 1;
        } 
        return 0; 
    } 
}