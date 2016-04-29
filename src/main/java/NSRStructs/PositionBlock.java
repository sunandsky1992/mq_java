package NSRStructs;

/**
 * Created by ss on 16-4-18.
 */
public class PositionBlock {
    private String queueName;
    private int queueNumber;
    private String queueId;
    private PositionBlock next;
    private String addr;
    private int port;


    PositionBlock(String addr, int port,int queueNumber,String queueName) {
        this.queueName = queueName;
        this.addr = addr;
        this.port = port;
        this.queueNumber = queueNumber;
        this.queueId = addr+":"+port+queueNumber;
        next = null;
    }

    public String getQueueId() {
        return queueId;
    }

    public PositionBlock getNext() {
        return next;
    }

    public void setNext(PositionBlock next) {
        this.next = next;
    }

    public String getAddr() {
        return addr;
    }

    public int getPort() {
        return port;
    }

    public int getQueueNumber() {
        return queueNumber;
    }

    public void setQueueNumber(int queueNumber) {
        this.queueNumber = queueNumber;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }
}

