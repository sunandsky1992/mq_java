package Command;

import Constant.Constant;
import FrontServer.FrontServer;
import FrontStructs.FrontSchelduledTask;
import FrontStructs.FrontSchelduledTaskUnit;
import FrontStructs.PositionBlock;
import Queue.Message;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ss on 16-5-27.
 */
public class FrontCommandServer extends CommandServer {
    private static FrontServer frontServer = FrontServer.getFrontServer();

    @Override
    public void analysisCommand(byte[] command) {
        int position = 1;
        if ((command[0]>>7&0x1) ==1) {
          //  System.out.println("insert message");
            int queueNameLength  = byteToInt(command, position, Constant.QUEUE_NAME_LENGTH);
            position += Constant.QUEUE_NAME_LENGTH;
            String queueName = getQueueName(command, position, queueNameLength);
            position += queueNameLength;
            int messageNum = getInt(command,position);
            position += Constant.INT_LENGTH;

            List<String> messages = new ArrayList<String>();
            for (int i=0;i<messageNum;i++) {
                int messageLength = getInt(command, position);
                position += Constant.INT_LENGTH;
                String message = byteToString(command,position,messageLength);
                position += messageLength;
                messages.add(message);
            }
            sendMessageToStore(queueName, messages);
        } else if ((command[0]>>6 & 0x1)  == 1) {
            System.out.println("read message");
            int queueNameLength  = byteToInt(command, position, Constant.QUEUE_NAME_LENGTH);
            position += Constant.QUEUE_NAME_LENGTH;
            String queueName = getQueueName(command, position, queueNameLength);
            position += queueNameLength;
            int messageNum = byteToInt(command,position,Constant.INT_LENGTH);
            List<String> messages = getMessage(queueName,messageNum);
            System.out.println(messages.size());
            sendMessageToUser(queueName, messages);
        } else if ((command[0]>>5 & 0x1) == 1) {

        } else if ((command[0]>>4 & 0x1) == 1) {
            System.out.println("syn insert map");
            int num = byteToInt(command,position,Constant.INT_LENGTH);
            position+=Constant.INT_LENGTH;
            for (int i=0;i<num;i++) {
                int queueNameLength = byteToInt(command, position, Constant.QUEUE_NAME_LENGTH);
                position+=Constant.QUEUE_NAME_LENGTH;
                String queueName = byteToString(command, position, queueNameLength);
                position+=queueNameLength;
                int addLength = byteToInt(command, position, Constant.INT_LENGTH);
                position+=Constant.INT_LENGTH;
                String addr = byteToString(command, position, addLength);
                position+=addLength;
                long timestamp = byteToLong(command, position, Constant.TIMESTAMP_LENGTH);
                position+=Constant.TIMESTAMP_LENGTH;

                String strs[] = addr.split(":");
                String address = strs[0];
                int port = Integer.parseInt(strs[1]);
                String queueId = strs[2];

                FrontSchelduledTaskUnit frontSchelduledTaskUnit = new FrontSchelduledTaskUnit();
                frontSchelduledTaskUnit.setAddr(address);
                frontSchelduledTaskUnit.setPort(port);
                frontSchelduledTaskUnit.setQueueId(queueId);
                frontSchelduledTaskUnit.setQueueName(queueName);
                frontSchelduledTaskUnit.setTimestamp(timestamp);
                FrontSchelduledTask.getFrontSchelduledTask().insert(frontSchelduledTaskUnit);
            }
        }
    }

    @Override
    public void task() {

    }

    private void sendMessageToUser(String queueName, List<String> messages) {
        int length = 2;
        //length += l;
        //length++;
        length+=2;//消息总数
        int messageNumber = 0;
        for (String message :messages) {
            length += Constant.MESSAGE_LENGTH;//消息长度
            length += message.length();//消息实际长度
            messageNumber++;
        }
        byte command[] = new byte[length];
        int position = 0;
        insertIntToBytes(command, length, Constant.TOTAL_LENGTH, position);
        position += Constant.TOTAL_LENGTH;
        insertIntToBytes(command, messageNumber, Constant.MESSAGE_NUMBER, position);
        position +=Constant.MESSAGE_NUMBER;
        for (String message:messages) {
            int messageLength = message.length();
            insertIntToBytes(command,messageLength,Constant.MESSAGE_LENGTH,position);
            position+=Constant.MESSAGE_LENGTH;
            insertStringToBytes(command, message, messageLength, position);
            position+=messageLength;
        }
        try {
            sendCommandWithOutReturn(command,connect.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private List<String> getMessage(String queueName,int messageNum) {
        List<String> res = new ArrayList<String>();
        PositionBlock positionBlock = getReadMap(queueName);
        if (positionBlock == null)
            return res;
        int num = 0;
        while (num < messageNum) {
            int remindNum = messageNum - num;
            List<String> newMessages = getMeesageFromStore(positionBlock,remindNum);
            num += newMessages.size();
            res.addAll(newMessages);
            if (num < messageNum) {
                PositionBlock newPositionBlock = getReadMap(queueName);
                if (newPositionBlock.getAddr().equals(positionBlock.getAddr()) &&
                        newPositionBlock.getPort() == positionBlock.getPort()) {
                    break;
                }
                positionBlock = newPositionBlock;
            }
        }
        return res;
    }

    private PositionBlock getReadMap(String queueName){
        PositionBlock positionBlock = frontServer.getReadMap(queueName);
        if (positionBlock == null) {
            positionBlock = getReadPositionByNSR(queueName);
            System.out.println(positionBlock.getQueueId());
            if (positionBlock==null)
                return null;
            frontServer.putReadMap(positionBlock);
        }
        return positionBlock;
    }

    private List<String> getMeesageFromStore(PositionBlock positionBlock, int messageNum) {
        int length = Constant.TOTAL_LENGTH + Constant.COMMAND_LENGTH + Constant.QUEUE_NAME_LENGTH +
                Constant.MESSAGE_NUMBER +positionBlock.getQueueId().length();
        byte[] command = new byte[length];
        int position = 0;
        insertIntToBytes(command,length,Constant.TOTAL_LENGTH,position);
        position += Constant.TOTAL_LENGTH;
        insertIntToBytes(command,64,Constant.COMMAND_LENGTH,position);
        position += Constant.COMMAND_LENGTH;
        insertIntToBytes(command,positionBlock.getQueueId().length(),Constant.QUEUE_NAME_LENGTH,position);
        position += Constant.QUEUE_NAME_LENGTH;
        insertStringToBytes(command,positionBlock.getQueueId(),positionBlock.getQueueId().length(),position);
        position += positionBlock.getQueueId().length();
        insertIntToBytes(command,messageNum,Constant.INT_LENGTH,position);
        position += Constant.INT_LENGTH;
        byte[] res = sendCommand(command, positionBlock.getAddr(), positionBlock.getPort());
        return getMessageFromByte(res);
    }

    private List<String> getMessageFromByte(byte[] res) {
        List<String> messages = new ArrayList<String>();
        int position = 0;
        int number = byteToInt(res,position,Constant.INT_LENGTH);
        position += Constant.INT_LENGTH;
        for (int i=0;i<number;i++) {
            int len = byteToInt(res,position,Constant.INT_LENGTH);
            position += Constant.INT_LENGTH;
            String message = byteToString(res,position,len);
            position += len;
            messages.add(message);
        }
        return messages;
    }

    private void sendMessageToStore(String queueName, List<String> messages) {
        PositionBlock positionBlock = frontServer.getInsertMap(queueName);
        if (positionBlock == null) {
            positionBlock = getInsertPositionByNSR(queueName);
            frontServer.putInsertMap(positionBlock);
        }

        if (positionBlock == null)
            return;

        int length = Constant.TOTAL_LENGTH + Constant.COMMAND_LENGTH +Constant.QUEUE_NAME_LENGTH
                +Constant.MESSAGE_NUMBER +positionBlock.getQueueId().length();
        for (String message:messages) {
            length += Constant.INT_LENGTH;
            length += message.length();
        }

        byte[] command = new byte[length];
        int position = 0;
        insertIntToBytes(command,length,Constant.TOTAL_LENGTH,position);
        position += Constant.TOTAL_LENGTH;
        insertIntToBytes(command, -128, Constant.COMMAND_LENGTH, position);
        position += Constant.COMMAND_LENGTH;
        insertIntToBytes(command, positionBlock.getQueueId().length(), Constant.QUEUE_NAME_LENGTH,position);
        position += Constant.QUEUE_NAME_LENGTH;
        insertStringToBytes(command,positionBlock.getQueueId(),positionBlock.getQueueId().length(),position);
        position += positionBlock.getQueueId().length();
        insertIntToBytes(command,messages.size(),Constant.MESSAGE_NUMBER,position);
        position += Constant.MESSAGE_NUMBER;
        for (String message:messages) {
            insertIntToBytes(command,message.length(),Constant.MESSAGE_LENGTH,position);
            position+=Constant.MESSAGE_LENGTH;
            insertStringToBytes(command, message, message.length(), position);
            position+=message.length();
        }
        sendCommandWithOutReturn(command, positionBlock.getAddr(), positionBlock.getPort());
    }

    private PositionBlock getReadPositionByNSR(String queueName) {
        int length = 2;
        length += Constant.COMMAND_LENGTH +Constant.QUEUE_NAME_LENGTH + queueName.length();
        byte[] command = new byte[length];

        int position = 0;
        insertIntToBytes(command,length,Constant.TOTAL_LENGTH,position);
        position += Constant.TOTAL_LENGTH;
        command[position] = -128;
        position++;
        insertIntToBytes(command,queueName.length(),Constant.QUEUE_NAME_LENGTH,position);
        position += Constant.QUEUE_NAME_LENGTH;
        insertStringToBytes(command,queueName,queueName.length(),position);

        byte[] res = sendCommand(command, Constant.NSR_ADDR, Constant.NSR_PORT);
        if (res == null)
            return null;
        String resStr = byteToString(res,0,res.length-Constant.TOTAL_LENGTH);
        if (resStr.equals("err")) {
            return null;
        }
        String[] strs = resStr.split(":");
        String addr = strs[0];
        int port = Integer.parseInt(strs[1]);
        String queueId = strs[2];
        return new PositionBlock(queueName,addr,port,queueId);
    }

    private PositionBlock getInsertPositionByNSR(String queueName) {
        int length = 2;
        length += Constant.COMMAND_LENGTH +Constant.QUEUE_NAME_LENGTH + queueName.length();
        byte[] command = new byte[length];

        int position = 0;
        insertIntToBytes(command,length,Constant.TOTAL_LENGTH,position);
        position += Constant.TOTAL_LENGTH;
        command[position] = 64;
        position += Constant.COMMAND_LENGTH;
        insertIntToBytes(command,queueName.length(),Constant.QUEUE_NAME_LENGTH,position);
        position += Constant.QUEUE_NAME_LENGTH;
        insertStringToBytes(command, queueName, queueName.length(), position);
        position += queueName.length();

        byte[] res = sendCommand(command, Constant.NSR_ADDR, Constant.NSR_PORT);
        if (res == null)
            return null;
        String resStr = byteToString(res,0,res.length - Constant.TOTAL_LENGTH);
        String[] strs = resStr.split(":");
        String addr = strs[0];
        int port = Integer.parseInt(strs[1]);
        String queueId = strs[2];
        return new PositionBlock(queueName,addr,port,queueId);
    }

    private byte[] sendCommand(byte[] command, String addr, int port) {
        Socket socket = null;
        try {
            //Todo 使用connects 暂时储存所有socket链接
            socket = new Socket(addr,port);
            OutputStream out = socket.getOutputStream();
            out.write(command);
            out.flush();

            InputStream in = socket.getInputStream();
            byte[] firstTwoByte = new byte[Constant.TOTAL_LENGTH];
            in.read(firstTwoByte, 0, 2);
            int length = (firstTwoByte[0] << 8) + firstTwoByte[1];
            byte resBytes[] = new byte[length];
            in.read(resBytes, 0, length - Constant.TOTAL_LENGTH);
            socket.close();
            return resBytes;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void sendCommandWithOutReturn(byte[] command, String addr, int port) {
        Socket socket = null;
        try {
            //Todo 使用connects 暂时储存所有socket链接
            socket = new Socket(addr,port);
            OutputStream out = socket.getOutputStream();
            out.write(command);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    private void sendCommandWithOutReturn(byte[] command, OutputStream out) {
        try {
            out.write(command);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
