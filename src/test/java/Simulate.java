import org.junit.Test;

import java.util.*;

/**
 * Created by ss on 16-6-15.
 */
public class Simulate {
    private class Queue{
        public int queueId;
        public int storeId;
        public int messageNum;
    }

    private class Store{
        public int storeId;
        public int load;
        public int[] historyRecord = new int[3];
        public int[] getHistoryRecord() {
            return historyRecord;
        }

    }

    private int min = 100;

    private int max = 500;

    private int mid = 400;
    @Test
    public void Simulate() throws InterruptedException {
        int num=0;
        Queue[] queues = new Queue[20];
        Store[] stores = new Store[4];
        for (int i=0;i<20;i++) {
            queues[i] = new Queue();
            queues[i].queueId = i;
        }

        for (int i=0;i<4;i++) {
            stores[i] = new Store();
            stores[i].storeId = i;
        }
        while (true) {


            Arrays.sort(stores, new Comparator<Store>() {
                public int compare(Store store, Store t1) {
                    if (store.getHistoryRecord()[0]>t1.getHistoryRecord()[0])
                        return 1;
                    else if (store.getHistoryRecord()[0]<t1.getHistoryRecord()[0])
                        return -1;
                    return 0;
                }
            });

            List<Store> releaseStores = new ArrayList<Store>();
            List<Store> balanceStores = new ArrayList<Store>();
            List<Store> idleStores = new ArrayList<Store>();
            List<Store> normalStores = new ArrayList<Store>();
            int realseNum = getReleaseNumByMessageNum(stores);
            int totalIdleLoad = 0;
            releaseStores.addAll(Arrays.asList(stores).subList(0, realseNum));

            for (Store storeLoad:stores) {
                System.out.println("=============================");
                System.out.println(storeLoad.storeId+":"+storeLoad.getHistoryRecord()[0]+" "+storeLoad.getHistoryRecord()[1]+" "+storeLoad.getHistoryRecord()[2]);
                System.out.println("=============================");

                if (releaseStores.contains(storeLoad)) {
                    continue;
                }

                if (storeLoad.getHistoryRecord()[0]>=max &&
                        storeLoad.getHistoryRecord()[1]>=max &&
                        storeLoad.getHistoryRecord()[2]>=max) {
                    balanceStores.add(storeLoad);
                } else if (storeLoad.getHistoryRecord()[0]<=mid &&
                        storeLoad.getHistoryRecord()[1]<=mid &&
                        storeLoad.getHistoryRecord()[2]<=mid) {
                    totalIdleLoad += storeLoad.getHistoryRecord()[0];
                    idleStores.add(storeLoad);
                } else {
                    totalIdleLoad += storeLoad.getHistoryRecord()[0];
                    normalStores.add(storeLoad);
                }
            }

            if (totalIdleLoad <= 0)
                totalIdleLoad = 1;

            List<Queue> updatePositions = new ArrayList<Queue>();
            for (Queue queue:queues) {
                int storeId = queue.storeId;
                System.out.println("storeId :"+storeId+" "+queue.messageNum);
                Store storeLoad = stores[storeId];
                for (Store s:stores) {
                    if (s.storeId == queue.storeId)
                        storeLoad = s;
                }
                if ((storeLoad.getHistoryRecord()[0]>this.max &&
                        storeLoad.getHistoryRecord()[1]>this.max &&
                        storeLoad.getHistoryRecord()[2]>this.max )|| releaseStores.contains(storeLoad) ) {
                    double m1 = (double)storeLoad.getHistoryRecord()[0]*100/(mid + storeLoad.getHistoryRecord()[0]);
                    if (Math.abs(m1 -0) <0.0000001) {
                        m1 = 0.1;
                    }
//                    if (releaseStores.contains(storeLoad))
//                        m1 *= 100;
                    for (Store idleStoreLoad:idleStores) {
                        double g = (totalIdleLoad-idleStoreLoad.getHistoryRecord()[0])*m1/totalIdleLoad;
                        int r = new Random().nextInt(100);
                        System.out.println("g:"+g+"  "+"r:"+r);
                        if (r>g) {
                            queue.storeId = idleStoreLoad.storeId;
                            System.out.println("change position: "+storeLoad.storeId+" ->"+idleStoreLoad.storeId);

                            break;
                        }
                    }
                }
            }


            System.out.println("===============================================");
            System.out.println("release:");
            for (Store storeLoad:releaseStores) {
                System.out.println("storeId :"+storeLoad.storeId);
                System.out.println(storeLoad.getHistoryRecord()[0]);
            }
            System.out.println("balance:");
            for (Store storeLoad:balanceStores) {
                System.out.println("storeId :"+storeLoad.storeId);
                System.out.println(storeLoad.getHistoryRecord()[0]);
            }
            System.out.println("idle:");
            for (Store storeLoad:idleStores) {
                System.out.println("storeId :"+storeLoad.storeId);
                System.out.println(storeLoad.getHistoryRecord()[0]);
            }
            System.out.println("normal:");
            for (Store storeLoad:normalStores) {
                System.out.println("storeId :"+storeLoad.storeId);
                System.out.println(storeLoad.getHistoryRecord()[0]);
            }
            System.out.println("===============================================");


            if (num<80) {
                for (int i=num/20*5;i<num/20*5+5;i++) {
                    queues[i].messageNum+=4;
                    List<Store> t = new ArrayList<Store>();
                    t.addAll(idleStores);
                    t.addAll(normalStores);
                    if (queues[i].messageNum == 4) {
                        Collections.sort(t, new Comparator<Store>() {
                            public int compare(Store store, Store t1) {
                                if (store.getHistoryRecord()[0]>t1.getHistoryRecord()[0])
                                    return 1;
                                else if (store.getHistoryRecord()[0]<t1.getHistoryRecord()[0])
                                    return -1;
                                return 0;
                            }
                        });
                        queues[i].storeId = t.get(0).storeId;
                    }
                }
            }
            if (num>160) {
                for (int i=(num-160)/20*5;i<(num-160)/20*5+5;i++) {
                    queues[i].messageNum-=4;
                }
            }

            for (Store store:stores){
                store.getHistoryRecord()[2] = store.getHistoryRecord()[1];
                store.getHistoryRecord()[1] = store.getHistoryRecord()[0];
                store.getHistoryRecord()[0] = 0;
            }

            for (Queue queue:queues) {
                for (Store store:stores) {
                    if (store.storeId == queue.storeId)
                        store.getHistoryRecord()[0] += queue.messageNum;
                }
            }

            num++;

            Thread.sleep(1000);
        }
    }


    public int getReleaseNumByMessageNum(Store[] stores) {
        int totalNum = stores.length;
        double totalUsed = 0;
        for (Store store:stores) {
            int messageNum =store.getHistoryRecord()[0];
            totalUsed+=messageNum;
        }
        int releaseNum = (int) Math.ceil((totalUsed)/ this.mid);
        if (releaseNum == 0)
            releaseNum = 1;
        if (totalNum-releaseNum<0)
            return 0;
        return totalNum-releaseNum;
    }
}
