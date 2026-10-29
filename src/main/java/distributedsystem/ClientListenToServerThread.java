package distributedsystem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientListenToServerThread extends Thread
{
    private final Socket clientSocket;

    public ClientListenToServerThread (Socket clientSocket)
    {
        this.clientSocket = clientSocket;
    }

    public void run()
    {
        try (
                clientSocket;
                BufferedReader responseReader = // stream to read text response from server
                        new BufferedReader(
                                new InputStreamReader(clientSocket.getInputStream()));
        )
        {
            String serverResponse;
            while ((serverResponse = responseReader.readLine()) != null)
            {
                System.out.println(serverResponse);
            }
        } catch (Exception e)
        {
            System.err.println("Error in ClientListenToServerThread: " + e.getMessage());
            System.exit(1);
        }
    }
}
