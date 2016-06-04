package Command;

import Constant.Constant;
import NSRServer.NSRServer;
import NSRServer.NSRServerDefault;
import NSRServer.NSRStrategyDefault;
import NSRStructs.PositionBlock;
import NSRStructs.StoreLoad;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by ss on 16-4-25.
 */
public class NSRCommandServer extends CommandServer {
    private static NSRServer nsrServer = NSRServerDefault.getInstant();

    @Override
    public void analysisCommand(byte[] command) {
        int position = 1;

        if ((command[0]>>7&0x1) ==1) {
            System.out.printf("get next read position %s%n", command);
            int queueLength = byteToInt(command, position, Constant.QUEUE_NAME_LENGTH);
            position = position + Constant.QUEUE_NAME_LENGTH;
            String queueName = getQueueName(command, position,queueLength);
            System.out.println(queueName+" "+queueName.length());

            PositionBlock positionBlock = getNextReadPosition(queueName);
            String res;
            if (positionBlock == null) {
                res = "err";
            } else {
                res = positionBlock.getAddr()+":"+positionBlock.getPort()+":"+positionBlock.getQueueId();
            }
            sendString(res);

        } else if ((command[0]>>6 & 0x1)  == 1) {
            System.out.println("get insert position " + command);
            int queueLength = byteToInt(command, position, Constant.QUEUE_NAME_LENGTH);
            position = position + Constant.QUEUE_NAME_LENGTH;
            String queueName = getQueueName(command, position, queueLength);
            System.out.println(queueName + " " + queueName.length());
            PositionBlock positionBlock = getInsertPosition(queueName);
            String res = positionBlock.getAddr()+":"+positionBlock.getPort()+":"+positionBlock.getQueueId();
            sendString(res);

        } else if ((command[0]>>5 & 0x1) == 1) {
            System.out.println("heart beat " + command);
            int queueLength = byteToInt(command, position, Constant.QUEUE_NAME_LENGTH);
            position = position + Constant.QUEUE_NAME_LENGTH;
            String storeName = getQueueName(command, position, queueLength);
            position = position + queueLength;
            int addLength = getInt(command, position);
            position = position + Constant.INT_LENGTH;
            String addr = byteToString(command, position, addLength);
            position += addLength;
            int port = getInt(command, position);
            position += Constant.INT_LENGTH;
            int memoryUsed = getInt(command, position);
            position += Constant.INT_LENGTH;
            int CPUsed = getInt(command, position);
            position += Constant.INT_LENGTH;
            int DISKUsed = getInt(command, position);
            position += Constant.INT_LENGTH;
            int IOUsed = getInt(command, position);
            position += Constant.INT_LENGTH;
            int messageNum = getInt(command,position);
            position += Constant.INT_LENGTH;
            String storeId = addr+":"+port;
            StoreLoad storeLoad = nsrServer.getStoreLoad(storeId);
            if (storeLoad == null)
                 storeLoad = new StoreLoad(addr,port);
            storeLoad.setCpuUsed(CPUsed);
            storeLoad.setMemoryUsed(memoryUsed);
            storeLoad.setNetwordWidthUsed(IOUsed);
            storeLoad.updateHistoryRecord(messageNum);
            updateStoreLoad(storeLoad);
        }
    }

    @Override
    public void task() {

    }

    private void updateStoreLoad(StoreLoad storeLoad) {
        nsrServer.updateLoadMap(storeLoad);
    }

    private  PositionBlock getInsertPosition(String queueName) {
        synchronized (nsrServer.getLinkedStorePositionMap()) {
            return nsrServer.getInsertPosition(queueName);
        }
    }

    private PositionBlock getNextReadPosition(String queueName) {
    //Todo 如果有一个正在执行则等待执行结果
        synchronized (nsrServer.getLinkedStorePositionMap()) {
            PositionBlock positionBlock = nsrServer.getCurrentReadMap(queueName);
            if (positionBlock == null) {
                return null;
            }
            String queueId = positionBlock.getQueueId();
            int queueLength = getQueueLength(queueId,positionBlock.getQueueName());
            if (queueLength > 0) {
                return positionBlock;
            } else {
                positionBlock = nsrServer.getNextPosition(queueName);
                return positionBlock;
            }
        }
    }

    private int getQueueLength(String queueId,String queueName) {
        int commandLength = Constant.TOTAL_LENGTH+Constant.COMMAND_LENGTH+Constant.QUEUE_NAME_LENGTH+queueId.length();
        byte[] command = new byte[commandLength];
        int position = 0;
        insertIntToBytes(command, commandLength, Constant.TOTAL_LENGTH, position);
        position+=Constant.TOTAL_LENGTH;
        insertIntToBytes(command, 32, Constant.COMMAND_LENGTH, position);
        position+=Constant.COMMAND_LENGTH;
        insertIntToBytes(command, queueId.length(), Constant.QUEUE_NAME_LENGTH, position);
        position+= Constant.QUEUE_NAME_LENGTH;
        insertStringToBytes(command, queueId, queueId.length(), position);
        PositionBlock positionBlock = nsrServer.getCurrentReadMap(queueName);

        Socket socket = null;
        try {
            //Todo 使用connects 暂时储存所有socket链接
            socket = new Socket(positionBlock.getAddr(),positionBlock.getPort());
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
            return byteToInt(resBytes,0,length-Constant.TOTAL_LENGTH);
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void sendString(String res) {
        int length = 2;
        length+=res.length();
        byte command[] = new byte[length];
        int position = 0;
        insertIntToBytes(command,length,Constant.TOTAL_LENGTH,position);
        position += Constant.TOTAL_LENGTH;
        insertStringToBytes(command,res,res.length(),position);

        try {
            OutputStream out = connect.getOutputStream();
            out.write(command);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]){
        NSRCommandServer nsrCommandServer = new NSRCommandServer();
        nsrServer.updateReadMap("a", "localhost", 8000);
    }
}
