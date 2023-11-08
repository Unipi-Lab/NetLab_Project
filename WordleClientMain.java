import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WordleClientMain implements Runnable
{

    static String clientConfigFilePath = "clientconfig.txt";
    static List<String> udpNotifications = new ArrayList<>();
    static String groupIp;
    static int serverPort;
    static int udpPort;


    public static void main(String[] args) throws Exception
    {
        String serverAddress = "localhost";
        initializeParametersFromConfigFile();

        try (var socket = new Socket(serverAddress, serverPort))
        {
            System.out.println("Please insert username password to register");
            var scanner = new Scanner(System.in);
            var in = new Scanner(socket.getInputStream());
            var out = new PrintWriter(socket.getOutputStream(), true);
            String response;
            while (scanner.hasNextLine())
            {
                out.println(scanner.nextLine());
                response = in.nextLine();
                System.out.println(response);
                if (response.equals("Player logged in"))
                {
                    Thread t = new Thread(new WordleClientMain());
                    t.start();
                }
                if (response.equals("Sharing other people last games"))
                {
                    for (String udpNotification : udpNotifications)
                    {
                        System.out.println(udpNotification);
                    }
                }
            }
        }
    }

    @Override
    public void run()
    {
        try
        {
            receiveUDPMessage();
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void receiveUDPMessage() throws IOException
    {
        byte[] buffer = new byte[1024];
        MulticastSocket socket = new MulticastSocket(udpPort);
        InetAddress group = InetAddress.getByName(groupIp);
        socket.joinGroup(group);
        while(true)
        {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);
            String msg = new String(packet.getData(), packet.getOffset(), packet.getLength());
            udpNotifications.add(msg);
        }
    }

    private static void initializeParametersFromConfigFile()
    {
        try (BufferedReader br = new BufferedReader(new FileReader(clientConfigFilePath)))
        {
            String line;
            List<String> lines = new ArrayList<>();
            while ((line = br.readLine()) != null)
            {
                String[] split = line.split("=");
                lines.add(split[1]);
            }
            serverPort = Integer.parseInt(lines.get(0));
            groupIp = lines.get(1);
            udpPort= Integer.parseInt(lines.get(2));

        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

}