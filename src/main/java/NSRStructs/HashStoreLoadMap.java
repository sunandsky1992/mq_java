package NSRStructs;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ss on 16-4-14.
 */
public class HashStoreLoadMap {
    private Map<String, StoreLoad> storeLoadMap = new HashMap<String,StoreLoad>();

    private int total = 0;

    private int used = 0;

    public Map<String,StoreLoad> getStoreLoadMap(){
        return storeLoadMap;
    }

    public void addStoreLoad(StoreLoad storeLoad) {
        String storeId = storeLoad.getStoreId();
        storeLoadMap.put(storeId,storeLoad);
    }

    public void deleteStoreLoad(String storeId) {
        if (storeLoadMap.containsKey(storeId))
            storeLoadMap.remove(storeId);
    }

    public StoreLoad getStoreMap(String storeId) {
        return storeLoadMap.get(storeId);
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getUsed() {
        return used;
    }

    public void setUsed(int used) {
        this.used = used;
    }
}
