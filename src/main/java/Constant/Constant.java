package Constant;

/**
 * Created by ss on 16-4-7.
 */
public class Constant {
    //各种协议长度
    public static final int TOTAL_LENGTH = 2;
    public static final int QUEUE_NUMBER = 1;
    public static final int QUEUE_NAME_LENGTH = 1;
    public static final int MESSAGE_NUMBER = 2;
    public static final int MESSAGE_LENGTH = 2;
    public static final int CHAR_LENGTH = 2;
    public static final int INT_LENGTH = 2;
    public static final int COMMAND_LENGTH = 1;
    //各种类型commandServer
    public static final String STORE_COMMAND_SERVER = "store_command_server";
    public static final String NSR_COMMAND_SERVER = "nsr_command_server";
    public static final String FRONT_COMMAND_SERVER = "front_command_server";

    //各种类型的资源状态
    public static final String STATUS_RELEASE = "status_release";
    public static final double RELEASE_LINE = 50;
    public static final String STATUS_NORMAL = "status_normal";
    public static final double NORMAL_LINE = 70;
    public static final String STATUS_EXPAND = "status_expand";
    public static final double EXPAND_LINE = 90;

    //NSR地址
    public static final String NSR_ADDR = "127.0.0.1";

    public static final int NSR_PORT = 8000;
}
