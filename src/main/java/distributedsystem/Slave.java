package distributedsystem;

import java.util.Queue;

public class Slave {
    private String optimalJob;
    private Queue<Job> waitingJobs;

    Slave (String optimalJob) {
        this.optimalJob = optimalJob;
        waitingJobs = new Queue<Job>();
    }

    public String getOptimalJob() {
        return optimalJob;
    }

    public void beginProcessing (Queue<Job> waitingJobs) {
        long OPTIMAL_JOB_PROCESS_TIME = 2000;
        long REGULAR_JOB_PROCESS_TIME = 10000;

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

    public void addJob (Job newJob) {
        waitingJobs.add(newJob);
    }
}
