package StoreServer;

import Constant.Constant;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by ss on 16-6-2.
 */
public class StoreHistoryInfo {
    private AtomicInteger currentNum = new AtomicInteger(0);
    private int array[] = new int[Constant.STORE_RECORD_LENGTH];
    private static StoreHistoryInfo storeHistoryInfo = new StoreHistoryInfo();

    private StoreHistoryInfo(){

    }

    public static StoreHistoryInfo getStoreHistoryInfo() {
        return storeHistoryInfo;
    }

    public int updateRecord() {
        System.arraycopy(array,0,array,1,Constant.STORE_RECORD_LENGTH-1);
        array[0] = currentNum.getAndSet(0);
        return array[0];
    }

    private int getAndSetRecord() {
        return getAndSetRecord(0);
    }

    public int getAndaddRecord(int value) {
        return currentNum.addAndGet(value);
    }
    public int getAndSetRecord(int value) {
        return currentNum.getAndSet(value);
    }

    public void addCurrentNum(){
        currentNum.addAndGet(1);
    }
}
