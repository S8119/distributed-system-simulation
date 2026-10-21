import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws IOException
    {

        // We are assuming that the user will provide the host name and port number as command line arguments
        if (args.length != 2)
        {
            System.err.println(
                    "Usage: java Client <host name> <port number>");
            System.exit(1);
        }

        String hostName = args[0];

        // Arbitrary initialization to avoid compilation error, will be set in the try-catch block below
        int portNumber = 0;

        try
        {
            portNumber = Integer.parseInt(args[1]);
        } catch (NumberFormatException e)
        {
            System.err.println("Port number must be an integer.");
            System.exit(1);
        }

        ClientJobReceiverThread clientJobReceiverThread = new ClientJobReceiverThread(new Socket(hostName, portNumber));
        clientJobReceiverThread.start();
    }
}
