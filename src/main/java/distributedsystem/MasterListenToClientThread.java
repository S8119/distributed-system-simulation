package distributedsystem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class MasterListenToClientThread extends Thread
{
    private ServerSocket serverSocket;
    private HashMap<Integer, Job> inProgressJobs;

    public MasterListenToClientThread(ServerSocket serverSocket, HashMap<Integer, Job> inProgressJobs)
    {
        this.serverSocket = serverSocket;
        this.inProgressJobs = inProgressJobs;
    }

    public void run()
    {
        try (
                Socket clientSocket = serverSocket.accept();
                PrintWriter slaveAWriter = new PrintWriter(clientSocket.getOutputStream(), true);
                PrintWriter slaveBWriter = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
                )
        {
            String clientMessage;
            while ((clientMessage = reader.readLine()) != null)
            {
                String jobType = clientMessage.substring(0, 1);
                if (!jobType.equals("A") && !jobType.equals("B"))
                {
                    System.err.println("Job type must be either A or B.");
                    System.exit(1);
                }

                // Arbitrary initialization to avoid compilation error, will be set in the try-catch block below
                int jobNumber = 0;
                try
                {
                    jobNumber = Integer.parseInt(clientMessage.substring(1));
                } catch (NumberFormatException e)
                {
                    System.err.println("Job number must be an integer.");
                    System.exit(1);
                }

                synchronized (inProgressJobs)
                {
                    Job inProgressJob = new Job(jobType, jobNumber, clientMessage);
                    inProgressJobs.put(jobNumber, inProgressJob);
                }

                String slaveToUse = findBestSlave(jobType);
                if (slaveToUse.equals("A"))
                {
                    slaveAWriter.println(clientMessage);
                } else if (slaveToUse.equals("B"))
                {
                    slaveBWriter.println(clientMessage);
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private String findBestSlave(String jobType)
    {
        // Implement logic to determine which slave to use based on job type and current load
        // For simplicity, we can just return "A" or "B" based on some criteria
        // This is a placeholder implementation
        if (jobType.equals("A"))
        {
            return "A";
        } else
        {
            return "B";
        }
    }
}
