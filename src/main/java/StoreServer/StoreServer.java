package StoreServer;

import Queue.Queue;

import java.util.List;

/**
 * Created by ss on 16-4-5.
 */
public interface StoreServer {

    public void storeMessage(List<Queue> queues);

    public Queue getMessage(int number, String QueueId);

    public void sendMessage(List<Queue> queues);
}
