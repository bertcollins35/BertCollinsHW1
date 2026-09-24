

import java.util.Arrays;
import java.util.Random;

public class Dicework{
    

    private final int[] dices = new int[6];
    private final int[] meld = {0,0,0,0,0,0};

    public Boolean isFarkle(){
        return this.computeScore(this.dices) == 0;
    }
    public void randomizeDice(){
        int[] possibledices = {1,2,3,4,5,6};
        int randomIndex;
        Random random = new Random();

        for(int i = 0; i < 6; i++){
            randomIndex = random.nextInt(possibledices.length);
            this.dices[i] = possibledices[randomIndex];
        }
    }

    public int[] getDice(){
        return this.dices;
    }

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
        return this.meld;
    }

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

    private int[] checkForDuplicates(int[] arr){                               
        int[] returnedDuplicate = {0,0,0,0};
        int[][] nums = {{1,0},{2,0},{3,0},{4,0},{5,0},{6,0}};

        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 6; j++){
                if(arr[j] == nums[i][0]){
                    nums[i][1]++;
                }
            }
        }

        int dice1 = 0;
        int dice1Occurences = 0;
        int dice2 = 0;
        int dice2Occurences = 0;
        for(int i = 0; i < 6;i++){
            if(nums[i][1] >= 3){
                dice1 = nums[i][0];
                dice1Occurences = nums[i][1];
                break;
            }
        }
        for(int i = 0; i < 6; i++){
            if(nums[i][1] >= 3 && nums[i][0] != dice1){
                dice2 = nums[i][0];
                dice2Occurences = nums[i][1];
                break;
            }
        }
        
        returnedDuplicate[0] = dice1;
        returnedDuplicate[1] = dice1Occurences;
        returnedDuplicate[2] = dice2;
        returnedDuplicate[3] = dice2Occurences;

        return returnedDuplicate;
    }

    public int computeScore(int[] meld){
        int[] meldClone = new int[6];
        Boolean keepGoing = false;
        int score = 0;

        for(int i = 0; i < 6; i++){
            meldClone[i] = meld[i];
        }
        Arrays.sort(meldClone);

        for(int i = 0; i < 6; i++){
            if(meldClone[i] != (i+1)){
                keepGoing = false;
                break;
            }                           //straight flush
            keepGoing = true;
        }
        if(keepGoing){
            return 1000;
        }
       
        keepGoing = false;
        for(int i = 0; i < 6; i += 2){                                
            if(meldClone[i] != meldClone[i+1] || meldClone[i] == 0){
                keepGoing = false;                  //Three pairs
                break;
            }
            if(i > 0 && meldClone[i] == meldClone[i-2]){             
                keepGoing = false;
                break;
            }
            keepGoing = true;
        }                                                              
        if(keepGoing){
            return 750;
        }

        int[] duplicates = checkForDuplicates(meldClone);
        int[][] listDuplicates = {{duplicates[0], duplicates[1]},
                                  {duplicates[2], duplicates[3]}};

        for(int i = 0; i < 2; i++){
            if(listDuplicates[i][0] == 1){
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
                if(listDuplicates[i][0] == j){
                    score = score + (100 * listDuplicates[i][0] * (listDuplicates[i][1] - 2));
                }
            }
       }

        boolean onesTripled = false;                                   
        boolean fivesTripled = false;                                
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
                score += 100;
            }
        }

        for(int i = 0; i < 6; i++){
            if(meldClone[i] == 5 && !fivesTripled){                  
                score += 50;
            }
        }

        return score;
    }
}