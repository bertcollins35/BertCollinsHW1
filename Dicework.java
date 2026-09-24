import java.util.Arrays;        //Libs used for array work and random number generation
import java.util.Random;

public class DiceWork{
    

    private final int[] dices = new int[6];         //dice and meld arrays
    private final int[] meld = {0,0,0,0,0,0};

    public Boolean isFarkle(){
        return this.computeScore(this.dices) == 0;      //Farkles if the overall score ends up being 0
    }
    public void randomizeDice(){
        int[] possibledices = {1,2,3,4,5,6};
        int randomIndex;
        Random random = new Random();

        for(int i = 0; i < 6; i++){                             //assigns each dice a random num 1-6
            randomIndex = random.nextInt(possibledices.length);
            this.dices[i] = possibledices[randomIndex];
        }
    }

        //Changes meld based on what the user inputed. Also makes sure user 
        //is able to put meld back to change score
        public void changeMeld(String meldLetter){
        if(meldLetter.contains("A") || meldLetter.contains("a")){      
            if(this.meld[0] != 0){
                this.meld[0] = 0;
            } else {
                this.meld[0] = this.dices[0];
            }
        }
        if(meldLetter.contains("B") || meldLetter.contains("b")){     
            if(this.meld[1] != 0){
                this.meld[1] = 0;
            } else {
                this.meld[1] = this.dices[1];
            }
        }
        if(meldLetter.contains("C") || meldLetter.contains("c")){      
            if(this.meld[2] != 0){
                this.meld[2] = 0;
            } else {
                this.meld[2] = this.dices[2];
            }
        }
        if(meldLetter.contains("D") || meldLetter.contains("d")){    
            if(this.meld[3] != 0){
                this.meld[3] = 0;
            } else {
                this.meld[3] = this.dices[3];
            }
        }
        if(meldLetter.contains("E") || meldLetter.contains("e")){      
            if(this.meld[4] != 0){
                this.meld[4] = 0;
            } else {
                this.meld[4] = this.dices[4];
            }
        }
        if(meldLetter.contains("F") || meldLetter.contains("f")){    
            if(this.meld[5] != 0){
                this.meld[5] = 0;
            } else {
                this.meld[5] = this.dices[5];
            }
        }
    }

    public int[] getMeld(){
        return this.meld;   //Lets the user get the meld info by returning private member variable
    }

    //prints table using the Aaron crandall printing style in his version of Farkle
    public void printTable(int score){
        System.out.println();
        System.out.print("Hand: ");
        for(int i = 0; i < 6; i++){
            System.out.printf("%s ",this.dices[i]);
        }
        System.out.println();
        System.out.println("*************************** Current hand and meld *******************");
        System.out.println(" Die   Hand |   Meld");
        System.out.println("------------+---------------");
        char option = 'A';
        for(int i = 0; i < 6; i++ ) {
            System.out.printf(" ( %s )    ",option);
            if(this.dices[i] != 0) {
                System.out.print(dices[i]);
            } else {
                System.out.print(" ");
            }
            System.out.print(("   |     "));
            if(meld[i] != 0) {
                System.out.print(meld[i]);
            } else {
                System.out.print(" ");
            }
            System.out.println();
            option++;
        }
        System.out.println("------------+---------------");

        System.out.println(" (K) BanK Meld & End Round");
        System.out.println(" (Q) Quit game");
        System.out.println();
        System.out.printf("Score is: %s%n", score);
        System.out.println();
    }

    //Checks for duplicates (aka triples or more) to help with scoring
    //Returns an array [number for first possible triple, how many times its seen, num2 possible triple, # of times seen]
    private int[] checkForDuplicates(int[] arr){   //takes in meld array                            
        int[] returnedDuplicate = {0,0,0,0};
        int[][] nums = {{1,0},{2,0},{3,0},{4,0},{5,0},{6,0}};   //to go through every possible triple 1-6
                                                                //First index of every array: number 
                                                                //Second index of every array: # of times seen in dice roll
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 6; j++){
                if(arr[j] == nums[i][0]){               //Goes through every dice number and counts how much
                    nums[i][1]++;                       //each number appears in it
                }
            }
        }

        int dice1 = 0;
        int dice1Occurences = 0;            //seperates the two possible triple dice combinations
        int dice2 = 0;                      //using variables, with *Occurences showing how much of that num showed
        int dice2Occurences = 0;
        for(int i = 0; i < 6;i++){
            if(nums[i][1] >= 3){
                dice1 = nums[i][0];             //checks if each number occured 3 or more times; indicating a triple
                dice1Occurences = nums[i][1];   //and assigns to respective variables 
                break;
            }
        }
        for(int i = 0; i < 6; i++){
            if(nums[i][1] >= 3 && nums[i][0] != dice1){
                dice2 = nums[i][0];                     //repeats process with a second possible pair
                dice2Occurences = nums[i][1];
                break;
            }
        }
        
        returnedDuplicate[0] = dice1;
        returnedDuplicate[1] = dice1Occurences;     //assigns values and returns
        returnedDuplicate[2] = dice2;
        returnedDuplicate[3] = dice2Occurences;

        return returnedDuplicate;
    }

    public int computeScore(int[] meld){
        int[] meldClone = new int[6];   //assign new array in the heap in order to use comparisons properly
        Boolean keepGoing = false;      //and not mess up memory addresses
        int score = 0;

        for(int i = 0; i < 6; i++){
            meldClone[i] = meld[i];     //copy from meld
        }
        Arrays.sort(meldClone);         //sort list for easier manipularity

        for(int i = 0; i < 6; i++){
            if(meldClone[i] != (i+1)){
                keepGoing = false;          //detect a straight flush
                break;
            }                           
            keepGoing = true;
        }
        if(keepGoing){
            return 1000;
        }
       
        keepGoing = false;
        for(int i = 0; i < 6; i += 2){                                
            if(meldClone[i] != meldClone[i+1] || meldClone[i] == 0){
                keepGoing = false;                  //Detect three pairs
                break;
            }
            if(i > 0 && meldClone[i] == meldClone[i-2]){             
                keepGoing = false;
                break;
            }
            keepGoing = true;
        }                                                              
        if(keepGoing){      //keepGoing boolean to signify if straight flush or three pairs occured
            return 750;
        }

        int[] duplicates = checkForDuplicates(meldClone);
        int[][] listDuplicates = {{duplicates[0], duplicates[1]},   //get possible triples from helper function
                                  {duplicates[2], duplicates[3]}};

        for(int i = 0; i < 2; i++){
            if(listDuplicates[i][0] == 1){          //For special triple with ones
                if(listDuplicates[i][1] >= 3){
                    score = score + 1000;
                }
                if(listDuplicates[i][1] > 3){
                    score = score + (100 * (listDuplicates[i][1] - 3)); 
                }
            }
        }

       for(int i = 0; i < 2; i++){
            for(int j = 2; j < 7; j++){
                if(listDuplicates[i][0] == j){      //for any other number triple (2-6)
                    score = score + (100 * listDuplicates[i][0] * (listDuplicates[i][1] - 2));
                }
            }
       }

        boolean onesTripled = false;                                   
        boolean fivesTripled = false;           //check ones or fives have been triples                      
        for(int i = 0; i < 2; i++){
            if(listDuplicates[i][0] == 1){
                onesTripled = true;                               
            }
            if(listDuplicates[i][0] == 5){
                fivesTripled = true;                                  
            }
        }

        for(int i = 0; i < 6; i++){
            if(meldClone[i] == 1 && !onesTripled){                    
                score += 100;       //check for remaining ones that count toward score
            }
        }

        for(int i = 0; i < 6; i++){
            if(meldClone[i] == 5 && !fivesTripled){     //check for remaining fives that count toward score                
                score += 50;
            }
        }

        return score;       //return the final score!!!!! (finally)
    }
}