package distributedsystem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class SlaveListenToMasterThread extends Thread
{
    private final Socket slaveSocket;

    public SlaveListenToMasterThread(Socket clientSocket)
    {
        this.slaveSocket = clientSocket;
    }

    public void run()
    {
        try (
                slaveSocket;
                BufferedReader responseReader = // stream to read text response from server
                        new BufferedReader(
                                new InputStreamReader(slaveSocket.getInputStream()));
        )
        {
            String serverResponse;
            while ((serverResponse = responseReader.readLine()) != null)
            {
                Job job = new Job(serverResponse.substring(0, 1), Integer.getInteger(serverResponse.substring(1)));
                System.out.println("Received job: " + job.toString());

                Slave slaveA = new Slave("A");
                Slave slaveB = new Slave("B");

                String delegatedSlave = delegateJob(job); //returns name of delegated slave
                if(delegatedSlave.equals("A"))
                {
                    slaveA.addJob(job);
                }
                else
                {
                    slaveB.addJob(job);
                }

            }
        } catch (Exception e)
        {
            System.err.println("Error in SlaveListenToMasterThread: " + e.getMessage());
            System.exit(1);
        }
    }

    //delegateJob method here
}

