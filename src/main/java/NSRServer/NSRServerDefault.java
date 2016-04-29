package NSRServer;

import NSRStructs.*;

/**
 * Created by ss on 16-4-26.
 */
public class NSRServerDefault implements NSRServer {
    HashInsertPositionMap hashInsertPositionMap = new HashInsertPositionMap();

    HashStoreLoadMap hashStoreLoadMap = new HashStoreLoadMap();

    LinkedStorePositionMap linkedStorePositionMap = new LinkedStorePositionMap();

    public void updateInsertMap(String queueId) {

    }

    public void updateReadMap(String queueId) {

    }

    public void updateLoadMap(String storeId, StoreLoad storeLoad) {

    }

    public PositionBlock getCurrentInsertMap(String queueId) {
        return null;
    }

    public PositionBlock getCurrentReadMap(String queueId) {
        return null;
    }

    public void synInsertMap() {

    }

    public void synReadMap() {

    }
}
