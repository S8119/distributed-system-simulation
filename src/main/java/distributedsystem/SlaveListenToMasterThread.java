package distributedsystem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;



public class SlaveListenToMasterThread extends Thread {
    private final Socket slaveSocket;
    private final Slave slaveA;
    private final Slave slaveB;

    public SlaveListenToMasterThread(Socket clientSocket) {
        this.slaveSocket = clientSocket;
        this.slaveA = new Slave("A");
        this.slaveB = new Slave("B");

    }

    public void run() {
        try (
                slaveSocket;
                BufferedReader responseReader = // stream to read text response from server
                        new BufferedReader(
                                new InputStreamReader(slaveSocket.getInputStream()));
        ) {
            String serverResponse;
            while ((serverResponse = responseReader.readLine()) != null) {
                Job job = new Job(serverResponse.substring(0, 1),
                                  Integer.parseInt(serverResponse.substring(1)));
                System.out.println("Received job: " + job.toString());

               Slave.delegateJob(job, slaveA, slaveB);

            }
        } catch (Exception e) {
            System.err.println("Error in SlaveListenToMasterThread: " + e.getMessage());
            System.exit(1);
        }
    }


}

