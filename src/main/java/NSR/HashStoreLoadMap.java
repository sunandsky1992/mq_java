package NSR;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ss on 16-4-14.
 */
public class HashStoreLoadMap {
    Map<String,StoreLoad> storeLoadMap = new HashMap<String,StoreLoad>();

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
}
