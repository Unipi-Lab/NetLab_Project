import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.util.Scanner;

public class WordleClientMain implements Runnable
{
    public static void main(String[] args) throws Exception
    {
        try (var socket = new Socket("localhost", 59898))
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
        MulticastSocket socket = new MulticastSocket(4321);
        InetAddress group = InetAddress.getByName("230.0.0.0");
        socket.joinGroup(group);
        for (int i = 0; i < 10; i++)
        {
            System.out.println("Waiting for multicast message...");
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);
            String msg = new String(packet.getData(), packet.getOffset(), packet.getLength());
            System.out.println("[Multicast UDP message received] >> " + msg);
        }
        socket.leaveGroup(group);
        socket.close();
    }

}