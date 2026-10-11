/*
 This program implements a singleplayer version of the board game Farkle. 
 CPSC 224-01 Fall 2026
 Programming Assignment #1
 Sources: crandallFarkle.cpp
 **Run using java farkle.java**

 @author Bert Collins
 @version v2.7 9/23/26

*/
public class Farkle{
    public static void main(String args[]){



                        /* ************************************ */
                        /*         Turn on/off test mode        */
                                    Boolean test = false;
                        /* ************************************ */




        StartGame start = new StartGame();  //starts game :)
        printCredentials();
        start.start(test);
    }

    public static void printCredentials(){
        System.out.println("************************************************************************");
        System.out.println("*                      Zag Farkle by Bert Collins!                     *");
        System.out.println("************************************************************************");
    }
}


