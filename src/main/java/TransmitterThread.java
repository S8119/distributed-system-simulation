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

    }

    private void setUp()
    {

    }

    @Override
    public void run()
    {
    }

    public void close()
    {

    }
}
