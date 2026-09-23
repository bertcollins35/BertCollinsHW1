import java.util.Scanner;

public class StartGame{
    public void start(){
        int score = 0;
        String input;
        Scanner scanner = new Scanner(System.in);
        Dicework dicework = new Dicework();
        dicework.randomizeDice();
        if(dicework.isFarkle()){
            dicework.printTable(0);
            System.out.println("OOOOOF you farkled, try again another time");
            scanner.close();
            return;
        }
        while (true){
            dicework.printTable(score);
            System.out.print("Enter letters for your choice(s): ");
            input = scanner.nextLine();
            if(!checkValidInputs(input)){
                continue;
            }
            dicework.checkMeld(input);
            if(input.equals("K") || input.equals("k") || 
               input.equals("Q") || input.equals("q")){
                System.out.printf("Round over. Total score is: %s%n",score);
                break;
            }
            dicework.changeMeld(input);
            score = dicework.computeScore();
        }
        scanner.close();
    }
    public static Boolean checkValidInputs(String input){
        if(!input.matches("[ABCDEFKQabcdefkq]")){
            return false;
        } else{
            return true;
        }
    }
}