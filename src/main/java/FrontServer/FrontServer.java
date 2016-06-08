package FrontServer;

import Constant.Constant;
import ScheduleTask.FrontScheduleTask;
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

    private static FrontServer frontServer = new FrontServer();

    private FrontServer() {
    }

    public static FrontServer getFrontServer(){
        return frontServer;
    }

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
        FrontScheduleTask frontSchelduledTask = new FrontScheduleTask();
        frontSchelduledTask.task();
        SocketServer server = new SocketServer("localhost",8200, Constant.FRONT_COMMAND_SERVER,"storeTest");
        server.listen();

    }
}
