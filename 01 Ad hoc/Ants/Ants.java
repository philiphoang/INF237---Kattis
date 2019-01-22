import java.util.Scanner; 

public class asd {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int testcases = scan.nextInt(); //Number of testcases

        for (int i = 0; i < testcases; i++) {
            int polelength = scan.nextInt(); //Length of the pole 
            int n = scan.nextInt(); //Number of ants residing on the pole 

            //Initialize variable 
            int midmost = polelength;
            int outermost = polelength/2; 
            int mid = polelength/2;
            int shortestTime = polelength;
            int latestTime = -1; 

            for (int j = 0; j < n; j++) {
                int antpos = scan.nextInt(); //Position of an ant
                
                int currentMid = Math.abs(mid - antpos); //How far the ant is from the middle

                // Case 1. Find the ant that is nearest the middle 
               
                if (currentMid < midmost) {
                    midmost = currentMid;
                    shortestTime = shortest(antpos, polelength);
                }

                // Case 2. Find the ant that is furthest away from the middle
                latestTime = latest(antpos, polelength, latestTime);
                
            }

            System.out.println(shortestTime + " " + latestTime);
        }
    }

    static int shortest(int antpos, int polelength) {
        int middle = polelength/2; 
        if (antpos < middle)
            return antpos;
        else 
            return Math.abs(polelength - antpos);
    }

    static int latest(int antpos, int polelength, int bestTime) {
        int current  = antpos;
        if (antpos < polelength/2)
            current = Math.abs(polelength - antpos);
            
        if (current > bestTime)
            bestTime = current;
        return bestTime;
  
    }
}

