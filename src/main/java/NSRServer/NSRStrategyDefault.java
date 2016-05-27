package NSRServer;

import Constant.Constant;
import NSRStructs.*;

import java.util.*;
import java.util.Map.Entry;

/**
 * Created by ss on 16-5-18.
 */
public class NSRStrategyDefault {
    public static void balance(HashStoreLoadMap storeLoads,
                        LinkedStorePositionMap linkedStorePositionMap, HashInsertPositionMap hashInsertPositionMap) {

    }

    public static PositionBlock getInsertNewQueue(String queueName,
                                        HashStoreLoadMap storeLoads, LinkedStorePositionMap linkedStorePositionMap) {
        return getInsertNewQueue(queueName, 0, storeLoads, linkedStorePositionMap);
    }

    public static PositionBlock getInsertNewQueue(String queueName, int queueNumber,
                                        HashStoreLoadMap storeLoads, LinkedStorePositionMap linkedStorePositionMap) {
        StoreLoad storeLoad = getStoreLoadByStatus(storeLoads.getStoreLoadMap());
        PositionBlock positionBlock = new PositionBlock(storeLoad.getIpAddr(),storeLoad.getPort(),queueNumber,queueName);
        linkedStorePositionMap.insertPosition(queueName, storeLoad.getIpAddr(), storeLoad.getPort());
        return positionBlock;
    }

    public static StoreLoad getStoreLoadByStatus(Map<String,StoreLoad> storeLoadMap) {
        String status = "";
        int totalNum = storeLoadMap.size();
        double totalUsed = 0;
        //TODO map排序怎么办

        for (Entry<String,StoreLoad> entry:storeLoadMap.entrySet()) {
            double memoryUsed = entry.getValue().getMemoryUsed();
            totalUsed+=memoryUsed;
        }
        double avg = totalUsed/totalNum;
        if (avg<Constant.RELEASE_LINE) {
            int releaseNum = (int) (totalUsed/Constant.RELEASE_LINE);
            return getStoreLoadByMemorySort(storeLoadMap,releaseNum);
        } else {
            return getStoreLoadByMemorySort(storeLoadMap,0);
        }
    }

    public static StoreLoad getStoreLoadByMemorySort(Map<String,StoreLoad> storeLoadMap,int n) {
        List<StoreLoad> storeLoadList = new ArrayList<StoreLoad>();
        storeLoadList.addAll(storeLoadMap.values());
        Collections.sort(storeLoadList,new StoreLoadSort());
        return storeLoadList.get(n);
    }

    private static class StoreLoadSort implements Comparator<StoreLoad> {
        public int compare(StoreLoad t1, StoreLoad t2) {
            if (Math.abs(t1.getMemoryUsed() - t2.getMemoryUsed())<0.0000001)
                return 0;
            else if (t1.getMemoryUsed()>t2.getMemoryUsed())
                return 1;
            else return -1;
        }
    }
}
