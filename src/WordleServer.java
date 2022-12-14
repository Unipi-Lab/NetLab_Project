import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.lang.reflect.Type;
import java.net.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;

public class WordleServer
{
    static int wordTime = 30000;//30 seconds
    static int maxPlayerAttempts = 12;
    static String jsonFilePath = "C:\\Users\\thump\\Desktop\\usersStats.json";
    static String wordsFilePath = "C:\\Users\\thump\\Desktop\\words.txt";
    static String answer = "";
    static ConcurrentHashMap<String, UserStats> usersStats = new ConcurrentHashMap<>();

    static String[] words;

    //make  words static and check if the word is in the vocabulary ecc
    public static void main(String[] args) throws Exception
    {
        try (var listener = new ServerSocket(59898))
        {
            words = getWordsFromTxtFile();
            answer = words[new Random().nextInt(words.length)];
            System.out.println("The word is " + answer);
            if (new File(jsonFilePath).exists())
            {
                usersStats = loadRegisteredPlayersFromJsonFile();
            }
            var pool = Executors.newFixedThreadPool(20);
            while (true)
            {
                pool.execute(new GameManager(listener.accept()));
            }
        }
    }

    private static String[] getWordsFromTxtFile() throws FileNotFoundException
    {
        Scanner sc = new Scanner(new File(wordsFilePath));
        List<String> lines = new ArrayList<>();
        while (sc.hasNextLine())
        {
            lines.add(sc.nextLine());
        }

        return lines.toArray(new String[0]);

    }

    private static ConcurrentHashMap<String, UserStats> loadRegisteredPlayersFromJsonFile() throws FileNotFoundException
    {
        System.out.println("The game server is running...");
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader(jsonFilePath));
        Type concurrentHashMapType = new TypeToken<ConcurrentHashMap<String, UserStats>>()
        {
        }.getType();
        return gson.fromJson(reader, concurrentHashMapType);
    }

    private static class GameManager implements Runnable
    {
        private final Socket socket;

        GameManager(Socket socket)
        {
            this.socket = socket;
        }

        @Override
        public void run()
        {
            System.out.println("Connected: " + socket);
            boolean isPlayerRegistered = false;
            boolean isPlayerLoggedIn = false;
            boolean isPlayerPlaying = false;
            boolean isPlayerAboutToLogOut = false;
            boolean isPlayerAboutToSendWord = false;
            boolean hasTheGameEnded = false;
            String loggedInPlayer = "";
            int playerAttempts = 0;
            try
            {

                var in = new Scanner(socket.getInputStream());
                var out = new PrintWriter(socket.getOutputStream(), true);
                long startTime = System.currentTimeMillis();
                while (in.hasNextLine())
                {

                    String word = in.nextLine();
                    long currentTime = System.currentTimeMillis();
                    if (currentTime - startTime >= wordTime)
                    {
                        hasTheGameEnded = false;
                        isPlayerAboutToSendWord = false;
                        isPlayerPlaying = false;
                        playerAttempts = 0;
                        startTime = currentTime;
                        answer = words[new Random().nextInt(words.length)];
                        out.println("The word has changed");
                        System.out.println("The new word is " + answer);
                    }
                    else
                    {

                        if (isPlayerLoggedIn)
                        {
                            switch (word)
                            {
                                case "logout" ->
                                {
                                    isPlayerAboutToLogOut = true;
                                    out.println("Type your username to log out");
                                }
                                case "playWORDLE" ->
                                {
                                    if (!hasTheGameEnded)
                                    {
                                        isPlayerPlaying = true;
                                        out.println("You are playing WORDLE");
                                    }
                                    else
                                    {
                                        out.println("You have reached the maximum number of attempts");
                                    }
                                }
                                case "sendWord" ->
                                {
                                    if (isPlayerPlaying)
                                    {
                                        if (playerAttempts >= maxPlayerAttempts)
                                        {
                                            isPlayerPlaying = false;
                                            out.println("You have reached the maximum number of attempts. You are not playing WORDLE anymore");
                                        }
                                        else
                                        {
                                            isPlayerAboutToSendWord = true;
                                            out.println("Type a word");
                                        }
                                    }
                                    else
                                    {
                                        out.println("You are not playing WORDLE");
                                    }
                                }
                                case "sendMeStatistics" ->
                                {
                                    if (!isPlayerPlaying)
                                    {
                                        usersStats.get(loggedInPlayer).printStats(out);
                                    }
                                    else
                                    {
                                        out.println("You need to finish the current game");
                                    }
                                }
                                case "share" ->
                                {
                                    if (hasTheGameEnded)
                                    {
                                        String lastGameResult = usersStats.get(loggedInPlayer).getLastGameResult();
                                        int lastGameAttempts = usersStats.get(loggedInPlayer).getLastGameAttempts();
                                        String ipAddress = "230.0.0.0";
                                        String udpNotification = loggedInPlayer + " stats => Last game result = " + lastGameResult + "| Last game attempts = " + lastGameAttempts;
                                        sendUDPMessage(udpNotification, ipAddress, 4321);
                                        out.println("Sharing complete");

                                    }
                                    else
                                    {
                                        out.println("You have not played WORDLE yet");
                                    }
                                }
                                case "showMeSharing" ->
                                {
                                    out.println("Sharing other people last games");
                                }
                                default ->
                                {
                                    if (isPlayerAboutToSendWord)
                                    {
                                        List<String> wordsList = Arrays.asList(words);

                                        if (wordsList.contains(word))
                                        {
                                            isPlayerAboutToSendWord = false;
                                            playerAttempts++;
                                            StringBuilder b = getSuggestions(word);
                                            if (isVictory(word))
                                            {
                                                hasTheGameEnded = true;
                                                out.println(b + " YOU WON");
                                                isPlayerPlaying = false;
                                                usersStats.get(loggedInPlayer).addGamePlayed();
                                                usersStats.get(loggedInPlayer).addGameWon();
                                                usersStats.get(loggedInPlayer).addAttemptsOfCurrentWin(playerAttempts);
                                                usersStats.get(loggedInPlayer).setLastGameResult("Victory");
                                                updateJSONFile();
                                            }
                                            else
                                            {
                                                if (playerAttempts == maxPlayerAttempts)
                                                {
                                                    hasTheGameEnded = true;
                                                    isPlayerPlaying = false;
                                                    out.println(b + " YOU LOST");
                                                    usersStats.get(loggedInPlayer).addGamePlayed();
                                                    usersStats.get(loggedInPlayer).setWinStreakLength();
                                                    usersStats.get(loggedInPlayer).setLastGameResult("Loss");
                                                    updateJSONFile();
                                                }
                                                else
                                                {
                                                    out.println(b);
                                                }
                                            }
                                        }
                                        else
                                        {
                                            isPlayerAboutToSendWord = false;
                                            out.println("The word is not in the vocabulary");
                                        }
                                    }
                                    else if (isPlayerAboutToLogOut)
                                    {
                                        if (word.equals(loggedInPlayer))
                                        {
                                            isPlayerAboutToLogOut = false;
                                            isPlayerLoggedIn = false;
                                            isPlayerPlaying = false;
                                            out.println("You have been logged out");
                                        }
                                        else
                                        {
                                            out.println("This is not your username");
                                        }
                                    }
                                    else
                                    {
                                        out.println("Logged in. Please type a valid command");
                                    }
                                }
                            }
                        }
                        else
                        {
                            String[] usernamePassword = word.split(" ");
                            if (usernamePassword.length != 2)
                            {
                                out.println("Invalid command please type username password");
                            }
                            else
                            {
                                String username = usernamePassword[0];
                                String password = usernamePassword[1];
                                if (!isPlayerRegistered)
                                {
                                    if (password.equals(" "))
                                    {
                                        out.println("Password is empty");
                                    }
                                    else if (usersStats.containsKey(username))
                                    {
                                        out.println("User is already registered. Please log in");
                                        isPlayerRegistered = true;
                                    }
                                    else
                                    {

                                        usersStats.put(username, new UserStats(password));
                                        updateJSONFile();
                                        isPlayerRegistered = true;
                                        out.println("Player registered. Now log in");
                                    }
                                }
                                else
                                {
                                    if (usersStats.containsKey(username) && password.equals(usersStats.get(username).getPassword()))
                                    {
                                        isPlayerLoggedIn = true;
                                        loggedInPlayer = username;
                                        out.println("Player logged in");
                                    }
                                    else
                                    {
                                        out.println("Wrong username or password. Please try again");
                                    }
                                }
                            }
                        }

                    }
                }
            } catch (Exception e)

            {
                System.out.println("Error:" + socket);
            } finally

            {
                try
                {
                    socket.close();
                } catch (IOException ignored)
                {
                }
                System.out.println("Closed: " + socket);
            }
        }

        public static void sendUDPMessage(String message, String ipAddress, int port) throws IOException
        {
            DatagramSocket socket = new DatagramSocket();
            InetAddress group = InetAddress.getByName(ipAddress);
            byte[] msg = message.getBytes();
            DatagramPacket packet = new DatagramPacket(msg, msg.length, group, port);
            socket.send(packet);
            socket.close();
        }

        private boolean isVictory(String word)
        {
            int greenLettersCount = 0;
            for (int i = 0; i < word.length(); i++)
            {
                char c = word.charAt(i);
                if (answer.charAt(i) == c)
                {
                    greenLettersCount++;
                }
            }
            return greenLettersCount == answer.length();
        }

        private static StringBuilder getSuggestions(String word)
        {
            StringBuilder b = new StringBuilder();
            HashMap<Character, Integer> answerMap = new HashMap<>();
            countAnswerLetters(answerMap);
            decrementGreenLettersCount(word, answerMap);
            buildSuggestions(word, b, answerMap);
            return b;
        }

        private static void countAnswerLetters(HashMap<Character, Integer> answerMap)
        {
            for (int i = 0; i < answer.length(); i++)
            {
                answerMap.merge(answer.charAt(i), 1, Integer::sum);
            }
        }

        private static void decrementGreenLettersCount(String word, HashMap<Character, Integer> answerMap)
        {
            for (int i = 0; i < word.length(); i++)
            {
                char c = word.charAt(i);
                if (answer.charAt(i) == c)
                {
                    answerMap.merge(c, -1, Integer::sum);
                }
            }
        }

        private static void buildSuggestions(String word, StringBuilder b, HashMap<Character, Integer> answerMap)
        {
            for (int i = 0; i < word.length(); i++)
            {
                char c = word.charAt(i);
                if (answer.charAt(i) == c)
                {
                    b.append('+');
                }
                else if (answer.contains(Character.toString(c)) && answerMap.get(c) > 0)
                {
                    answerMap.merge(c, -1, Integer::sum);
                    b.append('?');
                }
                else
                {
                    b.append('X');
                }
            }
        }

        private static void updateJSONFile() throws IOException
        {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(usersStats);
            FileWriter writer = new FileWriter(jsonFilePath);
            writer.write(json);
            writer.close();
        }
    }
}
