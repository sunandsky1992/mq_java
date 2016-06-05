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
        String status = getStatusByMemory(storeLoads.getStoreLoadMap());
    }

    public void balance(Map<String,StoreLoad> storeLoadMap,LinkedStorePositionMap linkedStorePositionMap) {
        int releaseNum = getReleaseNumByMemory(storeLoadMap);
        List<StoreLoad> storeLoads = getSortedStoreLoadByMessageNum(storeLoadMap);
        List<StoreLoad> releaseStoreLoad = getSortedStoreLoadByMessageNum(storeLoadMap).subList(0, releaseNum);
        List<StoreLoad> balanceStoreLoad = new ArrayList<StoreLoad>();
        List<StoreLoad> idleStoreLoads    = new ArrayList<StoreLoad>();
        //idle转移一部分 根据到平均值的差来根据概率转移队列
        int totalIdleLoad = 0;
        for (StoreLoad storeLoad:storeLoads) {
            if (releaseStoreLoad.contains(storeLoad)) {
                continue;
            }

            if (storeLoad.getHistoryRecord()[0]>Constant.STORE_BALANCE_LINE &&
                    storeLoad.getHistoryRecord()[1]>Constant.STORE_BALANCE_LINE &&
                    storeLoad.getHistoryRecord()[2]>Constant.STORE_BALANCE_LINE) {
                balanceStoreLoad.add(storeLoad);
            } else if (storeLoad.getHistoryRecord()[0]<Constant.STORE_BALANCE_LINE &&
                    storeLoad.getHistoryRecord()[1]<Constant.STORE_BALANCE_LINE &&
                    storeLoad.getHistoryRecord()[2]<Constant.STORE_BALANCE_LINE) {
                totalIdleLoad += storeLoad.getHistoryRecord()[0];
                idleStoreLoads.add(storeLoad);
            }
        }



        Set<Entry<String,PositionBlock>> positionBlocks =
                linkedStorePositionMap.getQueuePosition().entrySet();
        for (Entry<String,PositionBlock> entry:positionBlocks) {
            String storeId = entry.getValue().getAddr()+":"+entry.getValue().getPort();
            StoreLoad storeLoad = storeLoadMap.get(storeId);
            if ((storeLoad.getHistoryRecord()[0]>Constant.STORE_BALANCE_LINE &&
                    storeLoad.getHistoryRecord()[1]>Constant.STORE_BALANCE_LINE &&
                    storeLoad.getHistoryRecord()[2]>Constant.STORE_BALANCE_LINE )|| releaseStoreLoad.contains(storeLoad) ) {
                int m1 = storeLoad.getHistoryRecord()[0]*100/(Constant.STORE_BALANCE_LINE*4 + storeLoad.getHistoryRecord()[0]);
                if (releaseStoreLoad.contains(storeLoad))
                    m1 *= 100;
                for (StoreLoad idleStoreLoad:idleStoreLoads) {
                    int g = idleStoreLoad.getHistoryRecord()[0]*m1/totalIdleLoad;
                    int r = new Random().nextInt(100);
                    if (r<g) {
                        linkedStorePositionMap.insertPosition(entry.getValue().getQueueName()
                                ,idleStoreLoad.getIpAddr(),idleStoreLoad.getPort());
                        break;
                    }
                }
            }
        }
    }

    public int getReleaseNumByMemory(Map<String,StoreLoad> storeLoadMap) {
        int totalNum = storeLoadMap.size();
        double totalUsed = 0;
        for (Entry<String,StoreLoad> entry:storeLoadMap.entrySet()) {
            double memoryUsed = entry.getValue().getMemoryUsed();
            totalUsed+=memoryUsed;
        }
        int releaseNum = (int) (totalUsed/Constant.RELEASE_LINE);
        return totalNum-releaseNum;
    }

    public List<StoreLoad> getSortedStoreLoadByMessageNum(Map<String,StoreLoad> storeLoadMap) {
        List<StoreLoad> storeLoadList = new ArrayList<StoreLoad>();
        storeLoadList.addAll(storeLoadMap.values());
        Collections.sort(storeLoadList,new StoreLoadSortByMessage());
        return storeLoadList;
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
        Collections.sort(storeLoadList,new StoreLoadSortByMessage());
        return storeLoadList.get(n);
    }

    public static StoreLoad getStoreLoadByMessageNumSort(Map<String,StoreLoad> storeLoadMap,int n) {
        List<StoreLoad> storeLoadList = new ArrayList<StoreLoad>();
        storeLoadList.addAll(storeLoadMap.values());
        Collections.sort(storeLoadList,new StoreLoadSortByMemory());
        return storeLoadList.get(n);
    }

    public static String getStatusByMemory(Map<String,StoreLoad> storeLoadMap) {
        String status = "";
        int totalNum = storeLoadMap.size();
        double totalUsed = 0;
        for (Entry<String,StoreLoad> entry:storeLoadMap.entrySet()) {
            double memoryUsed = entry.getValue().getMemoryUsed();
            totalUsed+=memoryUsed;
        }
        double avg = totalUsed/totalNum;
        if (avg<Constant.RELEASE_LINE) {
            return Constant.STATUS_RELEASE;
        } else if (avg<Constant.EXPAND_LINE){
            return Constant.STATUS_NORMAL;
        } else {
            return Constant.STATUS_EXPAND;
        }
    }

    public static String getStatusByMessageNum(Map<String,StoreLoad> storeLoadMap) {
        String status = "";
        int totalNum = storeLoadMap.size();
        double totalUsed = 0;
        for (Entry<String,StoreLoad> entry:storeLoadMap.entrySet()) {
            double memoryUsed = entry.getValue().getMemoryUsed();
            totalUsed+=memoryUsed;
        }
        double avg = totalUsed/totalNum;
        if (avg<Constant.RELEASE_LINE) {
            return Constant.STATUS_RELEASE;
        } else if (avg<Constant.EXPAND_LINE){
            return Constant.STATUS_NORMAL;
        } else {
            return Constant.STATUS_EXPAND;
        }
    }

    private static class StoreLoadSortByMemory implements Comparator<StoreLoad> {
        public int compare(StoreLoad t1, StoreLoad t2) {
            if (Math.abs(t1.getMemoryUsed() - t2.getMemoryUsed())<0.0000001)
                return 0;
            else if (t1.getMemoryUsed()>t2.getMemoryUsed())
                return 1;
            else return -1;
        }
    }

    private static class StoreLoadSortByMessage implements Comparator<StoreLoad> {
        public int compare(StoreLoad t1, StoreLoad t2) {
            if (t1.getHistoryRecord()[0] == t2.getHistoryRecord()[0])
                return 0;
            else if (t1.getMemoryUsed()>t2.getMemoryUsed())
                return 1;
            else return -1;
        }
    }
}
