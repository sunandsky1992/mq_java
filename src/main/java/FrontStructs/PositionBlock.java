package FrontStructs;

/**
 * Created by ss on 16-5-27.
 */
public class PositionBlock {
    private String queueName;

    private int port;

    private String queueId;

    private String addr;

    public PositionBlock(String queueName, String addr, int port, String queueId) {
        this.queueName = queueName;
        this.port = port;
        this.queueId = queueId;
        this.addr = addr;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getQueueId() {
        return queueId;
    }

    public void setQueueId(String queueId) {
        this.queueId = queueId;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
}
