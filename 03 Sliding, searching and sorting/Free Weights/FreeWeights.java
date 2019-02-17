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
public class FreeWeights {
    public static void main(String[] args) {
        Kattio io = new Kattio(System.in);

        List<Integer> row1 = new ArrayList<>();
        List<Integer> row2 = new ArrayList<>();

        
        int n = io.getInt();
        for (int i = 0; i < n; i++) {
            row1.add(io.getInt());
        }

        for (int i = 0; i < n; i++) {
            row2.add(io.getInt());
        }

 
        List<Integer> sortedRows = new ArrayList<>(row1);
        sortedRows.addAll(row2);
        sortedRows.add(0); //If all weights are already paired, might not even lift 

        Collections.sort(sortedRows);

        //Binary Search on lists
        
        int lo = -1;
        int hi = sortedRows.size();
        int current = 0;

        while (lo + 1 < hi) {
            int mid = (lo+hi)/2;
            int midWeightFromSorted = sortedRows.get(mid);

            boolean paired = checkPair(row1, midWeightFromSorted) && checkPair(row2, midWeightFromSorted);
            if (paired) {
                hi = mid;
                current = midWeightFromSorted;
            } else {
                lo = mid;
            }
        } 

        System.out.println(current);
    }

    public static boolean checkPair(List<Integer> row, int currentWeight) {
        int holding = -1; //Currently holding no weight

        for (int i = 0; i < row.size(); i++) {
            if (row.get(i) > currentWeight)
                if (holding == -1) { 
                    holding = row.get(i); //Hold this weight
                }
                else {
                    if (holding != row.get(i)) { 
                        return false;
                    }
                    holding = -1;
                }
            }
        return holding == -1;
    }
}