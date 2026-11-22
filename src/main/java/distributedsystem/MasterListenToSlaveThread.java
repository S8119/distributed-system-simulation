package distributedsystem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class MasterListenToSlaveThread extends Thread
{
    private ServerSocket serverSocket;
    private HashMap<Integer, Job> inProgressJobs;

    public MasterListenToSlaveThread(ServerSocket serverSocket, HashMap<Integer, Job> inProgressJobs)
    {
        this.serverSocket = serverSocket;
        this.inProgressJobs = inProgressJobs;
    }

    public void run()
    {
        try (
                Socket slaveSocket = serverSocket.accept();
                PrintWriter clientAWriter = new PrintWriter(slaveSocket.getOutputStream(), true);
                PrintWriter clientBWriter = new PrintWriter(slaveSocket.getOutputStream(), true);
                BufferedReader reader = new BufferedReader(new InputStreamReader(slaveSocket.getInputStream()))
        )
        {
            String slaveMessage;
            while ((slaveMessage = reader.readLine()) != null)
            {
                // Arbitrary initialization to avoid compilation error, will be set in the try-catch block below
                int jobNumber = 0;
                try
                {
                    jobNumber = Integer.parseInt(slaveMessage.substring(1));
                } catch (NumberFormatException e)
                {
                    System.err.println("Job number must be an integer.");
                    System.exit(1);
                }

                Job completedJob;
                synchronized (inProgressJobs)
                {
                    completedJob = inProgressJobs.remove(jobNumber);
                }
                if (completedJob != null)
                {
                    String client = completedJob.getClient();
                    if (client.equals("A"))
                    {
                        clientAWriter.println(slaveMessage);
                    } else if (client.equals("B"))
                    {
                        clientBWriter.println(slaveMessage);
                    }
                }
            }
        } catch (Exception e)
        {
            System.err.println("Error in MasterListenToSlaveThread: " + e.getMessage());
            System.exit(1);
        }
    }
}
