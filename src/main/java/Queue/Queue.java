package Queue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ss on 16-4-5.
 */
public class Queue {
    String queueId;

    List<Message> messages;

    public Queue(List<Message> messages,String queueId) {
        this.messages = messages;
        this.queueId = queueId;
    }

    public Queue(String queueId) {
        this.queueId = queueId;
        messages = new ArrayList<Message>();
    }

    public void insertMessage(Message message) {
        messages.add(message);
    }

    public void insertMessage(List<Message> messages) {
        this.messages.addAll(messages);
    }

    public void removeMessage(Message message) {
        messages.remove(message);
    }

    public void removeMessage(List<Message> messages) {
        this.messages.removeAll(messages);
    }

    public List<Message>  getMessages() {
        return messages;
    }

    public String getQueueId() {
        return queueId;
    }
}
