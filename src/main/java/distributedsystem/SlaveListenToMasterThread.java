package distributedsystem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;



public class SlaveListenToMasterThread extends Thread {
    private final Socket slaveSocket;
    private final String whichSlave; // "A" or "B"

    public SlaveListenToMasterThread(Socket clientSocket, String whichSlave) {
        this.slaveSocket = clientSocket;
        this.whichSlave = whichSlave;
    }

    public void run() {
        try (
                slaveSocket;
                BufferedReader responseReader = // stream to read text response from server
                        new BufferedReader(
                                new InputStreamReader(slaveSocket.getInputStream()));
                PrintWriter writer = // stream to send text request to server
                        new PrintWriter(slaveSocket.getOutputStream(), true)
        ) {
            String serverResponse;
            while ((serverResponse = responseReader.readLine()) != null) {
                System.out.println("Received job: " + serverResponse);

                String jobType = serverResponse.substring(0, 1);
                if (!jobType.equals(whichSlave)) {
                    sleep(10000); // Simulate slow processing for slower slave type
                } else {
                    sleep(2000); // Simulate fast processing for faster slave type
                }
                writer.println(serverResponse);
            }
        } catch (Exception e) {
            System.err.println("Error in SlaveListenToMasterThread: " + e.getMessage());
            System.exit(1);
        }
    }
}

