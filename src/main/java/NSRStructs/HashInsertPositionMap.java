package NSRStructs;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ss on 16-4-25.
 */
public class HashInsertPositionMap {
    Map<String, PositionBlock> insertMap = new HashMap<String,PositionBlock>();

    private static HashInsertPositionMap hashInsertPositionMap = new HashInsertPositionMap();

    private HashInsertPositionMap() {

    }

    public static HashInsertPositionMap getHashInsertPosition() {
        return hashInsertPositionMap;
    }
    public Map<String,PositionBlock> getStoreLoadMap(){
        return insertMap;
    }

    public void changeInsertPosition(PositionBlock positionBlock) {
        String queueName = positionBlock.getQueueName();
        insertMap.put(queueName,positionBlock);
    }
}
