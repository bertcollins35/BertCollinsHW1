import java.util.Scanner;

public class StartGame{
    public void start(Boolean test){
        int finalScoreGoal = 10000;
        int numOfPlayers = 1;                  
        int turnScore;                      
        int currentPlayer = 0;              
        int endgamePlayer = -1;    //For whoever reaches the goal first
        String input;
        String[] randomPlayerNames = {"Bert", "John", "Yin", "Braden", "Logan", "Collins", "Kenya", "Emily"};
        Scanner scanner = new Scanner(System.in);   //sets up scanner for user to input from command line
        DiceWork dicework = new DiceWork(); //initializes DiceWork, which is the main control class

        System.out.println("How many points would you like to play in this game? (default=10000): ");
        String scoreInput = scanner.nextLine();     //ask user for max score for game
        if(!scoreInput.isBlank()){              
            finalScoreGoal = Integer.parseInt(scoreInput);
        }

        System.out.println("How many players are going to be playing? (default=1): ");   //Ask how many players playing
        String playersInput = scanner.nextLine();         
        if(!playersInput.isBlank()){                       
            numOfPlayers = Integer.parseInt(playersInput);  
        }
        if(numOfPlayers < 1){     //Check for atleast a single player playing
            numOfPlayers = 1;
        }
        Player[] players = new Player[numOfPlayers];
        for(int i = 1; i <= numOfPlayers; i++){
            System.out.println("What is player #" + i + "'s name?: ");
            String playerName = scanner.nextLine();
            if(playerName.isBlank() && numOfPlayers == 1){        
                players[i - 1] = new Player("Unknown Player");      
            }else if(playerName.isBlank()){                         
                players[i - 1] = new Player(randomPlayerNames[i - 1]);
            }else{
                players[i - 1] = new Player(playerName);
            }
        }

        System.out.print("Hey ");
        for (int i = 0; i < numOfPlayers; i++){
            System.out.print(players[i].getPlayerName());
            if(i < numOfPlayers - 1){       
                System.out.print(", "); 
            }
        }
        System.out.println("! Welcome to Farkle!");

        if(test){
            TestCases tc = new TestCases();
            int num;
            tc.printTestInfo();
            input = scanner.nextLine();             //Test cases for if turned on in Farkle.java
            num = Integer.parseInt(input);
            tc.selectTest(num, players[0].getPlayerName(), dicework);  // The test case only applies to first player
        }else{
            dicework.randomizeDice();
        }

        while (true){      
            turnScore = playTurn(players[currentPlayer], dicework, scanner);
            if(turnScore == -1){
                System.out.println(players[currentPlayer].getPlayerName() + " quit the game. Thanks for playing!");    //Q exits the whole game
                scanner.close();        //check for when a player quits the game
                return;
            }
            players[currentPlayer].addScore(turnScore);     //bank the turn
            printScores(players);                           //show everyone's scores after every turn

            if(endgamePlayer == -1 && players[currentPlayer].getScore() >= finalScoreGoal){ //Check if someone reaches the final goal score
                endgamePlayer = currentPlayer;    
                System.out.println("***** ENDGAME! " + players[currentPlayer].getPlayerName() + " reached " + finalScoreGoal + " points! *****");
                System.out.println("Everyone else gets one more turn to beat them.");
            }

            currentPlayer++;                        //move to the next player
            if(currentPlayer == numOfPlayers){
                currentPlayer = 0;
            }
            if(currentPlayer == endgamePlayer){
                break;          //back to whoever started the endgame, so everyone else has had their last turn
            }

            dicework.clearMeld();           //new dice for each of the player's different turns
            dicework.randomizeDice();
        }

        printWinner(players, endgamePlayer);  //Prints the final winner screen
        scanner.close();        //closes the line reading
    }

    //Plays one player's turn. Returns the points they bank, 0 if they farkle, or -1 if they quit
    public int playTurn(Player player, DiceWork dicework, Scanner scanner){   //Original game loop moved down to factor in multiple players
        int score = 0;
        int hotHandScore = 0;
        Boolean hotHand = false;
        Boolean reRoll = true;    
        String input;
        int[] meld;     //sets up meld array

        System.out.println();
        System.out.printf("========== %s's turn (total points: %d) ==========%n", player.getPlayerName(), player.getScore());  

        while (true){       //loops until the player banks, farkles, or quits

            if(reRoll && dicework.isFarkle(score)){
                dicework.printTable(0);     //detects a farkle on the first roll or after a reroll
                System.out.println("OOOOOF you farkled, no points this turn");     
                return 0;                  
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
            System.out.print(player.getPlayerName() + ", Enter letters of your choice: ");   
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

            if(input.equals("K") || input.equals("k")){    
                System.out.printf("Turn over. %s banked %d points%n", player.getPlayerName(), score);  
                return score;                           
            }
            if(input.equals("Q") || input.equals("q")){    //  Q quits the whole game
                return -1;                                  
            }
            dicework.changeMeld(input); //Switches the melds to whatever user inputted
            meld = dicework.getMeld();          //gets meld to later calculate for score
            score = dicework.computeScore(meld) + hotHandScore;

        }
    }

    //Prints every player's total points
    public void printScores(Player[] players){    //Print the scores along with the table
        System.out.println();
        System.out.println("Current total points:");
        for(int i = 0; i < players.length; i++){
            System.out.printf("    %-10s %d%n", players[i].getPlayerName(), players[i].getScore());
        }
        System.out.println();
    }

    //Finds the winner and prints the end of game banner with everyone's scores
    public void printWinner(Player[] players, int endgamePlayer){    
        Player winner = players[endgamePlayer];     //whoever started the endgame wins unless someone banked more points
        for(int i = 0; i < players.length; i++){
            if(players[i].getScore() > winner.getScore()){
                winner = players[i];
            }
        }
        System.out.println("************************************************************************");
        System.out.println("*  GAME OVER! " + winner.getPlayerName() + " wins with " + winner.getScore() + " points!");
        System.out.println("************************************************************************");
        printScores(players);
    }

    public static Boolean checkValidInputs(String input){
        if(!input.matches("[A-Fa-f]+|[KkQqRr]")){
            return false;
        } else{                     //helper function that checks if a users input is valid
            return true;
        }
    }
}