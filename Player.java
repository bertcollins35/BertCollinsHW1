public class Player{
    private String playerName;   
    private int score;           

    public Player(String playerName){
        this.playerName = playerName;
        this.score = 0;
    }

    public String getPlayerName(){
        return this.playerName;
    }

    public int getScore(){      
        return this.score;
    }

    public void addScore(int points){  
        this.score = this.score + points;
    }
}