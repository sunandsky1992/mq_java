package StoreServer;

import Command.StoreCommandServer;
import Constant.Constant;
import Queue.*;
import SocketServer.*;
import redis.clients.jedis.*;

import java.util.List;

/**
 * Created by ss on 16-3-29.
 */
public class StoreServerByRedis implements StoreServer{
    String serverId;

    private static JedisPool jedisPool;

    private static int MAX_ACTIVE = 1024;

    private static int MAX_IDLE = 200;

    private static int MAX_WAIT = 10000;

    private static String ADDR = "127.0.0.1";

    private static int PORT =  6379;

    public StoreServerByRedis(){
        this.serverId = "storeServer";

        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(MAX_ACTIVE);
        config.setMaxIdle(MAX_IDLE);
        config.setMaxWaitMillis(MAX_WAIT);
        jedisPool = new JedisPool(config, ADDR, PORT,10000);

    }

    public StoreServerByRedis(String serverId) {
        this.serverId = serverId;
    }

    public void storeMessage(Queue queue) {
        Jedis jedis = jedisPool.getResource();
        Transaction tx = jedis.multi();
        List<Message> messages = queue.getMessages();
            for (Message message:messages) {
                tx.rpush(queue.getQueueId().getBytes(),message.getContent());
            }
        tx.exec();
        jedis.close();
    }

    public Queue getMessage(int number, String queueId) {
        Jedis jedis = jedisPool.getResource();
        Transaction tx = jedis.multi();
        Response<List<String>> response = tx.lrange(queueId, 0, number - 1);
        tx.ltrim(queueId, number, -1);
        List<Object> messages = tx.exec();
        List<String> res = response.get();
        jedis.close();

        Queue queue = new Queue(queueId);
        for (String s:res) {
            Message message = new Message(s.getBytes(),0);
            queue.insertMessage(message);
        }
        return queue;
    }

    public long getQueueLength(String queueId) {
        Jedis jedis = jedisPool.getResource();
        long res = jedis.llen(queueId);
        jedis.close();
        return res;
    }



    public static void main(String args[]){
        SocketServer server = new SocketServer("localhost",8001,Constant.STORE_COMMAND_SERVER,"storeTest");
        server.listen();
    }
}
