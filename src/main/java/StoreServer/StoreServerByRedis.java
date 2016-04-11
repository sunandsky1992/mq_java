package StoreServer;

import Queue.Queue;

import java.util.List;

/**
 * Created by ss on 16-3-29.
 */
public class StoreServerByRedis implements StoreServer{
    String serverId;

    public StoreServerByRedis(){
        this.serverId = "storeServer";
    }

    public StoreServerByRedis(String serverId) {
        this.serverId = serverId;
    }

    public void storeMessage(List<Queue> queues) {

    }

    public Queue getMessage(int number, String QueueId) {
        return null;
    }

    public void sendMessage(List<Queue> queues) {

    }
}
