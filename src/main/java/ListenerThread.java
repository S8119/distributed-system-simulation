import java.io.BufferedReader;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class ListenerThread extends Thread
{

    private Socket socket;
    private BufferedReader reader;
    private BlockingQueue<String> inBoundQueue;


    public ListenerThread(Socket socket, BlockingQueue<String> inBoundQueue)
    {
        this.socket = socket;
        this.inBoundQueue = inBoundQueue;

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
