package NSRServer;

import Constant.Constant;
import NSRStructs.*;
import ScheduleTask.NSRScheduleTask;
import SocketServer.SocketServer;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ss on 16-4-26.
 */
public class NSRServerDefault extends NSRServer {
    private NSRServerDefault(){

    }

    private static NSRServerDefault nsrServerDefault = new NSRServerDefault();

    public static NSRServerDefault getInstant(){
        return nsrServerDefault;
    }

    public void updateReadMap(String name, String addr, int port) {
        linkedStorePositionMap.insertPosition(name, addr, port);
    }

    public void updateLoadMap(StoreLoad storeLoad) {
        hashStoreLoadMap.addStoreLoad(storeLoad);
    }

    public PositionBlock getInsertPosition(String queueName) {
        PositionBlock positionBlock = linkedStorePositionMap.getInsertPosition(queueName);
        if (positionBlock == null) {
            positionBlock = NSRStrategyDefault.getInsertNewQueue(queueName, 0, hashStoreLoadMap, linkedStorePositionMap);
        }
        return positionBlock;
    }

    public PositionBlock getNextPosition(String queueName) {
        return linkedStorePositionMap.getNextReadPosition(queueName);
    }

    public PositionBlock getCurrentInsertMap(String queueName) {
        return linkedStorePositionMap.getInsertPosition(queueName);
    }

    public PositionBlock getCurrentReadMap(String queueName) {
        return linkedStorePositionMap.readPosition(queueName);
    }

    public StoreLoad getStoreLoad(String storeId) {
        return hashStoreLoadMap.getStoreMap(storeId);
    }

    public static void main(String args[]){

        NSRScheduleTask task = new NSRScheduleTask();
        task.task();
        SocketServer server = new SocketServer("localhost",8000, Constant.NSR_COMMAND_SERVER,"storeTest");

        server.listen();
    }
}
