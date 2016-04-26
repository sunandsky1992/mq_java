package NSR.Structs;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ss on 16-4-25.
 */
public class HashInsertPositionMap {
    Map<String, PositionBlock> insertMap = new HashMap<String,PositionBlock>();

    public Map<String,PositionBlock> getStoreLoadMap(){
        return insertMap;
    }

    public void changeInsertPosition(PositionBlock positionBlock) {
        String queueName = positionBlock.getQueueName();
        insertMap.put(queueName,positionBlock);
    }
}
