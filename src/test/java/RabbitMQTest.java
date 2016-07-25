/**
 * Created by ss on 16-7-20.
 */
import com.rabbitmq.client.*;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeoutException;

public class RabbitMQTest {
    private final static String QUEUE_NAME = "hello";
    int num = 0;
    @Test
    public  void sendAndRead()
            throws java.io.IOException, TimeoutException, InterruptedException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String message = "Hello World!";
        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(QUEUE_NAME, true, consumer);
        while (true) {
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            num++;
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String t = new String(delivery.getBody());
            num++;
            if (num%100 == 0) {
                System.out.println(num + " " + new Date());
            }
        }
        //System.out.println(" [x] Sent '" + message + "'");



        //channel.close();
        //connection.close();
    }
}
