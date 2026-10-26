import java.io.BufferedReader;
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
