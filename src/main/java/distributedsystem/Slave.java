package distributedsystem;

import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

public class Slave
{
    private String optimalJob;
    private Queue<Job> waitingJobs;

    private static final long OPTIMAL_JOB_PROCESS_TIME = 2000;
    private static final long REGULAR_JOB_PROCESS_TIME = 10000;

    Slave(String optimalJob) {
        this.optimalJob = optimalJob;
        waitingJobs = new LinkedBlockingQueue<>();
    }

    public String getOptimalJob() {
        return optimalJob;
    }

    public void beginProcessing() {
        while (!waitingJobs.isEmpty()) {
            Job currJob = waitingJobs.remove();
            String currJobType = currJob.getType();

            try {
                if (currJobType.equals(optimalJob)) {
                    System.out.println("Processing job: " + currJob.toString());
                    Thread.sleep(OPTIMAL_JOB_PROCESS_TIME);
                } else {
                    System.out.println("Processing job: " + currJob.toString());
                    Thread.sleep(REGULAR_JOB_PROCESS_TIME);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void addJob(Job newJob) {
        waitingJobs.add(newJob);
    }

    public static void main(String[] args)
    {
        if (args.length != 3 || (!args[0].equals("A") && !args[0].equals("B")))
        {
            System.err.println(
                    "Usage: java Slave <optimal job (A or B)> <master host name> <master port number>");
            System.exit(1);
        }

        String hostName = args[1];
        // Arbitrary initialization to avoid compilation error, will be set in the try-catch block below
        int portNumber = 0;
        try
        {
            portNumber = Integer.parseInt(args[2]);
        } catch (NumberFormatException e)
        {
            System.err.println("Port number must be an integer.");
            System.exit(1);
        }

        try (Socket clientSocket = new Socket(hostName, portNumber))
        {
            SlaveListenToMasterThread slaveListenToMasterThread = new SlaveListenToMasterThread(clientSocket, args[0]);
            slaveListenToMasterThread.start();
        } catch (Exception e)
        {
            System.err.println("Error connecting to master: " + e.getMessage());
            System.exit(1);
        }
    }
}
