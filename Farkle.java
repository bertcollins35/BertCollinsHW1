import java.util.Scanner;

public class Farkle{
    public static void main(String args[]){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Would you like to start the game?");
        StartGame start = new StartGame();
        String decision = scanner.nextLine();
        if(decision.equals("Yes") || decision.equals("yes")){
            start.start();
        }else {
            System.out.println("Thanks you for your time");
        }
        scanner.close();
    }
}
