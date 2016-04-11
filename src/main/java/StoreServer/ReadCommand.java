package StoreServer;

/**
 * Created by ss on 16-4-9.
 */
public class ReadCommand {
    private String queueName;

    private int number;

    public ReadCommand(String queueName, int queueNum) {
        this.queueName = queueName;
        this.number = queueNum;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
