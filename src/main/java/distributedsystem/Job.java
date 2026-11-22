package distributedsystem;

public class Job
{
    private String type; //A or B
    private int id;
    private String client; // the client that submitted the job; A or B

    Job(String type, int id, String client)
    {
        this.type = type;
        this.id = id;
        this.client = client;
    }

    public String getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public String getClient() {
        return client;
    }

    @Override
    public String toString()
    {
        return type + id;
    }
}
