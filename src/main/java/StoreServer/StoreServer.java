package StoreServer;

import Queue.Queue;

import java.util.List;

/**
 * Created by ss on 16-4-5.
 */
public interface StoreServer {

    public void storeMessage(Queue queue);

    public Queue getMessage(int number, String QueueId);

}
