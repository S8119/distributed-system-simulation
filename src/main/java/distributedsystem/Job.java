package distributedsystem;

public class Job
{
    private String type; //A or B
    private int id;

    Job(String type, int id)
    {
        this.type = type;
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString()
    {
        return type + id;
    }
}
