package NSRServer;

import NSRStructs.*;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ss on 16-4-26.
 */
public abstract class NSRServer {
    static HashInsertPositionMap hashInsertPositionMap = new HashInsertPositionMap();

    static LinkedStorePositionMap linkedStorePositionMap = new LinkedStorePositionMap();

    static HashStoreLoadMap hashStoreLoadMap = new HashStoreLoadMap();

    static Map<String,Socket> connectMap = new HashMap<String,Socket>();

    public abstract void updateReadMap(String name, String addr, int port);

    public abstract void updateLoadMap(StoreLoad storeLoad);

    public abstract PositionBlock getCurrentInsertMap(String queueName);

    public abstract PositionBlock getCurrentReadMap(String queueName);

    public abstract PositionBlock getNextPosition(String queueName);

    public abstract PositionBlock getInsertPosition(String queueName);

    public HashInsertPositionMap getHashInsertPositionMap() {
        return hashInsertPositionMap;
    }

    public HashStoreLoadMap getHashStoreLoadMap() {
        return hashStoreLoadMap;
    }

    public LinkedStorePositionMap getLinkedStorePositionMap() {
        return linkedStorePositionMap;
    }

    public Map<String, Socket> getConnectMap() {
        return connectMap;
    }

    public abstract StoreLoad getStoreLoad(String storeId);

}
