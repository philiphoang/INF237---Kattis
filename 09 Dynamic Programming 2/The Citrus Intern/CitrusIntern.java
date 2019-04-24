import java.io.*;
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

    //Alle disse er lister som er like langt som vertices
    //in //Best cost with v in it 
    //up
    //down //down represent the node under it 

    //Leaves:
    //1. Finn roten først (indegree 0)
    //kjør dfs(v) på root
        //set in(v) = w(v) 
        //up(v) = 0
        //down(v) = infinity 
        //for u in adj(v):
            //dfs(u)

    //Non-leaves
    //In(v) = w(v)
        //men w(v) har vi allerede kalkulert
        //in(v) += sum(up(u))
            //where u is a child of v
        //outup(v) += sum(min(in(u),outdown(u))
        //outdown(v) += sum(min(in(u), outdown(u)) 
    
        //if in(u) > down(u)
            //for alle u

        //delta(v) = min
            //max[in(u)-outdown(u),0]
    
public class CitrusIntern {
    static int N;
    static ArrayList<Member> graph;
    static long[] cost;
    static long[] in, up, down;
    public static void main(String[] args) {
        Kattio io = new Kattio(System.in);
        graph = new ArrayList<>();
        N = io.getInt();   
        in = new long[N];
        up = new long[N];
        down = new long[N];

        
        cost = new long[N];

        boolean[] isChild = new boolean[N];

        for (int i = 0; i < N; i++) {
            long c = io.getLong();
            graph.add(new Member(i, N));
            cost[i] = c;
            int subs = io.getInt();

            for (int j = 0; j < subs; j++) {
                Member m = graph.get(i);
                int subId = io.getInt();
                m.children.add(subId);
                isChild[subId] = true;
            }
        }
    
        int root = 0;
        for (int i = 0; i < N; i++) {
            if (!isChild[i]) {
                root = i;
                break;
            }
        }

        dfs(root);

        if (in[root] > down[root]) {
            System.out.println(down[root]);
        }
        else {
            System.out.println(in[root]);
        }
    }


    static void dfs(int v) {
        in[v] = cost[v];
        up[v] = 0;
        down[v] = Integer.MAX_VALUE;
        
        long minsteDifferansen = Long.MAX_VALUE;
        for (int u : graph.get(v).children) {
            dfs(u);
            in[v] += up[u]; 
            up[v] += Math.min(in[u], down[u]);
    
            long diff = in[u] - down[u];
            
            if (minsteDifferansen > diff) 
                minsteDifferansen = diff;
        }
        down[v] = up[v];
        if (minsteDifferansen >= 0)
            down[v] += minsteDifferansen;
    
    }
}

class Member {
    
    ArrayList<Integer> children;
    int node;

    public Member(int node, int N) {
        this.node = node;

        children = new ArrayList<>();
    }
}