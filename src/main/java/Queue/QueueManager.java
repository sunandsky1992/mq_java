package Queue;

import java.util.List;

/**
 * Created by ss on 16-3-29.
 */
public interface QueueManager {
    List<Message> messages = null;

    int insertQueue(String queueId, Message message);

    List<Message> getQueue(String queueId, int num);
}
