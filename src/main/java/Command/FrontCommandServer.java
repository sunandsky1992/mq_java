package Command;

import Constant.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ss on 16-5-27.
 */
public class FrontCommandServer extends CommandServer {
    @Override
    public void analysisCommand(byte[] command) {
        int position = 1;
        if ((command[0]>>7&0x1) ==1) {
            System.out.println("insert message");
            int queueNameLength  = byteToInt(command, position, Constant.QUEUE_NAME_LENGTH);
            position += Constant.QUEUE_NAME_LENGTH;
            String queueName = getQueueName(command,position,queueNameLength);
            position += queueNameLength;
            int messageNum = getInt(command,position);
            List<String> messages = new ArrayList<String>();
            for (int i=0;i<messageNum;i++) {
                int messageLength = getInt(command,position);
                position += Constant.QUEUE_NAME_LENGTH;
                String message = byteToString(command,position,messageLength);
                position += messageLength;
                messages.add(message);
            }
            sendMessageToStore(queueName, messages);
        } else if ((command[0]>>6 & 0x1)  == 1) {


        } else if ((command[0]>>5 & 0x1) == 1) {

        }
    }

    private void sendMessageToStore(String queueName, List<String> messages) {
        
    }
}
