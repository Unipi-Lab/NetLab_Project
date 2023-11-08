import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class UserStats
{
    String password;
    private int gamesPlayed;
    private int gamesWon;
    private int winCounter;
    private int lastWinStreakLength;
    private int maxWinStreakLength;

    private String lastGameResult;


    private final List<Integer> attemptsPerWin;


    public UserStats(String password)
    {
        this.password = password;
        this.gamesPlayed = 0;
        this.gamesWon = 0;
        this.lastWinStreakLength = 0;
        this.maxWinStreakLength = 0;
        this.attemptsPerWin = new ArrayList<>();
    }

    public String getPassword()
    {
        return password;
    }

    public void addGamePlayed()
    {
        this.gamesPlayed++;
    }

    public void addGameWon()
    {
        this.gamesWon++;
        this.winCounter++;
    }

    public void addAttemptsOfCurrentWin(int attempts)
    {
        this.attemptsPerWin.add(attempts);
    }

    public void setWinStreakLength()
    {
        this.lastWinStreakLength = this.winCounter;
        if (maxWinStreakLength < this.lastWinStreakLength)
        {
            this.maxWinStreakLength = this.lastWinStreakLength;
        }
        this.winCounter = 0;
    }

    public void setLastGameResult(String result)
    {
        this.lastGameResult = result;
    }

    public String getLastGameResult()
    {
        return this.lastGameResult;
    }

    public int getLastGameAttempts()
    {
        return this.attemptsPerWin.get(this.attemptsPerWin.size() - 1);
    }


    public void printStats(PrintWriter out)
    {
        out.println("Games played: " + this.gamesPlayed + " -- Games won: " + this.gamesWon + " -- Last win streak length: " + this.lastWinStreakLength + " -- Max win streak length: " + this.maxWinStreakLength + " -- Attempts per win: " + this.attemptsPerWin);
    }
}
