import java.util.Scanner;

public class Difference {
    public static void main(String args[]) {
        Scanner scan = new Scanner(System.in);
        while (scan.hasNextLong()) {
            long x = scan.nextLong();
            long y = scan.nextLong();
            long difference = calculateDifference(x, y);
            System.out.println(difference);
        }
    }

    public static long calculateDifference(long x, long y) {
        long difference = 0;
        if (x > y) 
            difference = x - y;
        else 
            difference = y - x;       
        return difference;
    }

}