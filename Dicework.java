//TODO: DO isFarkle(), checkMeld(), and computeScore()

import java.util.Random;

public class Dicework{
    

    private final int[] dices = new int[6];
    private final int[] meld = {0,0,0,0,0,0};

    public Boolean isFarkle(){
        //check if current dice that roll are farkledized
        return false;
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
        if(meldLetter.contains("A")){
            this.meld[0] = this.dices[0];
        }
        if(meldLetter.contains("B")){
            this.meld[1] = this.dices[1];
        }
        if(meldLetter.contains("C")){
            this.meld[2] = this.dices[2];
        }
        if(meldLetter.contains("D")){
            this.meld[3] = this.dices[3];
        }
        if(meldLetter.contains("E")){
            this.meld[4] = this.dices[4];
        }
        if(meldLetter.contains("F")){
            this.meld[5] = this.dices[5];
        }
    }

    public int[] getMeld(){
        return this.meld;
    }

    public void checkMeld(String input){
        //do the check meld, so that you can put it back
    }

    public void printTable(int score){
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
         System.out.printf("Score is: %s%n", score);
    }

    public int computeScore(){
        //compute overall score by checking melds
        return 0;
    }
}