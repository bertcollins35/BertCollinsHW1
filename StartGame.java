import java.util.Scanner;

public class StartGame{
    public void start(){
        int score = 0;
        String input;
        Scanner scanner = new Scanner(System.in);
        Dicework dicework = new Dicework();
        int[] meld;
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
                System.out.println("Invalid input, try again."); 
                continue;
            }
            if(input.equals("K") || input.equals("k") || 
               input.equals("Q") || input.equals("q")){
                System.out.printf("Round over. Total score is: %s%n",score);
                break;
            }
            dicework.changeMeld(input);
            meld = dicework.getMeld();
            score = dicework.computeScore(meld);
        }
        scanner.close();
    }
    public static Boolean checkValidInputs(String input){
        if(!input.matches("[A-Fa-f]+|[KkQq]")){
            return false;
        } else{
            return true;
        }
    }
}