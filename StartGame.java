import java.util.Scanner;

public class StartGame{
    public void start(){
        int score = 0;
        String input;
        Scanner scanner = new Scanner(System.in);   //sets up scanner for user to input from command line
        DiceWork dicework = new DiceWork(); //initializes DiceWork, which is the main control class
        int[] meld;     //sets up meld array
        dicework.randomizeDice();

        if(dicework.isFarkle()){
            dicework.printTable(0);     //detects farkle
            System.out.println("OOOOOF you farkled, try again another time");
            scanner.close();
            return;
        }
        while (true){       //always looping unless user quits

            dicework.printTable(score);
            System.out.print("Enter letters for your choice(s): ");
            input = scanner.nextLine(); 
            if(!checkValidInputs(input)){
                System.out.println("Invalid input, try again."); //Checks so that user doesn't input anything
                continue;                                           //they arent supposed to be inputting
            }

            if(input.equals("K") || input.equals("k") || 
               input.equals("Q") || input.equals("q")){
                System.out.printf("Round over. Total score is: %s%n",score);    //Quits
                break;
            }
            dicework.changeMeld(input); //Switches the melds to whatever user inputted
            meld = dicework.getMeld();          //gets meld to later calculate for score
            score = dicework.computeScore(meld);
        }
        scanner.close();        //closes the line reading
    }
    public static Boolean checkValidInputs(String input){
        if(!input.matches("[A-Fa-f]+|[KkQq]")){
            return false;
        } else{                     //helper function that checks if a users input is valid
            return true;
        }
    }
}