package NSRServer;

import NSRStructs.*;

import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by ss on 16-4-26.
 */
public abstract class NSRServer {
    static HashInsertPositionMap hashInsertPositionMap = HashInsertPositionMap.getHashInsertPosition();

    static LinkedStorePositionMap linkedStorePositionMap = LinkedStorePositionMap.getLinkedStorePositionMap();

    static HashStoreLoadMap hashStoreLoadMap = HashStoreLoadMap.getHashStoreLoadMap();

    static Map<String,Socket> connectMap = new HashMap<String,Socket>();

    static Set<String> frontAddr = new HashSet<String>();

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

    public static Set<String> getFrontAddr() {
        return frontAddr;
    }

    public static void setFrontAddr(Set<String> frontAddr) {
        NSRServer.frontAddr = frontAddr;
    }
}
