package Command;

/**
 * Created by ss on 16-4-5.
 */
public class ByteCommand {
    //front
    //10000000
    public static final String FRONT_INSERT_MESSAGE = "FRONT_INSERT_MESSAGE"; // 插入消息
    //01000000
    public static final String FRONT_GET_MESSAGE = "FRONT_GET_MESSAGE"; // 获取消息
    //00100000
    public static final String FRONT_GET_QUEUE = "FRONT_GET_QUEUE"; // 获取队列地址
    //00010000
    public static final String FRONT_SYN_ADDR = "FRONT_SYN_ADDR"; // 同步队列地址

    //store
    public static final String STORE_INSERT_MESSAGE = "STORE_INSERT_MESSAGE";

    public static final String STORE_GET_MESSAGE = "STORE_GET_MESSAGE";

    public static final String STORE_CLOSE_BROKER = "STORE_CLOSE_BROKER";

    public static final String STORE_HEART_BEAT = "STORE_HEART_BEAT";

    public static final String STORE_GET_POSITION = "STORE_GET_POSITION";

    //nsr
    public static final String NSR_SYN_ADDR = "NSR_SYN_ADDR";

    public static final String NSR_GET_QUEUE = "NSR_GET_QUEUE";
}
