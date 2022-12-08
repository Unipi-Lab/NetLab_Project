import java.util.ArrayList;
import java.util.List;

public class UserStats
{
    private int gamesPlayed;
    private int gamesWon;
    private int winCounter;
    private int lastWinStreakLength;
    private int maxWinStreakLength;
    private final List<Integer> attemptsPerWin;


    public UserStats()
    {
        this.gamesPlayed = 0;
        this.gamesWon = 0;
        this.lastWinStreakLength = 0;
        this.maxWinStreakLength = 0;
        this.attemptsPerWin = new ArrayList<>();
    }

    public int getGamesPlayed()
    {
        return this.gamesPlayed;
    }

    public void addGamePlayed()
    {
        this.gamesPlayed++;
    }

    public int getGamesWon()
    {
        return this.gamesWon;
    }

    public void addGameWon()
    {
        this.gamesWon++;
        this.winCounter++;
    }

    public int getLastWinStreakLength()
    {
        return this.lastWinStreakLength;
    }

    public int getMaxWinStreakLength()
    {
        return this.maxWinStreakLength;
    }

    public List<Integer> getAttemptsPerWin()
    {
        return this.attemptsPerWin;
    }

    public void setWinStreakLength()
    {
        this.lastWinStreakLength = this.winCounter;
        if (maxWinStreakLength < this.lastWinStreakLength)
        {
            this.maxWinStreakLength = this.lastWinStreakLength;
        }
    }


    public void printStats()
    {
        System.out.println("Games played: " + this.gamesPlayed);
        System.out.println("Games won: " + this.gamesWon);
        System.out.println("Last win streak length: " + this.lastWinStreakLength);
        System.out.println("Max win streak length: " + this.maxWinStreakLength);
        System.out.println("Attempts per win: " + this.attemptsPerWin);
    }
}
