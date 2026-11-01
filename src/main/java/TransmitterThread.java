import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class TransmitterThread extends Thread
{
    private Socket socket;
    private PrintWriter writer;
    private BlockingQueue<String> outboundQueue;

    public TransmitterThread(Socket socket, BlockingQueue<String> outboundQueue)
    {
        this.socket = socket;
        this.outboundQueue = outboundQueue;

    }

    private void setUp()
    {
        try
        {
            writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e)
        {

        }

    }

    @Override
    public void run()
    {
        try
        {
            setUp();
            while (true)
            {
                String message = outboundQueue.take();
                writer.println(message);
            }

        } catch (InterruptedException e)
        {
            System.out.println("Transmitter stopped");
        } finally
        {
            close();
        }
    }

    public void close()
    {
        if (writer != null)
        {
            writer.close();
        }
        try
        {
            if(socket != null)
            {
                socket.close();
            }
        } catch (IOException e)
        {

        }

    }
}
