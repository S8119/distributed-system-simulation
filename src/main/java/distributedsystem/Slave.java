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
        waitingJobs = new LinkedBlockingQueue<Job>();
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
        if (args.length != 1 || (!args[0].equals("A") && !args[0].equals("B"))){
            System.err.println(
                    "Usage: java Slave <optimal job (A or B)>");
            System.exit(1);
        }

        Slave slave = new Slave(args[0]);
        try (Socket clientSocket = new Socket(hostName, portNumber))
        {
            SlaveListenToMasterThread slaveListenToMasterThread = new SlaveListenToMasterThread(clientSocket, args[0]);
            slaveListenToMasterThread.start();
        }
    }
}
