package NSRServer;

import Constant.Constant;
import NSRStructs.*;

import java.util.*;
import java.util.Map.Entry;

/**
 * Created by ss on 16-5-18.
 */
public class NSRStrategyDefault {
    public static void balance(Map<String,StoreLoad> storeLoadMap,LinkedStorePositionMap linkedStorePositionMap) {
        int releaseNum = getReleaseNumByMessageNum(storeLoadMap);
        List<StoreLoad> storeLoads = getSortedStoreLoadByMessageNum(storeLoadMap);
        if (releaseNum > storeLoadMap.size())
            releaseNum = storeLoadMap.size();
        List<StoreLoad> releaseStoreLoad = getSortedStoreLoadByMessageNum(storeLoadMap).subList(0, releaseNum);
        List<StoreLoad> balanceStoreLoad = new ArrayList<StoreLoad>();
        List<StoreLoad> idleStoreLoads   = new ArrayList<StoreLoad>();
        //idle转移一部分 根据到平均值的差来根据概率转移队列
        int totalIdleLoad = 0;
        for (StoreLoad storeLoad:storeLoads) {
            System.out.println("=============================");
            System.out.println(storeLoad.getStoreId()+":"+storeLoad.getHistoryRecord()[0]+" "+storeLoad.getHistoryRecord()[1]+" "+storeLoad.getHistoryRecord()[2]);
            System.out.println("=============================");

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

        if (totalIdleLoad <= 0)
            totalIdleLoad = 1;

        Set<Entry<String,PositionBlock>> positionBlocks =
                linkedStorePositionMap.getQueuePosition().entrySet();
        for (Entry<String,PositionBlock> entry:positionBlocks) {
            String storeId = entry.getValue().getAddr()+":"+entry.getValue().getPort();
            System.out.println("storeId "+storeId);
            StoreLoad storeLoad = storeLoadMap.get(storeId);
            if ((storeLoad.getHistoryRecord()[0]>Constant.STORE_BALANCE_LINE &&
                    storeLoad.getHistoryRecord()[1]>Constant.STORE_BALANCE_LINE &&
                    storeLoad.getHistoryRecord()[2]>Constant.STORE_BALANCE_LINE )|| releaseStoreLoad.contains(storeLoad) ) {
                int m1 = storeLoad.getHistoryRecord()[0]*100/(Constant.STORE_BALANCE_LINE*4 + storeLoad.getHistoryRecord()[0]);
                if (releaseStoreLoad.contains(storeLoad))
                    m1 *= 100;
                for (StoreLoad idleStoreLoad:idleStoreLoads) {
                    int g = (totalIdleLoad-idleStoreLoad.getHistoryRecord()[0])*m1/totalIdleLoad;
                    int r = new Random().nextInt(100);
                    System.out.println("g:"+g+"  "+"r:"+r);
                    if (r>g) {
                        linkedStorePositionMap.insertPosition(entry.getValue().getQueueName()
                                , idleStoreLoad.getIpAddr(), idleStoreLoad.getPort());
                        System.out.println("change position: "+storeLoad.getStoreId()+" ->"+idleStoreLoad.getStoreId());
                        break;
                    }
                }
            }
        }

        System.out.println("===============================================");
        System.out.println("release:");
        for (StoreLoad storeLoad:releaseStoreLoad) {
            System.out.println(storeLoad.getStoreId());
            System.out.println(storeLoad.getHistoryRecord()[0]);
        }
        System.out.println("balance:");
        for (StoreLoad storeLoad:balanceStoreLoad) {
            System.out.println(storeLoad.getStoreId());
            System.out.println(storeLoad.getHistoryRecord()[0]);
        }
        System.out.println("idle:");
        for (StoreLoad storeLoad:idleStoreLoads) {
            System.out.println(storeLoad.getStoreId());
            System.out.println(storeLoad.getHistoryRecord()[0]);
        }
        System.out.println("===============================================");

    }


    public static int getReleaseNumByMemory(Map<String,StoreLoad> storeLoadMap) {
        int totalNum = storeLoadMap.size();
        double totalUsed = 0;
        for (Entry<String,StoreLoad> entry:storeLoadMap.entrySet()) {
            double memoryUsed = entry.getValue().getMemoryUsed();
            totalUsed+=memoryUsed;
        }
        int releaseNum = (int) (totalUsed/Constant.RELEASE_LINE);
        if (totalNum-releaseNum<0)
            return 0;
        return totalNum-releaseNum;
    }

    public static int getReleaseNumByMessageNum(Map<String,StoreLoad> storeLoadMap) {
        int totalNum = storeLoadMap.size();
        double totalUsed = 0;
        for (Entry<String,StoreLoad> entry:storeLoadMap.entrySet()) {
            int messageNum = entry.getValue().getHistoryRecord()[0];
            totalUsed+=messageNum;
        }
        int releaseNum = (int) (totalUsed/Constant.RELEASE_LINE);
        if (releaseNum == 0)
            releaseNum = 1;
        if (totalNum-releaseNum<0)
            return 0;
        return totalNum-releaseNum;
    }

    public static List<StoreLoad> getSortedStoreLoadByMessageNum(Map<String,StoreLoad> storeLoadMap) {
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
