public class TestCases{

    public void printTestInfo(){
        System.out.println("************************************************************************");
        System.out.println("*      You are now in test mode. Please select what you want to test    *");
        System.out.println("************************************************************************");

        System.out.println();
        System.out.println("Available Dice combinations for testing:");
        System.out.println();
        System.out.println("1. 1,2,3,4,5,6 (Straight)");
        System.out.println("2. 2,6,3,2,4,3 (Farkle)");
        System.out.println("3. 3,3,3,4,6,6 (Triple 3's)");
        System.out.println("4. 1,6,3,2,4,3 (Single 1)");

        System.out.println();
        System.out.println("Other tests");
        System.out.println("5. Print players name");
    }
    public void selectTest(int num, String name, DiceWork dicework){
        switch(num){
            case 1:
                dicework.changeDice(1,2,3,4,5,6);
                break;
            case 2:
                dicework.changeDice(2,6,3,2,4,3);
                break;
            case 3:
                dicework.changeDice(3,3,3,4,6,6);
                break;
            case 4:
                dicework.changeDice(1,6,3,2,4,3);
                break;
            case 5:
                System.out.println("Testing name that was set properly: " + name);
                dicework.randomizeDice();
                break;
            default:
                System.out.println("Please enter a valid test");
                dicework.randomizeDice();
        }   
    }
}