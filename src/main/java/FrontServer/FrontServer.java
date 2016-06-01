package FrontServer;

import Constant.Constant;
import FrontStructs.PositionBlock;
import SocketServer.SocketServer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ss on 16-5-26.
 */
public  class FrontServer {
    Map<String,PositionBlock> insertMap = new HashMap<String,PositionBlock>();
    Map<String,PositionBlock> readMap = new HashMap<String, PositionBlock>();

    public void putInsertMap(String queueName, String addr, int port,String queueId){
        PositionBlock positionBlock = new PositionBlock(queueName,addr,port,queueId);
        insertMap.put(queueName,positionBlock);
    }

    public void putInsertMap(PositionBlock positionBlock) {
        insertMap.put(positionBlock.getQueueName(),positionBlock);
    }

    public PositionBlock getInsertMap(String queueName) {
        return insertMap.get(queueName);
    }

    public void putReadMap(String queueName, String addr, int port, String queueId) {
        PositionBlock positionBlock = new PositionBlock(queueName,addr,port,queueId);
        readMap.put(queueName,positionBlock);
    }

    public void putReadMap(PositionBlock positionBlock) {
        readMap.put(positionBlock.getQueueName(),positionBlock);
    }

    public PositionBlock getReadMap(String queueName) {
        return readMap.get(queueName);
    }

    public static void main(String args[]) {
        SocketServer server = new SocketServer("localhost",8002, Constant.FRONT_COMMAND_SERVER,"storeTest");
        server.listen();

    }
}
