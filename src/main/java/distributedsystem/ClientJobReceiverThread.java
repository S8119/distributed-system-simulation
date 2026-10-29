package distributedsystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientJobReceiverThread extends Thread
{
    private final Socket clientSocket;
    private static int nextIdNumber;

    public ClientJobReceiverThread (Socket clientSocket)
    {
        this.clientSocket = clientSocket;
    }

    public void run()
    {
        try (
                clientSocket;
                PrintWriter requestWriter = // stream to write text requests to server
                        new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader stdIn = // standard input stream to get user's requests
                        new BufferedReader(
                                new InputStreamReader(System.in))
        ) {
            String userInput;
            while ((userInput = stdIn.readLine()) != null)
            {
                if (userInput.equalsIgnoreCase("A") || userInput.equalsIgnoreCase("B"))
                {
                    String jobName = userInput + nextIdNumber;
                    nextIdNumber++;
                    requestWriter.println(jobName); // send request to server
                } else
                {
                    System.out.println("Invalid input. Please enter 'A' or 'B'.");
                }
            }
        } catch (UnknownHostException e)
        {
            System.err.println("Don't know about host");
            System.exit(1);
        } catch (IOException e)
        {
            System.err.println("Couldn't get I/O for the connection");
            System.exit(1);
        }
    }
}
