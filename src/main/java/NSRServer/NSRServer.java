package NSRServer;

import NSRStructs.PositionBlock;
import NSRStructs.StoreLoad;

/**
 * Created by ss on 16-4-26.
 */
public interface NSRServer {
    void updateInsertMap(String queueId);

    void updateReadMap(String queueId);

    void updateLoadMap(String storeId, StoreLoad storeLoad);

    PositionBlock getCurrentInsertMap(String queueId);

    PositionBlock getCurrentReadMap(String queueId);

    void synInsertMap();

    void synReadMap();
}
