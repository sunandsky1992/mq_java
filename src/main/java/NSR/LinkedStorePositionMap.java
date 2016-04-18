package NSR;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ss on 16-4-14.
 */
class PositionBlock {
    private int queueNumber;
    private String queueId;
    private PositionBlock next;
    private String addr;
    private int port;

    PositionBlock(String addr, int port,int queueNumber) {
        this.addr = addr;
        this.port = port;
        this.queueNumber = queueNumber;
        this.queueId = addr+":"+port+queueNumber;
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
}

public class LinkedStorePositionMap {
    Map<String, PositionBlock> queuePosition;

    
    LinkedStorePositionMap () {
        queuePosition = new HashMap<String, PositionBlock>();
    }

    public void insertPosition(String queueName, String addr, int port) {

    }

}
