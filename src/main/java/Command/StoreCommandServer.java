package Command;

import Constant.Constant;
import Queue.*;
import StoreServer.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ss on 16-4-6.
 */
public class StoreCommandServer extends CommandServer {
    int position;

    Socket connect ;

    StoreServer storeServer;

    public StoreCommandServer(Socket connect) {
        this.storeServer = new StoreServerByRedis();
        this.connect = connect;
    }

    public void analysisCommand(byte[] command) {
        position = 3;//前两位是总长度
        if ((command[2]>>7&0x1) ==1) {
            Queue queue = getInsertQueue(command);
            insertMessage(queue);
        } else if ((command[2]>>6 & 0x1)  == 1){
            ReadCommand readCommand = getReadQueue(command);
            Queue queue = readMessage(readCommand);
            sendMessage(queue);
        }
    }

    private ReadCommand getReadQueue(byte[] command) {
            int queueNameLength = byteToInt(command, position, Constant.QUEUE_NAME_LENGTH);
            position+=Constant.QUEUE_NAME_LENGTH;
            String queueId = byteToString(command, position, queueNameLength);
            position+=queueNameLength;
            int messageNum = byteToInt(command, position, Constant.MESSAGE_NUMBER);
            position+=Constant.MESSAGE_NUMBER;
            ReadCommand readCommand = new ReadCommand(queueId,messageNum);
        return readCommand;
    }

    private Queue getInsertQueue(byte[] command) {
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
        return queue;
    }

    private void insertMessage(Queue queue) {
        storeServer.storeMessage(queue);
    }

    private Queue readMessage(ReadCommand readCommand) {
        Queue queue = storeServer.getMessage(readCommand.getNumber(),readCommand.getQueueName());
        return queue;
    }

    private void sendMessage(Queue queue) {
        int length = 2;
        //length += l;
        //length++;
        length+=2;//消息总数
        int messageNumber = 0;
        for (Message message :queue.getMessages()) {
            length += 2;//消息长度
            length += message.getContent().length;//消息实际长度
            messageNumber++;
        }
        byte command[] = new byte[length];

        int position = 0;
        insertIntToBytes(command,length,Constant.TOTAL_LENGTH,position);
        position += Constant.TOTAL_LENGTH;
        insertIntToBytes(command,messageNumber,Constant.MESSAGE_NUMBER,position);
        position +=Constant.MESSAGE_LENGTH;
        for (Message message:queue.getMessages()) {
            int messageLength = message.getContent().length;
            insertIntToBytes(command,messageLength,Constant.MESSAGE_LENGTH,position);
            position+=Constant.MESSAGE_LENGTH;
            System.arraycopy(command,0,command,position,messageLength);
            position+=messageLength;
        }

        try {
            OutputStream out = connect.getOutputStream();
            out.write(command);
            out.flush();
            connect.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        byte[] command = {0,8,(byte)64,1,97,1,0,1,97};
        byte[] test2 = "a".getBytes();
        System.out.println(Arrays.toString(test2));
        System.out.println(byteToString(test2, 0, 2));
        StoreCommandServer t = new StoreCommandServer(null);
        t.analysisCommand(command);
    }
}
