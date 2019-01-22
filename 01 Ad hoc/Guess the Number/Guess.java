import java.util.Scanner;

public class Guess {
    public static void main(String[] args) {
        int min = 1; 
        int max = 1000; 
        int answer = (min + max) / 2; 
        Scanner scan = new Scanner(System.in);
        System.out.println(answer);
        
        while (true) {
            String guess = scan.nextLine();
            if (guess.equals("higher")) {
                min = answer; 
                answer = (min + max + 1) / 2; 
            }
            else if (guess.equals("lower")) {
                max = answer;
                answer = (min + max) / 2;
            }    
            else if (guess.equals("correct")) {
                break;
            }

            System.out.println(answer);
        }
        scan.close();
    }
}