package Command;

import Constant.Constant;
import Queue.*;
import StoreServer.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ss on 16-4-6.
 */
public class StoreCommandServer extends CommandServer {
    int position;

    StoreServer storeServer;

    StoreCommandServer(StoreServer storeServer) {
        this.storeServer = storeServer;
    }

    public void analysisCommand(byte[] command) {
        position = 1;
        if ((command[0]>>7&0x1) ==1) {
            List<Queue> queues = getInsertQueue(command);
            insertMessage(queues);
        } else if ((command[0]>>6 & 0x1)  == 1){
            List<ReadCommand> readCommands = getReadQueue(command);
            List<Queue> queues = readMessage(readCommands);
        }
    }

    private List<ReadCommand> getReadQueue(byte[] command) {
        List<ReadCommand> readCommands = new ArrayList<ReadCommand>();
        int queueNum = byteToInt(command, position, Constant.QUEUE_NUMBER);
        position += Constant.QUEUE_NUMBER;
        while (queueNum-- > 0) {
            int queueNameLength = byteToInt(command, position, Constant.QUEUE_NAME_LENGTH);
            position+=Constant.QUEUE_NAME_LENGTH;
            String queueId = byteToString(command, position, queueNameLength);
            position+=queueNameLength;
            int messageNum = byteToInt(command, position, Constant.MESSAGE_NUMBER);
            position+=Constant.MESSAGE_NUMBER;
            ReadCommand readCommand = new ReadCommand(queueId,messageNum);
            readCommands.add(readCommand);
        }
        return readCommands;
    }

    private List<Queue> getInsertQueue(byte[] command) {
        List<Queue> queues = new ArrayList<Queue>();
        int queueNum = byteToInt(command,position,Constant.QUEUE_NUMBER);
        position+= Constant.QUEUE_NUMBER;
        while (queueNum-- >0) {
            int queueNameLength = byteToInt(command, position, Constant.QUEUE_NAME_LENGTH);
            position+=Constant.QUEUE_NAME_LENGTH;
            String queueId = byteToString(command,position,queueNameLength);
            position+=queueNameLength;
            Queue queue = new Queue(queueId);
            int messageNum = byteToInt(command,position,Constant.MESSAGE_NUMBER);
            position+=Constant.MESSAGE_NUMBER;
            while (messageNum-->0) {
                int messageLength = byteToInt(command, position, Constant.MESSAGE_LENGTH);
                position+=Constant.MESSAGE_LENGTH;
                byte[] messageByte = Arrays.copyOfRange(command, position, position + messageLength);
                position+=messageLength;
                Message message = new Message(messageByte,0);
                queue.insertMessage(message);
            }
            queues.add(queue);
        }
        return queues;
    }

    private void insertMessage(List<Queue> queues) {
        storeServer.storeMessage(queues);
    }

    private List<Queue> readMessage(List<ReadCommand> readCommands) {
        List<Queue> queues = new ArrayList<Queue>();
        for (ReadCommand readCommand : readCommands) {
            Queue queue = storeServer.getMessage(readCommand.getNumber(),readCommand.getQueueName());
            queues.add(queue);
        }
        return queues;
    }

    private void sendMessage(List<Queue> queues) {
        storeServer.sendMessage(queues);
    }

    public static void main(String args[]) {
        byte[] command = {(byte)128,1,2,0,97,1,0,2,0,97};
        byte[] test2 = "a".getBytes();
        System.out.println(Arrays.toString(test2));
        System.out.println(byteToString(test2, 0, 2));
        StoreCommandServer t = new StoreCommandServer(new StoreServerByRedis());
        t.analysisCommand(command);
    }
}
