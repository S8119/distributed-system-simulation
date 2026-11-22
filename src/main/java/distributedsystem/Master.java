package distributedsystem;

import java.net.ServerSocket;
import java.util.HashMap;

public class Master
{
    public static void main(String[] args)
    {
        // We are assuming that the user will provide the port number as command line argument
        if (args.length != 1)
        {
            System.err.println(
                    "Usage: java distributedsystem.Master <port number>");
            System.exit(1);
        }

        // Arbitrary initialization to avoid compilation error, will be set in the try-catch block below
        int portNumber = 0;

        try
        {
            portNumber = Integer.parseInt(args[0]);
        } catch (NumberFormatException e)
        {
            System.err.println("Port number must be an integer.");
            System.exit(1);
        }

        HashMap<Integer, Job> inProgressJobs = new HashMap<>();

        try (ServerSocket serverSocket = new ServerSocket(portNumber))
        {
            MasterListenToClientThread masterListenToClientThread =
                    new MasterListenToClientThread(serverSocket, inProgressJobs);
            masterListenToClientThread.start();

            MasterListenToSlaveThread masterListenToSlaveThread =
                    new MasterListenToSlaveThread(serverSocket, inProgressJobs);
            masterListenToSlaveThread.start();
        }
        catch (Exception e)
        {
            System.err.println("Error starting server: " + e.getMessage());
            System.exit(1);
        }
    }
}
