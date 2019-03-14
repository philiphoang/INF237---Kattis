import java.util.Arrays;
import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.OutputStream;
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

class NestedDolls {
    public static void main(String[] args) {
        Kattio io = new Kattio(System.in);
        
        int testCases = io.getInt();

        for (int i = 0; i < testCases; i++) {
            List<Doll> dollList = new ArrayList<>();

            int m = io.getInt();
           
            // Add dolls to a list
            for (int j = 0; j < m; j++) {
                int w = io.getInt();
                int h = io.getInt();
    
                dollList.add(new Doll(w, h));
            }

            // Sort the dolls 
            dollList.sort( 
                (thisDoll, thatDoll) -> thisDoll.compareTo(thatDoll));

            for (Doll d : dollList) {
                System.out.println("(" + d.getWidth() + ", " + d.getHeight() + ")");
            }
            // Dilworth's Theorem 
            int longest = 0;        
            for (int d = 0; d < dollList.size(); d++) {
                int numDolls = 1;    

                Doll currentDoll = dollList.get(d); //Compare this doll to the other 
            
                for (int k = d + 1; k < dollList.size(); k++) {
                    // Check anti-chain: Longest non-increasing subsequence
                    Doll otherDoll = dollList.get(k);
                    
                    if (currentDoll.getHeight() >= otherDoll.getHeight()) {
                        numDolls++;
                    } 

                }
                
                if (numDolls > longest) {
                    longest = numDolls;
                }
            }
            System.out.println(longest);              
        }
    }
}

class Doll {
    private int width;
    private int height; 

    public Doll(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height; 
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int compareTo(Doll thatDoll) {
        //Sort ascending on width
        //Sort descending on height
        if (this.width > thatDoll.width)
            return 1;
        else if (this.width < thatDoll.width) 
            return -1;
        else 
            if (this.height < thatDoll.height)
                return 1;
            else if (this.height > thatDoll.height)
                return -1;

        return 0;
    }
 }
