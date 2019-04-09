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
import java.util.Scanner;


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

public class KnightsInFen {
    static String[][] answer = 
    {
        {"1","1","1","1","1"},
        {"0","1","1","1","1"},
        {"0","0"," ","1","1"},
        {"0","0","0","0","1"},
        {"0","0","0","0","0"}
    };

    static final int SIZE = 5;
    static int best;
    static String[][] board = new String[SIZE][SIZE];
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        int N = reader.nextInt();
        reader.nextLine();

        for (int test = 0; test < N; test++) {
            best = 11;
            for (int i = 0; i < SIZE; i++) {
                String line = reader.nextLine();
                String[] s = line.split("");
                for (int j = 0; j < SIZE; j++) {
                    board[i][j] = s[j];
                }
            }
           
            //Begin here
            Knight first = null;
            for (int i = 0; i < SIZE; ++i) {
                for (int j = 0; j < SIZE; ++j) {
                    if (board[i][j].equals(" ")) {
                        first = new Knight(i, j, 0);
                    }
                }
            }

            runAlgorithm(first);
            if (best >= 11) 
                System.out.println("Unsolvable in less than 11 move(s).");
            else 
                System.out.println("Solvable in " + best + " move(s).");
            
        }
        reader.close();
    }

    static void runAlgorithm(Knight current) {
        if (current.x == 2 && current.y == 2) {
            if (checkAnswer()) {
                if (best > current.moves) 
                    best = current.moves;
            }
        }

        if (current.moves < 10) {
            //UpLeft corner (left)
            int nextX = current.x - 2;
            int nextY = current.y - 1;
            if (checkValidPosition(nextX, nextY)) {
                Knight nextKnight = new Knight(nextX, nextY, current.moves+1);
                swap(current, nextKnight);
                runAlgorithm(nextKnight);
                swap(current, nextKnight);
            }

            //UpLeft corner (up)
            nextX = current.x - 1;
            nextY = current.y - 2;
            if (checkValidPosition(nextX, nextY)) {
                Knight nextKnight = new Knight(nextX, nextY, current.moves+1);
                swap(current, nextKnight);
                runAlgorithm(nextKnight);
                swap(current, nextKnight);
            }

            //UpRight corner (up)
            nextX = current.x - 2;
            nextY = current.y + 1;
            if (checkValidPosition(nextX, nextY)) {
                Knight nextKnight = new Knight(nextX, nextY, current.moves+1);
                swap(current, nextKnight);
                runAlgorithm(nextKnight);
                swap(current, nextKnight);
            }

            //UpRight corner (right)
            nextX = current.x + 1;
            nextY = current.y - 2;
            if (checkValidPosition(nextX, nextY)) {
                Knight nextKnight = new Knight(nextX, nextY, current.moves+1);
                swap(current, nextKnight);
                runAlgorithm(nextKnight);
                swap(current, nextKnight);
            }

            //DownRight corner (right)
            nextX = current.x + 1;
            nextY = current.y + 2;
            if (checkValidPosition(nextX, nextY)) {
                Knight nextKnight = new Knight(nextX, nextY, current.moves+1);
                swap(current, nextKnight);
                runAlgorithm(nextKnight);
                swap(current, nextKnight);
            }

            //DownRight corner (down)
            nextX = current.x + 2;
            nextY = current.y + 1;
            if (checkValidPosition(nextX, nextY)) {
                Knight nextKnight = new Knight(nextX, nextY, current.moves+1);
                swap(current, nextKnight);
                runAlgorithm(nextKnight);
                swap(current, nextKnight);
            }

            //DownLeft corner (down)
            nextX = current.x + 2;
            nextY = current.y - 1;
            if (checkValidPosition(nextX, nextY)) {
                Knight nextKnight = new Knight(nextX, nextY, current.moves+1);
                swap(current, nextKnight);
                runAlgorithm(nextKnight);
                swap(current, nextKnight);
            }
        
            //DownLeft corner (left)
            nextX = current.x - 1;
            nextY = current.y + 2;
            if (checkValidPosition(nextX, nextY)) {
                Knight nextKnight = new Knight(nextX, nextY, current.moves+1);
                swap(current, nextKnight);
                runAlgorithm(nextKnight);
                swap(current, nextKnight);
            }
        }
    }

    static boolean checkValidPosition(int xPos, int yPos) {
        return (xPos >= 0 && xPos < SIZE &&
                yPos >= 0 && yPos < SIZE);
    }

    static boolean checkAnswer() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (!board[i][j].equals(answer[i][j]))
                    return false;
            }
        }
        return true;
    }

    static void swap(Knight current, Knight other) {
        String temp = board[current.x][current.y];
        board[current.x][current.y] = board[other.x][other.y];
        board[other.x][other.y] = temp;
    }
}

class Knight {
    int x;
    int y;
    int moves;

    public Knight(int x, int y, int moves) {
        this.x = x;
        this.y = y;
        this.moves = moves;
    }
}