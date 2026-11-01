import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;

public class ListenerThread extends Thread
{

    private Socket socket;
    private BufferedReader reader;
    private BlockingQueue<String> inBoundQueue;
    private Consumer<String> handler;


    public ListenerThread(Socket socket, BlockingQueue<String> inBoundQueue)
    {
        this.socket = socket;
        this.inBoundQueue = inBoundQueue;

    }

    private void setUp()
    {
        InputStream in = null;
        try
        {
            in = socket.getInputStream();
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }

        InputStreamReader isr = new InputStreamReader(in);

        BufferedReader breader = new BufferedReader(isr);

        reader = breader;

    }

    @Override
    public void run()
    {
        try
        {
            setUp();
            String line;

            while ((line = reader.readLine()) != null)
            {
                handler.accept(line);
            }
            System.out.println("Connection closed by other side.");

        } catch (IOException e)
        {
            System.out.println("Lister error:" + e.getMessage());
        } finally {
            close();
        }

    }

    public void close()
    {
        try
        {
            if(reader != null)
            {
                reader.close();
            }
        } catch (IOException e)
        {

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
