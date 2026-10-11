import java.util.Scanner;

public class StartGame{
    public void start(Boolean test){
        int score = 0;
        int hotHandScore = 0;
        Boolean hotHand = false;
        Boolean reRoll = false;
        String input;
        String name;
        Scanner scanner = new Scanner(System.in);   //sets up scanner for user to input from command line
        DiceWork dicework = new DiceWork(); //initializes DiceWork, which is the main control class
        int[] meld;     //sets up meld array

        System.out.println("Please enter a player name: ");
        name = scanner.nextLine();                          //asks user for name
        System.out.println("Hey " + name + "! Welcome to Farkle!");

        if(test){
            TestCases tc = new TestCases();
            int num;
            tc.printTestInfo();
            input = scanner.nextLine();             //Test cases for if turned on in Farkle.java
            num = Integer.parseInt(input);
            tc.selectTest(num, name, dicework);
        }else{
            dicework.randomizeDice();
        }
        
        if(dicework.isFarkle(0)){
                dicework.printTable(0);     //detects farkle after the first roll
                System.out.println("OOOOOF you farkled, try again another time");
                System.out.println("Your score is: 0");
                scanner.close();
                return;
            }

        while (true){       //always looping unless user quits

            if(reRoll && dicework.isFarkle(score)){
                dicework.printTable(0);     //detects any farkle after a reroll
                System.out.println("OOOOOF you farkled, try again another time");
                System.out.println("Your score is: 0");
                scanner.close();
                return;
            }
            reRoll = false;
            hotHand = false;
            if(dicework.isHotHand()){
                System.out.println("***** HOT HAND! ****");     //Checks for a hot hand and true if occurs
                System.out.println("Would you like to roll 6 new dice, or bank and end your turn?");    
                System.out.println("************************");
                hotHand = true;
            }

            dicework.printTable(score);
            System.out.print(name + ", Enter letters of your choice: ");    
            input = scanner.nextLine(); 
            if(!checkValidInputs(input)){
                System.out.println("Invalid input, try again."); //Checks so that user doesn't input anything
                continue;                                           //they arent supposed to be inputting
            }
            
            if(input.equals("R") || input.equals("r")){ //new reroll option
                if(hotHand){
                    meld = dicework.getMeld();             
                    hotHandScore = dicework.computeScore(meld) + hotHandScore;
                    dicework.clearMeld();               //clears meld and randomizes dice while keeping score
                    dicework.randomizeDice();
                    hotHand = false;
                }
                dicework.rerollDice();      //rerolls dice
                reRoll = true;
            }

            if(input.equals("K") || input.equals("k") || 
               input.equals("Q") || input.equals("q")){
                System.out.printf("Round over. Total score is: %s%n",score);    //Quits
                break;
            }
            dicework.changeMeld(input); //Switches the melds to whatever user inputted
            meld = dicework.getMeld();          //gets meld to later calculate for score
            score = dicework.computeScore(meld) + hotHandScore;

        }
        scanner.close();        //closes the line reading
    }
    public static Boolean checkValidInputs(String input){
        if(!input.matches("[A-Fa-f]+|[KkQqRr]")){
            return false;
        } else{                     //helper function that checks if a users input is valid
            return true;
        }
    }
}