package distributedsystem;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

public class Slave
{
    private String optimalJob;
    private Queue<Job> waitingJobs;

    private static final long OPTIMAL_JOB_PROCESS_TIME = 2000;
    private static final long REGULAR_JOB_PROCESS_TIME = 10000;

    Slave(String optimalJob) {
        this.optimalJob = optimalJob;
        waitingJobs = new LinkedBlockingDeque<>();
    }

    public String getOptimalJob() {
        return optimalJob;
    }

    public void beginProcessing(Queue<Job> waitingJobs) {
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
}
