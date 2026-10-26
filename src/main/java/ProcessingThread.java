import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ProcessingThread extends Thread
{

    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private String slaveType;
    private int fastDelay;
    private int slowDelay;

    public ProcessingThread(Socket socket, String slaveType)
    {
        this.socket = socket;
        this.slaveType = slaveType;
        fastDelay = 20000;
        slowDelay = 10000;
    }

    private void setUp() throws IOException
    {
        reader = new BufferedReader(new InputStreamReader((socket.getInputStream())));
        writer = new PrintWriter(socket.getOutputStream(), true);

    }

    @Override
    public void run()
    {
        try
        {
            setUp();
            String job;
            while ((job = reader.readLine()) != null)
            {
                System.out.println("Slave" + slaveType + "received" + job);

                String[] parts = job.split(": ");
                String jobName = parts[0];
                String jobType = parts[1].trim();

                int delay;
                if (jobType.equals(slaveType))
                {
                    delay = fastDelay;
                } else
                {
                    delay = slowDelay;
                }

                System.out.println("Slave" + slaveType + "processing" + job
                        + "(" + delay / 1000 + "s)");
                Thread.sleep(delay);

                writer.println("Done:" + jobName);
                System.out.println("Slave " + slaveType + " finished " + jobName);
            }
            System.out.println("Master disconnected.");

        } catch (IOException e)
        {
            System.out.println("Slave " + slaveType + " error: " + e.getMessage());
        } catch (InterruptedException e)
        {
            System.out.println("Slave " + slaveType + " stopped.");
        } finally
        {
            close();
        }

    }

    public void close()
    {
        try
        {
            if (reader != null)
            {
                reader.close();
            }
        } catch (IOException e)
        {

        }
        if (writer != null)
        {
            writer.close();
        }
        try
        {
            if (socket != null)
            {
                socket.close();
            }
        } catch (IOException e)
        {

        }


    }


}
