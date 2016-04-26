package NSR.Structs;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ss on 16-4-14.
 */

public class LinkedStorePositionMap {
    Map<String, PositionBlock> queuePosition;

    int KEY_NUMBER = 1000;

    Object locks[] = new Object[KEY_NUMBER];

    LinkedStorePositionMap () {
        queuePosition = new HashMap<String, PositionBlock>();
    }

    public void deletePosition(String queueName) {
        int hashCode = calculateQueueNameHash(queueName);
        synchronized (locks[hashCode]) {
            PositionBlock positionBlock = queuePosition.get(queueName);
            if (positionBlock == null)
                queuePosition.put(queueName,null);
            else
                queuePosition.put(queueName,positionBlock.getNext());
        }
    }

    public PositionBlock readPosition(String queueName) {
        return queuePosition.get(queueName);
    }


    public void insertPosition(String queueName, String addr, int port) {
        int hashCode = calculateQueueNameHash(queueName);
        synchronized (locks[hashCode]) {
            int number = 0;
            PositionBlock positionBlock = queuePosition.get(queueName);
            if (positionBlock == null) {
                PositionBlock newBlock = new PositionBlock(addr,port,number,queueName);
                queuePosition.put(queueName,newBlock);
            } else {
                number = positionBlock.getQueueNumber();
                while (positionBlock.getNext()!=null) {
                    positionBlock = positionBlock.getNext();
                    number = positionBlock.getQueueNumber();
                }
                number++;
                PositionBlock newBlock = new PositionBlock(addr,port,number,queueName);
                positionBlock.setNext(newBlock);
            }
        }
    }


    int calculateQueueNameHash(String queueName) {
        int l = queueName.length();
        int hashCode = 0;
        for (int i=0;i<l;i++) {
            hashCode += queueName.charAt(i);
        }
        return hashCode % KEY_NUMBER;
    }
}
