import java.util.Scanner;

public class Oddities {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int size = scan.nextInt();

        int[] list = new int[size];

        for (int i = 0; i < list.length; i++)
            list[i] = scan.nextInt();

        for (int x : list) {
            guess(x);
        }
    }

    public static void guess(int num) {
        if (num % 2 == 0) 
            System.out.println(num + " is even");
        else 
            System.out.println(num + " is odd");
    }
}

